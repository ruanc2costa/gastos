package com.rizo.gastos.controller;

import com.rizo.gastos.dto.CartaoDTO;
import com.rizo.gastos.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @GetMapping
    public ResponseEntity<Page<CartaoDTO>> list(Pageable pageable){
        return ResponseEntity.ok(cartaoService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoDTO> findById(Long id){
        return ResponseEntity.ok(cartaoService.findById(id));
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<CartaoDTO> create(Long clienteId, CartaoDTO cartaoDTO){
        return ResponseEntity.ok(cartaoService.create(clienteId, cartaoDTO));
    }


}
