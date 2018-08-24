package com.example.sblesson20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception{
        System.out.println("Loading data...");

        appRoleRepository.save(new AppRole("USER"));
        appRoleRepository.save(new AppRole("ADMIN"));

        AppRole adminAppRole = appRoleRepository.findByAppRole("ADMIN");
        AppRole userAppRole = appRoleRepository.findByAppRole("USER");

        AppUser appUser = new
                AppUser("bob@bob.com", "password", "Bob", "Bobberson", true, "bob");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppRoles(Arrays.asList(userAppRole));
        appUserRepository.save(appUser);

        appUser = new
                AppUser("jim@jim.com", "password", "Jim", "Jimmerson", true, "jim");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppRoles(Arrays.asList(userAppRole));
        appUserRepository.save(appUser);

        appUser = new
                AppUser("sam@ev.com","password","Sam","Everyman",true,"sam");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppRoles(Arrays.asList(userAppRole));
        appUserRepository.save(appUser);

        appUser = new
                AppUser("admin@adm.com", "password", "Admin", "User", true, "admin");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppRoles(Arrays.asList(adminAppRole));
        appUserRepository.save(appUser);

    }
}