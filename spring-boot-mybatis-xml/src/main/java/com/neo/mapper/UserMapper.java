package com.neo.mapper;

import java.util.List;

import com.neo.model.User;

public interface UserMapper {
	
	User getOne(String id);

	void insert(User user);

}