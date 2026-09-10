package com.Huellitas.Hotel.service;

import com.Huellitas.Hotel.dto.Detalle_reservaRequestDTO;
import com.Huellitas.Hotel.dto.Detalle_reservaResponseDTO;
import com.Huellitas.Hotel.dto.Detalle_reservaResumenDTO;
import com.Huellitas.Hotel.model.DetalleReserva;
import com.Huellitas.Hotel.model.Reserva;
import com.Huellitas.Hotel.model.Servicio;
import com.Huellitas.Hotel.model.Mascota;
import com.Huellitas.Hotel.repository.DetalleReservaRepository;
import com.Huellitas.Hotel.repository.MascotaRepository;
import com.Huellitas.Hotel.repository.ReservaRepository;
import com.Huellitas.Hotel.repository.ServicioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Detalle_reservaService {

    private final DetalleReservaRepository detalleRepo;
    private final ReservaRepository reservaRepo;
    private final MascotaRepository mascotaRepo;
    private final ServicioRepository servicioRepo;

    @Transactional
    public Detalle_reservaResponseDTO crear(Detalle_reservaRequestDTO dto) {
        Reserva reserva = reservaRepo.findById(dto.getReservaId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + dto.getReservaId()));
        Mascota mascota = mascotaRepo.findById(dto.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada: " + dto.getMascotaId()));
        Servicio servicio = servicioRepo.findById(dto.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + dto.getServicioId()));

        DetalleReserva detalle = new DetalleReserva();
        detalle.setReserva(reserva);
        detalle.setMascota(mascota);
        detalle.setServicio(servicio);
        detalle.setTipoDeServicio(servicio.getNombre());
        detalle.setCantidadDeMascotas(dto.getCantidadDeMascotas());

        double precioUnitario = dto.getPrecioUnitario() != null ? dto.getPrecioUnitario() : servicio.getPrecio();
        detalle.setPrecioUnitario(precioUnitario);
        double subtotal = precioUnitario * dto.getCantidadDeMascotas();
        detalle.setSubtotal(subtotal);

        DetalleReserva saved = detalleRepo.save(detalle);

        // actualizar total de la reserva
        reserva.setTotal(reserva.getTotal() + subtotal);
        reserva.getDetallesReserva().add(saved);
        reservaRepo.save(reserva);

        return toResponse(saved);
    }

    public Detalle_reservaResponseDTO obtenerPorId(Long id) {
        DetalleReserva d = detalleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado: " + id));
        return toResponse(d);
    }

    public List<Detalle_reservaResumenDTO> listarPorReserva(Long reservaId) {
        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + reservaId));
        return reserva.getDetallesReserva().stream().map(this::toResumen).collect(Collectors.toList());
    }

    @Transactional
    public void eliminar(Long id) {
        DetalleReserva d = detalleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado: " + id));
        Reserva reserva = d.getReserva();
        // restar subtotal
        reserva.setTotal(reserva.getTotal() - d.getSubtotal());
        reserva.getDetallesReserva().removeIf(det -> det.getId().equals(id));
        reservaRepo.save(reserva);
        detalleRepo.deleteById(id);
    }

    private Detalle_reservaResponseDTO toResponse(DetalleReserva d) {
        Detalle_reservaResponseDTO r = new Detalle_reservaResponseDTO();
        r.setId(d.getId());
        r.setTipoDeServicio(d.getTipoDeServicio());
        r.setCantidadDeMascotas(d.getCantidadDeMascotas());
        r.setPrecioUnitario(d.getPrecioUnitario());
        r.setSubtotal(d.getSubtotal());
        r.setMascotaId(d.getMascota() != null ? d.getMascota().getId() : null);
        r.setServicioId(d.getServicio() != null ? d.getServicio().getId() : null);
        r.setReservaId(d.getReserva() != null ? d.getReserva().getId() : null);
        return r;
    }

    private Detalle_reservaResumenDTO toResumen(DetalleReserva d) {
        Detalle_reservaResumenDTO s = new Detalle_reservaResumenDTO();
        s.setId(d.getId());
        s.setTipoDeServicio(d.getTipoDeServicio());
        s.setCantidadDeMascotas(d.getCantidadDeMascotas());
        s.setSubtotal(d.getSubtotal());
        s.setServicioId(d.getServicio() != null ? d.getServicio().getId() : null);
        s.setServicioNombre(d.getServicio() != null ? d.getServicio().getNombre() : null);
        return s;
    }
}
