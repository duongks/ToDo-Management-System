package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TaskRepository;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;
@Controller
public class EditController {
	@Autowired
	private TaskRepository repo;
	@GetMapping("/main/edit/{id}")
	public String index(Model model,@PathVariable Integer id) {
	Tasks task=repo.getById(id);
	model.addAttribute("task",task);
		return "/edit";
	}
	@PostMapping("/main/edit/{id}")	
	public String create(@Validated TaskForm taskForm,@PathVariable Integer id,BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user,Model model) {
//		if (bindingResult.hasErrors()) {
//			Tasks task=repo.getById(id);
////			List<Tasks> list=repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
//			model.addAttribute("task", task);
//			return "/eidt";
//		}
		Tasks task=repo.getById(id);
		task.setName(user.getName());
		task.setTitle(taskForm.getTitle());
		task.setText(taskForm.getText());
		task.setDate(taskForm.getDate().atTime(0, 0));
		task.setDone(taskForm.isDone());
		repo.save(task);
		
		return "redirect:/main";
	}
	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
			return"redirect:/main";
	}
}
