package com.spring.practice.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.practice.bean.UserRequestBean;
import com.spring.practice.domain.User;
import com.spring.practice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private UserService service;

	@PutMapping("/updateUser")
	public String updateUser(@Valid @ModelAttribute("user") UserRequestBean bean, BindingResult result, Model model,
			Principal principal, RedirectAttributes redirect) {
		log.info("updating user....");
		User user = service.findByUsername(principal.getName());
		if (!ObjectUtils.isEmpty(user)) {
			model.addAttribute("userData", bean);
			if (result.hasErrors()) {
				return "admin/view_user";
			}
			Boolean isUpdated = service.updateUser(bean);
			if (isUpdated.booleanValue()) {
				redirect.addFlashAttribute("isUpdatedMsg", true);
				log.info("User isUpdated : {} ", isUpdated);
				return "redirect:/home";
			}
		}
		return "redirect:/logout_user";

	}

	@GetMapping("/fetchUserById/{id}")
	public String fetchUserById(@PathVariable(value = "id", required = false) Long id, Model model,
			Principal principal) {
		User user = service.findByUsername(principal.getName());
		if (!ObjectUtils.isEmpty(user)) {
			if (id != null) {
				User hasUser = service.findById(id);
				if (!ObjectUtils.isEmpty(hasUser)) {
					model.addAttribute("user", hasUser);
					return "admin/view_user";
				}
			}
			return "redirect:/home";
		}
		return "redirect:/logout_user";
	}

}
