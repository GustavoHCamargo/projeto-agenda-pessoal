package com.Agenda.Agenda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Agenda.Agenda.model.Atividade;
import com.Agenda.Agenda.model.Materia;
import com.Agenda.Agenda.model.Professor;
import com.Agenda.Agenda.repository.AtividadeRepository;

import jakarta.validation.Valid;

@Controller
public class AtividadeController {
	
	private static final Logger logger = LoggerFactory.getLogger(AtividadeController.class);
	
	@Autowired
	private AtividadeRepository ar;
	
	// LISTAR ATIVIDADES

	@RequestMapping("/")
	public ModelAndView listaAtividades() {
		ModelAndView mv = new ModelAndView("index");
		 Iterable<Atividade> todasAtividades = ar.findAll();
	        List<Atividade> atividadesPendentes = new ArrayList<>();
	        List<Atividade> atividadesConcluidas = new ArrayList<>();
	        
	        for (Atividade atividade : todasAtividades) {
	            if (atividade.isFeita()) {
	                atividadesConcluidas.add(atividade);
	            } else {
	                atividadesPendentes.add(atividade);
	            }
	        }
	        
	        mv.addObject("atividadesPendentes", atividadesPendentes);
	        mv.addObject("atividadesConcluidas", atividadesConcluidas);
	        return mv;
	}
	
	// MÉTODOs QUE ATUALIZAM PROFESSOR
	// FORMULÁRIO EDIÇÃO DE PROFESSOR

	@RequestMapping(value = "/editar-atividade", method = RequestMethod.GET)
	public ModelAndView editarAtividade(long id) {
		Atividade atividade = ar.findById(id);
		ModelAndView mv = new ModelAndView("atividade/update-atividade");
		mv.addObject("atividade", atividade) ;
		return mv;
	}
	
	
	@RequestMapping(value = "/editar-atividade", method = RequestMethod.POST)
	public String updateAtividade(@Valid Atividade atividade, BindingResult result, RedirectAttributes attributes) {
		ar.save(atividade);
		attributes.addFlashAttribute("success", "Atividade alterada com sucesso!");

		long idlong = atividade.getId();
		String id = "" + idlong;
		return "redirect:/atividades";
		
	}
	
		
		
	    @PostMapping("/atividade/{id}/toggleFeita")
	    @ResponseBody
	    public String toggleFeita(@PathVariable long id) {
	        Atividade atividade = ar.findById(id);
	        logger.info("Atividade encontrada: " + atividade.getNome());
	        atividade.setFeita(!atividade.isFeita());
	        ar.save(atividade);
	        return "success";
 
	    }
	    
		// DELETAR ATIVIDADE PELO NOME
		@RequestMapping("/deletarAtividadeIndex")
		public String deletarAtividadeIndex(String nome) {
			Atividade atividade = ar.findByNome(nome);
			Materia materia = atividade.getMateria();
			String id = "" + materia.getId();

			ar.delete(atividade);

			return "redirect:/";
		}
	    
	


}
