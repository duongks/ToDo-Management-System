package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TaskRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller

public class CalendarController {
	@Autowired
	private TaskRepository repo;

	@GetMapping("/main")
	public String Calendarlist(Model model, @AuthenticationPrincipal AccountUserDetails user,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		System.out.println(date);
		LocalDate dayNow = LocalDate.now();
		if (date == null) {

		} else {
			dayNow = date;
		}
		LocalDate d2 = LocalDate.of(dayNow.getYear(), dayNow.getMonthValue(), 1);
		LocalDate d3 = LocalDate.of(dayNow.getYear(), dayNow.getMonthValue(), dayNow.lengthOfMonth());

		List<List<LocalDate>> lists = new ArrayList<>();
		List<LocalDate> listDate = new ArrayList<>();

		DayOfWeek w1 = d2.getDayOfWeek();
		DayOfWeek w2 = d3.getDayOfWeek();

		LocalDate daystart = d2.minusDays(7 - (7 - w1.getValue()));
		LocalDate daystart1 = d2.minusDays(7 - (7 - w1.getValue()));

		int dayLengt = daystart.lengthOfMonth() - daystart.getDayOfMonth() + d2.lengthOfMonth()
				+ (7 - w2.getValue() + 1);
		LocalDate daysend = daystart1.plusDays(dayLengt - 2);
		for (int j = 0; j < dayLengt; j++) {

			listDate.add(daystart);
			daystart = daystart.plusDays(1);
			if (daystart.getDayOfWeek() == DayOfWeek.SUNDAY) {
				lists.add(listDate);
				listDate = new ArrayList<>();
			}
		}

		String day = dayNow.format(DateTimeFormatter.ofPattern("yyyy年MM月"));

		model.addAttribute("prev", dayNow.minusMonths(1));
		model.addAttribute("next", dayNow.plusMonths(1));
		model.addAttribute("month", day);
		model.addAttribute("matrix", lists);

		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<>();

		List<Tasks> list;

		if (user.getName().equals("admin-name")) {
			list = repo.findAll();
			for (Tasks tasks2 : list) {
				tasks.add(tasks2.getDate().toLocalDate(), tasks2);
			}
		} else {
			list = repo.findByDateBetween(daystart1.atTime(0, 0), daysend.atTime(23, 59), user.getName());
			for (Tasks tasks3 : list) {
				tasks.add(tasks3.getDate().toLocalDate(), tasks3);
			}
		}
		model.addAttribute("tasks", tasks);

		return "main";
	}
}