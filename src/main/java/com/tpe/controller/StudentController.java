package com.tpe.controller;

import com.tpe.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller//clasin controller clasini oldugunu belirtir
@RequestMapping("/students")//http://localhost:8080/SpringMCC/Students seklinde gelen istekleri burada karsilasin dedik
//class ya da method uzerinde kullanilabilir.Class uzerinde kullarsak classdaki tum methodlar icin methodda sadece o method
public class StudentController {

    //requesti http metodu ile yapacagiz.Ne istiyoruz
    //Controllerden gelen requeste gore geriye ModelAndView objesi(data+view name) veya String tipinde view name dondurulur
    @GetMapping()//http://localhost:8080/SpringMCC/Students seklinde gelen sorgulari bu method karsilasin
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
//@ModelAttribute annatationu studentformdaki bilgilerle studen tipinde bir obje olusturup bu objenin kullanilmasi saglar.
    //Yani view ile controller arasinda data transferini sagliyor.

    //formun submit butonuna tikladigimizda http://localhost:8080/SpringMCC/Students/saveStudent, ve post metodu olacak

    //kaydetme isleminden sonra tum listeyi de gosterelim
    @PostMapping("/saveStudent")
    public String createStudent(@ModelAttribute("student") Student student){
        //zaten studenta yonlendriecegi modelattribute icine "student" yazmayabiliriz


        return null;
    }

}
