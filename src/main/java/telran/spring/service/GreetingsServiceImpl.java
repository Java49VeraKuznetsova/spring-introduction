package telran.spring.service;

import java.util.*;

import java.io.*;
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
    @Value("${app.unknown.name:unknown guest}")
    String unknownName;
    @Value("${app.file.name:persons.data}")
    String fileName;
	@Override
	public String getGreetings(long id) {
		
		Person person =  greetingsMap.get(id);
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
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
			output.writeObject(new ArrayList<Person>(greetingsMap.values()));
			log.info("persons data have been saved");
		} catch (Exception e) {
			log.error("{}", e);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void restore(String fileName) {
		try(ObjectInputStream input =
				new ObjectInputStream(new FileInputStream(fileName))) {
			List<Person> employeesList = (List<Person>) input.readObject();
			employeesList.forEach(this::addPerson);
			log.info("restored from file");
		} catch (FileNotFoundException e) {
			log.warn("No file with data found");
		} catch (Exception e) {
			log.error("{}", e);
		}
		
		
	}
	@PostConstruct
	void restoreFromFile() {
		restore(fileName);
		
	}
	@PreDestroy
	void saveToFile() {
		save(fileName);
	}

}