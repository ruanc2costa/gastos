package com.rizo.gastos.controller;

import com.rizo.gastos.dto.LoginRequestDTO;
import com.rizo.gastos.dto.RegisterRequestDTO;
import com.rizo.gastos.dto.ResponseDTO;
import com.rizo.gastos.infra.security.SecurityFilter;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.infra.security.TokenService;
import com.rizo.gastos.repository.UsuarioRepository;
import com.rizo.gastos.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO body) {
        List<Usuario> usuarios = usuarioRepository.findByEmail(body.email());

        if (usuarios.isEmpty() || !passwordEncoder.matches(body.password(), usuarios.get(0).getSenha())) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = usuarios.get(0);
        String token = tokenService.generateToken(usuario);
        String nome = usuario.getEmail();
        log.info("Nome do usuário: " + nome);
        // Certifique-se de que o nome do usuário está correto
        return ResponseEntity.ok(new ResponseDTO(token, nome));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO body) {
        List<Usuario> usuarios = usuarioRepository.findByEmail(body.email());

        if (usuarios.isEmpty()) {
            Usuario newUser = new Usuario();
            newUser.setEmail(body.email());
            newUser.setNome(body.name());
            newUser.setSenha(passwordEncoder.encode(body.password()));
            newUser.setRole("ROLE_USER"); // Defina um role padrão, como "ROLE_USER"
            usuarioRepository.save(newUser);
            // Enviar e-mail de verificação aqui
            emailService.sendSimpleEmail(body.email(), "Email de verificação", "Clique no link para verificar seu e-mail: http://localhost:8080/auth/validate?token=" + UUID.randomUUID().toString());
            return ResponseEntity.ok("Usuário registrado com sucesso. Verifique seu e-mail para ativar a conta.");
        } else {
            return ResponseEntity.badRequest().body("User already exists");
        }
    }
}
