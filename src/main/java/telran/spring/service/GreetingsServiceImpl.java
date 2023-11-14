package telran.spring.service;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
//import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.spring.Person;
@Service
@Slf4j
public class GreetingsServiceImpl implements GreetingsService {
    Map<Long, Person> greetingsMap = new HashMap<>();
    @Value("${app.greeting.message:Hello}")
    String greetingMessage;
    @Value("${app.unknown.name:unknown quest}")
    String unknownName;
    @Value("${app.file.name:persons.data:personsSpring.data}")
    String fileName;
  //  String fileName = "personsSpring.data";
	@Override
	public String getGreetings(long id) {
		
		Person person =  greetingsMap.get(id);
//		String name = person == null ? "Unknown guest" : person.name();
		String name = "";
		if (person == null) {
			name = unknownName;
			log.warn("person with id {} not found", id);
		} else {
			name = person.name();
			log.debug("person name is {}", name);
		}
		return String.format("%s, %s", greetingMessage, name);
	}
	
	@Override
	public Person getPerson(long id) {
		
		Person person = greetingsMap.get(id);
		if(person == null) {
			log.warn("person with id {} not found", id);
		} else {
			log.debug("persons with id {} exists", id);
		}
		return person;
	}
	@Override
	public List<Person> getPersonsByCity(String city) {
		
		return greetingsMap.values().stream()
				.filter(p -> p.city().equals(city))
				.toList();
	}
	@Override
	public Person addPerson(Person person) {
		long id = person.id();
		if (greetingsMap.containsKey(id) ){
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		 greetingsMap.put(id, person);
		 log.debug("person with id {} has been saved", id);
		 return person;
	}
	@Override
	public Person deletePerson(long id) {
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		Person person =  greetingsMap.remove(id);
		log.debug("person with id {} has been removed", person.id());
		return person;
	}
	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		greetingsMap.put(id, person);
		log.debug("person with id {} has been update", person.id());
		return person;
	}

	@Override
	public void save(String fileName) {
		// TODO saving persons data into ObjectOutputStream
		log.info("saved to file");
		ArrayList<Person> listPerson = (ArrayList<Person>) greetingsMap.values()
				.stream()
				.collect(Collectors.toList());
		if (Files.exists(Path.of(fileName))){
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			
			stream.writeObject(listPerson);
		} catch (Exception e) {
			//throw new RuntimeException(e);
			log.error("smth wrong to write into file " + fileName);
			throw new RuntimeException(String.format("smth wrong to write into file %s", fileName));
			
		}
		} else {
			log.error("no file for save "+fileName);
		}
		
		
	}

	@Override
	public void restore(String fileName)  {
		//TODO restoring from file using ObjectInputStream
		log.info("restored from file");
		//restore(fileName);
		if (Files.exists(Path.of(fileName))){
		try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
			List<Person> listPerson = (List<Person>) stream.readObject();
			
			listPerson
			.forEach(p -> addPerson(p));
		
					
		}  catch (Exception e) {
			log.error("smth wrong to read from file " + fileName);
			//throw new RuntimeException(e.toString());
			throw new RuntimeException(String.format("smth wrong to read from file %s", fileName));
		}
		} else {
			log.error("no file for restore "+fileName);
		}
		
		
	}
	@PostConstruct
	void restoreFromFile() {
		log.info("restore file "+fileName);
		restore(fileName);
	}
	@PreDestroy
	void saveToFile() {
		save(fileName);
		log.info("save file "+fileName);
	}

}