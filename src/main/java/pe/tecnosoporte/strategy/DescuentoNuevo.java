package pe.tecnosoporte.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import pe.tecnosoporte.model.TipoCliente;

@Component
public class DescuentoNuevo implements DescuentoStrategy {
    public TipoCliente tipoCliente() { return TipoCliente.NUEVO; }
    public BigDecimal porcentaje() { return BigDecimal.ZERO; }
}
