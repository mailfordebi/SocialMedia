package com.sm.services;

import com.sm.dao.SocialMediaDAO;

public class SocialMediaService {
	SocialMediaDAO socialMediaDAO = new SocialMediaDAO();

	public void createPost(String userId, String postId, String content) throws Exception {
		socialMediaDAO.createPost(userId, postId, content);
	}

	public void getNewsFeed(String userId) throws Exception {
		socialMediaDAO.getNewsFeed(userId);
	}

	public void follow(String followerId, String followeeId) throws Exception {
		socialMediaDAO.follow(followerId, followeeId);
	}

	public void unfollow(String followerId, String followeeId) throws Exception {
		socialMediaDAO.unfollow(followerId, followeeId);
	}
}
