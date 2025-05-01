package com.mscpcr.mscpcr.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        // registry.addViewController("/role_assign").setViewName("role_assign");
        // registry.addViewController("/add_venue").setViewName("add_venue");
        // registry.addViewController("/add_natureOfStuff").setViewName("add_natureOfStaff");
        // registry.addViewController("/addProgram").setViewName("addProgram");
        // registry.addViewController("/resourcePerson").setViewName("resourcePerson");
        // registry.addViewController("/userForm").setViewName("userForm");

//        registry.addViewController("/Menus").setViewName("Menus");

    }

}