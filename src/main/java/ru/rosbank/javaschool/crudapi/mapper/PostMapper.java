package ru.rosbank.javaschool.crudapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;


@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mappings({
          @Mapping(target = "authorId", expression = "java(entity.getAuthor().getId())"),
          @Mapping(target = "authorName", expression = "java(entity.getAuthor().getName())")
  })
  PostResponseDto entityToPostResponseDto(PostEntity entity);

  @Mappings({
      @Mapping(target = "likes", constant = "0"),
      @Mapping(target = "removed", constant = "false")
  })
  PostEntity dtoToPostEntity(PostSaveRequestDto dto);
}
