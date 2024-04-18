package com.rizo.gastos.controller;

import com.rizo.gastos.dto.DespesaDTO;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import com.rizo.gastos.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable  Long id){
        return ResponseEntity.ok(despesaService.findById(id));
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<DespesaDTO> create(@PathVariable  Long clienteId, @RequestBody DespesaDTO despesaDTO){
        return ResponseEntity.ok(despesaService.create(clienteId, despesaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> update(@PathVariable Long id, DespesaDTO despesaDTO){
        return ResponseEntity.ok(despesaService.update(id, despesaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        despesaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ResponseEntity<List<DespesaDTO>> listByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserId = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(UserId).get(0);
        return ResponseEntity.ok(despesaService.listByUser(usuario.getId()));
    }
}
