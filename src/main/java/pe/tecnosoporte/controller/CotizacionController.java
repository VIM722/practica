package pe.tecnosoporte.controller;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pe.tecnosoporte.model.ServicioTecnico;
import pe.tecnosoporte.model.SolicitudCotizacion;
import pe.tecnosoporte.model.TipoCliente;
import pe.tecnosoporte.service.CotizacionService;
import pe.tecnosoporte.strategy.DescuentoContexto;

@Controller
public class CotizacionController {
    private final CotizacionService cotizaciones;
    private final DescuentoContexto descuentos;

    public CotizacionController(CotizacionService cotizaciones, DescuentoContexto descuentos) {
        this.cotizaciones = cotizaciones;
        this.descuentos = descuentos;
    }

    @InitBinder("solicitud")
    void configurarFormulario(WebDataBinder binder) {
        binder.setAllowedFields("nombre", "servicio", "cantidad", "tipoCliente");
    }

    @ModelAttribute("servicios")
    ServicioTecnico[] servicios() { return ServicioTecnico.values(); }

    @ModelAttribute("tiposCliente")
    TipoCliente[] tiposCliente() { return TipoCliente.values(); }

    @ModelAttribute("porcentajes")
    Map<String, BigDecimal> porcentajes() {
        Map<String, BigDecimal> porcentajes = new LinkedHashMap<>();
        for (TipoCliente tipo : TipoCliente.values()) {
            porcentajes.put(tipo.name(), descuentos.obtener(tipo).porcentaje());
        }
        return porcentajes;
    }

    @GetMapping("/")
    String inicio() { return "redirect:/cotizaciones/nueva"; }

    @GetMapping("/cotizaciones/nueva")
    String formulario(Model model) {
        model.addAttribute("solicitud", new SolicitudCotizacion());
        model.addAttribute("seccion", "cotizar");
        return "cotizacion";
    }

    @PostMapping("/cotizaciones")
    String cotizar(@Valid @ModelAttribute("solicitud") SolicitudCotizacion solicitud,
                   BindingResult errores, Model model) {
        model.addAttribute("seccion", "cotizar");
        if (errores.hasErrors()) { return "cotizacion"; }
        model.addAttribute("cotizacion", cotizaciones.cotizar(solicitud));
        return "resumen";
    }

    @GetMapping("/servicios")
    String catalogo(Model model) { model.addAttribute("seccion", "servicios"); return "servicios"; }

    @GetMapping("/ayuda")
    String ayuda(Model model) { model.addAttribute("seccion", "ayuda"); return "ayuda"; }
}
