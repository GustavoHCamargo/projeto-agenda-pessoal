package com.Agenda.Agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Agenda.Agenda.model.Atividade;
import com.Agenda.Agenda.model.Materia;
import com.Agenda.Agenda.model.Professor;
import com.Agenda.Agenda.repository.AtividadeRepository;
import com.Agenda.Agenda.repository.MateriaRepository;
import com.Agenda.Agenda.repository.ProfessorRepository;

import jakarta.validation.Valid;

@Controller
public class ProfessorController {
	
	@Autowired
	private ProfessorRepository pr; 
	@Autowired
	private MateriaRepository mr; 
	
	// CADASTRAR PROFESSOR
		@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.GET)
		public String form() {
			return "professor/formProfessor";
		}

		@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.POST)
		public String form(@Valid Professor professor, BindingResult result, RedirectAttributes attributes) {

			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos...");
				return "redirect:/cadastrarProfessor";
			}

			pr.save(professor);
			attributes.addFlashAttribute("mensagem", "Professor cadastrado com sucesso!");
			return "redirect:/cadastrarProfessor";
		}
		
		
		// LISTAR PROFESSORES

		@RequestMapping("/professores")
		public ModelAndView listaProfessores() {
			ModelAndView mv = new ModelAndView("professor/listarProfessor");
			Iterable<Professor> professores = pr.findAll();
			mv.addObject("professores", professores);
			return mv;
		}
		
		
		// MÉTODOs QUE ATUALIZAM PROFESSOR
		// FORMULÁRIO EDIÇÃO DE PROFESSOR

		@RequestMapping(value = "/editar-professor", method = RequestMethod.GET)
		public ModelAndView editarProfessor(long id) {
			Professor professor = pr.findByid(id);
			ModelAndView mv = new ModelAndView("professor/update-professor");
			mv.addObject("professor", professor);
			return mv;
		}

		// UPDATE PROFESSOR
		@RequestMapping(value = "/editar-professor", method = RequestMethod.POST)
		public String updateProfessor(@Valid Professor professor, BindingResult result, RedirectAttributes attributes) {
			pr.save(professor);
			attributes.addFlashAttribute("success", "Professor alterado com sucesso!");

			long idlong = professor.getId();
			String id = "" + idlong;
			return "redirect:/professores";
		}
		
		
		// DELETAR PROFESSOR PELO ID
		@RequestMapping("/deletarProfessor")
		public String deletarProfessor(long id) {
			Professor professor= pr.findByid(id);
			List<Materia> materias = mr.findByProfessor(professor);
			// Desvincular o professor de cada matéria
			for (Materia materia : materias) {
				materia.setProfessor(null);
				mr.save(materia);
				}
			pr.delete(professor);
			return "redirect:/professores";
		}

}
