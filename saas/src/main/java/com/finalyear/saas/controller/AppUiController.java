package com.finalyear.saas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "")
	public ModelAndView home() {
		return mav("index.html");
	}

	@RequestMapping(value = "/scilab")
	public ModelAndView scilab() {
		return mav("scilab.html");
	}
	@RequestMapping(value = "/compiler")
	public ModelAndView compiler() {
		return mav("compiler.html");
	}
	@RequestMapping(value = "/addcontest")
	public ModelAndView addcontest() {
		return mav("addcontest.html");
	}
	@RequestMapping(value = "/addquestion")
	public ModelAndView addquestion() {
		return mav("addquestion.html");
	}
	
	@RequestMapping(value = "/contest")
	public ModelAndView contest() {
		return mav("contest.html");
	}
	
	@RequestMapping(value = "/question")
	public ModelAndView question() {
		return mav("question.html");
	}
	
	@RequestMapping(value = "/contestlist")
	public ModelAndView contestlist() {
		return mav("contestlist.html");
	}

	@RequestMapping(value = "/ui/signup")
	public ModelAndView signup() {
		return mav("signup.html");
	}

	@RequestMapping(value = "/ui/login")
	public ModelAndView login() {
		return mav("login.html");
	}
	
	@RequestMapping(value = "/mysql")
	public ModelAndView mysql() {
		return mav("mysql.html");
	}
	
}
