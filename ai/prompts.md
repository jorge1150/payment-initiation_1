# Registro de prompts de IA

Este documento centraliza los prompts utilizados durante el desarrollo del servicio y permite trazar cómo la IA asistió cada paso. Anotar cada interacción relevante facilita la auditoría del ejercicio.

## P-001 – Estructura hexagonal inicial
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor (GPT-5.1)
- **Contexto:** Solicitar generación de estructura base, pom mínimo y documentación inicial.
- **Prompt:** _[pegar prompt utilizado]_
- **Resumen de la respuesta:** _[resumir la propuesta devuelta por la IA]_
- **Archivos generados/modificados:** `pom.xml`, `.gitignore`, `README.md`, `ai/*`, skeletons en `src/*`.
- **Correcciones manuales:** _[describir ajustes manuales posteriores]_.

## P-00X – _Título del prompt_
- **Fecha:** 
- **Herramienta:** 
- **Contexto:** 
- **Prompt:** 
- **Resumen de la respuesta:** 
- **Archivos generados/modificados:** 
- **Correcciones manuales:** 

## P-002 – Generación contrato OpenAPI Payment Initiation
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor
- **Contexto:** Elaborar el contrato OpenAPI 3.0 para el microservicio Payment Initiation / PaymentOrder tomando como referencia el WSDL legado y las guías BIAN.
- **Prompt:** Solicitud para definir paths POST/GET, modelos y errores estándar, asegurando compatibilidad con openapi-generator (spring, interfaceOnly, bean validation, Java 17).
- **Resumen de la respuesta:** Se generó `openapi/payment-initiation.yaml` con los recursos `PaymentOrderInitiationRequest`, `PaymentOrderResource`, `PaymentOrderStatusResource`, y se creó una copia inicial en `ai/generations/`.
- **Archivos generados/modificados:** `openapi/payment-initiation.yaml`, `ai/generations/openapi-payment-initiation-initial.yaml`, `ai/prompts.md`, `ai/decisions.md`, `README.md`.
- **Correcciones manuales:** Pendiente completar según revisión humana.

## P-003 – Configuración de openapi-generator y controlador PaymentOrdersController
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor
- **Contexto:** Configurar el plugin openapi-generator en el `pom.xml`, añadir build-helper y crear un controlador `PaymentOrdersController` que implemente la interfaz `PaymentOrdersApi` generada.
- **Prompt:** Solicitud para añadir el plugin `openapi-generator-maven-plugin`, registrar las fuentes generadas y materializar un controlador alineado con la arquitectura hexagonal que delegue en los casos de uso existentes.
- **Resumen de la respuesta:** Se añadió la configuración del plugin y build-helper al `pom.xml`, se creó `PaymentOrdersController` y se actualizaron los documentos de IA/README.
- **Archivos generados/modificados:** `pom.xml`, `src/main/java/com/hiberus/payment_initiation/infrastructure/rest/PaymentOrdersController.java`, `ai/prompts.md`, `ai/decisions.md`, `README.md`.
- **Correcciones manuales:** Pendiente completar según revisión de ejecución de mvn y ajustes de compilación.

## P-004 – Configuración de herramientas de calidad (JaCoCo, Checkstyle, SpotBugs)
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor
- **Contexto:** Configuración de herramientas de calidad en el `pom.xml` y creación del primer test unitario para cumplir con los requisitos de cobertura mínima.
- **Prompt:** Solicitud para configurar JaCoCo (>= 80% líneas), Checkstyle con configuración propia y SpotBugs, además de crear un test unitario básico que permita iniciar la cobertura de código.
- **Resumen de la respuesta:** Se añadieron plugins de calidad (JaCoCo, Checkstyle, SpotBugs) al `pom.xml`, se crearon archivos de configuración (`config/checkstyle/checkstyle.xml`, `config/spotbugs/exclude.xml`), se implementó una versión mínima de `CreatePaymentOrderUseCase` y se creó un test unitario con Mockito.
- **Archivos generados/modificados:** `pom.xml`, `config/checkstyle/checkstyle.xml`, `config/spotbugs/exclude.xml`, `src/main/java/com/hiberus/payment_initiation/application/usecase/CreatePaymentOrderUseCase.java`, `src/test/java/com/hiberus/payment_initiation/application/usecase/CreatePaymentOrderUseCaseTest.java`, `README.md`, `ai/prompts.md`, `ai/decisions.md`.
- **Correcciones manuales:** Pendiente ajustar reglas de Checkstyle/SpotBugs y nuevas pruebas según feedback.
- **Nota adicional (P-004-fix):** Se añadió la dependencia `org.openapitools:jackson-databind-nullable:0.2.6` al `pom.xml` para resolver errores de compilación relacionados con `JsonNullable` usado por el código generado de OpenAPI. Esta librería es la recomendada por openapi-generator para soportar valores nullable en la serialización/deserialización JSON.

## P-005 – Corrección de dependencias para código generado de OpenAPI
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor
- **Contexto:** Tras generar el código desde OpenAPI, se detectaron errores de compilación debido a la ausencia de dependencias para las anotaciones usadas por openapi-generator (Bean Validation y Swagger).
- **Prompt:** Solicitud para añadir las dependencias faltantes (`spring-boot-starter-validation` y `swagger-annotations`) al `pom.xml` para que compile el código generado.
- **Resumen de la respuesta:** Se añadieron `spring-boot-starter-validation` (sin versión, heredada del parent Spring Boot) y `io.swagger.core.v3:swagger-annotations` (versión 2.2.22) al `pom.xml`, resolviendo los errores de compilación del código generado.
- **Archivos generados/modificados:** `pom.xml`, `ai/prompts.md`, `ai/decisions.md`, `README.md`.
- **Correcciones manuales:** Ninguna.

## P-006 – Ajuste de JaCoCo para excluir código generado y mejora de cobertura
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor
- **Contexto:** La build fallaba con cobertura del 6% porque JaCoCo estaba contando el código generado de OpenAPI. Se necesitaba excluir ese código del cálculo y añadir tests adicionales para aumentar la cobertura del código propio.
- **Prompt:** Solicitud para configurar exclusiones en JaCoCo para el código generado (`com/hiberus/payment_initiation/generated/**`) y crear tests adicionales para `GetPaymentOrderUseCase` y `PaymentOrdersController`.
- **Resumen de la respuesta:** Se añadieron exclusiones en la configuración del plugin JaCoCo para excluir el código generado, se implementó `GetPaymentOrderUseCase` con inyección de dependencias, se crearon tests completos con Mockito para `GetPaymentOrderUseCase` y `PaymentOrdersController`, y se actualizó el test del controlador para probar los métodos reales.
- **Archivos generados/modificados:** `pom.xml`, `src/main/java/com/hiberus/payment_initiation/application/usecase/GetPaymentOrderUseCase.java`, `src/test/java/com/hiberus/payment_initiation/application/usecase/GetPaymentOrderUseCaseTest.java`, `src/test/java/com/hiberus/payment_initiation/infrastructure/rest/PaymentOrderRestControllerTest.java`, `ai/prompts.md`, `ai/decisions.md`, `README.md`.
- **Correcciones manuales:** Ninguna.

## P-007 – Aumento de cobertura JaCoCo cubriendo paquetes al 0%
- **Fecha:** YYYY-MM-DD
- **Herramienta:** Cursor
- **Contexto:** El reporte de JaCoCo mostraba varios paquetes al 0% de cobertura (shared.exception, infrastructure.persistence, infrastructure.soap, infrastructure.config, y la raíz del proyecto), dejando el total en ~65%, por debajo del umbral mínimo del 80%.
- **Prompt:** Solicitud para crear tests sencillos que aumenten la cobertura en los paquetes al 0%, sin modificar la configuración de JaCoCo (manteniendo el umbral en 0.80 y las exclusiones existentes).
- **Resumen de la respuesta:** Se crearon tests para `PaymentInitiationApplication` (test de contexto Spring Boot y ejecución del main), `DomainException` (tests de constructores con mensaje y causa), `PaymentOrderPersistenceAdapter` (tests de métodos save y findById), y `LegacyPaymentOrderSoapClient` (tests de getCurrentStatus). Estos tests cubren las clases que estaban al 0% para elevar la cobertura global al 80%+.
- **Archivos generados/modificados:** `src/test/java/com/hiberus/payment_initiation/PaymentInitiationApplicationTest.java`, `src/test/java/com/hiberus/payment_initiation/shared/exception/SharedExceptionsTest.java`, `src/test/java/com/hiberus/payment_initiation/infrastructure/persistence/PersistenceAdapterTest.java`, `src/test/java/com/hiberus/payment_initiation/infrastructure/soap/SoapAdapterTest.java`, `ai/prompts.md`, `ai/decisions.md`, `README.md`.
- **Correcciones manuales:** Se simplificó `PaymentInitiationApplicationTest` para eliminar `@SpringBootTest` y usar solo un test unitario que ejecuta el método `main()` capturando la excepción esperada. Se corrigieron errores de Checkstyle (movimiento de `LineLength` fuera de `TreeWalker`, eliminación de tokens no válidos, eliminación de propiedades no soportadas en `JavadocMethod`). Se añadieron exclusiones en SpotBugs para campos no leídos en `PaymentOrder` y `PaymentOrdersController` que se usarán cuando se complete la implementación. Tras estas correcciones, `mvn clean verify` pasa exitosamente.

