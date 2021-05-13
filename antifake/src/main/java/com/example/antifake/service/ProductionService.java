package com.example.antifake.service;

import com.example.antifake.entity.Production;
import com.example.antifake.entity.ProductionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ProductionService {
    @Resource
    private ProductionRepository productionRepository;

    @Transactional
    public void save(Production production){
        productionRepository.save(production);
    }

    @Transactional
    public void delete(Long id){
        productionRepository.deleteById(id);
    }

    public Iterable<Production> getAll(){
        return productionRepository.findAll();
    }

    @Transactional
    public Optional<Production> find(Long id){
        return productionRepository.findById(id);
    }
}
