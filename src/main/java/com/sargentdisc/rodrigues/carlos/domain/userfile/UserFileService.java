package com.sargentdisc.rodrigues.carlos.domain.userfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sargentdisc.domain.model.userfile.UserFile;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
		UserFileException.class })
public class UserFileService {
	
	@Autowired
	UserFileRepository repository;
	
	@Transactional(readOnly = false)
	public UserFile save(UserFile userFile) throws UserFileException{
		return repository.save(userFile);
	}	

}
