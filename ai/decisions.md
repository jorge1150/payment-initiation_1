# Decisiones asistidas por IA

Este registro documenta decisiones de diseño evaluadas junto con la IA. Cada entrada debe incluir el contexto, las alternativas consideradas y la justificación de la decisión final, resaltando la intervención humana.

## D-001 – Estructura inicial hexagonal
- **Contexto:** Selección de estructura base para el microservicio alineada a BIAN Payment Initiation.
- **Alternativas sugeridas por IA:** Paquetes agrupados por capas hexagonales, organización por bounded context, uso de módulos separados.
- **Decisión tomada:** Mantener un único módulo Maven con paquetes `domain`, `application`, `infrastructure`, `shared`.
- **Motivo de la decisión:** Facilita iteraciones rápidas en la prueba técnica y mantiene claridad de responsabilidades.
- **Impacto en la arquitectura/código:** Base estandarizada que permitirá acoplar OpenAPI contract-first y adaptadores SOAP en fases posteriores.

## D-00X – _Título de la decisión_
- **Contexto:** 
- **Alternativas sugeridas por IA:** 
- **Decisión tomada:** 
- **Motivo de la decisión:** 
- **Impacto en la arquitectura/código:** 

## D-002 – Diseño del contrato OpenAPI para Payment Initiation / PaymentOrder
- **Contexto:** Necesidad de definir un contrato REST contract-first que sustituya al servicio SOAP heredado y respete el dominio BIAN Payment Order.
- **Alternativas sugeridas por IA:** Estructuras de recursos separados para la orden completa y para el estado, inclusión de Problem Details genérico, uso de parámetros reutilizables y esquemas `AccountIdentification`/`Amount`.
- **Decisión tomada:** Adoptar los esquemas `PaymentOrderInitiationRequest`, `PaymentOrderResource`, `PaymentOrderStatusResource`, `AccountIdentification`, `Amount` y `ErrorResponse`, con operaciones POST/GET que reflejen los endpoints requeridos.
- **Motivo de la decisión:** Mantener claridad semántica, facilitar la generación de interfaces Spring con openapi-generator y alinear nomenclatura con BIAN y estándares bancarios.
- **Impacto en la arquitectura/código:** El adaptador REST implementará las interfaces generadas (`PaymentOrdersApi`) y se garantizará consistencia entre el contrato y las capas domain/application.

