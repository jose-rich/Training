package com.user.auth.dto;

import com.user.auth.constant.Role;
import com.user.auth.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

	private static final long serialVersionUID = -7134195975158556179L;

	private Long userId;
	private String firstName;
	private String lastName;
	private String email;

	@JsonIgnore
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	private Date joinedDate;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

}
