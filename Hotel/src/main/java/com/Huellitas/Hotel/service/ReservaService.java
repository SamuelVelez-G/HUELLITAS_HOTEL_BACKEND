package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.ReservaRequestDTO;
import com.Huellitas.Hotel.dto.ReservaResponseDTO;
import com.Huellitas.Hotel.dto.UsuarioResumenDTO;
import com.Huellitas.Hotel.model.Reserva;
import com.Huellitas.Hotel.model.Usuario;
import com.Huellitas.Hotel.repository.ReservaRepository;
import com.Huellitas.Hotel.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public ReservaResponseDTO crearReserva(ReservaRequestDTO datos) {

        Usuario usuario = usuarioRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

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
    public Optional<ReservaResponseDTO> buscarReservaId(Long id) {
        return reservaRepository.findById(id)
                .map(this::mapearAReservaResponseDTO);
    }

    private ReservaResponseDTO mapearAReservaResponseDTO(Reserva reserva) {
        UsuarioResumenDTO usuarioResumen = new UsuarioResumenDTO(
                reserva.getUsuario().getId(),
                reserva.getUsuario().getNombre()
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