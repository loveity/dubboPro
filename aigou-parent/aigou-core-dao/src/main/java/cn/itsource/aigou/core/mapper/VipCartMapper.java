package cn.itsource.aigou.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.itsource.aigou.core.domain.VipCart;

public interface VipCartMapper extends BaseMapper<VipCart> {

	/**
	 * 用户删除购物车
	 * @param ssoId
	 * @param cartIds
	 */
	void delCartByUser(@Param("ssoId") Long ssoId, @Param("cartIdArr") Long[] cartIdArr);

	/**
	 * 修改购物车条目数量
	 * @param ssoId
	 * @param cartId
	 * @param number
	 */
	void changeNumber(@Param("ssoId") Long ssoId, @Param("cartId") Long cartId, @Param("number") Integer number);

	/**
	 * 选中购物车
	 * @param ssoId
	 * @param cartIdArr
	 */
	void updateSelectCart(@Param("ssoId") Long ssoId, @Param("cartIdArr") Long[] cartIdArr);

	/**
	 * 取消用户已选择的购物车所有条目
	 * @param ssoId
	 */
	void cancelSelectAll(Long ssoId);

	/**
	 * 获取用户购物车数据
	 * @param ssoId
	 * @return
	 */
	List<VipCart> getCarts(Long ssoId);

	/**
	 * 通过ssoId获取购物车中skuId的购物条目
	 * @param ssoId
	 * @param skuId
	 * @return
	 */
	VipCart getBySsoSku(@Param("ssoId") Long ssoId, @Param("skuId") Long skuId);

	/**
	 * 删除购物车已购买部分的数据
	 * @param ssoId
	 */
	void clearBuy(Long ssoId);
}