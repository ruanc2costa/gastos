package com.rizo.gastos.controller;

import com.rizo.gastos.dto.DespesaDTO;
import com.rizo.gastos.service.DespesaService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @GetMapping
    public ResponseEntity<Page<DespesaDTO>> list(Pageable pageable){
        return ResponseEntity.ok(despesaService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> findById(Long id){
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
        return ResponseEntity.noContent().build();
    }
}
