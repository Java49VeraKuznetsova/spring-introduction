package telran.spring.service;

import java.util.List;

public interface GreetingsService {
 String getGreetings(long id);
 //TODO update For HW #57
 Person getPerson (long id);
 Person addPerson (Person person);
 List<Person> getPersonsByCity(String city);
 Person updatePerson(Person person);
 String addName (IdName idName);
 String deleteName (long id);
 String updateName(IdName idName);
 Person deletePerson(long id);
 
}
