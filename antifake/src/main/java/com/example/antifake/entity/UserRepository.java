package com.example.antifake.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    public List<User> findByusername(String name);
}