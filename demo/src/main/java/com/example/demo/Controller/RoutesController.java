package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RoutesController {


    @GetMapping("/")
    public String Index() {
        return "index";
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
