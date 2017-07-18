package com.rey.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.vertx.core.net.SelfSignedCertificate;

public class AppUtils {
	public static List<UserBean> userBeans;
	public static List<String> skills=new ArrayList<>();
	public static Random random = new Random();
	static {
		skills.add("program");
		skills.add("game");
		skills.add("sing");
		skills.add("none");

		userBeans = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			UserBean bean = new UserBean("user" + i, random.nextInt(25));
			bean.setSkill(skills.get(random.nextInt(skills.size())));
			userBeans.add(bean);
		}
	}

	public static final String HEADER = "header";
	public static final String WS_ADDRESS = "wss://134.60.149.170:8181";
	public static SelfSignedCertificate certificate = SelfSignedCertificate.create();
	public static int Version = 0;
	public static Random VERSION_RANDOM = new Random(1000);

}
