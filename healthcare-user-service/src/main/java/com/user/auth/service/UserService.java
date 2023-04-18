package com.user.auth.service;

import com.user.auth.dto.PaginatedUserResponse;
import com.user.auth.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	UserDetails loadUserByUsername(String userName);

	/*String extractJWT(AuthenticationRequest userAuthenticateRequest) throws Exception;*/

	UserDto addUser(UserDto userDto);

	PaginatedUserResponse getAllUsers(Pageable pageable);

	UserDto getUserById(long userId);

	String deleteById(long userId);

	UserDto updateUser(UserDto userDto);

}