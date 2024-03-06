package com.rizo.gastos.service;

import com.rizo.gastos.dto.CartaoDTO;
import com.rizo.gastos.model.Cartao;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.CartaoRepository;
import com.rizo.gastos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CartaoDTO create(Long usuarioId, CartaoDTO cartaoDTO){
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Cartao cartao = modelMapper.map(cartaoDTO, Cartao.class);
        cartao.setUsuario(usuario);
        cartaoRepository.save(cartao);
        return modelMapper.map(cartao, CartaoDTO.class);
    }

    public CartaoDTO findById(Long id){
       Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado"));
         return modelMapper.map(cartao, CartaoDTO.class);
    }

    public Page<CartaoDTO> list(Pageable pageable){
        return cartaoRepository.findAll(pageable).map(cartao -> modelMapper.map(cartao, CartaoDTO.class));
    }

    public CartaoDTO update(Long id, CartaoDTO cartaoDTO){
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado"));
        cartao.setNome(cartaoDTO.getNome());
        cartao.setBanco(cartaoDTO.getBanco());
        cartao.setLimite(cartaoDTO.getLimite());
        return modelMapper.map(cartaoRepository.save(cartao), CartaoDTO.class);
    }

    public void delete(Long id){
        cartaoRepository.deleteById(id);
    }
}
