package com.finalyear.saas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalyear.saas.dao.UsersDao;
import com.finalyear.saas.pojo.UsersPojo;

@Service
public class SignupService {

	@Autowired
	private UsersDao dao;

	public void add(UsersPojo u) throws ApiException {
		UsersPojo existing = dao.select(u.getEmail());
		if (existing != null) {
			throw new ApiException("User with given email already exists");
		}
		dao.insert(u);
	}

}
