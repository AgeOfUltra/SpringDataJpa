package com.example.springbootjdbc.controller;


import com.example.springbootjdbc.data.Message;
import com.example.springbootjdbc.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class MessageController {

    private final ContactService service;

    @Autowired
    public MessageController(ContactService service) {
        this.service = service;
    }

    @RequestMapping("/message")
    public String displayMessage(Model model){
        model.addAttribute("contact", new Message());
        return "message.html";
    }
    @ModelAttribute("message")
    public Message getMessage() {
        return new Message();
    }
    @PostMapping("/saveMessage")
    public String displayHome(@ModelAttribute @Valid Message message, Errors error, Model model) {
        log.info("Received message: {}", message);
        if (error.hasErrors()) {
            log.error("Contact form validation failed: {}", error.getAllErrors());
            return "message.html";
        }
        service.saveMessage(message);
        return "redirect:/message";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(){
        List<Message> contactList = service.findMessageWithOpenStatus();
        ModelAndView view = new ModelAndView("displayMessages.html");// when the user is invoking the /displayMessages view the displayMessages.html page.
        view.addObject("contactList", contactList);
        return view;
    }

    @RequestMapping("/closeMessage/{id}")
    public String closeMessageAction(@PathVariable(name = "id") int id){
        System.out.println(id);
        boolean res = service.changeTicketStatus(id);
        System.out.println(res ? "success" : "fail");
        return  "redirect:/displayMessages";
    }


}
