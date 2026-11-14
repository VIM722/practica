package pe.tecnosoporte.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import pe.tecnosoporte.model.TipoCliente;

/** Contrato intercambiable para las políticas de descuento. */
public interface DescuentoStrategy {
    TipoCliente tipoCliente();
    BigDecimal porcentaje();

    default BigDecimal calcular(BigDecimal subtotal) {
        return subtotal.multiply(porcentaje()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}
