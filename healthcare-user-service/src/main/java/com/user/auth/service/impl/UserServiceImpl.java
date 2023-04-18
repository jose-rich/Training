package com.user.auth.service.impl;

import com.user.auth.dto.PaginatedUserResponse;
import com.user.auth.dto.UserDto;
import com.user.auth.entity.UserEntity;
import com.user.auth.mapper.UserMapper;
import com.user.auth.repository.UserRepository;
import com.user.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
//@Profile(value = { "dev", "prod" })
public class UserServiceImpl implements UserService {

	@Lazy
	@Autowired
	public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, UserMapper userMapper) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userName);
		if (optionalUserEntity.isPresent()) {
			UserEntity userEntity = optionalUserEntity.get();
			return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
		}
		return null;
	}

	/*@Override
	public String extractJWT(AuthenticationRequest userAuthenticateRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					userAuthenticateRequest.getUserName(), userAuthenticateRequest.getPassword()));
		} catch (BadCredentialsException exception) {
			logger.error("Incorrect UserName or Password");
			return null;
		}

		final UserDetails userDetails = loadUserByUsername(userAuthenticateRequest.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		return jwt;
	}*/

	@Override
	@CachePut(value = "user", key = "#userDto.userId")
	public UserDto addUser(UserDto userDto) {
		if (Objects.nonNull(userDto)) {
			UserEntity userEntity = userMapper.toUserEntity(userDto);
			userRepository.save(userEntity);
			//UserEntity userEntity = userDao.addNewUser(UserDataUtil.convertToUserEntity(userDto));
			//userIndexService.save(UserIndex.convertFromUserEntity(userEntity));
			return userMapper.toUserDto(userEntity);
		}
		logger.error("Error in Addition of new record - Empty Record Can't be Added");
		return null;
	}

	@Override
	public PaginatedUserResponse getAllUsers(Pageable pageable) {
		Page<UserEntity> userEntities = userRepository.findAll(pageable);
		return PaginatedUserResponse.builder().numberOfItems(userEntities.getTotalElements())
				.numberOfPages(userEntities.getTotalPages())
				.userDtoList(userMapper.toUserDtoList(userEntities.getContent()))
				.build();
	}

	@Override
	public UserDto getUserById(long userId) {
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if (Objects.nonNull(userEntity)) {
			return userMapper.toUserDto(userEntity.get());
		} else {
			return null;
		}
	}

	@Override
	public String deleteById(long userId) {
		try {
			Optional<UserEntity> optionalEntity = userRepository.findById(userId);
			if (!optionalEntity.isEmpty() && optionalEntity.isPresent()) {
				userRepository.deleteById(userId);
			} else {
				logger.error("Record with User Id " + userId + " is not found for Deletion");
				return "Record with User Id " + userId + " is not found for Deletion";
			}
		} catch (Exception exception) {
			logger.error("Error in Deletion of the record with User Id " + userId + exception);
			return "Record with User Id  " + userId + "is not Deleted";
		}

		return "Record with User Id " + userId + " Deleted Successfully!!!";
	}

	@Override
	public UserDto updateUser(UserDto userDto) {

		try {
			Optional<UserEntity> existingUserEntity = userRepository.findById(userDto.getUserId());
			if (Objects.nonNull(existingUserEntity)) {
				UserEntity userEntity = userMapper.toUserEntity(userDto);
				userEntity.setUserId(existingUserEntity.get().getUserId());
				logger.info("User Record with ID " + userEntity.getUserId() + " is Updated");
				userRepository.save(userEntity);
				return userMapper.toUserDto(userEntity);
			} else {
				logger.error("No Record is found for Updation");
				return null;
			}
		} catch (Exception exception) {
			logger.error("Error in Updation of the record - Empty Record Can't be Updated");
			return null;
		}
	}

}
