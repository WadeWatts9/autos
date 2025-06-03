package com.veiculos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    private final String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images" + File.separator + "veiculos" + File.separator;

    @PostMapping("/image")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        
        try {
            System.out.println("=== UPLOAD DE IMAGEM ===");
            System.out.println("Arquivo recebido: " + (file != null ? file.getOriginalFilename() : "null"));
            System.out.println("Tamanho: " + (file != null ? file.getSize() : 0) + " bytes");
            System.out.println("Diretório de upload: " + uploadDir);
            
            if (file.isEmpty()) {
                response.put("error", "Arquivo não selecionado");
                return ResponseEntity.badRequest().body(response);
            }

            // Verificar se é uma imagem
            String contentType = file.getContentType();
            System.out.println("Content-Type: " + contentType);
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("error", "Arquivo deve ser uma imagem");
                return ResponseEntity.badRequest().body(response);
            }

            // Criar diretório se não existir
            Path uploadPath = Paths.get(uploadDir);
            System.out.println("Caminho absoluto: " + uploadPath.toAbsolutePath());
            if (!Files.exists(uploadPath)) {
                System.out.println("Criando diretório...");
                Files.createDirectories(uploadPath);
            }

            // Gerar nome único para o arquivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;
            System.out.println("Nome do arquivo gerado: " + filename);

            // Salvar arquivo
            Path filePath = uploadPath.resolve(filename);
            System.out.println("Salvando em: " + filePath.toAbsolutePath());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Verificar se o arquivo foi salvo
            if (Files.exists(filePath)) {
                System.out.println("Arquivo salvo com sucesso!");
            } else {
                System.out.println("ERRO: Arquivo não foi salvo!");
            }

            // Retornar caminho da imagem
            String imagePath = "/images/veiculos/" + filename;
            response.put("imagePath", imagePath);
            response.put("success", "Imagem enviada com sucesso");
            response.put("filename", filename);
            
            System.out.println("Retornando caminho: " + imagePath);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            System.out.println("ERRO ao salvar arquivo: " + e.getMessage());
            e.printStackTrace();
            response.put("error", "Erro ao salvar arquivo: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 