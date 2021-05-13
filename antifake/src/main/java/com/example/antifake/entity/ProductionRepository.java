package com.example.antifake.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    List<Production> findBySerialNum(String serialNum);
}

//@Transactional
//public interface UserRepository extends CrudRepository<User, Long> {
//    public List<User> findByUserName(String name);
//}