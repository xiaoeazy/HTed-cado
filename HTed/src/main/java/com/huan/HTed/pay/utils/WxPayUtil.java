package com.huan.HTed.pay.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WxPayUtil{

    /**
     * 获取随机字符串 (采用截取8位当前日期数  + 4位随机整数)
     * @return 
     */
    public static String getNonceStr() {
        //获得当前日期
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currTime = outFormat.format(now);
        //截取8位
        String strTime = currTime.substring(8, currTime.length());
        //得到4位随机整数
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < 4; i++) {
            num = num * 10;
        }
        num = (int)random * num;
        return strTime + num;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * MD5 加密，转为指定类型
     * @param text
     * @param key
     * @param input_charset
     * @return
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }


    /**
     * 元转换成分
     * @param money
     * @return
     */
    public static String getMoney(String amount) {
        if(amount==null){
            return "";
        }
        // 金额转化为分为单位
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
    }

    /**
     * 解析xml得到 prepay_id 预支付id
     * @param result
     * @return
     * @throws DocumentException 
     */
    public static String getPayNo(String result) throws DocumentException{
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        SAXReader read = new SAXReader();
        Document doc = read.read(in);
        //得到xml根元素
        Element root = doc.getRootElement();
        //遍历  得到根元素的所有子节点 
        @SuppressWarnings("unchecked")
        List<Element> list =root.elements();
        for(Element element:list){
            //装进map
            map.put(element.getName(), element.getText());
        }
        //返回码
        String return_code = map.get("return_code");
        //返回信息
        String result_code = map.get("result_code");
        //预支付id
        String prepay_id = "";
        //return_code 和result_code 都为SUCCESS 的时候返回 预支付id
        if(return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")){
            prepay_id = map.get("prepay_id");
        }
        return prepay_id;
    }
    /**
     * 解析 回调时的xml装进map 返回
     * @param result 
     * @return
     * @throws DocumentException 
     */
    public static Map<String, String> getNotifyUrl(String result) throws DocumentException{
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        SAXReader read = new SAXReader();
        Document doc = read.read(in);
        //得到xml根元素
        Element root = doc.getRootElement();
        //遍历  得到根元素的所有子节点 
        @SuppressWarnings("unchecked")
        List<Element> list =root.elements();
        for(Element element:list){
            //装进map
            map.put(element.getName().toString(), element.getText().toString());
        }
        return map;
    }

    /**
     * 验证签名，判断是否是从微信发过来
     * 验证方法：接收微信服务器回调我们url的时候传递的xml中的参数 然后再次加密，看是否与传递过来的sign签名相同
     * @param map
     * @return
     */
    public static boolean verifyWeixinNotify(Map<String, String> map,String key) {  
        //根据微信服务端传来的各项参数 进行再一次加密后  与传过来的 sign 签名对比
        String mapStr = createLinkString(map);
        String signOwn = WxPayUtil.sign(mapStr, key, "utf-8").toUpperCase();         //根据微信端参数进行加密的签名
        String signWx = map.get("sign");                //微信端传过来的签名
        if(signOwn.equals(signWx)){
            //如果两个签名一致，验证成功
            return true;
        }
        return false;
    }  

    /**
     * 
     * @param requestUrl请求地址
     * @param requestMethod请求方法
     * @param outputStr参数
     */
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        // 创建SSLContext
        StringBuffer buffer=null;
        try{
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();

        //往服务器端写内容
        if(null !=outputStr){
            OutputStream os=conn.getOutputStream();
            os.write(outputStr.getBytes("utf-8"));
            os.close();
        }
        // 读取服务器端返回的内容
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        buffer = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
                      buffer.append(line);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
        } 
}
