package com.finalyear.saas.dao;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.finalyear.saas.pojo.UsersPojo;

@Repository
public class UsersDao extends AbstractDao {
	private static String select_email = "select p from UsersPojo p where email=:email";
	@Transactional
	public void insert(UsersPojo u) {
		em().persist(u);	
	}
	@Transactional
	public UsersPojo select(String email) {
		TypedQuery<UsersPojo> query = getQuery(select_email, UsersPojo.class);
		query.setParameter("email", email);
		return getSingle(query);
	}
}
