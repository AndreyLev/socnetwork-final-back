package ru.rosbank.javaschool.crudapi.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.rosbank.javaschool.crudapi.dto.UserResponseDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.mapper.PostMapper;
import ru.rosbank.javaschool.crudapi.mapper.UserMapper;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;
import ru.rosbank.javaschool.crudapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    @Test
    void getByIdShouldThrowExceptionWhereUsersPresentInRepo() {
        val repository = mock(UserRepository.class);
        doReturn(List.of(new UserEntity(1, "Vasya", "vaska", "12345678", "vaska@gmail.com", false),
                new UserEntity(2, "Nikita", "nikicent", "87654321", "nikita@gmail.com", false),
        new UserEntity(3, "Anton", "antosha", "87651234", "antosha@gmail.com", false))).when(repository).findAll();
        val service = new UserService(repository, Mappers.getMapper(UserMapper.class));

       assertNotNull(service.getById(3));
    }

    @Test
    void getByIdShouldThrowExceptionWhereNoSuchUserInRepo() {
        val repository = mock(UserRepository.class);
        doReturn(List.of(new UserEntity(1, "Vasya", "vaska", "12345678", "vaska@gmail.com", false),
                new UserEntity(2, "Nikita", "nikicent", "87654321", "nikita@gmail.com", false),
                new UserEntity(3, "Anton", "antosha", "87651234", "antosha@gmail.com", false))).when(repository).findAll();
        val service = new UserService(repository, Mappers.getMapper(UserMapper.class));

        assertThrows(NotFoundException.class, () -> service.getById(4));
    }

    @Test
    void getByIdShouldReturnDtoWhenNoUsersInRepo() {
        val repository = mock(UserRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val service = new UserService(repository, Mappers.getMapper(UserMapper.class));

        assertThrows(NotFoundException.class, () -> service.getById(2));
    }

    @Test
    void getByUsernameShouldThrowExceptionWhereUsersPresentInRepo() {
        val repository = mock(UserRepository.class);
        doReturn(List.of(new UserEntity(1, "Vasya", "vaska", "12345678", "vaska@gmail.com", false),
                new UserEntity(2, "Nikita", "nikicent", "87654321", "nikita@gmail.com", false),
                new UserEntity(3, "Anton", "antosha", "87651234", "antosha@gmail.com", false))).when(repository).findAll();
        val service = new UserService(repository, Mappers.getMapper(UserMapper.class));

        assertNotNull(service.getByUsername("antosha"));
    }

    @Test
    void getByUsernameShouldThrowExceptionWhereNoSuchUserInRepo() {
        val repository = mock(UserRepository.class);
        doReturn(List.of(new UserEntity(1, "Vasya", "vaska", "12345678", "vaska@gmail.com", false),
                new UserEntity(2, "Nikita", "nikicent", "87654321", "nikita@gmail.com", false),
                new UserEntity(3, "Anton", "antosha", "87651234", "antosha@gmail.com", false))).when(repository).findAll();
        val service = new UserService(repository, Mappers.getMapper(UserMapper.class));

        assertThrows(NotFoundException.class, () -> service.getByUsername("someUsername"));
    }

    @Test
    void getByUsernameShouldReturnDtoWhenNoUsersInRepo() {
        val repository = mock(UserRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val service = new UserService(repository, Mappers.getMapper(UserMapper.class));

        assertThrows(NotFoundException.class, () -> service.getByUsername("antosha"));
    }

}