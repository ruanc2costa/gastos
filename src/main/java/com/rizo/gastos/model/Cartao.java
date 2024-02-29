package com.rizo.gastos.model;

import com.rizo.gastos.dto.CartaoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "cartao")
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String banco;
    @NotNull
    private BigDecimal limite;

    @OneToMany
    @JoinColumn(name = "despesa_id")
    private List<Despesa> despesas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cartao() {
    }

    public Cartao(Long id, String nome, String banco, BigDecimal limite) {
        this.id = id;
        this.nome = nome;
        this.banco = banco;
        this.limite = limite;
    }

    public Cartao(CartaoDTO cartaoDTO) {
        this.id = cartaoDTO.getId();
        this.nome = cartaoDTO.getNome();
        this.banco = cartaoDTO.getBanco();
        this.limite = cartaoDTO.getLimite();
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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
