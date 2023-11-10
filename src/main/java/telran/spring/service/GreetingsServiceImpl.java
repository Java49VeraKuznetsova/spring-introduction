package telran.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.spring.Person;
@Service
@Slf4j
public class GreetingsServiceImpl implements GreetingsService {
    Map<Long, Person> greetingsMap = new HashMap<>();
	@Override
	public String getGreetings(long id) {
		log.info("service method getGreetings received id {}", id);
		Person person =  greetingsMap.get(id);
		String name = person == null ? "Unknown guest" : person.name();
		return "Hello, " + name;
	}
	
	@Override
	public Person getPerson(long id) {
		log.info("service method getPerson received id {}", id);
		return greetingsMap.get(id);
	}
	@Override
	public List<Person> getPersonsByCity(String city) {
		log.debug("service method getPersonsByCity received city {}", city);
		return greetingsMap.values().stream()
				.filter(p -> p.city().equals(city))
				.toList();
	}
	@Override
	public Person addPerson(Person person) {
		log.info("service method addPerson received {}", person);
		long id = person.id();
		if (greetingsMap.containsKey(id) ){
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		 greetingsMap.put(id, person);
		 return person;
	}
	@Override
	public Person deletePerson(long id) {
		log.info("service method deletePerson received id {}", id);
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		return greetingsMap.remove(id);
	}
	@Override
	public Person updatePerson(Person person) {
		log.info("service method updatePerson received {}", person);
		long id = person.id();
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		greetingsMap.put(id, person);
		return person;
	}

}