# Integrantes y responsabilidades

La asignación sigue el orden comunicado por el equipo. Las conclusiones se formulan a partir del rol y de los componentes del sistema; cada integrante debe revisarlas para su sustentación.

| Orden | Integrante | Rol asignado | Componentes relacionados |
| --- | --- | --- | --- |
| 1 | Huaranca Echevarria, Airton Denilson | Configuración de Spring Boot, estructura y modelos | `pom.xml`, `application.properties` y `model/` |
| 2 | Diaz Trillo, Piero Alonso | Controladores GET y POST, recepción y validación de datos | `CotizacionController`, `SolicitudCotizacion` y `messages.properties` |
| 3 | Palomares Santana, Maria Fernanda | Vistas HTML, diseño de interfaz e integración con Thymeleaf | `templates/`, `static/css/` y `static/js/` |
| 4 | Mateo Chuquispuma, Victor Toribio | Patrón Strategy, estrategias concretas y cálculo de descuentos | `strategy/` y `CotizacionService` |
| 5 | Tasayco Ronceros, Patricia Georgina | Pruebas funcionales, integración y coordinación documental | `src/test/`, `scripts/verificar_web.py` y `docs/evidencias/` |

## Conclusiones individuales por rol

### Huaranca Echevarria, Airton Denilson

Desde el rol de configuración y modelos, concluyo que una estructura clara y un catálogo definido en el servidor permiten mantener consistentes los precios y los datos de la cotización. El aspecto de mayor cuidado es conservar la compatibilidad entre Java, Spring Boot y las dependencias. La organización de `model/` y el uso de `BigDecimal` muestran cómo el diseño inicial influye en la precisión y el mantenimiento del sistema.

### Diaz Trillo, Piero Alonso

Desde el rol de controladores y validación, concluyo que las rutas GET y POST deben coordinar el flujo sin incorporar las fórmulas del negocio. El principal cuidado técnico es manejar tanto las restricciones de los datos como los errores de conversión de cantidades y categorías. La combinación de `@Valid`, `BindingResult` y mensajes en español permite devolver un formulario comprensible y evitar cotizaciones con entradas inválidas.

### Palomares Santana, Maria Fernanda

Desde el rol de vistas e interfaz, concluyo que Thymeleaf permite presentar el catálogo y los resultados en coherencia con el modelo del servidor. El aspecto de mayor cuidado es conservar la legibilidad en escritorio y móvil y asociar cada error con su campo. La vista previa facilita el uso, mientras que el resumen generado por Java comunica el subtotal, el descuento y el total definitivo.

### Mateo Chuquispuma, Victor Toribio

Desde el rol de Strategy y cálculo, concluyo que separar las políticas de descuento en clases intercambiables facilita ampliar el sistema sin agregar condiciones al controlador. El cuidado principal es seleccionar una sola política por categoría y mantener una regla monetaria uniforme. El contexto de estrategias, la inyección de Spring y el redondeo con `BigDecimal` permiten obtener resultados verificables y reducir el acoplamiento de las reglas del negocio.

### Tasayco Ronceros, Patricia Georgina

Desde el rol de pruebas e integración documental, concluyo que la verificación debe cubrir resultados válidos, entradas incorrectas y el funcionamiento de la URL pública. El aspecto de mayor cuidado es conservar la correspondencia entre el código, los resultados observados y las capturas. Las pruebas automáticas, las 16 comprobaciones HTTP públicas y las evidencias del navegador permiten respaldar el informe y preparar una demostración reproducible.

## Aplicación publicada

[Abrir TecnoSoporte](https://tecnosoporte-pc3.onrender.com/cotizaciones/nueva).

Quedan por completar únicamente la universidad y el número de grupo en los datos académicos del informe.
