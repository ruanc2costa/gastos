package com.rizo.gastos.service;

import com.rizo.gastos.dto.UsuarioDTO;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    public Page<UsuarioDTO> list(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioDTO::new);
    }

    public Usuario findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return usuario;


    }

    public Usuario findByIdDespesa(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setIdade(usuarioDTO.getIdade());
        usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    public ResponseEntity<Void> delete(Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
