package telran.spring.service;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class GreetingsServiceImpl implements GreetingsService {
   Map<Long, String> greetingsMap = Map.of(123l, "David", 124l, "Sara", 125l, "Rivka");
	@Override
	public String getGreetings(long id) {
		String name = greetingsMap.getOrDefault(id, "Unknown Guest");
		return "Hello, " + name;
	}

}
