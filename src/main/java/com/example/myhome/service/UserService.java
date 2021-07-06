package com.example.myhome.service;

import com.example.myhome.model.Role;
import com.example.myhome.model.User;
import com.example.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        //패스워드 암호화
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

}
