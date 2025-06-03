package com.veiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logoutPage() {
        // Se alguém acessar /logout via GET, redireciona para a página principal
        return "redirect:/";
    }
} 