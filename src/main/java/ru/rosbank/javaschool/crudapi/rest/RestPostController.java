package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.service.PostService;
import ru.rosbank.javaschool.crudapi.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
  private final PostService service;
  private final UserService userService;


  public List<PostResponseDto> getAll() {
    return service.getAllNoRemoved();
  }


  @GetMapping("/lastFivePosts")
  public List<PostResponseDto> getLastFivePosts() {
    return service.getLastFivePosts();
  }

  @GetMapping("/{firstDrawnElementId}/newPosts")
  public int getNewPostsQuantity(@PathVariable int firstDrawnElementId) {
    return service.getNewPostsQuantity(firstDrawnElementId);
  }

  @PostMapping
  public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
    dto.setAuthor(userService.getUserEntityById(1));
    return service.save(dto);
  }

  @DeleteMapping("/{id}")

  public void removeById(@PathVariable int id) {
    service.removeById(id);
  }

  @PostMapping("/{lastElementId}/impression")
  public List<PostResponseDto> showPostsAfterId(@PathVariable int lastElementId) {
    return service.getNoRemovePostAfterLastDrawnPost(lastElementId);
  }

  @PostMapping("/{firstElementId}/newposts")
  public List<PostResponseDto> getPostsBeforeId(@PathVariable int firstElementId) {
    return service.getNoRemovePostBeforeFirstDrawnPost(firstElementId);
  }

  @PostMapping("/{firstElementId}/{lastElementId}/drawnposts")
  public List<PostResponseDto> getDrawnPosts(@PathVariable int firstElementId, @PathVariable int lastElementId) {
    return service.getDrawnPosts(firstElementId, lastElementId);
  }

  @PostMapping("/{id}/likes")
  public void likeById(@PathVariable int id) {
    service.likeById(id);
  }

  @DeleteMapping("/{id}/likes")
  public void dislikeById(@PathVariable int id) {
    service.dislikeById(id);
  }
}