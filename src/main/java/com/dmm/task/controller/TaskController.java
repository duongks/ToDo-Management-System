package com.dmm.task.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Task;
import com.dmm.task.data.repository.TaskRepository;
import com.dmm.task.service.AccountUserDetails;


@Controller
public class TaskController {
	@Autowired
	private TaskRepository repo;
	@GetMapping("/main/create/{date}")
	@Query("select a from Tasks a where a.date between :from and :to and name = :name")
	public String index(Model model) {
		
		List<Task> findByDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("name") String name);
		model.addAttribute("create/{date}",findByDateBetween);
		return "/create";
	}
	@PostMapping("/create/creates")	
	public String creates(BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		if (bindingResult.hasErrors()) {
			List<Task> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			model.addAttribute("create", list);
			return "/create";
		}
		Task task=new Task();
		task.setName(user.getName());
		task.setTitle(task.getTitle());
		task.setText(task.getText());
		task.setDate(LocalDateTime.now());
		
		repo.save(task);
		
		return "redirect:/create";
	}
	
	@PostMapping("/create/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
			return"redirect:/create";
	}
}
