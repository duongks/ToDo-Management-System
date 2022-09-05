package com.dmm.task.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.Calendar;
@Controller
public class CalendarController {
	Calendar calendar;
	
	@GetMapping("/main")
	public String getUsers(Model model) {
		List<Calendar> calendars = new ArrayList<>();
		model.addAttribute("main", calendars);
		return "main";
	}
}