package ru.rosbank.javaschool.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.crudapi.dto.UserResponseDto;
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


}
