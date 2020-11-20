package io.github.zyngjaku.tmsbackend.services;

import java.util.*;

import io.github.zyngjaku.tmsbackend.dao.UserRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepo.findUserByMail(mail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + mail);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getMail(),
                user.getPassword(),
                new HashSet<>(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()))));
    }
}