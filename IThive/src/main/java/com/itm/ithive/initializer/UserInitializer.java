package com.itm.ithive.initializer;

import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!usersRepository.findByUsername("admin").isPresent()) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setFirstname("Admin");
            admin.setLastname("Ithive");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.Admin);
            admin.setEmail("ithiveadmin@gmail.com");
            admin.setStatus(Status.Approved);
            usersRepository.save(admin);

        }

    }
}
