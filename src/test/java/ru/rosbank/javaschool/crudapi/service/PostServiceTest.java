package ru.rosbank.javaschool.crudapi.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.mapper.PostMapper;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

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
    void getLastFivePostsWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getLastFivePosts().size());
    }

    @Test
    void getLastFivePostsWherePresentPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(3, service.getLastFivePosts().size());
    }

    // TEST FOR NEW POSTS QUANTITY

    @Test
    void getNewPostsQuantityWhenPostsPresentInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(2, service.getNewPostsQuantity(1));
    }

    @Test
    void getNewPostsQuantityWhenNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getNewPostsQuantity(3));
    }

    @Test
    void getNewPostsQuantityWhenNoSuchPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getNewPostsQuantity(4));
    }

    // TESTS BEFORE POSTS
    @Test
    void getNoRemovePostBeforeFirstDrawnPostWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getNoRemovePostBeforeFirstDrawnPost(3).size());
    }

    @Test
    void getNoRemovePostBeforeFirstDrawnPostWherePresentPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(2, service.getNoRemovePostBeforeFirstDrawnPost(1).size());
    }

    @Test
    void getNoRemovePostBeforeFirstDrawnPostWhereNoSuchPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getNoRemovePostBeforeFirstDrawnPost(4).size());
    }


    // TESTS AFTER POSTS
    @Test
    void getNoRemovePostAfterLastDrawnPostWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getNoRemovePostAfterLastDrawnPost(3).size());
    }


    @Test
    void getNoRemovePostAfterLastDrawnPostWherePresentPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(2, service.getNoRemovePostAfterLastDrawnPost(3).size());
    }

    @Test
    void getNoRemovePostAfterLastDrawnPostWhereNoSuchPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getNoRemovePostBeforeFirstDrawnPost(4).size());
    }

    // TESTS FOR GET ALL NO REMOVED

    @Test
    void getAllNoRemovedWhenRemovedPostsPresentInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, true, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(2, service.getAllNoRemoved().size());
    }

    @Test
    void getAllNoRemovedWhenNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of()).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(0, service.getAllNoRemoved().size());
    }

    @Test
    void getAllNoRemovedWhenRemovedPostsIsNoRemovedInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertEquals(3, service.getAllNoRemoved().size());
    }

    // TESTS FOR REMOVE BY ID

    @Test
    void isRemovedByIdWhenPostsPresentInRepo() {
        val repository = mock(PostRepository.class);
        doReturn(List.of(new PostEntity(3, null, null, false, 0),
                new PostEntity(2, null, null, false, 0),
                new PostEntity(1, null, null, false, 0))).when(repository).findAll();
        val mapper = mock(PostMapper.class);
        val service = new PostService(repository, mapper);

        assertThrows(NotFoundException.class, () -> service.getById(2));

    }



    // TESTS TO FIX

    @Test
    void getByIdShouldThrowExceptionWhereNoPostsInRepo() {
        val repository = mock(PostRepository.class);
        val mapper = mock(PostMapper.class);
        doReturn(Optional.empty()).when(repository).findById(1);
        val service = new PostService(repository, mapper);

       assertThrows(NotFoundException.class, () -> service.getById(1));
    }

    @Test
    void getByIdShouldThrowExceptionWhereNoSuchPostInRepo() {
        val repository = mock(PostRepository.class);
        val mapper = mock(PostMapper.class);
        doReturn(Optional.empty()).when(repository).findById(anyInt());
        doReturn(Optional.of(new PostEntity())).when(repository).findById(1);
        val service = new PostService(repository, mapper);

        assertThrows(NotFoundException.class, () -> service.getById(1));
    }

    @Test
    void getByIdShouldReturnDtoWhenPostPresentInRepo() {
        val repository = mock(PostRepository.class);
        val mapper = mock(PostMapper.class);
        doReturn(Optional.of(new PostEntity())).when(repository).findById(2);
        val service = new PostService(repository, mapper);

        assertNotNull(service.getById(2));
    }
}