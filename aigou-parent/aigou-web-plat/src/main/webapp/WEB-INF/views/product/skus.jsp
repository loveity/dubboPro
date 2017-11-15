<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding:10px">

<a href="#" onclick="addSku()" class="easyui-linkbutton" iconCls="icon-add">添加Sku</a>

<div id="editSkuDiv" style="display:none;border:solid 1px #ccc;border-radius:4px;padding:10px;margin-top:10px;overflow:hidden;background:#f9f9f9;">
<form id="skuForm" action="/product/storeSku">
<input type="hidden" name="id" value=""/>
<input type="hidden" name="productId" value="${product.id}"/>
<div style="width:250px;float:left;">
<table class="table table-very table-basic">
			<tr><td>规格</td>
			<td><input class="easyui-textbox" data-options="required:true" style="width:200px;" name="skuName"/></td>
			</tr>
</table>
</div>
<div style="width:1000px;float:right;">
<table class="table table-very table-basic">
	<tr><td>市场价</td><td><input class="easyui-textbox" name="marketPrice" data-options="required:true" style="width:200px;"/></td><td>优惠价</td><td><input class="easyui-textbox" data-options="required:true" name="price" style="width:200px;"/></td><td>成本价</td><td><input class="easyui-textbox" name="costPrice" style="width:200px;"/></td></tr>
	<tr><td>可用库存</td><td><input class="easyui-textbox" data-options="required:true" name="availableStock" style="width:200px;"/></td><td>锁定库存</td><td><input class="easyui-textbox" data-options="required:true" name="frozenStock" style="width:200px;"/></td><td>排序</td><td><input class="easyui-textbox" name="sortIndex" style="width:200px;"/></td></tr>
	<tr><td>SKU主图</td>
	<td colspan="4" id="skuPicEditor"><input name="skuMainPic" class="qiniuUploader" multiply="false" /></td><td>
	<a  class="easyui-linkbutton button-lg button-default" onclick="MXF.ajaxForm(this,storeSkuCallback,formatSkuPrice)">提交</a>
	<a  class="easyui-linkbutton button-lg button-danger" onclick="$('#editSkuDiv').hide()">取消</a>
	</td>
	</tr>
</table>
</div>
</form>
</div>

<table class="table table-striped">
	<thead>
		<tr>
			<th>SKU编码</th>
			<th>规格名</th>
			<th>市场价</th>
			<th>优惠价</th>
			<th>成本价</th>
			<th>销量</th>
			<th>排序</th>
			<th>可用库存</th>
			<th>锁定库存</th>
			<th>预览图</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${skuList}" var="sku">
		<tr>
			<td>${sku.skuCode}</td>
			<td>${sku.skuName}</td>
			<td>${sku.marketPrice*0.01}</td>
			<td>${sku.price*0.01}</td>
			<td>${sku.costPrice*0.01}</td>
			<td>${sku.saleCount}</td>
			<td>${sku.sortIndex}</td>
			<td>${sku.availableStock}</td>
			<td>${sku.frozenStock}</td>
			<td>
				<c:if test="${sku.skuMainPic!=null && sku.skuMainPic!=''}">
					<img src="http://ondhqmral.bkt.clouddn.com/${sku.skuMainPic}?imageView2/1/w/30/h/30" />
				</c:if>
			</td>
			<td><a class="easyui-linkbutton button-default button-sm" onclick="editSku(${sku.id})">编辑</a></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>

<script type="text/javascript">
function clearSkuForm(){
	$('#skuForm').find('[name=id]').val('');
	$('#skuForm').find('[textboxname=skuName]').textbox('setValue','');
	$('#skuForm').find('[textboxname=marketPrice]').textbox('setValue','');
	$('#skuForm').find('[textboxname=saleCount]').textbox('setValue','');
	$('#skuForm').find('[textboxname=sortIndex]').textbox('setValue','');
	$('#skuForm').find('[textboxname=price]').textbox('setValue','');
	$('#skuForm').find('[textboxname=costPrice]').textbox('setValue','');
	$('#skuForm').find('[textboxname=availableStock]').textbox('setValue','');
	$('#skuForm').find('[textboxname=frozenStock]').textbox('setValue','');
	$('#skuForm').find('[textboxname=skuMainPic]').textbox('setValue','');

}
function addSku(){
	$('#editSkuDiv').show();
	clearSkuForm();
	MXF.initQiniuUploader($('input[name="skuMainPic"]'));
}

function editSku(id){
	$.post('/product/sku/'+id,{},function(data){
		$('#editSkuDiv').show();
		//清空原七牛云上传控件
		$('#skuPicEditor').empty();
		//重新添加
		$('#skuPicEditor').html('<input name="skuMainPic" class="qiniuUploader" multiply="false"/>');
		data.marketPrice = data.marketPrice *0.01;
		data.price = data.price*0.01;
		data.costPrice = data.costPrice*0.01;
		$('#skuForm').form('load',data);
		//重新渲染
		MXF.initQiniuUploader($('input[name="skuMainPic"]'));
	});
}

function changeOption(newValue,oldValue){
	var optionValue = $(this).combobox('getText');
	$(this).closest('tr').find('[name=optionValue]').val(optionValue);
}

function storeSkuCallback($form,data){
	$('#skuWindow').window('refresh');
}

function formatSkuPrice(param){
	console.debug(param);
	param.price = 100 * param.price;
	param.marketPrice = 100 * param.marketPrice;
	param.costPrice = 100 * param.costPrice;
}
</script>
