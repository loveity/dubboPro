package cn.itsource.aigou.service;


import java.util.List;
import java.util.Map;


import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.VipCart;

public interface IVipCartService extends IBaseService<VipCart> {

	/**
	 * 加入购物车
	 * @param ssoId
	 * @param skuId
	 * @param number
	 */
	void add(Long ssoId,Long skuId, Integer number);

	/**
	 * 用户删除购物车
	 * @param ssoId 用户id
	 * @param cartIdArr 购物车id数组
	 */
	void del(Long ssoId, Long[] cartIdArr);

	/**
	 * 修改购物车条目数量
	 * @param ssoId
	 * @param cartId
	 * @param number
	 */
	Sku changeNumber(Long ssoId, Long cartId, Integer number);

	/**
	 * 选中购物车
	 * @param ssoId
	 * @param cartIdArr
	 */
	void updateSelectCart(Long ssoId, Long[] cartIdArr);

	/**
	 * 马上购买
	 * @param ssoId
	 * @param skuId
	 * @param number
	 */
	void quickBuy(Long ssoId, Long skuId, Integer number);

	/**
	 * 统计用户当前购物车勾选的情况
	 * @param ssoId
	 * @return 
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 */
	Map<String, Object> caculate(Long ssoId);
	
	/**
	 * 获取用户的所有购物车数据
	 * @param ssoId
	 * @return
	 */
	List<VipCart> getCarts(Long ssoId);
	
	/**
	 * 统计列表的购物车信息
	 * @param cartList
	 * @return
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 */
	Map<String, Object> statistic(List<VipCart> cartList);

	/**
	 * 获取用户购物车的情况
	 * @param ssoId
	 * @return
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 *  
	 *  data : List-VipCart
	 */
	Map<String, Object> info(Long ssoId);
	/**
	 * 获取用户购物车选中的情况
	 * @param ssoId
	 * @return
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 *  
	 *  data : List-VipCart selected
	 */
	Map<String, Object> selectedInfo(Long ssoId);

	/**
	 * 删除购物车已购买部分的数据
	 * @param ssoId
	 */
	void clearBuy(Long ssoId);
	
}
