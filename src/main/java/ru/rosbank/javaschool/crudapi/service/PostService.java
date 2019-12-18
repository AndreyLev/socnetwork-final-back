package ru.rosbank.javaschool.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.mapper.PostMapper;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository repository;
  private final PostMapper mapper;


  public List<PostResponseDto> getAll() {
    return repository.findAll().stream()
            .map(mapper::entityToPostResponseDto)
            .collect(Collectors.toList());
  }

  public List<PostResponseDto> getLastFivePosts() {
    return repository.findAll().stream()
            .filter(o -> o.isRemoved() == false)
            .sorted((o1,o2) -> -(o1.getId() - o2.getId()))
            .limit(5)
            .map(mapper::entityToPostResponseDto)
            .collect(Collectors.toList());


  }

  public PostResponseDto getById(int id) {
    return repository.findById(id)
            .map(mapper::entityToPostResponseDto)
            .orElseThrow(NotFoundException::new);
  }

  public List<PostResponseDto> getNoRemovePostAfterLastDrawnPost(int lastDrawnPostId) {
    return repository.findAll().stream()
            .filter(o -> o.isRemoved() == false && o.getId() < lastDrawnPostId)
            .sorted((o1,o2) -> -(o1.getId() - o2.getId()))
            .limit(5)
            .map(mapper::entityToPostResponseDto)
            .collect(Collectors.toList());
  }

  public List<PostResponseDto> getNoRemovePostBeforeFirstDrawnPost(int firstDrawnPostId) {
    return repository.findAll().stream()
            .filter(o -> o.isRemoved() == false && o.getId() > firstDrawnPostId)
            .sorted((o1,o2) -> -(o1.getId() - o2.getId()))
            .map(mapper::entityToPostResponseDto)
            .collect(Collectors.toList());
  }

  public int getNewPostsQuantity(int firstDrawnPostId) {
    return getNoRemovePostBeforeFirstDrawnPost(firstDrawnPostId).size();
  }

  public List<PostResponseDto> getAllNoRemoved() {
    return repository.findAll().stream()
            .filter(o -> o.isRemoved() == false)
            .sorted((o1,o2) -> -(o1.getId() - o2.getId()))
            .map(mapper::entityToPostResponseDto)
            .collect(Collectors.toList());
  }

  public PostResponseDto save(PostSaveRequestDto dto) {
    return mapper.entityToPostResponseDto(repository.save(mapper.dtoToPostEntity(dto)));
  }

  public void removeById(int id) {
    repository.deleteById(id);
  }

  public void likeById(int id) {
    repository.increaseLikesById(id, 1);
  }

  public void dislikeById(int id) {
    repository.decreaseLikesById(id, 1);
  }

  public List<PostResponseDto> getDrawnPosts(int firstDrawnPostId, int lastDrawnPostId) {
    return repository.findAll().stream()
            .filter(o -> o.isRemoved() == false && o.getId() <= firstDrawnPostId && o.getId() >= lastDrawnPostId)
            .sorted((o1,o2) -> -(o1.getId() - o2.getId()))
            .map(mapper::entityToPostResponseDto)
            .collect(Collectors.toList());
  }
}
