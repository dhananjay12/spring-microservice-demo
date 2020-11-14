package com.mynotes.spring.cloud;

import com.mynotes.spring.cloud.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.mynotes.spring.cloud.exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserServiceRestController {

	private static final Logger log = LoggerFactory.getLogger(UserServiceRestController.class);

	private static List<User> userList = new ArrayList<>();
	static {
		userList.add(new User(1, "John", 24));
		userList.add(new User(2, "Jane", 22));
		userList.add(new User(3, "Max", 27));
		userList.add(new User(4, "Bob", 35));
		userList.add(new User(5, "Amy", 30));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUser(@PathVariable int id) {
		if (id < 0) {
			throw new ValidationException("Id cannot be less than 0");
		}
		User user = findUser(id);

		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/multiple")
	public ResponseEntity<?> getUsers(@RequestParam("ids") List<Integer> ids) {

		for (int i= 0 ; i<ids.size(); i++){
			log.info("Passed ids -> " + ids.get(i));
		}

		List<User> result =new ArrayList<>();

		for (int i= 0 ; i<ids.size(); i++){
			for(int j=0; j< userList.size(); j++){
				if(ids.get(i).equals(userList.get(j).getId())){
					result.add(userList.get(j));
					break;
				}
			}
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/headers")
	public ResponseEntity<?> multiValue(@RequestHeader MultiValueMap<String, String> headers) {
		headers.forEach((key, value) -> {
			log.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
		});

		return ResponseEntity.ok(headers);
	}

	private User findUser(int id) {
		return userList.stream().filter(user -> user.getId().equals(id)).findFirst()
				.orElseThrow(() -> new UserNotFoundException());
	}
}
