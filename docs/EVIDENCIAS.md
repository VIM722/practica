# Evidencias de implementación

Las capturas se tomaron el 14 de noviembre de 2026 en la aplicación Spring Boot local, con navegador real. **No son capturas de una aplicación publicada**. La URL de las pruebas es `http://127.0.0.1:8080`. Los nombres utilizados son datos de prueba, no clientes reales.

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

Los XML de JUnit omiten propiedades del entorno y salidas con rutas personales; conservan los resultados y los casos de prueba. Los informes originales se regeneran con `./mvnw verify` en `target/surefire-reports`.

Para completar las evidencias públicas después de desplegar: guardar la URL HTTPS real, commit desplegado, fecha, formulario, tres resúmenes y validación. Ejecutar `python scripts/verificar_web.py --url URL_REAL --salida docs/evidencias/pruebas-http-publicas.json`. Actualizar el informe Word, exportar a PDF y completar la URL en README.md.
