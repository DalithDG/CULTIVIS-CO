package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class RoutesController {


    @GetMapping("/")
    public String Index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping ("/product")
    public String Producto(){
        return "product";
    }

    @GetMapping ("/product-detall")
    public String ProductoDetall(){
        return "product-detall";
    }

    

}
