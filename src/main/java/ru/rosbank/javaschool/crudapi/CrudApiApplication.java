package ru.rosbank.javaschool.crudapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rosbank.javaschool.crudapi.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;
import ru.rosbank.javaschool.crudapi.service.UserService;

import java.util.List;

@SpringBootApplication
public class CrudApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudApiApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(PostRepository repository, UserService service) {
    UserSaveRequestDto vasyaSave = new UserSaveRequestDto(0, "Vasya", "vaska", "12345678", "vasya@gmail.com");
    UserEntity vasya = service.save(vasyaSave);
    UserSaveRequestDto nikitaSave = new UserSaveRequestDto(0, "Nikita", "nikicent", "87654321", "nikita@gmail.com");
    UserEntity nikita = service.save(vasyaSave);
    return args -> repository.saveAll(List.of(
        new PostEntity(0, vasya,"First", null, false, 0),
        new PostEntity(0, nikita,"Second", null, false, 0),
        new PostEntity(0, vasya,"Third", null, false, 0)
    ));
  }

}
