package com.sargentdisc.rodrigues.carlos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sargentdisc.rodrigues.carlos.domain.userfile.UserFile;
import com.sargentdisc.rodrigues.carlos.domain.userfile.UserFileException;
import com.sargentdisc.rodrigues.carlos.domain.userfile.UserFileRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1/userfile", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class UserFileController {

	
	@Autowired
	private UserFileRepository repository;
	
	@RequestMapping(value = "/{id}")
	@ApiOperation(value = "Get the UserFile from database")
	public ResponseEntity<UserFile> get(@PathVariable("id") Long id) throws UserFileException{
		final UserFile userFile = repository.findOne(id);
		if (userFile == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UserFile>(userFile, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "Get all UserFiles from database")
	public @ResponseBody List<UserFile> getAll() throws UserFileException{
		return repository.findAll();
		/*List<UserFile> files = repository.findAll();
		if (files.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserFile>>(files, HttpStatus.OK);
		*/
	}		
}
