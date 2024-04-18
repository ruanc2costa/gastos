package com.rizo.gastos.repository;
import com.rizo.gastos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    List<Usuario> findByEmail(String username);
}
