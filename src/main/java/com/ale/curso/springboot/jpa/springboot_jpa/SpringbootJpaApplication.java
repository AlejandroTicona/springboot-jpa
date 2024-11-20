package com.ale.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.ale.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.ale.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.ale.curso.springboot.jpa.springboot_jpa.repositories.IPersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private IPersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		personalizedQueriesConcatUpperAndLowerCase();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {
		System.out.println("========================== CONSULTAS MINUSCULAS MAYUSCULAS =======================");
		System.out.println("================== MINUSCULA ====================");
		List<String> min = repository.findAllFullNameConcatLower();
		min.forEach(System.out::println);
		System.out.println("================== MAYUSCULA ====================");
		List<String> may = repository.findAllFullNameConcatUpper();
		may.forEach(System.out::println);
		System.out.println("================== DIF CASE ====================");
		List<Object[]> dif = repository.findAllPersonDataListCase();
		dif.forEach(reg -> System.out.println("ID = " + reg[0] + " nombre = " + reg[1] + " apellido = " + reg[2]
				+ " Lenguaje de Prog = " + reg[3]));
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct() {
		System.out.println("CONSULTA POR CAMPOS PERSONALIZADOS LIST ==========");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out.println("ID = " + reg[0] + " nombre = " + reg[1] + " apellido = " + reg[2]
				+ " Lenguaje de Prog = " + reg[3]));
		System.out.println("======================CONSULTAS ALL NAMES==================");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);
		System.out.println("======================CONSULTAS DISTINCT NAMES==================");
		List<String> namesD = repository.findAllNamesDistinct();
		namesD.forEach(System.out::println);
		System.out.println("======================CONSULTAS DISTINCT LENGUAJES==================");
		List<String> leng = repository.findAllProgrammingLanguagesDistinct();
		leng.forEach(System.out::println);
		System.out.println("======================CUANTOS LENGUAJES HAY==================");
		Long c = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println(c);
	}

	@Transactional(readOnly = true)
	public void findOne() {
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findOneName("Diego");
		// if (!optionalPerson.isEmpty()) {
		// person = optionalPerson.get();
		// }
		// System.out.println(person);
		repository.findByNameContaining("car").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void list() {
		// List<Person> persons = (List<Person>) repository.findAll();
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Laura");
		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personValues = repository.obtenerPersonDataByProgrammingLanguage("Java");
		personValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});

	}

	@Transactional
	public void create() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el nombre: ");
		String name = sc.nextLine();
		System.out.println("Ingrese el apellido: ");
		String lastname = sc.nextLine();
		System.out.println("Ingrese el lenguaje de programacion");
		String programmingLanguage = sc.nextLine();
		sc.close();

		Person person = new Person(null, name, lastname, programmingLanguage);
		Person personNew = repository.save(person);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}

	@Transactional
	public void update() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona: ");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		// optionalPerson.ifPresent(person -> {
		if (optionalPerson.isPresent()) {
			Person personDB = optionalPerson.orElseThrow();
			System.out.println(personDB);
			System.out.println("Ingrese el lenguaje de programacion: ");
			String programmingLanguage = sc.next();
			personDB.setProgrammingLanguage(programmingLanguage);
			Person personUpdated = repository.save(personDB);
			System.out.println(personUpdated);
		} else {
			System.out.println("El usuario no esta presente, no existe");
		}

		// });
		sc.close();
	}

	@Transactional
	public void delete() {
		repository.findAll().forEach(System.out::println);
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = sc.nextLong();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);

		sc.close();
	}

	@Transactional
	public void delete2() {
		repository.findAll().forEach(System.out::println);
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(repository::delete,
				() -> System.out.println("Lo sentimos no existe la persona con ese id"));

		repository.findAll().forEach(System.out::println);

		sc.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {
		System.out.println(
				"=================== CONSULTA POR OBJETO PERSONA Y LENGUAJE DE PROGRAMACION ====================");
		List<Object[]> personRegs = repository.findAllMixPerson();

		personRegs.forEach(reg -> {
			System.out.println("Programming language = " + reg[1] + ", person= " + reg[0]);
		});

		System.out.println("Consulta que puebla y devuelve objeto entity de una interfaz personalizada");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);

		System.out.println("CONSULTA QUE PUEBLA Y DEVUELVE OBJETO DTO DE UNA CLASE PERSONALIZADA");
		List<PersonDto> personDto = repository.findAllPersonDto();
		personDto.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {
		Scanner sc = new Scanner(System.in);
		System.out.println("=================== CONSULTA SOBRE EL NOMBRE CON ID ====================");
		System.out.println("Ingresa el id: ");
		Long id = sc.nextLong();
		sc.close();
		System.out.println("=========== MOSTRANDO SOLO EL NOMBRE ==========");
		String name = repository.getNameById(id);
		System.out.println(name);
		System.out.println("=========== MOSTRANDO SOLO EL ID ==========");

		Long idDb = repository.getIdById(id);
		System.out.println(idDb);
		System.out.println("=========== MOSTRANDO TODO EL NOMBRE ==========");

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		// System.out.println("consulta por campos personalizados por el id");
		// Optional<Object[]> optionalReg = repository.obtenerPersonDataById(id);
		// if (optionalReg.isPresent()) {
		// Object[] personReg = (Object[])optionalReg.get();
		// System.out
		// .println("ID = " + personReg[0] + " nombre = " + personReg[1] + " apellido =
		// " + personReg[2]
		// + " Lenguaje de Prog = " + personReg[3]);
		// }

		System.out.println("CONSULTA POR CAMPOS PERSONALIZADOS LIST ==========");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out.println("ID = " + reg[0] + " nombre = " + reg[1] + " apellido = " + reg[2]
				+ " Lenguaje de Prog = " + reg[3]));
	}
}
