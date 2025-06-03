package com.veiculos.config;

import com.veiculos.model.Categoria;
import com.veiculos.model.Usuario;
import com.veiculos.model.Veiculo;
import com.veiculos.service.CategoriaService;
import com.veiculos.service.UsuarioService;
import com.veiculos.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário administrador
        if (!usuarioService.existsByEmail("admin@veiculos.com")) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@veiculos.com");
            admin.setSenha("admin123");
            admin.setRole("ADMIN");
            usuarioService.save(admin);
        }



        // Criar categorias
        if (categoriaService.findAll().isEmpty()) {
            Categoria caminhoes = new Categoria("Caminhões");
            Categoria camionetes = new Categoria("Camionetes");
            Categoria suv = new Categoria("SUV");
            Categoria passeio = new Categoria("Veículos de Passeio");
            Categoria motos = new Categoria("Motocicletas");

            categoriaService.save(caminhoes);
            categoriaService.save(camionetes);
            categoriaService.save(suv);
            categoriaService.save(passeio);
            categoriaService.save(motos);

            // Criar veículos de exemplo
            Veiculo veiculo1 = new Veiculo();
            veiculo1.setPlaca("ABC1234");
            veiculo1.setCor("Branco");
            veiculo1.setModelo("Civic");
            veiculo1.setMarca("Honda");
            veiculo1.setAno(2022);
            veiculo1.setDescricao("Honda Civic 2022 em excelente estado de conservação. Veículo automático com câmbio CVT, motor 2.0 i-VTEC de 155cv. Equipado com ar-condicionado digital, central multimídia com Android Auto e Apple CarPlay, sensor de estacionamento traseiro, câmera de ré, freios ABS com EBD, controle de estabilidade e tração, airbags frontais e laterais. Interior em couro bege, bancos com regulagem elétrica, volante multifuncional com comandos de áudio. Pneus novos, revisões em dia na concessionária. Aceita financiamento e troca.");
            veiculo1.setImagem("/images/veiculos/honda-civic.jpg");
            veiculo1.setCategoria(passeio);
            veiculoService.save(veiculo1);

            Veiculo veiculo2 = new Veiculo();
            veiculo2.setPlaca("DEF5678");
            veiculo2.setCor("Prata");
            veiculo2.setModelo("Hilux");
            veiculo2.setMarca("Toyota");
            veiculo2.setAno(2023);
            veiculo2.setDescricao("Toyota Hilux SW4 2023 SRX 4x4 Diesel Automática. Motor 2.8 turbo diesel de 204cv com torque de 51kgfm. Câmbio automático de 6 marchas, tração 4x4 com reduzida e bloqueio do diferencial. Equipada com ar-condicionado dual zone, central multimídia de 9 polegadas com GPS, câmera 360°, sensores de estacionamento dianteiro e traseiro, controle de descida, assistente de partida em rampa, 7 airbags, bancos em couro com aquecimento, teto solar panorâmico, rodas de liga leve 18 polegadas. Veículo zero km, com garantia de fábrica. Ideal para trabalho e lazer.");
            veiculo2.setImagem("/images/veiculos/toyota-hilux.jpg");
            veiculo2.setCategoria(camionetes);
            veiculoService.save(veiculo2);

            Veiculo veiculo3 = new Veiculo();
            veiculo3.setPlaca("GHI9012");
            veiculo3.setCor("Preto");
            veiculo3.setModelo("CR-V");
            veiculo3.setMarca("Honda");
            veiculo3.setAno(2021);
            veiculo3.setDescricao("Honda CR-V Touring 2021 AWD (tração integral) em perfeito estado. Motor 1.5 turbo de 190cv, câmbio CVT com paddle shifts. Sistema de tração inteligente AWD para todas as condições de terreno. Equipado com Honda Sensing (pacote de segurança ativa), frenagem automática de emergência, alerta de colisão frontal, assistente de permanência em faixa, controle de cruzeiro adaptativo. Interior premium com bancos em couro perfurado, ar-condicionado automático dual zone, teto solar elétrico, sistema de som premium com 12 alto-falantes, carregador wireless para celular. Porta-malas amplo de 589 litros. Único dono, manual e chave reserva.");
            veiculo3.setImagem("/images/veiculos/honda-crv.jpg");
            veiculo3.setCategoria(suv);
            veiculoService.save(veiculo3);

            Veiculo veiculo4 = new Veiculo();
            veiculo4.setPlaca("JKL3456");
            veiculo4.setCor("Azul");
            veiculo4.setModelo("Scania R450");
            veiculo4.setMarca("Scania");
            veiculo4.setAno(2020);
            veiculo4.setDescricao("Scania R450 A6x2 2020 - Cavalo mecânico para transporte de cargas pesadas. Motor DC13 de 450cv e 2300Nm de torque, câmbio automatizado Opticruise de 12 marchas com retarder integrado. Cabine Highline com ar-condicionado, cama beliche, geladeira, micro-ondas, sistema de entretenimento com GPS e câmera de ré. Freios a disco nas rodas dianteiras e traseiras com ABS, controle de estabilidade RSC, sistema de monitoramento de pressão dos pneus. Tanque de combustível de 400 litros, quinta roda JOST, suspensão pneumática traseira. Veículo revisado, pneus novos, pronto para trabalhar. Aceita consórcio e financiamento para autônomos.");
            veiculo4.setImagem("/images/veiculos/scania-r450.jpg");
            veiculo4.setCategoria(caminhoes);
            veiculoService.save(veiculo4);

            Veiculo veiculo5 = new Veiculo();
            veiculo5.setPlaca("MNO7890");
            veiculo5.setCor("Vermelho");
            veiculo5.setModelo("CB 600F");
            veiculo5.setMarca("Honda");
            veiculo5.setAno(2019);
            veiculo5.setDescricao("Honda CB 600F Hornet 2019 - Motocicleta naked esportiva em estado impecável. Motor 4 cilindros em linha de 599cc, 102cv de potência máxima, injeção eletrônica PGM-FI. Freios CBS (Combined Brake System) com discos de 296mm na dianteira e 220mm na traseira, suspensão dianteira telescópica invertida e traseira Pro-Link com amortecedor regulável. Painel digital completo com computador de bordo, farol em LED, pneus Michelin novos, escape original Honda. Moto revisada na concessionária, com manual do proprietário, chave reserva e nota fiscal. Ideal para uso urbano e viagens, combina performance e economia. Aceita moto de menor valor como parte do pagamento.");
            veiculo5.setImagem("/images/veiculos/honda-cb600f.jpg");
            veiculo5.setCategoria(motos);
            veiculoService.save(veiculo5);
        }
    }
} 