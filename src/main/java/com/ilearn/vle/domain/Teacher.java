package com.ilearn.vle.domain;

import java.util.List;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "education")
    private String education;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "enrolment", unique = true)
    private String enrolment;

    @OneToMany(mappedBy = "teacher")
    private List<Class> classes;

    public Teacher(String name, String education, String email, String enrolment) {
        this.name = name;
        this.education = education;
        this.email = email;
        this.enrolment = enrolment;
    }
}
