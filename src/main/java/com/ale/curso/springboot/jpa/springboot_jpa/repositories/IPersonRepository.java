package com.ale.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ale.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface IPersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);
}
