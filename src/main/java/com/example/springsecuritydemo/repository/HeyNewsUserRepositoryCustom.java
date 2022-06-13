package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.enums.UserType;

import java.util.List;

public interface HeyNewsUserRepositoryCustom {

    List<User> getHeyNewsUsers(UserType heyNewsUserType, Long lastRowId, Integer pageSize);

}
