package cn.itsource.aigou.es;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.facade.query.ProductQuery;

public class ProductSearchHelper {
	public static final String INDEX = "aigou";
	public static final String TYPE = "product";
	public static final String ALL_FIELD = "productAll";
	public static final String PRODUCT_STATE = "state";
	public static final String PRODUCT_TYPE = "productType";
	public static final String PRODUCT_BRAND = "brandId";
	public static final String PRODUCT_MAX_PRICE = "maxPrice";
	public static final String PRODUCT_MIN_PRICE = "minPrice";
	public static final String PRODUCT_SALE_COUNT = "saleCount";
	public static final String PRODUCT_VIEW_COUNT = "viewCount";
	public static final String PRODUCT_ONSALE_TIME = "onSaleTime";
	public static final String PRODUCT_COMMENT_COUNT = "commentCount";

	private static TransportClient client = null;

	/**
	 * 获取es客户端
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public static TransportClient getClient() {
		if (null == client) {
			Settings settings = Settings.builder().put("client.transport.sniff", true).build();
			try {
				client = new PreBuiltTransportClient(settings).addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(GlobalSetting.get(GlobalSettingNames.ES_CLUSTER_HOST)),
								Integer.parseInt(GlobalSetting.get(GlobalSettingNames.ES_CLUSTER_PORT))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	/**
	 * 新增或更新文档
	 * 
	 * @param id
	 *            文档ID
	 * @param json
	 *            文档json格式字符串数据
	 */
	public static void saveOrUpdate(String id, String json) {
		TransportClient client = ProductSearchHelper.getClient();
		IndexRequest indexRequest = new IndexRequest(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, id)
				.source(json);
		UpdateRequest updateRequest = new UpdateRequest(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, id)
				.doc(json).upsert(indexRequest);
		try {
			UpdateResponse updateResponse = client.update(updateRequest).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量新增/更新商品信息
	 * @param dataList
	 */
	public static void saveOrUpdate(List<Map<String, Object>> dataList) {
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();
		
		for (Map<String, Object> productMap : dataList) {
			
			Map<String, Object> aviProductMap = new HashMap<>();
			//删除productMap中null值
			for(String key : productMap.keySet()){
				if(null!=productMap.get(key)){
					aviProductMap.put(key, productMap.get(key));
				}
			}
			
			String id = aviProductMap.get("id").toString();
			String json = JSONObject.toJSONString(aviProductMap);
			IndexRequest indexRequest = new IndexRequest(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, id)
					.source(json);
			UpdateRequest updateRequest = new UpdateRequest(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, id)
					.doc(json).upsert(indexRequest);
			bulkRequest.add(updateRequest);
		}

		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
		}
	}

	/**
	 * 删除文档
	 * 
	 * @param id
	 *            文档ID
	 */
	public static void delete(String id) {
		DeleteResponse response = getClient().prepareDelete(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, id)
				.get();
	}
	
	/**
	 * 批量删除文档
	 * @param ids
	 */
	public static long delete(Long[] ids) {
		if(ids==null || ids.length==0) return 0;
		long[] idArr = new long[ids.length];
		for (int i=0;i<ids.length;i++) {
			idArr[i] = ids[i];
		}
		BulkIndexByScrollResponse response =
			    DeleteByQueryAction.INSTANCE.newRequestBuilder(getClient())
			        .filter(QueryBuilders.termsQuery("id", idArr)) 
			        .source("aigou")
			        .get();                                             
		long deleted = response.getDeleted();
		return deleted;
	}

	/**
	 * 通过ID获取文档
	 * 
	 * @param id
	 *            文档ID
	 * @return
	 */
	public static Map<String, Object> get(String id) {
		GetResponse response = getClient().prepareGet(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, id).get();
		if (response.isExists()) {
			return response.getSource();
		} else {
			return new HashMap<>();
		}
	}

	/**
	 * 批量获取文档
	 * 
	 * @param ids
	 *            文档ID数组
	 * @return
	 */
	public static List<Map<String, Object>> get(String... ids) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (ids == null || ids.length == 0)
			return resultList;
		MultiGetResponse multiGetItemResponses = getClient().prepareMultiGet()
				.add(ProductSearchHelper.INDEX, ProductSearchHelper.TYPE, ids).get();
		for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
			GetResponse response = itemResponse.getResponse();
			if (response.isExists()) {
				resultList.add(response.getSource());
			}
		}
		return resultList;
	}

	/**
	 * 通过dsl模板和参数获取文档
	 * 
	 * @param dslTemplate dsl模板
	 * @param params   参数 
	 * 如：
	 *  dslTtemplate ： {"query":{"match":{"id":"{{id}}"}}}
	 *  params(map) : new HashMap().put("id",1);
	 * @return
	 */
	public static List<Map<String, Object>> query(String dslTemplate, Map<String, Object> params) {
		SearchResponse response = new SearchTemplateRequestBuilder(getClient()).setScript(dslTemplate)
				.setScriptType(ScriptType.INLINE).setScriptParams(params).setRequest(new SearchRequest()).get()
				.getResponse();
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (SearchHit hit : response.getHits().getHits()) {
			resultList.add(hit.getSource());
		}
		return resultList;
	}

	/**
	 * 通过product查询对象对product的es数据进行查询分页
	 * @param query
	 * @return
	 */
	public static Page<Map<String, Object>> query(ProductQuery query) {
		SearchRequestBuilder sRequestBuilder = getClient().prepareSearch(ProductSearchHelper.INDEX)
				.setTypes(ProductSearchHelper.TYPE);
		// 设置搜索类型
		sRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		// 查询条件
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		List<QueryBuilder> must = boolQuery.must();

		// 关键字
		if (StringUtils.isNotBlank(query.getKeyword())) {
			must.add(QueryBuilders.matchQuery(ProductSearchHelper.ALL_FIELD, query.getKeyword()));
		}

		// 条件过滤器
		List<QueryBuilder> filters = boolQuery.filter();
		if (query.getProductType() != null) {
			filters.add(QueryBuilders.termQuery(PRODUCT_TYPE, query.getProductType()));
		}
		if (query.getBrand() != null) {
			filters.add(QueryBuilders.termQuery(PRODUCT_BRAND, query.getBrand()));
		}
		if (query.getPriceMax() != null) {
			filters.add(QueryBuilders.rangeQuery(PRODUCT_MIN_PRICE).from(0).to(query.getPriceMax()));
		}
		if (query.getPriceMin() != null) {
			filters.add(QueryBuilders.rangeQuery(PRODUCT_MAX_PRICE).from(query.getPriceMin()).to(Integer.MAX_VALUE));
		}

		// 添加到查询构造器
		sRequestBuilder.setQuery(boolQuery);

		// 排序字段
		String sort = query.getSort();
		// 排序方式
		String order = query.getOrder();
		SortOrder sortOrder = SortOrder.DESC;
		if (StringUtils.isNotBlank(order)) {
			if ("asc".equals(order.toLowerCase())) {
				sortOrder = SortOrder.ASC;
			}
		}

		if ("s".equals(sort)) {// 综合
		} else if ("xl".equals(sort)) {// 销量
			sRequestBuilder.addSort(PRODUCT_SALE_COUNT, sortOrder);
		}
		if ("xp".equals(sort)) {// 新品
			sRequestBuilder.addSort(PRODUCT_ONSALE_TIME, sortOrder);
		}
		if ("pl".equals(sort)) {// 评论
			sRequestBuilder.addSort(PRODUCT_COMMENT_COUNT, sortOrder);
		}
		if ("jg".equals(sort)) {// 价格
			if (sortOrder == SortOrder.DESC) {
				sRequestBuilder.addSort(PRODUCT_MAX_PRICE, sortOrder);
			} else {
				sRequestBuilder.addSort(PRODUCT_MIN_PRICE, sortOrder);
			}
		}
		if ("rq".equals(sort)) {// 人气
			sRequestBuilder.addSort(PRODUCT_VIEW_COUNT, sortOrder);
		}
		// 分页
		sRequestBuilder.setFrom(query.getStart()).setSize(query.getRows()).setExplain(false);
		// 开始查询
		SearchResponse response = sRequestBuilder.get();
		int total = (int)response.getHits().getTotalHits();
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (SearchHit hit : response.getHits().getHits()) {
			resultList.add(hit.getSource());
		}
		
		Page<Map<String, Object>> page = new Page<>();
		page.setRows(resultList);
		page.setTotal(total);
		return page;
	}

	/**
	 * 关闭es客户端
	 */
	public static void close() {
		getClient().close();
	}

	public static void main(String[] args) {

		/*TransportClient client = ProductSearchHelper.getClient();
		for (long i = 1; i < 100; i++) {
			String jsonDataText = "";
			Map<String, Object> product = new HashMap<>();
			product.put("name", "2017春秋款大花花纹连衣裙-" + i);
			product.put("brandId", 125L + i);
			product.put("id", i);
			jsonDataText = JSONObject.toJSONString(product);
			IndexResponse response = client.prepareIndex("aigou", "product", product.get("id").toString())
					.setSource(jsonDataText).get();
		}

		Map<String, Object> params = new HashMap<>();
		params.put("id", 47L);

		String dsl = "{}";
		try {
			XContentBuilder builder = jsonBuilder().startObject().field("query").startObject().field("match")
					.startObject().field("id", "{{id}}").endObject().endObject().endObject();
			dsl = builder.string();
			System.out.println(dsl);
		} catch (IOException e) {
		}
		SearchRequest searchRequest = new SearchRequest("aigou").types("product");
		SearchResponse response = new SearchTemplateRequestBuilder(client).setScript(dsl)
				.setScriptType(ScriptType.INLINE).setScriptParams(params).setRequest(searchRequest).get().getResponse();

		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getSource());
		}*/

		/*
		 * ProductQuery query = new ProductQuery(); 
		 * query.setBrand(1L);
		 * query.setProductType(2L); 
		 * query.setKeyword("连衣裙");
		 * query.setPriceMax(200); 
		 * query.setPriceMin(100); query.setPage(2);
		 * query.setSort("s"); 
		 * query(query);
		 * 
		 * client.close();
		 */
	}
}
