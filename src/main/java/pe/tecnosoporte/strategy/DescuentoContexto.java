package pe.tecnosoporte.strategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import pe.tecnosoporte.model.TipoCliente;

/** Spring descubre las estrategias. No hay if/switch por tipo en el controlador. */
@Component
public class DescuentoContexto {
    private final Map<TipoCliente, DescuentoStrategy> estrategias = new EnumMap<>(TipoCliente.class);

    public DescuentoContexto(List<DescuentoStrategy> disponibles) {
        for (DescuentoStrategy estrategia : disponibles) {
            if (estrategias.putIfAbsent(estrategia.tipoCliente(), estrategia) != null) {
                throw new IllegalStateException("Estrategia duplicada para " + estrategia.tipoCliente());
            }
        }
        for (TipoCliente tipo : TipoCliente.values()) {
            if (!estrategias.containsKey(tipo)) {
                throw new IllegalStateException("Falta una estrategia para " + tipo);
            }
        }
    }

    public DescuentoStrategy obtener(TipoCliente tipo) {
        if (tipo == null || !estrategias.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo de cliente no válido.");
        }
        return estrategias.get(tipo);
    }
}
