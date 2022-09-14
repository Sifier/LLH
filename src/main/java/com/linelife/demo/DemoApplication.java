package com.linelife.demo;

import com.linelife.demo.controller.AuthenticationController;
import com.linelife.demo.controller.ConstantController;
import com.linelife.demo.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
