package com.uxpsystems.app.dao;
import com.uxpsystems.app.entities.UserInfo;
public interface UserInfoDAO {
	public abstract UserInfo getActiveUser(String userName);
}