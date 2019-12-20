package ru.rosbank.javaschool.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.crudapi.dto.UserResponseDto;
import ru.rosbank.javaschool.crudapi.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.mapper.UserMapper;
import ru.rosbank.javaschool.crudapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserResponseDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToUserResponseDto)
                .collect(Collectors.toList());
    }

    public UserEntity getUserEntityById(long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public UserResponseDto getById(long id) {
         List<UserResponseDto> list = repository.findAll().stream()
                .filter(o -> o.getId() == id)
                 .limit(1)
                .map(mapper::entityToUserResponseDto)
                .collect(Collectors.toList())
                ;

         if (list.size() == 0) {
             throw new NotFoundException();
         }

         UserResponseDto desiredUserResponseDto = list.get(0);
         return desiredUserResponseDto;
    }

    public UserResponseDto getByUsername(String username) {
        List<UserResponseDto> list = repository.findAll().stream()
                .filter(o -> {
                    int result =  o.getUsername().compareToIgnoreCase(username);
                    if (result == 0) {
                        return true;
                    }
                    return false;
                })
                .map(mapper::entityToUserResponseDto)
                .collect(Collectors.toList());

        if (list.size() == 0) {
            throw new NotFoundException();
        }


        UserResponseDto desiredUserResponseDto = list.get(0);
        return desiredUserResponseDto;
    }

    public UserEntity save(UserSaveRequestDto dto) {
        return repository.save(mapper.dtoToUserEntity(dto));
    }


}
