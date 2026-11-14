# Evidencias de implementación

Las evidencias conservan las verificaciones locales y las verificaciones de la aplicación publicada realizadas el 14 de noviembre de 2026 con navegador real. URL pública: [TecnoSoporte en Render](https://tecnosoporte-pc3.onrender.com/cotizaciones/nueva). Las imágenes con sufijo `local` documentan el desarrollo; las imágenes con sufijo `publico` o `publica` corresponden a Render. Los nombres utilizados en los formularios son datos de prueba, no clientes reales.

| Archivo en evidencias/ | Contenido |
| --- | --- |
| 01-formulario-local.jpg | Formulario Thymeleaf en escritorio, viewport de 1440 píxeles |
| 02-caso01-local.jpg | Diagnóstico por 2, nuevo, S/ 80.00 |
| 03-caso02-local.jpg | Mantenimiento por 2, frecuente, S/ 126.00 |
| 04-caso03-local.jpg | Instalación por 3, institucional, S/ 127.50 |
| 05-validacion-local.jpg | Nombre vacío y cantidad 0, validaciones devueltas por Java |
| 06-movil-local.jpg | Formulario en viewport de 390 × 844 píxeles |
| pruebas-calculo.xml | 50 pruebas de cálculo y contexto Strategy |
| pruebas-web.xml | 27 pruebas de MVC, validación y Thymeleaf |
| pruebas-http-local.json | 16 comprobaciones HTTP reales y sus resultados |
| compilacion-wrapper.txt | Resumen real de verify con el wrapper y Maven 3.9.9 |
| github-ci.json | Enlace y resultado correcto de GitHub Actions para el commit de implementación |
| arquitectura-mvc.png | Diagrama de la arquitectura implementada |
| arquitectura-strategy.png | Diagrama del contexto y sus estrategias |
| 07-formulario-publico.jpg | Formulario servido por Render mediante HTTPS |
| 08-caso01-publico.jpg | Diagnóstico por 2, cliente nuevo, S/ 80.00 en Render |
| 09-caso02-publico.jpg | Mantenimiento por 2, frecuente, S/ 126.00 en Render |
| 10-caso03-publico.jpg | Instalación por 3, institucional, S/ 127.50 en Render |
| 11-validacion-publica.jpg | Nombre vacío y cantidad 0 rechazados en Render |
| pruebas-http-publicas.json | 16 comprobaciones correctas sobre la URL pública con fecha y resultados |

Los XML de JUnit omiten propiedades del entorno y salidas con rutas personales; conservan los resultados y los casos de prueba. Los informes originales se regeneran con `./mvnw verify` en `target/surefire-reports`.

Para repetir la verificación: `python scripts/verificar_web.py --url https://tecnosoporte-pc3.onrender.com --salida docs/evidencias/pruebas-http-publicas.json`. El informe Word y PDF incluye la URL y las capturas públicas. El commit de implementación verificado está registrado en `github-ci.json`; el identificador exacto del despliegue debe consultarse en el panel de Render.
