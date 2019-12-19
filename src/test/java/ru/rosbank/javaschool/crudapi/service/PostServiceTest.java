package ru.rosbank.javaschool.crudapi.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.rosbank.javaschool.crudapi.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.mapper.PostMapper;
import ru.rosbank.javaschool.crudapi.mapper.UserMapper;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;
import ru.rosbank.javaschool.crudapi.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PostServiceTest {

    // LASTS FIVE POSTS TESTS

    @Test
    void getZeroValueLastFivePostsWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getLastFivePosts().size());
    }

    @Test
    void getLastFivePostsWherePresentPostsInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(3, service.getLastFivePosts().size());
    }

    // TEST FOR NEW POSTS QUANTITY

    @Test
    void getNewPostsQuantityWhenPostsPresentInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(2, service.getNewPostsQuantity(1));
    }

    @Test
    void getZeroNewPostsQuantityWhenNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getNewPostsQuantity(3));
    }

    @Test
    void getZeroNewPostsQuantityWhenNoSuchPostsInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getNewPostsQuantity(4));
    }

    // TESTS BEFORE POSTS
    @Test
    void getNoRemovePostBeforeFirstDrawnPostWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getNoRemovePostBeforeFirstDrawnPost(3).size());
    }

    @Test
    void getNoRemovePostBeforeFirstDrawnPostWherePresentPostsInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(2, service.getNoRemovePostBeforeFirstDrawnPost(1).size());
    }

    @Test
    void getNoRemovePostBeforeFirstDrawnPostWhereNoSuchPostsInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getNoRemovePostBeforeFirstDrawnPost(4).size());
    }


    // TESTS AFTER POSTS
    @Test
    void getNoRemovePostAfterLastDrawnPostWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getNoRemovePostAfterLastDrawnPost(3).size());
    }


    @Test
    void getNoRemovePostAfterLastDrawnPostWherePresentPostsInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(2, service.getNoRemovePostAfterLastDrawnPost(3).size());
    }

    @Test
    void getZeroValueNoRemovePostAfterLastDrawnPostWhereNoSuchPostsInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getNoRemovePostBeforeFirstDrawnPost(4).size());
    }

    // TESTS FOR GET ALL NO REMOVED

    @Test
    void getAllNoRemovedWhenRemovedPostsPresentInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, true, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(2, service.getAllNoRemoved().size());
    }

    @Test
    void getZeroValueAllNoRemovedWhenNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getAllNoRemoved().size());
    }

    @Test
    void getAllNoRemovedWhenRemovedPostsIsNoRemovedInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(3, service.getAllNoRemoved().size());
    }

    // TESTS FOR GET DRAWN POSTS

    @Test
    void getDrawnPostsWhenPostsPresentInRepo() {
        val repository = mock(PostRepository.class);
        val vasya = mock(UserEntity.class);
        doReturn(List.of(new PostEntity(3, vasya,null, null, false, 0),
                new PostEntity(2, vasya, null, null, false, 0),
                new PostEntity(1, vasya, null, null, false, 0))).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(3, service.getDrawnPosts(3,1).size());
    }

    @Test
    void getZeroDrawnPostsWhenNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertEquals(0, service.getDrawnPosts(3,1).size());
    }




    // TESTS TO FIX

    @Test
    void getByIdShouldThrowExceptionWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(Optional.empty()).when(repository).findById(1);
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

       assertThrows(NotFoundException.class, () -> service.getById(1));
    }

    @Test
    void getByIdShouldThrowExceptionWhereNoSuchPostInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(Optional.empty()).when(repository).findById(anyInt());
        doReturn(Optional.of(new PostEntity())).when(repository).findById(1);
        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));

        assertThrows(NotFoundException.class, () -> service.getById(2));
    }

    // TODO : FIX
//    @Test
//    void getByIdShouldReturnDtoWhenPostPresentInRepo() {
//        val repository = mock(PostRepository.class);
//        val userRepository = mock(UserRepository.class);
//        val userService = new UserService(userRepository, Mappers.getMapper(UserMapper.class));
//         doReturn(Optional.of(new PostEntity(0, userService.save(new UserSaveRequestDto(0, "Случайный прохожий", "anonymous", "11111111", "anonymous@gmail.com"))
//                 ,"First", null, false, 0)))
//         .when(repository).findById(1);
//
//
//        val service = new PostService(repository, Mappers.getMapper(PostMapper.class));
//
//        assertNotNull(service.getById(1));
//    }
}