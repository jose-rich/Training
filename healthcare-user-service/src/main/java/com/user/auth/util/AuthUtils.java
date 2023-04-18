package com.user.auth.util;

import com.user.auth.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static UserEntity getCurrentUser() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
