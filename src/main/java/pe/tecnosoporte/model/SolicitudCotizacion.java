package pe.tecnosoporte.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SolicitudCotizacion {
    @NotBlank(message = "Ingresa el nombre del cliente.")
    @Size(max = 100, message = "El nombre debe tener como máximo 100 caracteres.")
    private String nombre;

    @NotNull(message = "Selecciona un servicio técnico.")
    private ServicioTecnico servicio;

    @NotNull(message = "Ingresa una cantidad entera de 1 a 5.")
    @Min(value = 1, message = "La cantidad mínima es 1.")
    @Max(value = 5, message = "La cantidad máxima es 5.")
    private Integer cantidad = 1;

    @NotNull(message = "Selecciona el tipo de cliente.")
    private TipoCliente tipoCliente = TipoCliente.NUEVO;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre == null ? null : nombre.strip(); }
    public ServicioTecnico getServicio() { return servicio; }
    public void setServicio(ServicioTecnico servicio) { this.servicio = servicio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }
}
