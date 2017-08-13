package com.lyt48.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lyt48.ssm.bean.User;
import com.lyt48.ssm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public String list(Model model) {
		List<User> list = userService.list();
		model.addAttribute("users", list);
		return "user";
	}
	
	@RequestMapping("/list2")
	public ModelAndView list2(ModelAndView mv) {
		List<User> list = userService.list();
		mv.setViewName("user");
		mv.addObject("users", list);
		return mv;
	}

}
