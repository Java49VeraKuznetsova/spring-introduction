package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.controller.GreetingsController;
import telran.spring.service.GreetingsService;

@WebMvcTest
public class GreeingsControllerTest {
	@Autowired //this annotation allows dependency injection inside following field
  GreetingsController controller;
	@MockBean
	GreetingsService greetingsService;
	@Autowired
	MockMvc mockMvc; //imitator of  Web Server
	Person personNormal = new Person(123, "Vasya",
			"Rehovot", "vasya@gmail.com", "054-1234567");
	Person personNormalUpdate = new Person(123, "Petya",
			"Rehovot", "vasya@gmail.com", "054-1234567");
	Person personWrongPhone = new Person (124, "Vasya",
			"Rehovot", "vasya@gmail.com", "54-1234567");
	Person personWrongMail = new Person(125, "Vasya",
			"Rehovot", "@gmail.com", "054-1234567");
	//Person personWrongID = new Person("abc", "Vasya","Rehovot", "vasya@gmail.com", "054-1234567");
	Person personWrongName = new Person(126, "V",
			"Rehovot", "vasya@gmail.com", "054-1234567");
	Person personWrongCity = new Person(127, "Vasya",
			"", "vasya@gmail.com", "054-1234567");
	
	@Autowired
	ObjectMapper objectMapper;
  @Test
  void applicationContextTest() {
	  assertNotNull(controller);
	  assertNotNull(greetingsService);
	  assertNotNull(mockMvc);
	  assertNotNull(objectMapper);
  }
  @Test
  void normalFlowAddPerson() throws Exception {
	  mockMvc.perform(post("http://localhost:8080/greetings")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personNormal)))
	  .andDo(print())
	  .andExpect(status().isOk());
  }
  @Test
  void AddPersonWrongPhone() throws Exception {
	  String response = mockMvc.perform(post("http://localhost:8080/greetings")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personWrongPhone)))
	  .andDo(print())
	  .andExpect(status().isBadRequest())
	  .andReturn().getResponse()
	  .getContentAsString();
	  assertEquals("not Israel mobile phone", response);
  }
  @Test
  void AddPersonWrongCity() throws Exception {
	  String response = mockMvc.perform(post("http://localhost:8080/greetings")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personWrongCity)))
	  .andDo(print())
	  .andExpect(status().isBadRequest())
	  .andReturn().getResponse()
	  .getContentAsString();
	   assertEquals("must not be empty", response);
  }
  @Test
  void AddPersonWrongName() throws Exception {
	  String response = mockMvc.perform(post("http://localhost:8080/greetings")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personWrongName)))
	  .andDo(print())
	  .andExpect(status().isBadRequest())
	  .andReturn().getResponse()
	  .getContentAsString();
	   assertEquals("Wrong name structure ", response);
  }
  @Test
  void AddPersonWrongMail() throws Exception {
	  String response = mockMvc.perform(post("http://localhost:8080/greetings")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personWrongMail)))
	  .andDo(print())
	  .andExpect(status().isBadRequest())
	  .andReturn().getResponse()
	  .getContentAsString();
	   assertEquals("must be a well-formed email address", response);
  }
  @Test
  void normalFlowUpdatePerson() throws Exception {
	  mockMvc.perform(put("http://localhost:8080/greetings")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personNormalUpdate)))
	  .andDo(print())
	  .andExpect(status().isOk());
  }
  @Test
  void normalFlowGetPerson() throws Exception {
	  mockMvc.perform(get("http://localhost:8080/greetings/id/123")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personNormal)))
	  .andDo(print())
	  .andExpect(status().isOk());
  }
  @Test
  void normalFlowGetGreetings() throws Exception {
	  mockMvc.perform(get("http://localhost:8080/greetings/123")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personNormalUpdate))) //????
	  .andDo(print())
	  .andExpect(status().isOk());
  }
  @Test
  void normalFlowDeletePerson() throws Exception {
	  mockMvc.perform(delete("http://localhost:8080/greetings/123")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(personNormal))) 
	  .andDo(print())
	  .andExpect(status().isOk());
  }
}
