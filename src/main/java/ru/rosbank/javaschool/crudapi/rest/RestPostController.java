package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;
import ru.rosbank.javaschool.crudapi.service.PostService;
import ru.rosbank.javaschool.crudapi.service.UserService;

import java.util.List;

@RestController // ко всем методам будет дописано @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
  private final PostService service;
  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(RestPostController.class);

  // @ResponseBody
  // AutoConfiguration -> Jackson
  // HttpMessageConverters -> RequestResponse...
  @GetMapping // @RequestMapping(method = GET) -> GET /api/posts
  public List<PostResponseDto> getAll() {
    return service.getAllNoRemoved();
  }

//  // ТТП
//  @GetMapping(params = "q") // фильтрация по наличию параметра
//  public List<PostResponseDto> searchByContent(@RequestParam String q) {
//    return service.searchByContent(q);
//  }

  @GetMapping("/lastFivePosts")
  public List<PostResponseDto> getLastFivePosts() {
    // logger.info(Thread.currentThread().getName());
    return service.getLastFivePosts();
  }

  @GetMapping("/{firstDrawnElementId}/newPosts")
  public int getNewPostsQuantity(@PathVariable int firstDrawnElementId) {
    return service.getNewPostsQuantity(firstDrawnElementId);
  }

  // -> x-www-urlencoded...
  // -> multipart/form-data
  // Content-Type: MIME тип
  // POST -> create/update
  @PostMapping // DataBinding
  public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
    dto.setAuthor(userService.getUserEntityById(1));
    return service.save(dto);
  }

  // DELETE /api/posts/:id -> ?itemId=10 -> req.getParameter()
  @DeleteMapping("/{id}")
// public void removeById(@PathVariable("id") int id)
// if param name = path variable name, то дополнительно ничего не нужно
  public void removeById(@PathVariable int id) {
//    throw new BadRequestException("bad.request");
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