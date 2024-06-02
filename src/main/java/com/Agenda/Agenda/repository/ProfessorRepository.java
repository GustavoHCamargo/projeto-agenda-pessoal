package com.Agenda.Agenda.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Agenda.Agenda.model.Materia;
import com.Agenda.Agenda.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, String> {
	Professor findByid(long id);
	List<Professor> findByNome(String nome);
}
