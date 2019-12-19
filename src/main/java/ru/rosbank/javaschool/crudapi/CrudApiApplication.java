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
    UserSaveRequestDto anonymousSave = new UserSaveRequestDto(0, "Случайный прохожий", "anonymous", "11111111", "anonymous@gmail.com");
    UserEntity anonymous = service.save(anonymousSave);
    UserSaveRequestDto vasyaSave = new UserSaveRequestDto(0, "Вася", "vaska", "12345678", "vasya@gmail.com");
    UserEntity vasya = service.save(vasyaSave);
    UserSaveRequestDto nikitaSave = new UserSaveRequestDto(0, "Никита", "nikicent", "87654321", "nikita@gmail.com");
    UserEntity nikita = service.save(nikitaSave);
    return args -> repository.saveAll(List.of(
        new PostEntity(0, vasya,"Ух-ты, уже почти январь! а снег-то где?", null, false, 0),
        new PostEntity(0, nikita,"Люди живут не задумываясь… кажется, что выбор можно отложить на потом. Но наступает момент, когда откладывать больше нельзя. И ты вдруг понимаешь, что выбор уже сделан, а ты даже не заметил.\n" +
                "\n" +
                "Ирвин Уэлш", null, false, 0),
        new PostEntity(0, vasya,"Сегодня получил пару зачётов, ура!", null, false, 0)
    ));
  }

}
