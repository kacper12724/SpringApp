package com.example.springapp.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    public final String CURRENT_USER_KEY = "CURRENT_USER";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        /*if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        if(!user.getStatus().equals("I")) {
            throw new UsernameNotFoundException(username + " did not activate his account.");
        }
        httpSession.setAttribute(CURRENT_USER_KEY, user);
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auth);
    }*/
        if(user == null) {
            throw new UsernameNotFoundException(username);
          }
System.err.println(user.getUsername() + " " + user.getPassword() + " " + user.getStatus());
          List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN");
          return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(), // enabled. Use whatever condition you like
            true, // accountNonExpired. Use whatever condition you like
            true, // credentialsNonExpired. Use whatever condition you like
            true, // accountNonLocked. Use whatever condition you like
            auth);
    }

}