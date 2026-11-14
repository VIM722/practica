package pe.tecnosoporte.model;

import java.math.BigDecimal;

/** Catálogo autorizado: el navegador nunca determina el precio cobrado. */
public enum ServicioTecnico {
    DIAGNOSTICO("Diagnóstico de computadora", "Identificamos el origen del problema de tu equipo.", "40.00", "monitor"),
    MANTENIMIENTO("Mantenimiento preventivo", "Cuidamos tu equipo para mantener su rendimiento.", "70.00", "tool"),
    INSTALACION("Instalación de software", "Dejamos tus programas instalados y listos para usar.", "50.00", "download");

    private final String nombre;
    private final String descripcion;
    private final BigDecimal precio;
    private final String icono;

    ServicioTecnico(String nombre, String descripcion, String precio, String icono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = new BigDecimal(precio);
        this.icono = icono;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public String getIcono() { return icono; }
}
