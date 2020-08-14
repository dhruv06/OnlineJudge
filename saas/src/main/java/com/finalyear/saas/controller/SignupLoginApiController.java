package com.finalyear.saas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finalyear.saas.model.LoginForm;
import com.finalyear.saas.model.SignupForm;
import com.finalyear.saas.model.UserInfo;
import com.finalyear.saas.pojo.UsersPojo;
import com.finalyear.saas.service.ApiException;
import com.finalyear.saas.service.LoginService;
import com.finalyear.saas.service.SignupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SignupLoginApiController {
	
	@Autowired
	private SignupService sService;
	
	@Autowired
	private LoginService lService;
	
	@ApiOperation(value = "Adds a User")
	@RequestMapping(path = "/api/signup", method = RequestMethod.POST)
	public void addUser(@RequestBody SignupForm form) throws ApiException {
		UsersPojo u=convert(form);
		sService.add(u);
	}

	private UsersPojo convert(SignupForm form) {
		UsersPojo u = new UsersPojo();
		u.setEmail(form.getEmail());
		u.setUsername(form.getUsername());
		u.setPassword(form.getPassword());
		return u;
	}

	@ApiOperation(value = "Do Login")
	@RequestMapping(path = "/api/login", method = RequestMethod.POST)
	public UserInfo doLogin(@RequestBody LoginForm form) throws ApiException {
		return lService.checkCredentials(form);
	}
}
