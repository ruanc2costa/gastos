package com.rizo.gastos.dto;

import com.rizo.gastos.model.Cartao;
import com.rizo.gastos.model.Despesa;

import java.math.BigDecimal;
import java.util.List;

public class CartaoDTO {
    private Long id;
    private String nome;
    private String banco;
    private BigDecimal limite;

    private List<DespesaDTO> despesasDTO;
    public CartaoDTO() {
    }

    public CartaoDTO(Cartao cartao) {
        this.id = cartao.getId();
        this.nome = cartao.getNome();
        this.banco = cartao.getBanco();
        this.limite = cartao.getLimite();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBanco() {
        return banco;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
