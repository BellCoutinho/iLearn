package com.ilearn.vle.domain;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import com.ilearn.vle.domain.Project;
import com.ilearn.vle.domain.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role projectRole;
    
    public Student(String name, String email, String enrolment) {
        this.name = name;
        this.email = email;
        this.enrolment = enrolment;
    }
}
