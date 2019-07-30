package com.sm.services;

import com.sm.dao.SocialMediaDAO;

public class Test {
	public static void main(String[] args) throws Exception {
		SocialMediaDAO s = new SocialMediaDAO();
		// s.createPost("4", "8", "post1_256873gjhdvbhb");
		// s.follow("2", "5");
		System.out.println(s.getNewsFeed("2"));
	}
}
