package com.example.finance.security.services;

import com.example.finance.models.User;
import com.example.finance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  public static final int MAX_FAILED_ATTEMPTS = 3;

  private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public void increaseFailedAttempts(User user) {
    int newFailAttempts = user.getFailedAttempt() + 1;
    userRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
  }

  public void resetFailedAttempts(String username) {
    userRepository.updateFailedAttempts(0, username);
  }

  public void lock(User user) {
    user.setAccountNonLocked(false);
    user.setLockTime(new Date());

    userRepository.save(user);
  }

  public boolean unlockWhenTimeExpired(User user) {
    long lockTimeInMillis = user.getLockTime().getTime();
    long currentTimeInMillis = System.currentTimeMillis();

    if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
      user.setAccountNonLocked(true);
      user.setLockTime(null);
      user.setFailedAttempt(0);

      userRepository.save(user);

      return true;
    }

    return false;
  }

}