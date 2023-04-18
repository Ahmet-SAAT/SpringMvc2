package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller//clasin controller clasini oldugunu belirtir
@RequestMapping("/students")//http://localhost:8080/SpringMCC/Students seklinde gelen istekleri bu class karsilasin dedik
//class ya da method uzerinde kullanilabilir.Class uzerinde kullarsak classdaki tum methodlar icin methodda sadece o method
public class StudentController {

    @Autowired
    private StudentService service;

    //requesti http metodu ile yapacagiz.Ne istiyoruz
    //Controllerden gelen requeste gore geriye ModelAndView objesi(data+view name) veya String tipinde view name dondurulur
    @GetMapping("/hi")//http://localhost:8080/SpringMCC/Students/hi seklinde gelen sorgulari bu method karsilasin
    //bu method geriye ModelAndView objesi donecek
    public ModelAndView sayHi(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message","Hi;");
        mav.addObject("messagebody","I am Student Management System");
        mav.setViewName("hi");//hi.jsp dosyasini dondurecek
        return mav;
    }
    //view resolver mav icindeki view name gore hi.jsp dosyasini bulur.
    //mav icindeki datayi hi.jsp icerisine bind eder.
    //kullaniciya sunar

//1-Student Creation
//kullanicidan bilgileri almak icin form gosterelim

@GetMapping("/new")//http://localhost:8080/SpringMCC/Students/new
    public String sendStudentForm(@ModelAttribute("student")Student student){//student tipinde obje olustur formdan bunu al
        //studentForm.jsp de student adinda modelattribute var onunla eslestirdik.
        return "studentForm";//studentForm.jsp kullaniciya gosterilecek
}
//@ModelAttribute annatationu UI ile gelen studentformdaki input bilgilerle student tipinde bir obje olusturur.
// .
    //Yani view ile controller arasinda data transferini sagliyor.

    //formun submit butonuna tikladigimizda http://localhost:8080/SpringMCC/Students/saveStudent, ve post metodu olacak
    //studentForm.jsp de modelattributedan sonra action saveStudent,method post diyor.Bunun metodunu olusturacagiz

    //kaydetme isleminden sonra tum listeyi de gosterelim
    @PostMapping("/saveStudent")
    public String createStudent(@ModelAttribute("student") Student student){
        //zaten studenta yonlendirecegi modelattribute icine "student" yazmayabiliriz
        //Save icin once service o da bizi repoya gonderecek
        //StudentServicein save methodu lazim

        service.saveStudent(student);
        service.getAll();
        return "redirect:/students";//bu link listeyi gosteriyordu yeni liste olusturma bu linke git/yonlen
    }
    //read:
    @GetMapping//http://localhost:8080/SpringMCC/Students
    public ModelAndView listAllStudent(){
        List<Student> students =service.getAll();
        ModelAndView mav=new ModelAndView();
        mav.addObject("studentList",students);//studentListe studentslari koy
        mav.setViewName("students");//students.jsp//bunlarida students.jsp ye bind et
        return  mav;
    }

//tum studentlari listeleyen request: http://localhost:8080/SpringMCC/Students
}
