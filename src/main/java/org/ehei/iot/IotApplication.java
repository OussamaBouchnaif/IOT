package org.ehei.iot;

import org.ehei.iot.Entities.Role;
import org.ehei.iot.Entities.User;
import org.ehei.iot.Repository.RoleRepository;
import org.ehei.iot.Repository.UserRepository;
import org.ehei.iot.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IotApplication {



    public static void main(String[] args) {
        SpringApplication.run(IotApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){

        return  args -> {
            if(roleRepository.findByRole("directeur") == null)
                roleRepository.save(new Role("directeur"));
            if(roleRepository.findByRole("admin") == null)
                roleRepository.save(new Role("admin"));
            if(roleRepository.findByRole("employe") == null)
                roleRepository.save(new Role("employe"));

            if(userRepository.findByUsername("yassine")==null)
                userRepository.save(new User("yassine",bCryptPasswordEncoder.encode("123"),
                    null,"yassinemali300@gmail.com",roleRepository.findByRole("directeur")));
            if(userRepository.findByUsername("adnane")==null)
                userRepository.save(new User("adnane",bCryptPasswordEncoder.encode("123"),
                    null,"yassinemali300@gmail.com",roleRepository.findByRole("employe")));
            if(userRepository.findByUsername("admin")==null)
                userRepository.save(new User("admin",bCryptPasswordEncoder.encode("123"),
                    null,"yassinemali300@gmail.com",roleRepository.findByRole("admin")));
        };
    }
}
