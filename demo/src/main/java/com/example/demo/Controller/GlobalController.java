package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
    
    @ModelAttribute("usuarioLogueado")
    public Object agregarUsuario(HttpSession session) {
        return session.getAttribute("usuarioLogueado");
    }
    
}
