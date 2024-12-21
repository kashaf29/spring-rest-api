package com.kashaf.controller;

import com.kashaf.model.Student;
import com.kashaf.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequiredArgsConstructor // generates all the constructors (during development)
public class StudentController {

    @Autowired
    private StudentRepository studentRepository; // if I don't want to use autowired use final keyword along with @RequiredArgsConstructors

    @GetMapping("/student")
    public Iterable<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student/{id}")
    public Optional<Student> singleStudent(@PathVariable long id) {
        return studentRepository.findById(id);
    }

    @PostMapping("/student")
    public String createStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return "Student created in database";
    }

    @PutMapping("/student/{id}")
    public String updateStudent(@RequestBody Student student, @PathVariable long id) {
        var studentInDb = studentRepository.findById(id).orElseThrow(RuntimeException::new);
       /* studentInDb.setName(student.getName());
        studentInDb.setContact(student.getContact());
        studentInDb.setEmail(student.getEmail());
        studentRepository.save(studentInDb);  */

        BeanUtils.copyProperties(student, studentInDb, "id");
        studentRepository.save(studentInDb);
        return "Student updated in database";
    }

    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable long id) {
        studentRepository.deleteById(id);
        return "Student deleted";
    }

    @DeleteMapping("/student")
    public String deleteAllStudents() {
        studentRepository.deleteAll();
        return "Deleted all students";
    }
}

