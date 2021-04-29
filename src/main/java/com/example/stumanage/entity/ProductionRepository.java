package com.example.stumanage.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    List<Production> findBySerialNum(String serialNum);
}

//@Transactional
//public interface UserRepository extends CrudRepository<User, Long> {
//    public List<User> findByUserName(String name);
//}