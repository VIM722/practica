package pe.tecnosoporte.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import pe.tecnosoporte.model.TipoCliente;

@Component
public class DescuentoFrecuente implements DescuentoStrategy {
    public TipoCliente tipoCliente() { return TipoCliente.FRECUENTE; }
    public BigDecimal porcentaje() { return new BigDecimal("10"); }
}
