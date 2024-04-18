package com.rizo.gastos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rizo.gastos.model.Cartao;
import com.rizo.gastos.model.Despesa;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String data;
    private String categoria;

    private CartaoDTO cartaoDTO;
    public DespesaDTO() {
    }

    public DespesaDTO(Despesa despesa) {
        this.id = despesa.getId();
        this.nome = despesa.getNome();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getData();
        this.categoria = despesa.getCategoria();
    }

    public DespesaDTO(Long id, String nome, String descricao, BigDecimal valor, String string, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.data = string;
        this.categoria = categoria;
    }

    public DespesaDTO(Object o) {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
