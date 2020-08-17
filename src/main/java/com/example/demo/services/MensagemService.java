package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Mensagem;
import com.example.demo.repository.MensagemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MensagemService {
    
    @Autowired
    private MensagemRepository repository;

    public MensagemService() {}

    public Mensagem save(Mensagem entity) {
        return repository.save(entity);
    }

    public void delete(Mensagem entity) {
        repository.delete(entity);
    }

    public Optional<Mensagem> findById(Long id) {
        return repository.findById(id);
    }

    public List<Mensagem> findAll() {
        return repository.findAll();
    }
}