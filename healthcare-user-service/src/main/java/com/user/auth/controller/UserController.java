package com.user.auth.controller;

import com.user.auth.dto.PaginatedUserResponse;
import com.user.auth.dto.UserDto;
import com.user.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Lazy
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	private final UserService userService;

	@PostMapping("/")
	public UserDto addUser(@RequestBody UserDto userDto) {
		return userService.addUser(userDto);
	}

	@GetMapping("/")
	public PaginatedUserResponse getAllUsers(Pageable pageable) {
		return userService.getAllUsers(pageable);
	}

	@GetMapping("/{userId}")
	public UserDto getUserById(@PathVariable Long userId) {
		return userService.getUserById(userId);
	}

	/*@GetMapping("/role/{roleId}")
	public List<UserDto> getUserByRoleId(@PathVariable long roleId) {
		return userService.getUserByRoleId(roleId);
	}*/

	@DeleteMapping("/{userId}")
	public String deleteById(@PathVariable long userId) {
		return userService.deleteById(userId);
	}

	@PutMapping("/")
	public UserDto updateUser(@RequestBody UserDto userDto) {
		return userService.updateUser(userDto);
	}

}
