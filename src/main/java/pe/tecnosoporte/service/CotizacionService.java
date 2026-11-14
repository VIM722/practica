package pe.tecnosoporte.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import pe.tecnosoporte.model.Cotizacion;
import pe.tecnosoporte.model.SolicitudCotizacion;
import pe.tecnosoporte.strategy.DescuentoContexto;
import pe.tecnosoporte.strategy.DescuentoStrategy;

@Service
public class CotizacionService {
    private final DescuentoContexto descuentos;

    public CotizacionService(DescuentoContexto descuentos) { this.descuentos = descuentos; }

    public Cotizacion cotizar(SolicitudCotizacion solicitud) {
        BigDecimal subtotal = solicitud.getServicio().getPrecio()
                .multiply(BigDecimal.valueOf(solicitud.getCantidad())).setScale(2, RoundingMode.HALF_UP);
        DescuentoStrategy estrategia = descuentos.obtener(solicitud.getTipoCliente());
        BigDecimal descuento = estrategia.calcular(subtotal);
        return new Cotizacion(solicitud.getNombre(), solicitud.getServicio(), solicitud.getCantidad(),
                solicitud.getTipoCliente(), subtotal, estrategia.porcentaje(), descuento,
                subtotal.subtract(descuento));
    }
}
