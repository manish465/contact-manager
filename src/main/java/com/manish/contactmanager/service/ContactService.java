package com.manish.contactmanager.service;

import com.manish.contactmanager.dao.ContactRepository;
import com.manish.contactmanager.dao.PhoneNumberRepository;
import com.manish.contactmanager.dao.UserRepository;
import com.manish.contactmanager.dao.WorkRepository;
import com.manish.contactmanager.model.Contact;
import com.manish.contactmanager.model.PhoneNumber;
import com.manish.contactmanager.model.Work;
import com.manish.contactmanager.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final WorkRepository workRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final UserRepository userRepository;
    private final CustomUtils customUtils;
    private final BCryptPasswordEncoder tokenObject;

    @Autowired
    public ContactService(ContactRepository contactRepository, CustomUtils customUtils,
                          WorkRepository workRepository, PhoneNumberRepository phoneNumberRepository,
                          UserRepository userRepository){
        this.contactRepository = contactRepository;
        this.customUtils = customUtils;
        this.tokenObject = new BCryptPasswordEncoder();
        this.workRepository = workRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> getAllContactByUserIdService(long id, String authorizationHeader) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"normal");
        if(res != null) {
            return res;
        }

        res = new HashMap<>();

        res.put("code", 200);
        res.put("contacts", contactRepository.getAllContactsByUserId(id));

        return res;
    }

    public Map<String, Object> addContactById(long id, String authorizationHeader, Map<String, Object> data) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"normal");
        if(res != null) {
            return res;
        }

        Work currentWork = new Work((String) data.get("companyName"), (String) data.get("jobDesignation"));
        List<PhoneNumber> currentPhoneNumbers = new ArrayList<>();

        List<Map<String,String>> phoneNumbers = (List<Map<String,String>>) data.get("phoneNumbers");

        for(Map<String,String> phoneNumber : phoneNumbers){
            currentPhoneNumbers.add(new PhoneNumber(phoneNumber.get("countryCode"),
                    phoneNumber.get("number"),phoneNumber.get("type")));
        }

        Contact currentContact = new Contact((String) data.get("firstName"),
                (String) data.get("lastName"),
                (String) data.get("email"),
                (String) data.get("description"));

        currentContact.setWork(currentWork);
        currentContact.setUser(userRepository.findById(id).get());
        currentContact.setPhoneNumbers(currentPhoneNumbers);

        contactRepository.save(currentContact);
        workRepository.save(currentWork);

        res = new HashMap<>();

        res.put("code", 200);
        res.put("msg", "Contact Saved");

        return res;
    }

    public Map<String, Object> updateContactById(long userId, long contactId, String authorizationHeader, Map<String, Object> data) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"normal");
        if(res != null) {
            return res;
        }

        List<Contact> contacts = contactRepository.getAllContactsByUserId(userId);

        Contact curretContact = contacts.stream().filter(c -> c.getId() == contactId).findFirst().get();

        Work currentWork = new Work((String) data.get("companyName"), (String) data.get("jobDesignation"));
        List<PhoneNumber> currentPhoneNumbers = new ArrayList<>();

        for(Map<String,String> phoneNumber : (List<Map<String,String>>) data.get("phoneNumbers")){
            currentPhoneNumbers.add(new PhoneNumber(phoneNumber.get("countryCode"),
                    phoneNumber.get("number"),phoneNumber.get("type")));
        }

        curretContact.setWork(currentWork);
        curretContact.setPhoneNumbers(currentPhoneNumbers);
        curretContact.setFirstName((String) data.get("firstName"));
        curretContact.setLastName((String) data.get("lastName"));
        curretContact.setEmail((String) data.get("email"));
        curretContact.setDescription((String) data.get("description"));

        contactRepository.save(curretContact);

        res = new HashMap<>();

        res.put("code", 200);
        res.put("msg", "Contact Updated");

        return res;
    }

    public Map<String, Object> deleteContactById(long userId, long contactId, String authorizationHeader) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"normal");
        if(res != null) {
            return res;
        }

        contactRepository.deleteById(contactId);

        res = new HashMap<>();

        res.put("code", 200);
        res.put("msg", "Contact Deleted");

        return res;
    }
}
