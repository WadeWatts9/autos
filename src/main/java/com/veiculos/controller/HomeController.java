package com.veiculos.controller;

import com.veiculos.model.Categoria;
import com.veiculos.model.Veiculo;
import com.veiculos.service.CategoriaService;
import com.veiculos.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        List<Veiculo> veiculos = veiculoService.findAll();
        
        model.addAttribute("categorias", categorias);
        model.addAttribute("veiculos", veiculos);
        return "index";
    }

    @GetMapping("/veiculos")
    public String listarVeiculos(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        List<Veiculo> veiculos = veiculoService.findAll();
        
        model.addAttribute("categorias", categorias);
        model.addAttribute("veiculos", veiculos);
        return "veiculos";
    }

    @GetMapping("/categoria/{id}")
    public String veiculosPorCategoria(@PathVariable Long id, Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        List<Veiculo> veiculos = veiculoService.findByCategoriaId(id);
        Optional<Categoria> categoriaAtual = categoriaService.findById(id);
        
        model.addAttribute("categorias", categorias);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("categoriaAtual", categoriaAtual.orElse(null));
        return "veiculos";
    }

    @GetMapping("/veiculo/{id}")
    public String detalhesVeiculo(@PathVariable Long id, Model model) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        List<Categoria> categorias = categoriaService.findAll();
        
        if (veiculo.isPresent()) {
            model.addAttribute("veiculo", veiculo.get());
            model.addAttribute("categorias", categorias);
            return "veiculo-detalhes";
        } else {
            return "redirect:/veiculos";
        }
    }

    @GetMapping("/buscar/modelo")
    public String buscarPorModelo(@RequestParam String modelo, Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        List<Veiculo> veiculos = veiculoService.findByModelo(modelo);
        
        model.addAttribute("categorias", categorias);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("termoBusca", modelo);
        model.addAttribute("tipoBusca", "modelo");
        return "veiculos";
    }

    @GetMapping("/buscar/ano")
    public String buscarPorAno(@RequestParam String ano, Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        List<Veiculo> veiculos;
        
        try {
            Integer anoInt = Integer.parseInt(ano);
            veiculos = veiculoService.findByAno(anoInt);
        } catch (NumberFormatException e) {
            veiculos = List.of(); // Lista vazia se o ano não for válido
        }
        
        model.addAttribute("categorias", categorias);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("termoBusca", ano);
        model.addAttribute("tipoBusca", "ano");
        return "veiculos";
    }
} 