package com.manish.contactmanager.dao;

import com.manish.contactmanager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    public List<Contact> getAllContactsByUserId(long userId);
}
