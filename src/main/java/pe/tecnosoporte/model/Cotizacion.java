package pe.tecnosoporte.model;

import java.math.BigDecimal;

/** Resultado inmutable; esta práctica no almacena datos personales. */
public record Cotizacion(String nombreCliente, ServicioTecnico servicio, int cantidad,
                         TipoCliente tipoCliente, BigDecimal subtotal, BigDecimal porcentaje,
                         BigDecimal descuento, BigDecimal total) {
}
