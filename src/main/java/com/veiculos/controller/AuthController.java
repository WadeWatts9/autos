package com.veiculos.controller;

import com.veiculos.model.Usuario;
import com.veiculos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("usuario") Usuario usuario, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "auth/register";
        }

        if (usuarioService.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Este email já está cadastrado");
            return "auth/register";
        }

        try {
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso! Faça login para continuar.");
            return "redirect:/login";
        } catch (Exception e) {
            result.rejectValue("email", "error.usuario", "Erro ao cadastrar usuário");
            return "auth/register";
        }
    }
} 