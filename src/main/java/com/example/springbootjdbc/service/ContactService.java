package com.example.springbootjdbc.service;


import com.example.springbootjdbc.constants.StatusConstants;
import com.example.springbootjdbc.data.Message;
import com.example.springbootjdbc.repo.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    private final ContactRepository repo;

    @Autowired
    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    public void saveMessage(Message message){
        message.setStatus(StatusConstants.OPEN);
//        message.setCreatedBy(StatusConstants.ANONYMOUS);
//        message.setCreatedAt(LocalDateTime.now()); // it is doing by auditing now
        System.out.println("Message at DB: "+message);
        Message result = repo.save(message);
        System.out.println(result+"\n"+"This is the inserted Entity in the DB");
    }

    public List<Message> findMessageWithOpenStatus() {
        List<Message> result = repo.findAllByStatus(StatusConstants.OPEN);
        System.out.println(result);
        return result;
    }
    @Transactional
//    public boolean changeTicketStatus(int id){
//        Optional<Message> current = repo.findById(id);
//        boolean isUpdated= false;
//        if(current.isEmpty()){
//            return isUpdated;
//        }
//        current.ifPresent(contact-> {
//            contact.setStatus(StatusConstants.CLOSE);
////            contact.setUpdatedBy(updatedBy);
////            contact.setUpdatedAt(LocalDateTime.now()); // this is done by auditing
//        });
//        Message result = repo.save(current.get());
//        if(result.getUpdatedAt() != null){
//            isUpdated = true;
//        }
//        return isUpdated;
//    }
    public boolean changeTicketStatus(int id) {
        log.info("Attempting to change ticket status for id: {}", id);
        Optional<Message> current = repo.findById(id);
        if (current.isEmpty()) {
            log.warn("No message found with id: {}", id);
            return false;
        }
        Message message = current.get();
        message.setStatus(StatusConstants.CLOSE);
        try {
            repo.save(message);
            log.info("Successfully changed status for message id: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Error changing status for message id: {}", id, e);
            throw e;
        }
    }
}
