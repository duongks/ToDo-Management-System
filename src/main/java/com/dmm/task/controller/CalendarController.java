package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Task;

@Controller
public class CalendarController {

	@GetMapping("/main")
	public String Calendarlist(Model model) {
		LocalDate d1 = LocalDate.now();
		LocalDate d2 = LocalDate.of(d1.getYear(), d1.getMonthValue(), 1);
		LocalDate d3 = LocalDate.of(d1.getYear(), d1.getMonthValue(), d1.lengthOfMonth());

		List<List<LocalDate>> lists = new ArrayList<>();
		List<LocalDate> listDate = new ArrayList<>();

		DayOfWeek w1 = d2.getDayOfWeek();
		DayOfWeek w2 = d3.getDayOfWeek();

		LocalDate days = d2.minusDays(7 - (7 - w1.getValue()));
		int dayLengt = days.lengthOfMonth() - days.getDayOfMonth() + d2.lengthOfMonth() + (7 - w2.getValue() + 1);

		for (int j = 0; j < dayLengt; j++) {

			listDate.add(days);
			days = days.plusDays(1);
			if (days.getDayOfWeek() == DayOfWeek.SUNDAY) {
				lists.add(listDate);
				listDate = new ArrayList<>();
			}
		}
		model.addAttribute("matrix", lists);
		MultiValueMap<LocalDate, Task> task = new LinkedMultiValueMap<>();
		model.addAttribute("tasks", task);
		return "main";
	}
}