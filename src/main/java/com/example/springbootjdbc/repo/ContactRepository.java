package com.example.springbootjdbc.repo;

import com.example.springbootjdbc.data.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Message,Integer> {
    List<Message> findAllByStatus(String status);
}