package com.rizo.gastos.controller;

import com.rizo.gastos.dto.LoginRequestDTO;
import com.rizo.gastos.dto.RegisterRequestDTO;
import com.rizo.gastos.dto.ResponseDTO;
import com.rizo.gastos.dto.UsuarioDTO;
import com.rizo.gastos.infra.security.TokenService;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import com.rizo.gastos.service.UsuarioService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginRequestDTO body) {
        List<Usuario> usuario = usuarioRepository.findByEmail(body.email());

        if (passwordEncoder.matches(body.password(), usuario.get(0).getSenha())){
            String token = tokenService.generateToken(usuario.get(0));
            return ResponseEntity.ok(new ResponseDTO(token, usuario.get(0).getNome()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity RegisterUser(@RequestBody RegisterRequestDTO body) {
        List<Usuario> usuario = usuarioRepository.findByEmail(body.email());

        if (usuario.isEmpty()) {
            Usuario newUser = new Usuario();
            newUser.setEmail(body.email());
            newUser.setNome(body.name());
            newUser.setSenha(passwordEncoder.encode(body.password()));
            return ResponseEntity.ok(usuarioRepository.save(newUser));
        } else {
            return ResponseEntity.badRequest().body("User already exists");
        }


    }
}
