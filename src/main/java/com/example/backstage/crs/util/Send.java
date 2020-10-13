package com.example.backstage.crs.util;




import com.bcloud.msg.http.HttpSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Send {
	public String send(String postUrl,String pStr) throws JsonProcessingException {
		String line, result = "";
		try {
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(pStr);
			out.flush();
			out.close();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				//System.out.println("connect failed!");
			}
			// 获取响应内容体
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("------Send.sendUrl:"+postUrl);
		//System.out.println("------Send.sendParam:"+pStr);
		//System.out.println("------Send.sendResult:"+result);
		return result;
	}

	public void sendBtye(String accesstoken, Map<String,Object> map, HttpServletRequest request,String scene)throws Exception{
		RestTemplate rest = new RestTemplate();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accesstoken;
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			HttpEntity requestEntity = new HttpEntity(map, headers);
			//System.out.println("【41.1】HttpEntity requestEntity = new HttpEntity(map, headers)");
			ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
			//System.out.println("【41.2】rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0])");
			byte[] bytes = entity.getBody();
			//System.out.println("【41.3】byte[] bytes = entity.getBody():"+bytes);
			inputStream = new ByteArrayInputStream(bytes);

			String fileUrl = request.getSession().getServletContext().getRealPath("/")+"page/invitationewmimgs/"+scene+"_ewm.png";
			//System.out.println("【41.4】fileUrl:"+fileUrl);
			File file = new File(fileUrl);
			if (!file.exists()){
				//System.out.println("【41.5】fileUrl:"+fileUrl);
				file.createNewFile();
			}
			outputStream = new FileOutputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = inputStream.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
			if(inputStream != null){
				inputStream.close();
			}
			if(outputStream != null){
				outputStream.close();
			}
			//byte[] bytes = downLoadPic("http://res.ilvyougroup.com/assets/images/lykjfavicon.ico");
			//buff2Image(bytes,fileUrl);
		} catch (Exception e) {
			//System.out.println("微信永久小程序码URL接口异常"+e);
		}
	};
	public static void buff2Image(byte[] b,String tagSrc) throws Exception{
		FileOutputStream fout = new FileOutputStream(tagSrc);
		//将字节写入文件
		fout.write(b);
		fout.close();
	}
	public static String GetImageStr(String imgFile)
	{//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		//读取图片字节数组
		try
		{
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);//返回Base64编码过的字节数组字符串
	}
	public byte[] downLoadPic(String imgUrl)throws Exception{
		URL url = new URL(imgUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//设置超时间为3秒
		conn.setConnectTimeout(3*1000);
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//得到输入流
		InputStream inputStream = conn.getInputStream();
		//获取自己数组
		byte[] getData = readInputStream(inputStream);

		if(inputStream!=null){
			inputStream.close();
		}
		return getData;
	}
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024*100];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	// 发送消息
	public String wxSessionKey(String appId,String appSecret,String jscode) throws Exception {
		String postUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+jscode+"&grant_type=authorization_code";
		return send(postUrl,"");
	}
	// 获取AccessToken
	public String newAccessToken(String appid,String appsecret) throws Exception {
		String accesstoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
		return  send(accesstoken,"");
	}
	// 获取二维码
	public String wxaCode(String accesstoken,String paramStr) throws Exception {
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accesstoken;
		return  send(url,paramStr);
	}
	// 发送消息
	public String wxPay(String xmlParam) throws Exception {
		String postUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		return send(postUrl,xmlParam);
	}
	public String sendysMsg(String str) throws Exception {
		String postUrl = "http://www.yescloudtree.cn:28009";
		return send(postUrl,str);
	}
	public String sendNewysMsg(MsgParam msgParam) throws Exception {
		System.err.println(msgParam);
		String uri = "http://47.98.111.6:80/msg/HttpBatchSendSM";//应用地址
		String account = "ahhdyzm_facebody";//账号
		String pswd = "T9DJss$w6521";//密码
		String mobiles = msgParam.getMobilenum();//手机号码，多个号码使用","分割
		String content = msgParam.getMsg();//短信内容
		boolean needstatus = false;//是否需要状态报告，需要true，不需要false
		String product = "27529523";//产品ID
		String extno = "";//扩展码
		String respType = "json";//返回json格式响应
		//boolean encrypt = true;// 密码使用时间戳加密
		String returnString = "";
		try {
			returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno, respType);
			//System.out.println("returnString:"+returnString);
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
		return returnString;
	}
	public static void sendError(Exception es,String method) throws Exception {
		String uri = "http://47.98.111.6:80/msg/HttpBatchSendSM";//应用地址
		String account = "ahhdyzm_facebody";//账号
		String pswd = "T9DJss$w6521";//密码
		String mobiles = "15955340141,18110881833";
		String content = "报错方法:"+method+"||报错内容:";
		StringWriter sw = new StringWriter();
		es.printStackTrace(new PrintWriter(sw, true));
		if (sw.toString().length()>180){
			content += sw.toString().substring(0,180);
		}else {
			content += sw.toString();
		}

		//System.out.println(content);
		//String content = msgParam.getMsg();//短信内容
		boolean needstatus = false;//是否需要状态报告，需要true，不需要false
		String product = "27529523";//产品ID
		String extno = "";//扩展码
		String respType = "json";//返回json格式响应
		//boolean encrypt = true;// 密码使用时间戳加密
		String returnString = "";
		try {
			returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno, respType);
			//System.out.println("returnString:"+returnString);
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
	}
}
