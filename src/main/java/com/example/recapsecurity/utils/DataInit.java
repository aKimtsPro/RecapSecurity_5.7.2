package com.example.recapsecurity.utils;

import com.example.recapsecurity.models.entity.Roles;
import com.example.recapsecurity.models.entity.User;
import com.example.recapsecurity.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInit implements InitializingBean {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public DataInit(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword(encoder.encode("pass"));
        user.setActive(true);
        user.setRoles( List.of(Roles.USER) );

        repository.save(user);

        user = new User();
        user.setId(2);
        user.setUsername("admin");
        user.setPassword(encoder.encode("pass"));
        user.setActive(true);
        user.setRoles( List.of(Roles.ADMIN) );

        repository.save(user);
    }
}
