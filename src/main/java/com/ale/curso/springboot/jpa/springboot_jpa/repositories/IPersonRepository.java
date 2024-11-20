package com.ale.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ale.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.ale.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface IPersonRepository extends CrudRepository<Person, Long> {

    @Query("select p.id, UPPER(p.name), LOWER(p.lastname), p.programmingLanguage from Person p")
    List<Object[]> findAllPersonDataListCase();

    @Query("select LOWER(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatLower();

    // @Query("select CONCAT(p.name , ' ', p.lastname)from Person p")
    @Query("select UPPER(CONCAT(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatUpper();

    @Query(" select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllProgrammingLanguageDistinctCount();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguagesDistinct();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinct();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select new com.ale.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllObjectPersonPersonalized();

    @Query("select p.name from Person p where id = ?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where id = ?1")
    Long getIdById(Long id);

    @Query("select concat(p.name , ' ', p.lastname)as fullname from Person p where id = ?1")
    String getFullNameById(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id = ?1")
    Object[] obtenerPersonDataById(Long id);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerPersonDataList(String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.name like %?1%")
    List<Object[]> onesLike(String name);
}
