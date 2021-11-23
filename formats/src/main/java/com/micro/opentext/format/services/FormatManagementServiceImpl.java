package com.micro.opentext.format.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.opentext.format.model.DmFormat;
import com.micro.opentext.format.repository.FormatRepository;
@Service(value="userService")
public class FormatManagementServiceImpl implements FormatManagementService {
	@Autowired
    private FormatRepository userRepository;
	@Override
	public ArrayList<DmFormat> getAllUsers() {
		// TODO Auto-generated method stub
		//return userRepository.findAll();
		return null;
	}

	@Override
	public DmFormat getUser(Long userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}

	@Override
	public boolean updateUser(DmFormat user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createUser(DmFormat user) {
		// TODO Auto-generated method stub
		return false;
	}

}
