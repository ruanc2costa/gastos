package com.rizo.gastos.service;

import com.rizo.gastos.dto.UsuarioDTO;
import com.rizo.gastos.dto.DespesaDTO;
import com.rizo.gastos.model.Despesa;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Page<UsuarioDTO> list(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Primeiro, mapeamos o usuário para UsuarioDTO sem as despesas
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

        // Se houver despesas, as mapeamos separadamente e as definimos no UsuarioDTO
        if (usuario.getDespesas() != null && !usuario.getDespesas().isEmpty()) {
            List<DespesaDTO> despesaDTOList = usuario.getDespesas().stream()
                    .map(despesa -> modelMapper.map(despesa, DespesaDTO.class))
                    .collect(Collectors.toList());
            usuarioDTO.setDespesas(despesaDTOList);
        } else {
            // Se não houver despesas, podemos definir a lista de despesas do DTO como uma lista vazia
            usuarioDTO.setDespesas(new ArrayList<>());
        }

        return usuarioDTO;
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
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public ResponseEntity<Void> delete(Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
