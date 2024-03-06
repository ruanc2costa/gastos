package com.rizo.gastos.model;

import com.rizo.gastos.dto.DespesaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity(name = "despesa")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nome;
    private String descricao;
    @NotNull
    private BigDecimal valor;
    private String data;
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Despesa() {
    }

    public Despesa(Long id, String nome, String descricao, BigDecimal valor, String data, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public Despesa(DespesaDTO despesaDTO) {
        this.id = despesaDTO.getId();
        this.nome = despesaDTO.getNome();
        this.descricao = despesaDTO.getDescricao();
        this.valor = despesaDTO.getValor();
        this.data = despesaDTO.getData();
        this.categoria = despesaDTO.getCategoria();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
