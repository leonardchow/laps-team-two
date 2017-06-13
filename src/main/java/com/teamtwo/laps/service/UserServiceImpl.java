package com.teamtwo.laps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.teamtwo.laps.model.User;
import com.teamtwo.laps.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

	@Override
	public User authenticate(String uname, String pwd) {
		// TODO Auto-generated method stub
		User u = userRepository.findUserByNamePwd(uname, pwd);
		return u;
	}



	/* (non-Javadoc)
	 * @see edu.iss.cats.service.UserService#findAllUsers()
	 */


}
