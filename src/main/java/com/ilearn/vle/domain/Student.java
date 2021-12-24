package com.ilearn.vle.domain;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "enrolment", unique=true)
    private String enrolment;

    @ManyToOne
    @JoinColumn(name = "klass_fk")
    private Class klass;

    public Student(String name, String email, String enrolment) {
        this.name = name;
        this.email = email;
        this.enrolment = enrolment;
    }
}
