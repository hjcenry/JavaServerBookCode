package com.hjc.pay.client.pay;

/**
 * @ClassName: IPay
 * @Description: 支付接口，由支付服务器实现
 * @author 何金成
 * @date 2016年4月28日 下午5:03:20
 * 
 */
public interface IPay {
	public long generateOrder(Pay pay);

	public Pay queryOrder(long billno);

	public boolean sendGoods(long billno,boolean success);

	public int queryPaySum(long userid);
}
