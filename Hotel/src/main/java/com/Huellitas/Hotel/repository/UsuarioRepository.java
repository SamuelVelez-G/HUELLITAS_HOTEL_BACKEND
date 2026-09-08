package com.Huellitas.Hotel.repository;

import com.Huellitas.Hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

}
