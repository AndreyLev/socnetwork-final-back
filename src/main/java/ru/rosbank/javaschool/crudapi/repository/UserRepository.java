package ru.rosbank.javaschool.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    @Modifying
    @Query("UPDATE UserEntity u SET u.removed=false WHERE u.id = :id")
    void setRemovedById(@Param("id") int id);
}
