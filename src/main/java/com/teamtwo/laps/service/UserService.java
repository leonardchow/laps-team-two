package com.teamtwo.laps.service;



import com.teamtwo.laps.model.User;





public interface UserService {



	User authenticate(String uname, String pwd);
	


}