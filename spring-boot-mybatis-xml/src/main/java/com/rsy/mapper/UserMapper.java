package com.rsy.mapper;

import com.rsy.model.User;

public interface UserMapper {
	
	User getOne(String id);

	void insert(User user);

}