package pe.tecnosoporte.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pe.tecnosoporte.model.Cotizacion;
import pe.tecnosoporte.model.ServicioTecnico;
import pe.tecnosoporte.model.SolicitudCotizacion;
import pe.tecnosoporte.model.TipoCliente;
import pe.tecnosoporte.strategy.DescuentoContexto;
import pe.tecnosoporte.strategy.DescuentoFrecuente;
import pe.tecnosoporte.strategy.DescuentoInstitucional;
import pe.tecnosoporte.strategy.DescuentoNuevo;
import pe.tecnosoporte.strategy.DescuentoStrategy;

class CotizacionServiceTest {
    private final List<DescuentoStrategy> estrategias = List.of(
            new DescuentoNuevo(), new DescuentoFrecuente(), new DescuentoInstitucional());
    private final DescuentoContexto contexto = new DescuentoContexto(estrategias);
    private final CotizacionService servicio = new CotizacionService(contexto);

    static Stream<Arguments> combinaciones() {
        return Stream.of(ServicioTecnico.values()).flatMap(servicio ->
                Stream.of(TipoCliente.values()).flatMap(tipo -> IntStream.rangeClosed(1, 5)
                        .mapToObj(cantidad -> Arguments.of(servicio, tipo, cantidad))));
    }

    @ParameterizedTest(name = "{0} · {1} · cantidad {2}")
    @MethodSource("combinaciones")
    void calculaLas45Combinaciones(ServicioTecnico tecnico, TipoCliente tipo, int cantidad) {
        SolicitudCotizacion solicitud = solicitud(tecnico, tipo, cantidad);
        Cotizacion resultado = servicio.cotizar(solicitud);
        BigDecimal precio = switch (tecnico) {
            case DIAGNOSTICO -> new BigDecimal("40.00");
            case MANTENIMIENTO -> new BigDecimal("70.00");
            case INSTALACION -> new BigDecimal("50.00");
        };
        BigDecimal factor = switch (tipo) {
            case NUEVO -> new BigDecimal("1.00");
            case FRECUENTE -> new BigDecimal("0.90");
            case INSTITUCIONAL -> new BigDecimal("0.85");
        };
        BigDecimal esperado = precio.multiply(BigDecimal.valueOf(cantidad)).multiply(factor);
        assertThat(resultado.total()).isEqualByComparingTo(esperado);
        assertThat(resultado.subtotal().subtract(resultado.descuento())).isEqualTo(resultado.total());
        assertThat(resultado.total().scale()).isEqualTo(2);
        assertThat(resultado.nombreCliente()).isEqualTo("Cliente de prueba");
    }

    @Test
    void laPoliticaSeIntercambiaSinModificarElServicio() {
        DescuentoStrategy promocion = new DescuentoStrategy() {
            public TipoCliente tipoCliente() { return TipoCliente.FRECUENTE; }
            public BigDecimal porcentaje() { return new BigDecimal("20"); }
        };
        CotizacionService alternativo = new CotizacionService(new DescuentoContexto(
                List.of(new DescuentoNuevo(), promocion, new DescuentoInstitucional())));
        assertThat(alternativo.cotizar(solicitud(ServicioTecnico.MANTENIMIENTO,
                TipoCliente.FRECUENTE, 2)).total()).isEqualByComparingTo("112.00");
    }

    @Test
    void rechazaEstrategiasDuplicadas() {
        assertThatThrownBy(() -> new DescuentoContexto(List.of(new DescuentoNuevo(), new DescuentoNuevo())))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("duplicada");
    }

    @Test
    void rechazaConfiguracionSinEstrategiaParaUnTipo() {
        assertThatThrownBy(() -> new DescuentoContexto(List.of(new DescuentoNuevo())))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("Falta");
    }

    @Test
    void rechazaTipoNulo() {
        assertThatThrownBy(() -> contexto.obtener(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void redondeaDescuentosAMonedasDeDosDecimales() {
        assertThat(new DescuentoInstitucional().calcular(new BigDecimal("0.10")))
                .isEqualByComparingTo("0.02");
    }

    private SolicitudCotizacion solicitud(ServicioTecnico tecnico, TipoCliente tipo, int cantidad) {
        SolicitudCotizacion solicitud = new SolicitudCotizacion();
        solicitud.setNombre("  Cliente de prueba  ");
        solicitud.setServicio(tecnico);
        solicitud.setTipoCliente(tipo);
        solicitud.setCantidad(cantidad);
        return solicitud;
    }
}
