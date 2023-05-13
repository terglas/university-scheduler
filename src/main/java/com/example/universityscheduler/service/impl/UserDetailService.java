package com.example.universityscheduler.service.impl;

import com.example.universityscheduler.domain.UserAccount;
import com.example.universityscheduler.domain.UserDetailsImpl;
import com.example.universityscheduler.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

  private final UserAccountRepository userAccountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccount user = userAccountRepository.findByEmail(username);
    if (user != null) {
      return new UserDetailsImpl(user);
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}