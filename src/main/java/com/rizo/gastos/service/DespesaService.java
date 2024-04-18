package com.rizo.gastos.service;

import com.rizo.gastos.dto.DespesaDTO;
import com.rizo.gastos.model.Despesa;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.DespesaRepository;
import com.rizo.gastos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DespesaDTO create(Long usuarioId, DespesaDTO despesaDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Despesa despesa = modelMapper.map(despesaDTO, Despesa.class);
        despesa.setUsuario(usuario);
        despesaRepository.save(despesa);
        return modelMapper.map(despesa, DespesaDTO.class);
    }

    public DespesaDTO findById(Long id){
        return new DespesaDTO(despesaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada")));
    }

    public Page<DespesaDTO> list(Pageable pageable){
        return despesaRepository.findAll(pageable).map(DespesaDTO::new);
    }

    public DespesaDTO update(Long id, DespesaDTO despesaDTO){
        Despesa despesa = despesaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));
        despesa.setNome(despesaDTO.getNome());
        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setData(despesaDTO.getData());
        despesa.setCategoria(despesaDTO.getCategoria());
        return new DespesaDTO(despesaRepository.save(despesa));
    }

    public void delete(Long id){
        despesaRepository.deleteById(id);
    }

    public List<DespesaDTO> listByUser(Long usuarioId){
        return despesaRepository.findByUsuarioId(usuarioId).stream()
                .map(despesa -> modelMapper.map(despesa, DespesaDTO.class))
                .collect(Collectors.toList());
    }
}
