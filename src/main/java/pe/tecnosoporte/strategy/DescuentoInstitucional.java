package pe.tecnosoporte.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import pe.tecnosoporte.model.TipoCliente;

@Component
public class DescuentoInstitucional implements DescuentoStrategy {
    public TipoCliente tipoCliente() { return TipoCliente.INSTITUCIONAL; }
    public BigDecimal porcentaje() { return new BigDecimal("15"); }
}
