package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.exception.ResourceNotFoundExceptionn;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller//clasin controller clasini oldugunu belirtir
@RequestMapping("/students")
//http://localhost:8080/SpringMCC/Students seklinde gelen istekleri bu class karsilasin dedik
//class ya da method uzerinde kullanilabilir.Class uzerinde kullarsak classdaki tum methodlar icin methodda sadece o method
public class StudentController {

    @Autowired
    private StudentService service;

    //requesti http metodu ile yapacagiz.Ne istiyoruz
    //Controllerden gelen requeste gore geriye ModelAndView objesi(data+view name) veya String tipinde view name dondurulur
    @GetMapping("/hi")//http://localhost:8080/SpringMCC/Students/hi seklinde gelen sorgulari bu method karsilasin
    //bu method geriye ModelAndView objesi donecek
    public ModelAndView sayHi() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Hi;");
        mav.addObject("messagebody", "I am Student Management System");
        mav.setViewName("hi");//hi.jsp dosyasini dondurecek
        return mav;
    }
    //view resolver mav icindeki view name gore hi.jsp dosyasini bulur.
    //mav icindeki datayi hi.jsp icerisine bind eder.
    //kullaniciya sunar

//1-Student Creation
//kullanicidan bilgileri almak icin form gosterelim

    @GetMapping("/new")//http://localhost:8080/SpringMCC/Students/new
    public String sendStudentForm(@ModelAttribute("student") Student student) {//student tipinde obje olustur formdan bunu al
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
    public String createStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        //@Valid Student clasindaki constraintleri valide et diyor.notblank,notempty,notnull gibi
        //zaten studenta yonlendirecegi modelattribute icine "student" yazmayabiliriz
        //Save icin once service o da bizi repoya gonderecek
        //StudentServicein save methodu lazim
        if (bindingResult.hasErrors()) {
            return "studentForm";
        }
        service.saveStudent(student);
        service.getAll();
        return "redirect:/students";//bu link listeyi gosteriyordu yeni liste olusturma bu linke git/yonlen
    }

    //read:
    @GetMapping//http://localhost:8080/SpringMCC/Students
    public ModelAndView listAllStudent() {
        List<Student> students = service.getAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("studentList", students);//studentListe studentslari koy
        mav.setViewName("students");//students.jsp//bunlarida students.jsp ye bind et
        return mav;
    }


 /*   @GetMapping("/update")  //update http://localhost:8080/SpringMCC/Students/update?id=4
    public ModelAndView showForUpdate(@RequestParam("id") Long id) {//param olarak id gelsin verilen idyi bulsun ?id=id olacak
//nicin string degil de modelandvie yaptim.Cunku save yaparken bos form geliyor ama burada string + data geliyor.
        //ben gelen bu datalari update edecegim.Datada da geldigi icin modelandview
        Student foundstudent = service.getStudentById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("student", foundstudent);//studentformda student modeline foundstudenti bind etme
        mav.setViewName("studentForm");
        return mav;
    }*/

    @GetMapping("/update")  //update http://localhost:8080/SpringMCC/Students/update?id=4
    public String showForUpdate(@RequestParam("id") Long id, Model model) {
        Student foundstudent = service.getStudentById(id);
        model.addAttribute("student", foundstudent);
        return "studentForm";
    }


    @GetMapping("/delete/{id}")//delete http://localhost:8080/SpringMCC/Students/delete/1
    //path variable icin {} ifadesini kullandim.Simdi pathVariable annotation kullanacagiz
    public String deleteStudent(@PathVariable("id") Long id) {
        service.deleteStudent(id);
        return "redirect:/students";


    }
//birden fazla degiskeni endpoindte kullanacaksak request param tek degisken varsa path variable kullanalim.


    //Exception Handle
    //delete http://localhost:8080/SpringMCC/Students/delete/9 9 id yoksa hata verecek
    @ExceptionHandler(ResourceNotFoundExceptionn.class)//try islevi gorur.Yakalanmasini istedigim exceptionu yazdim
    public ModelAndView handleResourceNotFoundException(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ex.getMessage());//catch islevi
        mav.setViewName("notFound");
        return mav;
    }
    //ExceptionHandler belirtilen exception sinifi icin bir method belirlememizi saglar.
    //bu metod exceptionu yakalar ve ozel bir islem uygular.
    //burada eception varsa notfound.jsp nin gosterilmesini uygulayacagiz


    //restful service:tüm kayıtları döndüren:http://localhost:8080/SpringMVC/students/restAll
    @GetMapping("/restAll")
    @ResponseBody//responseun doğrudan HTTPye json olarak yazılmasını sağlıyor.
    public List<Student> getAllStudents(){
        List<Student> studentList=service.getAll();
        return studentList;
    }

}
