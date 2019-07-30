package com.sm.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.cj.util.StringUtils;

public class SocialMediaDAO {
	Connection con = null;

	public void createPost(String userId, String postId, String content) throws Exception {
		try {
			if (StringUtils.isNullOrEmpty(userId) || StringUtils.isNullOrEmpty(postId)) {
				throw new Exception("userId or postId is empty or null");
			}
			final String INSERT_QUERY = " insert into post (userId, postId, content, published_date)"
					+ " values (?, ?, ?, ?)";
			con = getConnection(con);
			PreparedStatement preparedStmt = con.prepareStatement(INSERT_QUERY);
			preparedStmt.setString(1, userId);
			preparedStmt.setString(2, postId);
			byte[] buff = postId.getBytes();
			Blob blobData = new SerialBlob(buff);
			preparedStmt.setBlob(3, blobData);
			Date dt = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			preparedStmt.setString(4, format.format(dt));
			if (preparedStmt.execute()) {
				throw new Exception("Unable to save the post");
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public List<String> getNewsFeed(String userId) throws Exception {
		try {
			List<String> postIds = new ArrayList<String>();
			final String NEWS_FEED_QUERY = "select postid FROM post where userid = ? or userid in (select followeeId from follow where followerId = ?) order by published_date desc limit 20";
			con = getConnection(con);
			PreparedStatement preparedStmt = con.prepareStatement(NEWS_FEED_QUERY);
			preparedStmt.setString(1, userId);
			preparedStmt.setString(2, userId);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				postIds.add(rs.getString("postid"));
			}
			return postIds;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public boolean follow(String followerId, String followeeId) throws Exception {
		final String FOLLOW_QUERY = " insert into follow (followerId, followeeId) values (?, ?)";
		try {
			if (StringUtils.isNullOrEmpty(followerId) || StringUtils.isNullOrEmpty(followeeId)) {
				throw new Exception("followerId or followeeId is empty or null");
			}
			con = getConnection(con);
			if (isRecordExist(followerId, followeeId, con)) {
				PreparedStatement preparedStmt = con.prepareStatement(FOLLOW_QUERY);
				preparedStmt.setString(1, followerId);
				preparedStmt.setString(2, followeeId);
				if (preparedStmt.execute()) {
					throw new Exception("Unable to follow");
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public boolean unfollow(String followerId, String followeeId) throws Exception {

		final String UNFOLLOW_QUERY = "delete from follow where followerId=? and followeeId= ?";
		try {
			if (StringUtils.isNullOrEmpty(followerId) || StringUtils.isNullOrEmpty(followeeId)) {
				throw new Exception("followerId or followeeId is empty or null");
			}
			con = getConnection(con);
			if (isRecordExist(followerId, followeeId, con)) {
				PreparedStatement preparedStmt = con.prepareStatement(UNFOLLOW_QUERY);
				preparedStmt.setString(1, followerId);
				preparedStmt.setString(2, followeeId);
				if (preparedStmt.execute()) {
					throw new Exception("Unable to unfollow");
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public Connection getConnection(Connection con) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/socialnetwork", "root", "root");
		return con;
	}

	private boolean isRecordExist(String followerId, String followeeId, Connection con) throws Exception {
		final String SELECT_QUERY = "select * from socialnetwork.user_info where userid in (?,?)";
		PreparedStatement preparedStmt = con.prepareStatement(SELECT_QUERY);
		preparedStmt.setString(1, followerId);
		preparedStmt.setString(2, followeeId);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

}
