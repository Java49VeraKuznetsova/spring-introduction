package telran.spring.service;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class GreetingsServiceImpl implements GreetingsService {
   Map<Long, String> greetingsMap = new HashMap<>(Map.of(123l, "David", 124l, "Sara", 125l, "Rivka"));
   
   Person person1 = new Person(123l, "David", "Rehovot");
   Person person2 = new Person (124l, "Sara", "Tel-Aviv");
   Person person3 = new Person(125l, "Rivka", "London");
 
   Map<Long, Person> greetingsMapPerson = new HashMap<>(Map.of(123l, person1, 124l, person2, 125l, person3));
   
  // List<Person> greetingsMapPerson1 = new ArrayList<>(List.of(person1, person2, person3));
   @Override
	public String getGreetings(long id) {
		String name = greetingsMap.getOrDefault(id, "Unknown Guest");
		return "Hello, " + name;
	}
   
	@Override
	public Person getPerson(long id) {
		Person personUnknown = new Person (0l, "Unknown Guest", "now city");
		Person personNew = greetingsMapPerson.getOrDefault(id,personUnknown);
		return personNew;
		//Vladimir
	//	Person person = personsMap.get(id);
		//if person == null - IllegalStateException
	}
	
	@Override
	public String addName(IdName idName) {
		String name = greetingsMap.putIfAbsent(idName.id(), idName.name());
		if(name != null ) {
			throw new IllegalStateException(idName.id() + " name with given ID already exists");
		}
		return idName.name();
	}
	
	@Override
	public Person addPerson(Person person) {
		// TODO Auto-generated method stub
		Person personNew =  greetingsMapPerson.putIfAbsent(person.id(),person);
		if(personNew != null ) {
			throw new IllegalStateException(person.id() + " name with given ID already exists");
		}
		return person;
	}
	
	
	@Override
	public String deleteName(long id) {
		String name = greetingsMap.remove(id);
		if (name == null) {
			throw new IllegalStateException(id + " not found");
		}
		return name;
	}
	
	@Override
	public Person deletePerson(long id) {
		Person person = greetingsMapPerson.remove(id);
		if (person == null) {
			throw new IllegalStateException(id + " not found");
		}
		// TODO Auto-generated method stub
		return person;
	}
	@Override
	public String updateName(IdName idName) {
	    if(!greetingsMap.containsKey(idName.id())) {
	    	throw new IllegalStateException(idName.id() + "  not found");
	    }
	    greetingsMap.put(idName.id(), idName.name());
		return idName.name();
	}
	
	@Override
	public Person updatePerson(Person person) {
		
		if (!greetingsMapPerson.containsKey(person.id())) {
			 throw new IllegalStateException (person.id() + "  not found");
		}
		greetingsMapPerson.put(person.id(), person);
		return person;
	}


	@Override
	public List<Person> getPersonsByCity(String city) {
		// TODO Auto-generated method stub
		List <Person> personsByCity = new ArrayList<>();
	  greetingsMapPerson.forEach((k, v) -> {
		 if( v.toString().contains(city)) {
				personsByCity.add((Person) v);
		 }
	    
	    });
	  /* from Webinar
		List <Person> personsByCity = 
				greetingsMapPerson.values()
				.stream()
				.filter (person -> person.city().equals(city))
				.toList();
		
		*/
		return personsByCity;
	}

	
	
}
