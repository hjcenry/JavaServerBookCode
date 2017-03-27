package com.hjc.herolrouter.client.message;

public class PayReq {
	/** 爱思数据 **/
	private String channel;
	private String gamename;
	private String goodname;
	private double price;
	private long billno;// 游戏服订单id
	private String orderid;// orderid 字符串 xyzs平台订单号
	/** xy数据 **/
	private String uid;// uid 整型 xyzs平台用户ID
	private int serverid;// serverid 字符串 透传参数。服务器ID，不分服，为0。也可以传其它值，
							// 或不传。此数据在开发商调用接口时传入，充值成功，
							// 回调开发商发货接口时候，由xyzs充值中心传给开发 商的回调接口，xyzs不做任何处理。
	private double amount;// 充值金额 浮点型 人民币消耗金额，单位：元
	private String extra;// 透传参数 字符串 透传参数。充值时，由开发商传入，不要传特殊字符， 或url易转义、需编码的字符，128
							// 字节内，可记录开 发商的订单号等额外信息。在充值成功，回调开发商发
							// 货接口时候，由xyzs充值中心传给开发商的回调接口， xyzs不做任何处理。
	private long ts;// 当前时间戳 整型
	private String sign;// 签名 字符串 见 签名生成方式
	private String sig;// 签名2 字符串 见 签名生成方式 签名参数说明：充值回调接口会收到2个签名参数，sign
	/** 快用数据 **/
	private String notify_data;
	private String dealseq;
	private String subject;
	private String v;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public String getGoodname() {
		return goodname;
	}

	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getBillno() {
		return billno;
	}

	public void setBillno(long billno) {
		this.billno = billno;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public int getServerid() {
		return serverid;
	}

	public void setServerid(int serverid) {
		this.serverid = serverid;
	}

	public String getNotify_data() {
		return notify_data;
	}

	public void setNotify_data(String notify_data) {
		this.notify_data = notify_data;
	}

	public String getDealseq() {
		return dealseq;
	}

	public void setDealseq(String dealseq) {
		this.dealseq = dealseq;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
