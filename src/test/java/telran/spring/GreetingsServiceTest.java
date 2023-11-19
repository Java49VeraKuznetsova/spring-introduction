package telran.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import telran.exceptions.NotFoundException;
import telran.spring.service.GreetingsService;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//defining order of the tests by @Order

class GreetingsServiceTest {
@Autowired	
GreetingsService greetingsService;
Person personNormal = new Person(123, "Vasya", "Rehovot", "vasya@gmail.com",
		"054-1234567");
Person personNormalUpdated = new Person(123, "Vasya", "Lod", "vasya@gmail.com",
		"054-1234567");
Person personNotFound = new Person(500, "Vasya", "Lod", "vasya@gmail.com",
		"054-1234567");
Person personNormal2 = new Person (131, "Nir", "Tel-Aviv", "nir@gmail.com",
		"058-1234567");
List <Person> listByCity = new ArrayList<>();
String city = "Tel-Aviv";


@BeforeAll
static void deleteFile() throws IOException {
	Files.deleteIfExists(Path.of("test.data"));
}
	@Test
	@Order(1)
	void loadApplicationContextTest() {
		assertNotNull(greetingsService);
	}
	@Test
	@Order(2)
	void addPersonNormalTest() {
		assertEquals(personNormal, 
				greetingsService.addPerson(personNormal));
	}
	@Test
	@Order(3)
	void addPersonAlreadyExistsTest() {
		assertThrowsExactly(IllegalStateException.class, () -> greetingsService.addPerson(personNormal));
	}
    @Test
    @Order(4)
    void updatePersonTest() {
    	assertEquals(personNormalUpdated, 
    			greetingsService.updatePerson(personNormalUpdated));
    }
    @Test
    @Order(5)
    void getPersonTest() {
    	assertEquals(personNormalUpdated, 
    			greetingsService.getPerson(123));
    }
    @Test
    @Order(6)
    void getPersonNotFound() {
    	assertNull(greetingsService.getPerson(500));
    }
    @Test
    @Order(7)
    void updateNotExistsTest() {
    	assertThrowsExactly(NotFoundException.class,
    			() -> greetingsService.updatePerson(personNotFound));
    }
    @Test
    @Order(8)
    void addPersonNormal2Test() {
    	assertEquals(personNormal2, greetingsService.addPerson(personNormal2));
    }
    @Test
    @Order(9)
    void getPersonByCityTest() {
    	listByCity.add(personNormal2);
    	assertEquals(listByCity, greetingsService.getPersonsByCity(city));
    }
    @Test
    @Order(10)
    void deletePersonTest() {
    	assertEquals(personNormal2, greetingsService.deletePerson(131));
    }
       @Test
    @Order(11)
    void deleteNotExistsTest() {
    	assertThrowsExactly(NotFoundException.class,
    			() -> greetingsService.deletePerson(131));
    }
       @Test
       @Order(12)
       void getPersonByCityEmpty() {
    	 listByCity.remove(personNormal2);
    	 assertEquals(listByCity, greetingsService.getPersonsByCity(city));
       }
    @Test
    @Order(13)
    void getGreetingsTest() {
    	String helloMessage = "Hello, Vasya";
    	assertEquals (helloMessage, greetingsService.getGreetings(123));
    }
    @Test
    @Order(14)
    void getGreetingsUnknownTest() {
    	String helloMessage = "Hello, unknown guest";
    	assertEquals (helloMessage, greetingsService.getGreetings(500));
    }
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    @Test
    @Order(15)
    void persistenceTest() {
    	assertEquals(personNormalUpdated,greetingsService.getPerson(123));
    	
    }
    @Test     //on CW61
    @Order(16)
    void getPersonsByCityTest2() {
    	List<Person> expected = List.of(personNormalUpdated);
    	assertIterableEquals(expected, greetingsService.getPersonsByCity("Lod"));
   assertTrue(greetingsService.getPersonsByCity("Rehovot").isEmpty());
    }
    @Test  //on CW61
    @Order(17)
       void deleteTest2() {
    	assertEquals(personNormalUpdated, greetingsService.deletePerson(123));
    	assertTrue(greetingsService.getPersonsByCity("Lod").isEmpty());
    	assertTrue(greetingsService.getPersonsByCity("Rehovot").isEmpty());
    	assertThrowsExactly(NotFoundException.class, () -> greetingsService.deletePerson(123));
    }
    
}
