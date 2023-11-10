package telran.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import telran.exceptions.NotFoundException;
import telran.spring.Person;
@Service
public class GreetingsServiceImpl implements GreetingsService {
    Map<Long, Person> greetingsMap = new HashMap<>();
	@Override
	public String getGreetings(long id) {
		
		Person person =  greetingsMap.get(id);
		String name = person == null ? "Unknown guest" : person.name();
		return "Hello, " + name;
	}
	
	@Override
	public Person getPerson(long id) {
		
		return greetingsMap.get(id);
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
		 return person;
	}
	@Override
	public Person deletePerson(long id) {
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		return greetingsMap.remove(id);
	}
	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		greetingsMap.put(id, person);
		return person;
	}

}