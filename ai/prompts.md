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

