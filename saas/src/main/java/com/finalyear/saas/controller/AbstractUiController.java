package com.finalyear.saas.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class AbstractUiController {

	
	@Value("${app.baseUrl}")
	private String baseUrl;

	protected ModelAndView mav(String page) {
		// Get current user
			// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

}