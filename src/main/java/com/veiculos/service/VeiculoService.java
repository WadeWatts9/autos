package com.veiculos.service;

import com.veiculos.model.Veiculo;
import com.veiculos.model.Categoria;
import com.veiculos.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    
    @Autowired
    private VeiculoRepository veiculoRepository;
    
    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }
    
    public Optional<Veiculo> findById(Long id) {
        return veiculoRepository.findById(id);
    }
    
    public List<Veiculo> findByCategoria(Categoria categoria) {
        return veiculoRepository.findByCategoria(categoria);
    }
    
    public List<Veiculo> findByCategoriaId(Long categoriaId) {
        return veiculoRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Veiculo> findByModelo(String modelo) {
        return veiculoRepository.findByModeloContainingIgnoreCase(modelo);
    }
    
    public List<Veiculo> findByAno(Integer ano) {
        return veiculoRepository.findByAno(ano);
    }
    
    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }
    
    public Veiculo update(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }
    
    public void deleteById(Long id) {
        veiculoRepository.deleteById(id);
    }
    
    public boolean existsByPlaca(String placa) {
        return veiculoRepository.existsByPlaca(placa);
    }
} 