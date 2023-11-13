package telran.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Value("${app.file.name:persons.data}")
    String fileName;
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
		log.info("restored from file");
		
	}

	@Override
	public void restore(String fileName) {
		//TODO restoring from file using ObjectInputStream
		restore(fileName);
		
		
	}
	@PostConstruct
	void restoreFromFile() {
		
	}
	@PreDestroy
	void saveToFile() {
		save(fileName);
	}

}