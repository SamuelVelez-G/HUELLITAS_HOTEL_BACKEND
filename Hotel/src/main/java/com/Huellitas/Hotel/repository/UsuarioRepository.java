package com.Huellitas.Hotel.repository;

public interface UsuarioRepository {
import com.Huellitas.Hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

}
