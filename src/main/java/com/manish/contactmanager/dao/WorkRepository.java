package com.manish.contactmanager.dao;

import com.manish.contactmanager.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work,Long> {
}
