package com.rey.utils;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {
	public static List<UserBean> userBeans;

	static {
		userBeans=new ArrayList<>();
		for(int i=0;i<5;i++) {
			UserBean bean=new UserBean("user"+i, 15+i);
			userBeans.add(bean);
		}
	}
	
	
	
	public static final String HEADER="header";
	public static final String WS_ADDRESS="ws://134.60.156.14:8080";

}
