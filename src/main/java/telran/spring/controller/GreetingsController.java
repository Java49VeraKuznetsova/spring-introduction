package telran.spring.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import telran.spring.service.GreetingsService;
import telran.spring.service.IdName;
import telran.spring.service.Person;


@RestController
@RequestMapping ("greetings")
@RequiredArgsConstructor
public class GreetingsController {
	final GreetingsService greetingsService;

	
	//@GetMapping("{id}")
    String getGreetings(@PathVariable long id) {
    	
    	return greetingsService.getGreetings(id);
    }
	
	@GetMapping ("id/{id}")
	Person getPerson (@PathVariable long id) {

		return greetingsService.getPerson(id);
	}
	
	//TODO update following control end point methods for HW #57 according to updated service
	//@PostMapping()
	String addName(@RequestBody IdName idName) {
	return greetingsService.addName(idName);
	
}
@PostMapping()
Person addPerson(@RequestBody Person person) {
return greetingsService.addPerson(person);

}
//@PutMapping
String updateName (@RequestBody IdName idName) {
	return greetingsService.updateName(idName);
}

@PutMapping
Person updatePerson (@RequestBody Person person) {
	return greetingsService.updatePerson(person);
}
//@DeleteMapping("{id}")
String deleteName(@PathVariable long id) {
	return greetingsService.deleteName(id);
}
@DeleteMapping("{id}")
Person deletePerson (@PathVariable long id) {
	return greetingsService.deletePerson(id);
}

@GetMapping("{city}") 
	List<Person> getPersonsByCity(@PathVariable String city) {
		return greetingsService.getPersonsByCity(city);
	}

//TODO
//end points for getting person by ID and getting persons by city
//see service
}
