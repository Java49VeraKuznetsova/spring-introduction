package telran.spring.controller;


import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import telran.spring.service.GreetingsService;


@RestController
@RequestMapping ("greetings")
@RequiredArgsConstructor
public class GreetingsController {
	final GreetingsService greetingsService;
	@GetMapping("{id}")
    String getGreetings(@PathVariable long id) {
    	
    	return greetingsService.getGreetings(id);
    }
}
