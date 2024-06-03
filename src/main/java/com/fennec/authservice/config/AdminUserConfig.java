package com.fennec.authservice.config;

import java.util.Optional;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fennec.authservice.model.Role;
import com.fennec.authservice.model.User;
import com.fennec.authservice.repository.RoleRepository;
import com.fennec.authservice.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class AdminUserConfig implements CommandLineRunner {

	private RoleRepository roleRepository;

	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
		Optional<User> userAdmin = userRepository.findByUsername("admin");

		userAdmin.ifPresentOrElse(
	//@formatter:off
        user -> {
          System.out.println("Admin already exists");
        },
        () -> {
          User user = new User();
          user.setUsername("admin");
          user.setPassword(passwordEncoder.encode("adminpassword"));
          user.setRoles(Set.of(roleAdmin));
          userRepository.save(user);
        }
        );
    //@formatter:on
	}

}
