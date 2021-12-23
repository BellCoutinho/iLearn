package com.ilearn.vle.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.ilearn.vle.domain.Class;
import com.ilearn.vle.repository.ClassRepository;

@RequestMapping("/classes")
@RestController
public class ClassController {
    
    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public List<Class> getClasses() {
        return this.classRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Class> getClassById(@PathVariable Long id) {
        return this.classRepository.findById(id);
    }

    @PostMapping
    public Class createClass(@RequestBody Class klass) {
        return this.classRepository.save(klass);
    }
    
    @PutMapping("/{id}")
    public Class updateClass(@PathVariable("id") Long id, @RequestBody Class klass) {
        Optional<Class> updatedClass = this.classRepository.findById(id);
        if (updatedClass.isPresent()) {
            updatedClass.get().setDiscipline(klass.getDiscipline());
            updatedClass.get().setRoom(klass.getRoom());
            return this.classRepository.save(updatedClass.get());
        }
        return this.classRepository.save(klass);
    }

    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable("id") Long id) {
        this.classRepository.delete(this.classRepository.findById(id).get());
    }
}
