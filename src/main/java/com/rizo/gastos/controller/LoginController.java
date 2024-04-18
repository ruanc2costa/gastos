package com.rizo.gastos.controller;

import com.rizo.gastos.dto.UsuarioDTO;
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

@RestController
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        Usuario savedUsuario = null;
        ResponseEntity response = null;
        try{
            String hashPwd = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(hashPwd);
            savedUsuario = usuarioRepository.save(usuario);
            if(savedUsuario.getId() > 0){
                response = ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso");
            }
        } catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu uma exceção devido a " + e.getMessage());
        }
        return response;
    }

}
