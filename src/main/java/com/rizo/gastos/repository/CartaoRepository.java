package com.rizo.gastos.repository;

import com.rizo.gastos.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{
}
