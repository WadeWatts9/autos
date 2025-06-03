package com.veiculos.controller;

import com.veiculos.model.Categoria;
import com.veiculos.model.Veiculo;
import com.veiculos.service.CategoriaService;
import com.veiculos.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Veiculo> veiculos = veiculoService.findAll();
        List<Categoria> categorias = categoriaService.findAll();
        
        model.addAttribute("totalVeiculos", veiculos.size());
        model.addAttribute("totalCategorias", categorias.size());
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("categorias", categorias);
        
        return "admin/dashboard";
    }

    // Gerenciamento de Veículos
    @GetMapping("/veiculos")
    public String listarVeiculos(Model model) {
        List<Veiculo> veiculos = veiculoService.findAll();
        model.addAttribute("veiculos", veiculos);
        return "admin/veiculos/lista";
    }

    @GetMapping("/veiculos/novo")
    public String novoVeiculo(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("veiculo", new Veiculo());
        model.addAttribute("categorias", categorias);
        return "admin/veiculos/form";
    }

    @PostMapping("/veiculos/salvar")
    public String salvarVeiculo(@Valid @ModelAttribute("veiculo") Veiculo veiculo, 
                               BindingResult result, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "admin/veiculos/form";
        }

        if (veiculo.getId() == null && veiculoService.existsByPlaca(veiculo.getPlaca())) {
            result.rejectValue("placa", "error.veiculo", "Esta placa já está cadastrada");
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "admin/veiculos/form";
        }

        try {
            veiculoService.save(veiculo);
            redirectAttributes.addFlashAttribute("successMessage", "Veículo salvo com sucesso!");
            return "redirect:/admin/veiculos";
        } catch (Exception e) {
            result.rejectValue("placa", "error.veiculo", "Erro ao salvar veículo");
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "admin/veiculos/form";
        }
    }

    @GetMapping("/veiculos/editar/{id}")
    public String editarVeiculo(@PathVariable Long id, Model model) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        List<Categoria> categorias = categoriaService.findAll();
        
        if (veiculo.isPresent()) {
            model.addAttribute("veiculo", veiculo.get());
            model.addAttribute("categorias", categorias);
            return "admin/veiculos/form";
        } else {
            return "redirect:/admin/veiculos";
        }
    }

    @GetMapping("/veiculos/excluir/{id}")
    public String excluirVeiculo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            veiculoService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir veículo!");
        }
        return "redirect:/admin/veiculos";
    }

    // Gerenciamento de Categorias
    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "admin/categorias/lista";
    }

    @GetMapping("/categorias/nova")
    public String novaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias/form";
    }

    @PostMapping("/categorias/salvar")
    public String salvarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "admin/categorias/form";
        }

        if (categoria.getId() == null && categoriaService.existsByNome(categoria.getNome())) {
            result.rejectValue("nome", "error.categoria", "Esta categoria já existe");
            return "admin/categorias/form";
        }

        try {
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria salva com sucesso!");
            return "redirect:/admin/categorias";
        } catch (Exception e) {
            result.rejectValue("nome", "error.categoria", "Erro ao salvar categoria");
            return "admin/categorias/form";
        }
    }

    @GetMapping("/categorias/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        Optional<Categoria> categoria = categoriaService.findById(id);
        
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "admin/categorias/form";
        } else {
            return "redirect:/admin/categorias";
        }
    }

    @GetMapping("/categorias/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir categoria! Verifique se não há veículos associados.");
        }
        return "redirect:/admin/categorias";
    }
} 