package com.rizo.gastos.repository;
import com.rizo.gastos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
}
