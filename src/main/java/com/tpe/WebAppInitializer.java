package com.tpe;


// web.xml yerine bu clasi kullanacagiz.Configurasyon ayarlarimizi yapacagiz
//dispatcher servletin(front controller) tanimlanmasi,konfigurasyonun tanimlanmasi

//bu classi extend ettigimizde dispatcher servletin baslar ve config ayarlarin bulundugu dosyanin yerini gosterir
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //bu metodlarin isini onceden web.xml de yapar.Cok fazla karmasiktir ama daha guvenli olabilirler.


    //Dispatcher:
    // Servlet WebAppContext->Controller,Handler Mapping,View Resolver
    //Root Web AppContext->Service,Repository


    @Override//Dbye erisim icin gerekli  config classi belirtecegiz
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                RootContexConfig.class
        };
    }


    @Override//Controller,Handler Mapping,View Resolver ile ilgili config clasini belirtecegiz
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                WebMvcConfig.class//config classimi belirttim
        };
    }


    @Override//Dispatcher servlet hangi url ile gelen requestleri karsilayacak bunu belirtecegiz
    protected String[] getServletMappings() {
        return new String[]{"/"};//slash pathi ile gelen url leri request olarak kabul et dedik
    }
}
