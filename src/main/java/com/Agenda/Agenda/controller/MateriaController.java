package com.Agenda.Agenda.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Agenda.Agenda.model.Materia;
import com.Agenda.Agenda.repository.MateriaRepository;
import com.Agenda.Agenda.repository.ProfessorRepository;
import com.Agenda.Agenda.repository.AtividadeRepository;
import com.Agenda.Agenda.model.Atividade;
import com.Agenda.Agenda.model.Professor;

@Controller
public class MateriaController {

	@Autowired
	private MateriaRepository mr;

	@Autowired
	private AtividadeRepository ar;
	
	@Autowired
	private ProfessorRepository pr;

	// CADASTRAR MATERIA
	@RequestMapping(value = "/cadastrarMateria", method = RequestMethod.GET)
	public ModelAndView form() {
	    ModelAndView mv = new ModelAndView("materia/formMateria");
	    Iterable<Professor> professores = pr.findAll();
	    mv.addObject("professores", professores);
	    return mv;
	}

	@RequestMapping(value = "/cadastrarMateria", method = RequestMethod.POST)
	public String form(@Valid Materia materia, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarMateria";
		}

		mr.save(materia);
		attributes.addFlashAttribute("mensagem", "Materia cadastrada com sucesso!");
		return "redirect:/cadastrarMateria";
	}

	// LISTAR MATERIAS

	@RequestMapping("/materias")
	public ModelAndView listaMaterias() {
		ModelAndView mv = new ModelAndView("materia/listaMateria");
		Iterable<Materia> materias = mr.findAll();
		mv.addObject("materias", materias);
		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesMateria(@PathVariable("id") long id) {
		Materia materia = mr.findByid(id);
		ModelAndView mv = new ModelAndView("materia/detalhesMateria");
		mv.addObject("materia", materia);

		Iterable<Atividade> atividades = ar.findByMateria(materia);
		mv.addObject("atividades", atividades);

		return mv;
	}

	// DELETAR MATERIA PELO ID
	@RequestMapping("/deletarVaga")
	public String deletarMateria(long id) {
		Materia materia = mr.findByid(id);
		mr.delete(materia);
		return "redirect:/materias";
	}

	// POST DETALHE MATERIA
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detalhesMateriaPost(@PathVariable("id") long id, @Valid Atividade atividade, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/{id}";
		}

		Materia materia = mr.findByid(id);
		atividade.setMateria(materia);
		ar.save(atividade);
		attributes.addFlashAttribute("mensagem", "Atividade adicionada com sucesso!");
		return "redirect:/{id}";
	}
	
	// DELETAR ATIVIDADE PELO NOME
	@RequestMapping("/deletarAtividade")
	public String deletarAtividade(String nome) {
		Atividade atividade = ar.findByNome(nome);
		Materia materia = atividade.getMateria();
		String id = "" + materia.getId();

		ar.delete(atividade);

		return "redirect:/" + id;
	}
	// MÉTODO QUE ATUALIZAM MATERIA
	// FORMULÁRIO EDIÇÃO DE MATÉRIA

	@RequestMapping(value = "/editar-materia", method = RequestMethod.GET)
	public ModelAndView editarMateria(long id) {
		Materia materia = mr.findByid(id);
		ModelAndView mv = new ModelAndView("materia/update-materia");
		mv.addObject("materia", materia);
		Iterable<Professor> professores = pr.findAll();
	    mv.addObject("professores", professores);
		
		return mv;
	}

	// UPDATE MATERIAS
	@RequestMapping(value = "/editar-materia", method = RequestMethod.POST)
	public String updateMateria(@Valid Materia materia, BindingResult result, RedirectAttributes attributes) {
		mr.save(materia);
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		long idlong = materia.getId();
		String id = "" + idlong;
		return "redirect:/" + id;
	}
	
}
