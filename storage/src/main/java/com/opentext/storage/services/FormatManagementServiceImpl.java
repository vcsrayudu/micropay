package com.opentext.storage.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opentext.storage.model.DmFormat;
import com.opentext.storage.repository.FormatRepository;


@Service(value="formatService")
public class FormatManagementServiceImpl implements FormatManagementService {
	@Autowired
    private FormatRepository formatRepository;
	@Override
	public ArrayList<DmFormat> getAllFormats() {
		// TODO Auto-generated method stub
		//return userRepository.findAll();
		return null;
	}

	@Override
	public DmFormat getFormat(Long formatId) {
		// TODO Auto-generated method stub
		return formatRepository.findById(formatId);
	}

	@Override
	public boolean updateFormat(DmFormat user) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean createFormat(DmFormat format) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFormat(Long formatId) {
		// TODO Auto-generated method stub
		return false;
	}

}
