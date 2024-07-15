package com.rizo.gastos.repository;

import com.rizo.gastos.model.PendenteUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PendenteUsuarioRepository extends JpaRepository<PendenteUsuario, Long> {
    List<PendenteUsuario> findByValidationToken(String validationToken);
    List<PendenteUsuario> findByEmail(String email);
}
// Compare this snippet from UsuarioRepository.java: