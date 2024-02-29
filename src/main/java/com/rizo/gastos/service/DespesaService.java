package com.rizo.gastos.service;

import com.rizo.gastos.dto.DespesaDTO;
import com.rizo.gastos.model.Despesa;
import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.DespesaRepository;
import com.rizo.gastos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DespesaDTO create(Long usuarioId, DespesaDTO despesaDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + usuarioId));

        Despesa despesa = new Despesa();
        despesa.setNome(despesaDTO.getNome());
        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setData(despesaDTO.getData()); // Ajuste conforme o formato da sua data
        despesa.setCategoria(despesaDTO.getCategoria());
        despesa.setUsuario(usuario);

        despesa = despesaRepository.save(despesa);

        return new DespesaDTO(despesa.getId(), despesa.getNome(), despesa.getDescricao(),
                despesa.getValor(), despesa.getData().toString(), despesa.getCategoria());
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
}
