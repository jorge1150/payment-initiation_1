# Payment Initiation Service

## Contexto
Proyecto de migración de un servicio bancario SOAP de órdenes de pago hacia un microservicio REST contract-first (OpenAPI 3.0) alineado con el Service Domain BIAN Payment Initiation / PaymentOrder. El objetivo es habilitar capabilities modernas de Payment Order sin romper integraciones existentes.

## Arquitectura y diseño
El servicio sigue arquitectura hexagonal y principios SOLID/Clean Architecture:
- **Domain**: entidades, value objects y puertos primarios/secundarios.
- **Application**: casos de uso orquestando la lógica y mapeos.
- **Infrastructure**: adaptadores REST, SOAP, persistencia y configuración Spring Boot.
- **Shared**: utilidades comunes (excepciones, mapeadores, etc.).

## Ejecución
### Ejecución local
- TODO: documentar prerequisitos (JDK 17, Maven Wrapper, perfiles Spring, etc.).
- TODO: incluir comando definitivo (`./mvnw spring-boot:run` u otro) una vez se agreguen los módulos necesarios.

### Ejecución con Docker
- TODO: agregar Dockerfile y docker-compose que orquesten dependencias (bases de datos simuladas, SOAP mock, etc.).
- TODO: documentar variables de entorno y estándares de observabilidad.

## Uso de Inteligencia Artificial
La prueba requiere evidenciar asistencia de IA. Este repositorio almacena prompts, decisiones y artefactos generados en `ai/`.

| ID | Herramienta (Cursor/ChatGPT) | Objetivo | Archivos afectados | Resumen de la respuesta | Correcciones manuales realizadas |
| --- | --- | --- | --- | --- | --- |
| AI-001 | Cursor (GPT-5.1) | Definir estructura base del microservicio hexagonal | `pom.xml`, skeletons Java, `.gitignore`, `README.md`, `ai/*` | Se generó pom mínimo, estructura de paquetes y documentación inicial | Ajustes menores de comentarios y TODOs |
| AI-002 | ChatGPT | Reservado para próximas iteraciones | TBD | TBD | TBD |

> Nota: Actualizar la tabla conforme se ejecuten nuevos prompts y se apliquen correcciones manuales.