package pe.tecnosoporte.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Integra Spring MVC, Bean Validation, Strategy y renderizado real de Thymeleaf. */
@SpringBootTest
@AutoConfigureMockMvc
class CotizacionWebTest {
    @Autowired private MockMvc mvc;

    @Test
    void muestraFormularioDinamico() throws Exception {
        mvc.perform(get("/cotizaciones/nueva"))
                .andExpect(status().isOk()).andExpect(view().name("cotizacion"))
                .andExpect(content().string(containsString("Diagnóstico de computadora")))
                .andExpect(content().string(containsString("action=\"/cotizaciones\"")))
                .andExpect(content().string(containsString("data-discount=\"15\"")));
    }

    @Test
    void inicioRedirigeAlFormulario() throws Exception {
        mvc.perform(get("/")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cotizaciones/nueva"));
    }

    @ParameterizedTest
    @CsvSource({"DIAGNOSTICO,2,NUEVO,80.00,0.00,80.00",
            "MANTENIMIENTO,2,FRECUENTE,140.00,14.00,126.00",
            "INSTALACION,3,INSTITUCIONAL,150.00,22.50,127.50"})
    void casosMinimosDelEnunciado(String servicio, String cantidad, String tipo,
                                 String subtotal, String descuento, String total) throws Exception {
        mvc.perform(post("/cotizaciones").param("nombre", "Cliente de prueba")
                        .param("servicio", servicio).param("cantidad", cantidad).param("tipoCliente", tipo))
                .andExpect(status().isOk()).andExpect(view().name("resumen"))
                .andExpect(model().attributeHasNoErrors("solicitud"))
                .andExpect(content().string(containsString("id=\"resultado-subtotal\">S/ " + subtotal + "</dd>")))
                .andExpect(content().string(containsString("id=\"resultado-descuento\">− S/ " + descuento + "</dd>")))
                .andExpect(content().string(containsString("id=\"resultado-total\">S/ " + total + "</strong>")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void validaNombreVacio(String nombre) throws Exception {
        mvc.perform(post("/cotizaciones").param("nombre", nombre).param("servicio", "DIAGNOSTICO")
                        .param("cantidad", "2").param("tipoCliente", "NUEVO"))
                .andExpect(status().isOk()).andExpect(view().name("cotizacion"))
                .andExpect(model().attributeHasFieldErrors("solicitud", "nombre"))
                .andExpect(content().string(containsString("Ingresa el nombre del cliente.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "6", "-1", "1.5", "abc", "", "999999999999999"})
    void validaCantidadEnServidor(String cantidad) throws Exception {
        mvc.perform(post("/cotizaciones").param("nombre", "Ana")
                        .param("servicio", "DIAGNOSTICO").param("cantidad", cantidad).param("tipoCliente", "NUEVO"))
                .andExpect(status().isOk()).andExpect(view().name("cotizacion"))
                .andExpect(model().attributeHasFieldErrors("solicitud", "cantidad"))
                .andExpect(content().string(not(containsString("id=\"resultado-total\""))));
    }

    @ParameterizedTest
    @CsvSource({"servicio,INVENTADO", "servicio,''", "tipoCliente,VIP", "tipoCliente,''"})
    void rechazaOpcionesManipuladas(String campo, String valor) throws Exception {
        var request = post("/cotizaciones").param("nombre", "Ana").param("cantidad", "2");
        request.param("servicio", campo.equals("servicio") ? valor : "DIAGNOSTICO");
        request.param("tipoCliente", campo.equals("tipoCliente") ? valor : "NUEVO");
        mvc.perform(request).andExpect(status().isOk()).andExpect(view().name("cotizacion"))
                .andExpect(model().attributeHasFieldErrors("solicitud", campo));
    }

    @Test
    void validaNombreLargo() throws Exception {
        mvc.perform(post("/cotizaciones").param("nombre", "a".repeat(101))
                        .param("servicio", "DIAGNOSTICO").param("cantidad", "1").param("tipoCliente", "NUEVO"))
                .andExpect(model().attributeHasFieldErrors("solicitud", "nombre"));
    }

    @Test
    void ignoraPreciosYDescuentosEnviadosPorElNavegador() throws Exception {
        mvc.perform(post("/cotizaciones").param("nombre", "Ana").param("servicio", "MANTENIMIENTO")
                        .param("cantidad", "2").param("tipoCliente", "FRECUENTE")
                        .param("precio", "0.01").param("porcentaje", "100").param("total", "0.00"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id=\"resultado-total\">S/ 126.00</strong>")));
    }

    @Test
    void escapaHtmlEnElNombreDelCliente() throws Exception {
        mvc.perform(post("/cotizaciones").param("nombre", "<script>alert(1)</script>")
                        .param("servicio", "DIAGNOSTICO").param("cantidad", "1").param("tipoCliente", "NUEVO"))
                .andExpect(status().isOk()).andExpect(view().name("resumen"))
                .andExpect(content().string(containsString("&lt;script&gt;alert(1)&lt;/script&gt;")))
                .andExpect(content().string(not(containsString("<script>alert(1)</script>"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/servicios", "/ayuda", "/css/app.css", "/js/app.js", "/img/icons.svg"})
    void sirvePaginasYRecursos(String ruta) throws Exception {
        mvc.perform(get(ruta)).andExpect(status().isOk());
    }

    @Test
    void healthCheckParaRender() throws Exception {
        mvc.perform(get("/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("UP"));
    }
}
