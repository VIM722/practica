package pe.tecnosoporte.model;

public enum TipoCliente {
    NUEVO("Nuevo", "Tu primera atención con nosotros"),
    FRECUENTE("Frecuente", "Un beneficio por volver a elegirnos"),
    INSTITUCIONAL("Institucional", "Atención para tu organización");

    private final String nombre;
    private final String descripcion;

    TipoCliente(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}
