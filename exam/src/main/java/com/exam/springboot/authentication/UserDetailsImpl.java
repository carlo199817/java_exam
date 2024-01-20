package com.exam.springboot.authentication;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

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
  

  private Collection<? extends GrantedAuthority> authorities;
  
  public UserDetailsImpl(Long id,String username,String password,String lastName,String firstName,String middleName,Date birthdate) {
	    this.id=id;  
	    this.username = username;
	    this.password = password;
	    this.lastName=lastName;
	    this.firstName = firstName;
	    this.middleName = middleName;
	    this.birthdate=birthdate;
  }

  public static UserDetailsImpl build(User user) {



    return new UserDetailsImpl(
        user.getId(), 
        user.getUsername(), 
        user.getPassword(), 
        user.getlastName(),
        user.getfirstName(),
        user.getmiddleName(),
        user.getbirthdate()
        );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }


  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  } 


  public String getPassword() {
    return password;
  }
  
  public String getlastName() {
	    return lastName;
	  }
  public String getfirstName() {
	    return firstName;
	  }

  public String getmiddleName() {
	    return middleName;
	  }
  public Date getbirthdate() {
	    return birthdate;
	  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  } 

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}