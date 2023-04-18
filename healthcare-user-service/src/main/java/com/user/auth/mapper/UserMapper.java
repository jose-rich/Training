package com.user.auth.mapper;

import com.user.auth.dto.UserDto;
import com.user.auth.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper  {

    UserDto toUserDto(UserEntity userEntity);

    UserEntity toUserEntity (UserDto userDto);

    List<UserDto> toUserDtoList(List<UserEntity> userEntityList);

    List<UserEntity> toUserEntityList (List<UserDto> userDtoList);


}
