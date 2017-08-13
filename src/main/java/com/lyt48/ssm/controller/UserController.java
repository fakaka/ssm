package com.lyt48.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lyt48.ssm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	public ModelAndView list(ModelAndView mv) {
		mv.setViewName("user");
		mv.addObject("usesr", userService.list());
		return mv;
	}

}
