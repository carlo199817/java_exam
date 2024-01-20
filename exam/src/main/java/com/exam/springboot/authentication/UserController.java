package com.exam.springboot.authentication;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.springboot.*;

import org.springframework.web.bind.annotation.RequestHeader;


import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class UserController {
	
  @Autowired 
  PasswordEncoder encoder;	
  
  @Autowired
  AuthenticationManager authenticationManager;
  
  @Autowired
  UserRepository userRepository;
  
  @Autowired
  UserUpdateRepository userupdateRepository;
  
  @GetMapping("/login/")
  public ResponseEntity<?> authenticateUser(@RequestParam Map<String,String> allParams) {
	    String encodedString = Base64.getEncoder().encodeToString(allParams.get("password").getBytes()); 
	    UserDetailsImpl userDetails = null;
	    try {
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(allParams.get("username"),  encodedString));
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		
		    userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    	}
	    	catch (Exception e) {
	    	      return ResponseEntity.ok(new MessageResponse("Login Failed!"));
	    	}
	    return ResponseEntity.ok(new LoginResponse(userDetails.getfirstName(),userDetails.getlastName(),userDetails.getmiddleName(),userDetails.getbirthdate()));
  }
  
  @PatchMapping("/register/{id}/") 
  public ResponseEntity<?> patchRegister(@PathVariable("id") long id,@RequestParam Map<String,String> allParams) {


	  
	  User users = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

	   Date date = new Date();
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.TRADITIONAL_CHINESE);
	        date = formatter.parse(allParams.get("birthdate"));

	    	}
	    	catch (Exception e) {
	    
	    	}
	  String encodedString = Base64.getEncoder().encodeToString(allParams.get("password").getBytes()); 
      UserUpdate userupdate = new UserUpdate(id,allParams.get("username"),encoder.encode(encodedString),allParams.get("firstName"),allParams.get("lastName"),allParams.get("middleName"),date); 
      userupdateRepository.save(userupdate);
      return ResponseEntity.ok(new UserResponse(userupdate.getId(),userupdate.getUsername(),userupdate.getPassword(),userupdate.getfirstName(),userupdate.getlastName(),userupdate.getmiddleName(),userupdate.getbirthdate()));
  }
  
  @DeleteMapping("/register/{id}/") 
  public ResponseEntity<?> deleteRegister(@PathVariable("id") long id,@RequestParam Map<String,String> allParams) {

	  User users = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

	   Date date = new Date();
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	        String dateInString = "7-Jun-2013";
	        date = formatter.parse(dateInString);
	    	}
	    	catch (Exception e) {
	    	}
	    
      UserUpdate userupdate = new UserUpdate(id,allParams.get("username"),allParams.get("password"),allParams.get("firstName"),allParams.get("lastName"),allParams.get("middleName"),date); 
      userupdateRepository.delete(userupdate);
      return ResponseEntity.ok(new MessageResponse("User "+users.getUsername()+" Deleted"));
  }
  
  
  
  @GetMapping("/register/{id}/")
  public ResponseEntity<?> getRegister(@PathVariable("id") long id) {
     User user = userRepository.findById(id) 
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
     return ResponseEntity.ok(new UserResponse(user.getId(),user.getUsername(),user.getPassword(),user.getfirstName(),user.getlastName(),user.getmiddleName(),user.getbirthdate()));
  }
  
  @GetMapping("/register/")
  public List<User> list() {
      return userRepository.findAll();
  }

  
  @PostMapping("/register/")
  public ResponseEntity<?> registerUser(@RequestParam Map<String,String> allParams) {

	  
    if (userRepository.existsByUsername(allParams.get("username"))) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }
    

	   Date date = new Date();
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.TRADITIONAL_CHINESE);
	        date = formatter.parse(allParams.get("birthdate"));

	    	}
	    	catch (Exception e) {
	    
	    	}
	    
    String encodedString = Base64.getEncoder().encodeToString(allParams.get("password").getBytes()); 
    User user = new User(allParams.get("username"),encoder.encode(encodedString),allParams.get("firstName"),allParams.get("lastName"),allParams.get("middleName"),date); 
    userRepository.save(user);
    return ResponseEntity.ok(new UserResponse(user.getId(),user.getUsername(),user.getPassword(),user.getfirstName(),user.getlastName(),user.getmiddleName(),user.getbirthdate()));
  }
}

