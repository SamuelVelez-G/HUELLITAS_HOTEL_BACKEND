package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.Detalle_reservaRequestDTO;
import com.Huellitas.Hotel.dto.Detalle_reservaResponseDTO;
import com.Huellitas.Hotel.dto.Detalle_reservaResumenDTO;

import java.util.List;

public interface Detalle_reservaService {
    Detalle_reservaResponseDTO crear(Detalle_reservaRequestDTO dto);
    Detalle_reservaResponseDTO obtenerPorId(Long id);
    List<Detalle_reservaResumenDTO> listarPorReserva(Long reservaId);
    void eliminar(Long id);
}
