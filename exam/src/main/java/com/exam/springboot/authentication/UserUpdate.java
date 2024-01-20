package com.exam.springboot.authentication;
import java.util.Date;

import jakarta.persistence.*; 
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
       })
public class UserUpdate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 120)
  private String username;


  @NotBlank
  @Size(max = 120)
  private String password;
  
  
  @NotBlank
  @Size(max = 120)
  private String lastName;
  
  
  @NotBlank
  @Size(max = 50)
  @Email
  private String firstName;
  
  @NotBlank
  @Size(max = 50)
  @Email
  private String middleName;
  
  @Temporal(TemporalType.DATE)
  private Date birthdate;
  
  
  public UserUpdate() {
  }

  public UserUpdate(Long id,String username,String password,String lastName,String firstName,String middleName,Date birthdate) {
	  this.id=id;
    this.username = username;
    this.password = password;
    this.lastName=lastName;
    this.firstName = firstName;
    this.middleName = middleName;
    this.birthdate=birthdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getfirstName() {
	    return firstName;
  }

  public void setfirstName(String firstName) {
	    this.firstName=firstName;
  }

  public String getlastName() {
	    return lastName;
	  }

	  public void setlastName(String lastName) {
	    this.lastName=lastName;
	  }
	  
	  public String getmiddleName() {
		    return middleName;
		  }

		  public void setmiddleName(String middleName) {
		    this.middleName=middleName;
		  }
		  public Date getbirthdate() {
			    return birthdate;
			  }

			  public void setbirthdate(Date birthdate) {
			    this.birthdate=birthdate;
			  }
}