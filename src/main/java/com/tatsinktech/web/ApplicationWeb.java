package com.tatsinktech.web;


//import com.tatsinktech.web.export.register.XmlViewComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
public class ApplicationWeb {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class, args);

    }

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

//    @Bean
//    public Jaxb2Marshaller jaxb2Marshaller() {
//
//        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//        jaxb2Marshaller.setClassesToBeBound(new Class[]{XmlViewComponent.class});
//        return jaxb2Marshaller;
//    }
}
