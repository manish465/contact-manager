package com.manish.contactmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "WORK")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private long id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "job_designation")
    private String jobDesignation;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contact;

    public Work() {
    }

    public Work( String companyName, String jobDesigantion) {
        this.companyName = companyName;
        this.jobDesignation = jobDesigantion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobDesignation() {
        return jobDesignation;
    }

    public void setJobDesignation(String jobDesignation) {
        this.jobDesignation = jobDesignation;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workId=" + id +
                ", companyName='" + companyName + '\'' +
                ", jobDesignation='" + jobDesignation + '\'' +
                ", contact=" + contact +
                '}';
    }
}
