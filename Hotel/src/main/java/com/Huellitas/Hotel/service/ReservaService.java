package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.ReservaRequestDTO;
import com.Huellitas.Hotel.dto.ReservaResponseDTO;
import com.Huellitas.Hotel.dto.UsuarioResponseDTO;
import com.Huellitas.Hotel.dto.UsuarioResumenDTO;
import com.Huellitas.Hotel.model.Reserva;
import com.Huellitas.Hotel.model.Usuario;
import com.Huellitas.Hotel.repository.ReservaRepository;
import com.Huellitas.Hotel.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ReservaResponseDTO crearReserva(ReservaRequestDTO datos) {
        Usuario usuario = usuarioRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + datos.usuarioId()));

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setFechaInicio(datos.fechaInicio());
        reserva.setFechaFin(datos.fechaFin());
        reserva.setTotal(datos.total());

        Reserva creada = reservaRepository.save(reserva);
        return mapearAReservaResponseDTO(creada);
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> buscarReservas() {
        return reservaRepository.findAll()
                .stream()
                .map(this::mapearAReservaResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaResponseDTO buscarReservaId(Long id) {
        return reservaRepository.findById(id)
                .map(this::mapearAReservaResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));
    }

    private ReservaResponseDTO mapearAReservaResponseDTO(Reserva reserva) {
        Usuario usuario = reserva.getUsuario();

        UsuarioResumenDTO usuarioResumen = new UsuarioResumenDTO(
                usuario.getId(),
                usuario.getNombre()
        );

        return new ReservaResponseDTO(
                reserva.getId(),
                usuarioResumen,
                reserva.getFechaReserva(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getTotal()
        );
    }
}