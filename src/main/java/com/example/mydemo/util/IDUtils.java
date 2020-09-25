package com.example.mydemo.util;

import java.util.Random;

/**
 * 各种id生成策略
 * <p>Title: IDUtils</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年7月22日下午2:32:10
 * @version 1.0
 */
public class IDUtils {
	/**
	 *付款或退款id生成：
	 */
	public static String genPayOrRefundId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();		
		//加上两位随机数
		Random random = new Random();
		int end = random.nextInt(9999);
		//如果不足两位前面补0
		String str ="shop"+ millis + String.format("%04d", end);
		return str;
	}
	


}
