package org.jsp.usermvcapp.controller;

import org.jsp.usermvcapp.dao.UserDao;
import org.jsp.usermvcapp.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	@Autowired
	private UserDao dao;

	@RequestMapping(value = "/open")
	public String open(@RequestParam String view) {
		return view;
	}
	
	@PostMapping(value = "/register")
	public ModelAndView saveUser(@ModelAttribute User u, ModelAndView view) {
		u = dao.saveUser(u);
		view.addObject("msg", "user saved with Id: " + u.getId());
		view.setViewName("print");
		return view;
	}

	@PostMapping(value = "/edit")
	public ModelAndView edit(@RequestParam int id, ModelAndView view) {
		User u = dao.findUserById(id);
		if (u != null) {
			view.addObject("u", u);
			view.setViewName("update");
			return view;
		} else {
			view.addObject("msg", "The Id is Invalid");
			view.setViewName("print");
			return view;
		}
	}

	@PostMapping(value = "/update")
	public ModelAndView updateUser(@ModelAttribute User u, ModelAndView view) {
		dao.updateUser(u);
		view.addObject("msg", "User is Updated");
		view.setViewName("print");
		return view;
	}
	
	@RequestMapping(value = "/verify-phone")
	public ModelAndView verifyPhone(@RequestParam long phone,@RequestParam String password,ModelAndView view) {
		User u=dao.verifyUser(phone, password);
		if (u != null) {
			view.addObject("u", u);
			view.setViewName("view");
			return view;
		} else {
			view.addObject("msg", "Phone number and Password are Wrong");
			view.setViewName("print");
			return view;
		}
	}
	
	@RequestMapping(value = "/verify-email")
	public ModelAndView verifyEmail(@RequestParam String email,@RequestParam String password,ModelAndView view) {
		User u=dao.verifyUser(email, password);
		if (u != null) {
			view.addObject("u", u);
			view.setViewName("view");
			return view;
		} else {
			view.addObject("msg", "Email Id and Password are Wrong");
			view.setViewName("print");
			return view;
		}
	}
	
	@PostMapping(value = "/verify-id")
	public ModelAndView verifyId(@RequestParam int id,@RequestParam String password,ModelAndView view) {
		User u=dao.verifyUser(id, password);
		if(u!=null) {
			view.addObject("u", u);
			view.setViewName("view");
			return view;
		}
		else {
			view.addObject("msg", "Enterd Id and Password are Incorrect");
			view.setViewName("print");
			return view;
		}
	}
	
	@PostMapping(value = "/delete")
	public ModelAndView delete(@RequestParam int id, ModelAndView view) {
		boolean check = dao.deleteUser(id);
		if (check) {
			view.addObject("msg", "User is Deleted with Id: "+id);
			view.setViewName("print");
			return view;
		} else {
			view.addObject("msg", "The Id is Invalid");
			view.setViewName("print");
			return view;
		}
	}
}
