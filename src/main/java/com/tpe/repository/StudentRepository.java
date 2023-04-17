package com.tpe.repository;

import com.tpe.domain.Student;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;


public interface StudentRepository {

    void save(Student student);

    List<Student> findAll();

    Optional<Student> findById(Long id);//nullpointer exception almamak icin optional kullandik
//null yerine bos bir optional objesi doner exceptiondan kurtuluruz

    void delete(Long id);


}
