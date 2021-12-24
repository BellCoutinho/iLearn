package com.ilearn.vle.domain;

import java.util.List;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "discipline", unique = true)
    private String discipline;

    @Column(name = "room")
    private String room;

    @ManyToOne
    @JoinColumn(name = "teacher_fk")
    private Teacher teacher;

    @OneToMany(mappedBy = "klass")
    private List<Student> students;

    public Class(String discipline, String room) {
        this.discipline = discipline;
        this.room = room;
    }
}
