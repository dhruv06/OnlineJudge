package com.finalyear.saas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalyear.saas.dao.UsersDao;
import com.finalyear.saas.model.LoginForm;
import com.finalyear.saas.model.UserInfo;
import com.finalyear.saas.pojo.UsersPojo;

@Service
public class LoginService {

	@Autowired
	private UsersDao dao;

	public UserInfo checkCredentials(LoginForm form) throws ApiException {
		UsersPojo existing = dao.select(form.getEmail());
		if (existing == null) {
			throw new ApiException("User with given email does not exists");
		} else {
			if (form.getPassword().equals(existing.getPassword())) {
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(existing.getEmail());
				userInfo.setUsername(existing.getUsername());
				return userInfo;
			} else {
				throw new ApiException("Check credentials !!");
			}
		}
	}

}
