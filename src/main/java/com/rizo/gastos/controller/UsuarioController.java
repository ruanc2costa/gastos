package com.rizo.gastos.controller;

import com.rizo.gastos.dto.UsuarioDTO;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> list(Pageable pageable){
        return ResponseEntity.ok(usuarioService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.create(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.update(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }


}
