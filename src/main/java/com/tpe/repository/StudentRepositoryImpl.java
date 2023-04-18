package com.tpe.repository;

import com.tpe.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Component//bu clasdan bir obje olusturabilirdik
@Repository//ntre mimaride component isini yapar.Bu class dbyle erisecek dedik ve obje olusumu sagladik.componentin gelismisi
 public class StudentRepositoryImpl implements StudentRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Student student) {
       Session session =sessionFactory.openSession();
       Transaction tx =session.beginTransaction();

       session.saveOrUpdate(student);//varsa update yoksa save yapar

       tx.commit();
       session.close();
      // sessionFactory.close();//ben degil spring kapatacak.O olusturdu cunku
    }

    @Override
    public List<Student> findAll() {
       Session session =sessionFactory.openSession();
       Transaction tx =session.beginTransaction();

       List<Student> studentList=session.createQuery("from Student",Student.class).getResultList();

       tx.commit();
       session.close();

        return studentList;
    }

    @Override
    public Optional<Student> findById(Long id) {

       Session session =sessionFactory.openSession();
       Transaction tx =session.beginTransaction();

      Student student=session.get(Student.class,id);
       //student objesi var ise dondur yoksa ici bos optional objesi dondur.Exception verme
      Optional<Student> optional=Optional.ofNullable(student);

       tx.commit();
       session.close();

       return optional;
    }

    @Override
    public void delete(Long id) {

        Session session =sessionFactory.openSession();
        Transaction tx =session.beginTransaction();

        Student student=session.load(Student.class,id);//silme icin get degil load getirdim proxy obje verecek
        session.delete(student);

        tx.commit();
        session.close();


    }
}
