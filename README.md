# TecnoSoporte · PC3 de Arquitectura de Software

Aplicación web real de cotización de servicios técnicos desarrollada con **Java 17, Spring Boot 3.5.16, Spring MVC, Thymeleaf y Strategy**. El cálculo y la validación se ejecutan en el servidor Java. JavaScript solo mejora la vista previa y el selector de cantidad; el formulario funciona también sin JavaScript.

Repositorio del código: [VIM722/practica](https://github.com/VIM722/practica).

Verificación del código publicado: [GitHub Actions completado correctamente](https://github.com/VIM722/practica/actions/runs/43644907030), commit `6594151e267e11550c22ba8aa5972411c2eee9de`. También se comprobó el wrapper Maven 3.9.9 en local: 77 pruebas correctas y 16 comprobaciones HTTP correctas sobre el servidor real.

**Aplicación publicada:** [TecnoSoporte en Render](https://tecnosoporte-pc3.onrender.com/cotizaciones/nueva).

La instancia pública superó 16 comprobaciones HTTP el 14 de noviembre de 2026. También se verificaron en el navegador los tres casos mínimos y la validación del formulario. Los resultados están en `docs/evidencias/pruebas-http-publicas.json` y las capturas públicas acompañan al informe.

## Integrantes y roles

| Orden | Integrante | Responsabilidad |
| --- | --- | --- |
| 1 | Huaranca Echevarria, Airton Denilson | Configuración de Spring Boot, estructura y modelos |
| 2 | Diaz Trillo, Piero Alonso | Controladores GET y POST, recepción y validación de datos |
| 3 | Palomares Santana, Maria Fernanda | Vistas HTML, diseño de interfaz e integración con Thymeleaf |
| 4 | Mateo Chuquispuma, Victor Toribio | Strategy, estrategias concretas y cálculo de descuentos |
| 5 | Tasayco Ronceros, Patricia Georgina | Pruebas funcionales, integración y coordinación documental |

Las conclusiones individuales por rol y los componentes relacionados se encuentran en [docs/EQUIPO.md](docs/EQUIPO.md) y en el informe Word y PDF.

## Funcionalidades

- Nombre del cliente obligatorio, máximo 100 caracteres; se eliminan espacios al inicio y al final.
- Diagnóstico de computadora: S/ 40.00; mantenimiento preventivo: S/ 70.00; instalación de software: S/ 50.00.
- Cantidad entera de 1 a 5, validada en el servidor.
- Clientes nuevos: 0%; frecuentes: 10%; institucionales: 15%.
- Resumen con precio unitario, cantidad, subtotal, porcentaje e importe del descuento y total.
- Interfaz adaptable a móviles, catálogo, ayuda e impresión del resumen o guardado en PDF desde el navegador.
- Estrategias inyectadas por Spring y seleccionadas por un registro; el controlador no contiene una cadena de condiciones para calcular descuentos.

No se requiere base de datos, usuario ni contraseña. Las cotizaciones no se almacenan: el alcance del examen es calcular y mostrar el resumen. Los datos de una solicitud no se comparten entre usuarios. No se añaden impuestos ni cargos a los precios del enunciado.

## Requisitos y versiones

| Herramienta | Versión |
| --- | --- |
| JDK | 17 recomendado y utilizado como objetivo de compilación |
| Spring Boot | 3.5.16, fijada en `pom.xml` |
| Maven Wrapper | 3.3.4; descarga Maven 3.9.9 |
| Maven instalado, alternativa al wrapper | 3.6.3 o superior; Maven 3.9.9 recomendado |
| Navegador | Chrome, Edge, Firefox o Safari actual |
| Python, solo para el verificador HTTP opcional | 3.10 o superior |
| Docker, solo para contenedores locales | Motor con soporte para Dockerfile multietapa |

La primera compilación necesita acceso a Maven Central. No hace falta instalar Tomcat: Spring Boot lo incluye. Configura `JAVA_HOME` apuntando al JDK, no a un JRE de Java 8.

## Estructura

```text
.
├── pom.xml                         Dependencias y compilación
├── mvnw / mvnw.cmd                 Lanzadores oficiales de Maven
├── .mvn/wrapper/                   Versión de Maven
├── .github/workflows/ci.yml        Verificación automática en GitHub Actions
├── .devcontainer/                  Ejecución en GitHub Codespaces
├── Dockerfile                     Construcción y ejecución de Java
├── render.yaml                    Despliegue Docker en Render
├── scripts/verificar_web.py        Pruebas HTTP locales o públicas
├── docs/                          Arquitectura y evidencias verificadas
├── src/main/java/pe/tecnosoporte/
│   ├── TecnoSoporteApplication.java
│   ├── controller/                Rutas GET y POST
│   ├── model/                     Catálogo, formulario y resultado
│   ├── service/                   Cálculo de la cotización
│   └── strategy/                  Contrato, contexto y tres estrategias
├── src/main/resources/
│   ├── application.properties
│   ├── messages.properties        Errores de conversión en español
│   ├── templates/                 Vistas HTML con Thymeleaf
│   └── static/                    CSS, JavaScript e iconos SVG locales
└── src/test/                      Pruebas de cálculo e integración MVC
```

## Instalación y ejecución local

Descomprime el ZIP y abre la carpeta `tecno-soporte`, que contiene `pom.xml`. Si partes de GitHub, los contenidos de esa carpeta deben estar en la raíz del repositorio:

```bash
git clone https://github.com/VIM722/practica.git
cd practica
```

En Windows PowerShell:

```powershell
java -version
$env:JAVA_HOME = 'C:\ruta\a\tu\jdk-17'
.\mvnw.cmd -B -ntp verify
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
export JAVA_HOME=/ruta/a/tu/jdk-17
chmod +x mvnw
./mvnw -B -ntp verify
./mvnw spring-boot:run
```

Maven descarga las dependencias automáticamente. También se puede usar `mvn` si ya está instalado. Abre [http://localhost:8080](http://localhost:8080). Detén el servidor con `Ctrl+C`.

Para ejecutar el JAR después de compilar:

```bash
java -jar target/tecno-soporte.jar
```

## Variables de entorno

| Variable | Predeterminado | Uso |
| --- | --- | --- |
| `PORT` | `8080` | Puerto HTTP; en Render se configura en 8080 |
| `JAVA_TOOL_OPTIONS` | No requerida en local | Docker establece memoria máxima del 70% y codificación UTF-8 |

El proyecto no necesita secretos ni claves. Spring Boot no carga automáticamente un archivo `.env`; establece las variables en tu terminal o en Render. Ejemplo para un puerto distinto:

```powershell
$env:PORT = '8081'
java -jar target/tecno-soporte.jar
```

## Uso desde la web de GitHub

### Subir los archivos al repositorio

1. Abre [VIM722/practica](https://github.com/VIM722/practica) con tu cuenta.
2. Usa **Add file → Upload files** para subir los contenidos de `tecno-soporte`, conservando sus carpetas. En la raíz deben verse `pom.xml`, `src`, `Dockerfile` y `render.yaml`; evita añadir una carpeta contenedora adicional.
3. Incluye también `.github`, `.mvn`, `.devcontainer`, `.gitignore`, `.gitattributes` y `.dockerignore`. Windows oculta algunos nombres que empiezan con punto; confirma que se subieron. Si el navegador omite estas carpetas, usa GitHub Desktop o la terminal de Codespaces.
4. Revisa la carga y confirma el commit en una rama propia. Si el repositorio contiene archivos previos, intégralos mediante un pull request; evita sobrescribir trabajo existente.
5. Comprueba la pestaña **Actions**: el flujo `Compilar y probar` debe terminar correctamente. Las ejecuciones conservan los resultados y el JAR como artefactos.

No subas únicamente el ZIP: Render necesita los archivos fuente expandidos. No subas `target`, cachés, archivos personales ni credenciales. El ZIP se entrega por separado al docente.

### Ejecutar con GitHub Codespaces

En **Code → Codespaces → Create codespace**, espera la preparación del entorno y ejecuta:

```bash
./mvnw spring-boot:run
```

Abre el puerto 8080 desde **Ports**. Codespaces sirve como entorno de desarrollo; su disponibilidad depende de la sesión y de los límites de la cuenta. Para la evaluación utiliza el servicio de Render.

## Despliegue en Render conectado a GitHub

GitHub Pages aloja sitios estáticos y no ejecuta este servidor Java. La publicación elegida para cumplir el enunciado es **Render con Docker**, manteniendo GitHub como repositorio.

1. Crea tu cuenta de [Render](https://dashboard.render.com) y conecta GitHub, concediendo acceso al repositorio `VIM722/practica`.
2. Selecciona **New → Blueprint** y el repositorio. Render leerá `render.yaml` desde la raíz.
3. Revisa el servicio `tecnosoporte-pc3`, el entorno **Docker**, el plan `free` y el health check `/health`. Revisa las condiciones y disponibilidad del plan antes de confirmar. No se necesita base de datos.
4. Inicia el despliegue. El Dockerfile descarga dependencias, ejecuta las pruebas y genera el JAR; después lo ejecuta con Java 17 y un usuario sin privilegios. Si una prueba falla, la construcción se detiene.
5. Espera el estado **Live**. Copia la URL HTTPS asignada por Render. El nombre final puede variar; este proyecto no presupone una dirección concreta.
6. Abre `/health`: debe devolver `{"status":"UP"}`. Abre `/cotizaciones/nueva` y ejecuta los cuatro casos mínimos de la siguiente sección.
7. Actualiza la URL en este README, el Word y el PDF. Captura el formulario, los tres resultados y la validación en la URL pública; agrega la fecha y el identificador del commit desplegado. Conserva las evidencias locales como pruebas del desarrollo.
8. Antes de la evaluación, vuelve a abrir la URL y verifica que el servicio responda. Un servicio gratuito puede suspenderse por inactividad o estar sujeto a límites; no se garantiza disponibilidad continua.

Alternativa manual a Blueprint: **New → Web Service → repositorio → Docker**. Deja **Root Directory** vacío, **Dockerfile Path** en `./Dockerfile`, **Docker Build Context** en `.`, health check en `/health` y variable `PORT=8080`. Si colocas el proyecto en una subcarpeta del repositorio, configura esa subcarpeta como Root Directory.

La integración GitHub puede actualizar Render al recibir nuevos commits. En Render, **After CI Checks Pass** permite esperar los controles de GitHub Actions; revisa y selecciona esta opción para el servicio si está disponible.

## Contenedor local opcional

Desde la carpeta de `pom.xml`:

```bash
docker build -t tecnosoporte-pc3 .
docker run --rm -p 8080:8080 -e PORT=8080 tecnosoporte-pc3
```

No se incluyó una imagen preconstruida. La construcción Docker requiere internet. El contenedor no necesita volúmenes ni servicios auxiliares.

## Pruebas y resultados esperados

```powershell
.\mvnw.cmd -B -ntp verify
```

```bash
./mvnw -B -ntp verify
```

Los informes XML y TXT se generan en `target/surefire-reports`. La suite verifica las 45 combinaciones del catálogo, la sustitución de estrategias, validación, renderizado Thymeleaf, escape de HTML y que el navegador no pueda imponer precios ni totales. El detalle de la ejecución comprobada acompaña a `docs/evidencias/`.

| Caso | Servicio | Cantidad | Cliente | Subtotal | Descuento | Total |
| --- | --- | --- | --- | --- | --- | --- |
| 01 | Diagnóstico | 2 | Nuevo | S/ 80.00 | S/ 0.00 | **S/ 80.00** |
| 02 | Mantenimiento | 2 | Frecuente | S/ 140.00 | S/ 14.00 | **S/ 126.00** |
| 03 | Instalación | 3 | Institucional | S/ 150.00 | S/ 22.50 | **S/ 127.50** |
| 04 | Cualquier servicio | 0 o nombre vacío | Cualquiera | No aplica | No aplica | Mensaje de validación, sin resumen |

Para comprobar por HTTP la instancia en ejecución:

```bash
python scripts/verificar_web.py --url http://localhost:8080 --salida pruebas-locales.json
python scripts/verificar_web.py --url https://tecnosoporte-pc3.onrender.com --salida pruebas-publicas.json
```

El segundo comando comprueba la instancia pública de este proyecto. El script no almacena clientes ni crea registros.

Rutas: `GET /` redirige al formulario; `GET /cotizaciones/nueva` lo muestra; `POST /cotizaciones` valida y cotiza; `GET /servicios` muestra el catálogo; `GET /ayuda` explica el uso; `GET /health` confirma disponibilidad. El PDF de la cotización se obtiene con el cuadro de impresión del navegador, no con un endpoint de descarga.

## Arquitectura y ampliaciones

Consulta [docs/ARQUITECTURA.md](docs/ARQUITECTURA.md). `CotizacionController` recibe y valida; `CotizacionService` multiplica precio y cantidad; `DescuentoContexto` selecciona la implementación de `DescuentoStrategy`; Thymeleaf muestra el resultado. Para agregar un tipo se amplía `TipoCliente` y se crea una estrategia `@Component`. El formulario y el registro la descubren automáticamente sin modificar el controlador ni el algoritmo principal.

## Entrega académica y datos pendientes

- ZIP: proyecto fuente completo, wrapper y evidencia técnica; se excluyen JAR generado, cachés y metadatos de Git.
- PDF: informe técnico. El DOCX se proporciona además como versión editable solicitada.
- `README.md`: este archivo también se entrega por separado.

Completar antes de presentar: universidad y número de grupo. Los integrantes y roles se registraron según el orden indicado por el equipo, y se incorporaron conclusiones para cada rol. La URL pública, las capturas y las pruebas corresponden a la instancia verificada en Render. Se siguió el TXT adicional, que permite IA para documentación; cada integrante debe revisar las conclusiones y comprender el código y el informe para la sustentación.

## Referencias oficiales

- [Requisitos de Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/system-requirements.html)
- [Docker en Render](https://render.com/docs/docker)
- [Referencia de Blueprints](https://render.com/docs/blueprint-spec)
- [Despliegues e integración con CI](https://render.com/docs/deploys)
- [Archivos en GitHub](https://docs.github.com/en/repositories/working-with-files/managing-files/adding-a-file-to-a-repository)
- [Alcance de GitHub Pages](https://docs.github.com/en/pages/getting-started-with-github-pages/what-is-github-pages)
