package com.Agenda.Agenda.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import jakarta.validation.constraints.NotEmpty;

@Entity
public class Materia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	private String nome;  
	
	@NotEmpty
	private String sala;
	
	@NotEmpty
	private String dia_horario;
	
	@ManyToOne
	private Professor professor;
	
	@OneToMany(mappedBy = "materia", cascade = CascadeType.REMOVE)
	private List<Atividade> atividades;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getDia_horario() {
		return dia_horario;
	}

	public void setDia_horario(String dia_horario) {
		this.dia_horario = dia_horario;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	
	
	
	

	
	
	
}
