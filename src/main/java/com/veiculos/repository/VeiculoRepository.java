package com.veiculos.repository;

import com.veiculos.model.Veiculo;
import com.veiculos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    
    List<Veiculo> findByCategoria(Categoria categoria);
    
    List<Veiculo> findByCategoriaId(Long categoriaId);
    
    @Query("SELECT v FROM Veiculo v WHERE LOWER(v.modelo) LIKE LOWER(CONCAT('%', :modelo, '%'))")
    List<Veiculo> findByModeloContainingIgnoreCase(@Param("modelo") String modelo);
    
    List<Veiculo> findByAno(Integer ano);
    
    boolean existsByPlaca(String placa);
    
    @Query("SELECT v FROM Veiculo v WHERE v.ano = :ano")
    List<Veiculo> findVeiculosByAno(@Param("ano") Integer ano);
} 