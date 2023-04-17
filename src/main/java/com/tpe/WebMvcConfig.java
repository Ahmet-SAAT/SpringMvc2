package com.tpe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration//bu classda configuration ayarlari yapacagim
@ComponentScan("com.tpe")//opsiyonel "com.tpe" defaultta class hangi package altindaysa onlari tarar
@EnableWebMvc//MVC için config etkinleştirmek(Springmvcye ozel)
public class WebMvcConfig implements WebMvcConfigurer {//static sorgular icin wwebservice

    //controller bize respondu string data halinde  gonderecek bunu view view resolver ceviriyordu
    @Bean//view name e karşılık gelen view dosyasının çözümlenmesi:viewresolver
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver=new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);//JavaStandartTagLibrary:JSP dosyaları içinde daha az kod yazmamızı sağlar
        resolver.setPrefix("/WEB-INF/views/");//view dosyaları nerde(dizin)
        resolver.setSuffix(".jsp");//view dosyalarının uzantısı
        return resolver;
    }

    //css,image gibi statik olan kaynakların dispatchera gönderilmesine gerek yoktu,web service yeterliydi
    //exp  : http://localhost:8080/SpringMvc2/resources/css/edu.jpeg
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").//bu pathdeki kaynakları statik olarak algila
                addResourceLocations("/resources/").//kaynakların yeri
                setCachePeriod(0);//cacheleme için belirli bir periyod süresi verilebilir.
    }
}
