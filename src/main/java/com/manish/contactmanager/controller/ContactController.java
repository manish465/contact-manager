package com.manish.contactmanager.controller;

import com.manish.contactmanager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping(path = "user/{id}")
    public Map<String, Object> getAllContactFromGivenUserId(@PathVariable("id") long id,
                                                            @RequestHeader(value="Authorization") String authorizationHeader){
        return this.contactService.getAllContactByUserIdService(id,authorizationHeader);
    }

    @GetMapping("user/{userId}/contact/{contactId}")
    public Map<String, Object> getAContactFromContactId(@PathVariable("userId") long userId,@PathVariable("contactId") long contactId,
                                                     @RequestHeader(value="Authorization") String authorizationHeader){
        return this.contactService.getAContactByContactId(userId,contactId,authorizationHeader);
    }

    @PostMapping("user/{id}")
    public Map<String, Object> addConatctByUserId(@PathVariable("id") long id,
                                                  @RequestHeader(value="Authorization") String authorizationHeader,
                                                  @RequestBody Map<String,Object> data){
        return this.contactService.addContactById(id,authorizationHeader,data);
    }

    @PutMapping("user/{userId}/contact/{contactId}")
    public Map<String, Object> updateConatctByUserId(@PathVariable("userId") long userId,@PathVariable("contactId") long contactId,
                                                  @RequestHeader(value="Authorization") String authorizationHeader,
                                                  @RequestBody Map<String,Object> data){
        return this.contactService.updateContactById(userId,contactId,authorizationHeader,data);
    }

    @DeleteMapping("user/{userId}/contact/{contactId}")
    public Map<String, Object> deleteConatctByUserId(@PathVariable("userId") long userId,@PathVariable("contactId") long contactId,
                                                     @RequestHeader(value="Authorization") String authorizationHeader){
        return this.contactService.deleteContactById(userId,contactId,authorizationHeader);
    }
}
