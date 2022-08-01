package com.rsy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rsy.model.User;
import com.rsy.mapper.UserMapper;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public int login(@RequestBody User user) {
        User userInDb = userMapper.getOne(user.getName());
        if (user.equals(userInDb)) {
            return 200;
        }
        return 500;
    }
    
    @RequestMapping(value="/newUser", method= RequestMethod.POST)
    public int newUser(@RequestBody User user) {
        // if the current userName already existed
        User userInDb = userMapper.getOne(user.getName());
        if (user.equals(userInDb)){
            return 500;
        }
        userMapper.insert(user);
        return 200;
    }
}