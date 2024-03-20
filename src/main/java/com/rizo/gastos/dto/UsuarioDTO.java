package com.rizo.gastos.dto;

import com.rizo.gastos.model.Cartao;
import com.rizo.gastos.model.Despesa;
import com.rizo.gastos.model.Usuario;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private int idade;
    private List<CartaoDTO> cartoesDTO;

    private List<DespesaDTO> despesas;


    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.idade = usuario.getIdade();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<CartaoDTO> getCartoesDTO() {
        return cartoesDTO;
    }

    public List<DespesaDTO> getDespesasDTO() {
        return despesas != null ? despesas.stream()
                .map(despesa -> new DespesaDTO(despesa.getId(), despesa.getNome(), despesa.getDescricao(), despesa.getValor(),
                        despesa.getData() == null ? "Valor padrão ou tratamento para null" : despesa.getData(), despesa.getCategoria()))
                .collect(Collectors.toList()) : null;
    }

    public void setCartoesDTO(List<CartaoDTO> cartoesDTO) {
        this.cartoesDTO = cartoesDTO;
    }

    public void setDespesas(List<DespesaDTO> despesas) {
        this.despesas = despesas;
    }

}