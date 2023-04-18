package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ResourceNotFoundExceptionn;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service//componentin gelismis hali obje olusturma isini yapacak
public class StudentServiceImpl implements StudentService{

@Autowired
private StudentRepository repository;

    @Override
    public void saveStudent(Student student) {
    repository.save(student);

    }

    @Override
    public List<Student> getAll() {
     return repository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
       Student student =repository.findById(id).
               orElseThrow(()->new ResourceNotFoundExceptionn("Student not found by id: "+id));
        //orElseThrow ile null gelmesini engelledik ve kendi ezceptionumuzu olusturduk.exception packagesine eledik
        //Student student =repository.findById(id)..get();da yapabilirdik  no such element excp atar
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
       //boyle bir id li student yoksa ne olacak
        Student student=getStudentById(id);//oyle bir idli student oldugunan emin olduk.Yoksa yukaridaki hatayi verir zaten
        repository.delete(student.getId());
    }
}
