package com.huan.HTed.pay.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.minidev.json.JSONObject;

@Controller
public class OwnPayController {
	/**
	 * 
	 * @param total_fee 总金额
	 * @param body	商品描述
	 * @param attach	附加数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/Pay/pay")
	@ResponseBody
	public JSONObject pay(String total_fee, String body, String attach) throws Exception {
		
//		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		String code = PayUtil.createCode(8);
//		
//		String appId = "替换为自己的小程序ID";// 小程序ID
//		String mchId = "替换为自己的商户号";// 商户号
//		String nonceStr = UUIDHexGenerator.generate();// 随机字符串
//		String out_trade_no = mchId + today + code;// 商户订单号
//		total_fee=WxPayUtil.getMoney(total_fee);
//		String spbillCreateIp = "替换为自己的终端IP";// 发起支付的ip
//		String notifyUrl = "外网能够访问的url,支付成功后的回调";//微信支付完成后给该链接发送消息，判断订单是否完成
//		String tradeType = "JSAPI";//交易类型
//		String openId = "替换为用户的openid";// 用户标识
//		//支付密钥
//		String key = "&key=自己的商户密钥";
//		
//		/**/
//		Payment paymentPo = new Payment();
//		paymentPo.setAppid(appId);
//		paymentPo.setMch_id(mchId);
//		paymentPo.setNonce_str(nonceStr);
//		String newbody = new String(body.getBytes("ISO-8859-1"), "UTF-8");// 以utf-8编码放入paymentPo，微信支付要求字符编码统一采用UTF-8字符编码
//		paymentPo.setBody(newbody);
//		paymentPo.setOut_trade_no(out_trade_no);
//		paymentPo.setTotal_fee(total_fee);
//		paymentPo.setSpbill_create_ip(spbillCreateIp);
//		paymentPo.setNotify_url(notifyUrl);
//		paymentPo.setTrade_type(tradeType);
//		paymentPo.setOpenid(openId);
//		
//		// 把请求参数打包成数组
//		 Map<String, String> sParaTemp = new HashMap<String, String>();
//		 sParaTemp.put("appid", appId);
//		 sParaTemp.put("mch_id", mchId);
//		 sParaTemp.put("nonce_str", nonceStr);
//		 sParaTemp.put("body",  body);
//		 sParaTemp.put("out_trade_no", out_trade_no);
//		 sParaTemp.put("total_fee",total_fee);
//		 sParaTemp.put("spbill_create_ip", spbillCreateIp);
//		 sParaTemp.put("notify_url",notifyUrl);
//		 sParaTemp.put("trade_type", tradeType);
//		 sParaTemp.put("attach", attach);//注意
//		 sParaTemp.put("openid", openId);
//		 
//		 //去掉空值 跟 签名参数(空值不参与签名，所以需要去掉)
//		 Map<String, String> map = PayUtil.paraFilter(sParaTemp);
//		 String mapStr = PayUtil.createLinkString(map);
//		// MD5运算生成签名
//		String mysign = PayUtil.sign(mapStr, key, "utf-8").toUpperCase();
//		paymentPo.setSign(mysign);
//		// 打包要发送的xml
//		String respXml = MessageUtil.messageToXML(paymentPo);
//		// 打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
//		respXml = respXml.replace("__", "_");
//		
//		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 统一下单API接口链接
//		String param = respXml;
//		String result = PayUtil.httpRequest(url, "POST", param);
//		
//		 //得到open_id;
//        String prepay_id =  WxPayUtil.getPayNo(result);
//        
//		
//        JSONObject JsonObject = new JSONObject();
//        
//		String returnNonceStr=UUIDHexGenerator.generate();
//		JsonObject.put("success", true);
//		JsonObject.put("appId", appId);
//		JsonObject.put("nonceStr", returnNonceStr);
//		JsonObject.put("package", "prepay_id="+prepay_id);
//		Long timeStamp= System.currentTimeMillis()/1000;
//		JsonObject.put("timeStamp", timeStamp+"");
//		String stringSignTemp = "appId="+appId+"&nonceStr=" + returnNonceStr + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
//		//再次签名
//		String paySign=PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
//		JsonObject.put("paySign", paySign);
		JSONObject JsonObject = new JSONObject();
		JsonObject.put("aa", "11");
		JsonObject.put("bb", "11");
		return JsonObject;
	}
}
