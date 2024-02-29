package com.rizo.gastos.service;

import com.rizo.gastos.dto.CartaoDTO;
import com.rizo.gastos.model.Cartao;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.CartaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public CartaoDTO create(Long clienteId, CartaoDTO cartaoDTO){
        Usuario usuario = usuarioService.findByIdDespesa(clienteId);
        Cartao cartao = new Cartao(cartaoDTO);
        usuario.getCartoes().add(cartao);
        cartaoRepository.save(cartao);
        cartao.setUsuario(usuario);
        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    public CartaoDTO findById(Long id){
        return new CartaoDTO(cartaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado")));
    }

    public Page<CartaoDTO> list(Pageable pageable){
        return cartaoRepository.findAll(pageable).map(CartaoDTO::new);
    }

    public CartaoDTO update(Long id, CartaoDTO cartaoDTO){
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado"));
        cartao.setNome(cartaoDTO.getNome());
        cartao.setBanco(cartaoDTO.getBanco());
        cartao.setLimite(cartaoDTO.getLimite());
        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    public void delete(Long id){
        cartaoRepository.deleteById(id);
    }
}
