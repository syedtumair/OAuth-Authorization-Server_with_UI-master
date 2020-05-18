package com.udelblue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.udelblue.repositories.UserRepository;

@Service
public class AccountService {

    @Autowired
    UserRepository userrepository;

    public boolean createAccount(String display_name, String password, String email,
            String first_name, String last_name, String userType) throws Exception {

        // encode password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);

        // create account
        String c = userrepository.createAccount(email.trim(), display_name.trim(), password.trim(),
                first_name.trim(), last_name.trim(), userType.trim());
        if (c.equals("0")) {
            throw new Exception("Creating account error");
        }

        String a = userrepository.createAuthority(display_name.trim(), "ROLE_USER");
        if (a.equals("0")) {
            throw new Exception("Creating authority error");
        }

        return true;

    }

}
