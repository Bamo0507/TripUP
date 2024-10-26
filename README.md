# TripUp

TripUp es una aplicación diseñada para facilitar la planificación de viajes, permitiendo a los usuarios crear itinerarios personalizados y acceder a información turística relevante. Con TripUp, los viajeros pueden organizar sus actividades diarias, descubrir lugares de interés y optimizar su tiempo para disfrutar al máximo de sus experiencias.

## Descripción del Proyecto

Planificar un viaje puede ser una tarea abrumadora que consume mucho tiempo y esfuerzo. Desde organizar destinos turísticos hasta buscar actividades y lugares de interés, los viajeros a menudo se enfrentan a un sinfín de detalles que manejar. TripUp surge como una solución integral para simplificar este proceso.

**Características principales:**

- **Creación de Itinerarios Personalizados:** Los usuarios pueden crear itinerarios dando un nombre al viaje y definiendo las fechas de inicio y fin. La aplicación genera automáticamente una lista de días, cada uno representado por una tarjeta interactiva.

- **Organización de Actividades Diarias:** Al seleccionar un día específico, los usuarios pueden agregar actividades detalladas, incluyendo horarios y descripciones. Esto les permite planificar su agenda diaria de manera eficiente.

- **Visualización de Actividades:** En la pantalla de lista de días, los usuarios pueden ver fácilmente qué días tienen actividades programadas y cuántas hay en cada uno, facilitando una visión general de su viaje.

- **Exploración de Lugares de Interés:** La sección "Explorar" permite a los usuarios acceder a información turística sobre lugares cercanos a su ubicación actual o buscar destinos específicos. Las tarjetas con imágenes y descripciones detalladas ofrecen una experiencia enriquecida.

**Objetivo del Proyecto:**

TripUp tiene como objetivo ayudar a los viajeros a dedicar menos tiempo a la planificación y más tiempo a disfrutar de sus viajes. Al tener toda la información relevante en un solo lugar, los usuarios pueden acceder fácilmente a sus itinerarios y descubrir nuevos lugares de interés sin la necesidad de utilizar múltiples herramientas simultáneamente.

**Audiencia Objetivo:**

- **Viajeros Frecuentes:** Personas que viajan regularmente y buscan una forma eficiente de organizar sus viajes.
- **Familias y Grupos de Amigos:** Grupos que necesitan coordinar actividades y horarios de manera colaborativa.
- **Turistas Novatos:** Usuarios que pueden sentirse abrumados por la planificación y buscan una herramienta sencilla y amigable.

---

**Nota:** Para probar la aplicación en su estado actual, se utiliza un repositorio local para la autenticación con las siguientes credenciales:

- **Usuario:** `1`
- **Contraseña:** `1`

Por favor, tomarlo en cuenta al correr la aplicación.
---

## Servicios

Para ofrecer una experiencia completa y funcional, TripUp utilizará los siguientes servicios:

### Firebase

Hemos evaluado el uso de servicios externos para obtener información de lugares turísticos y manejar la autenticación de usuarios. Sin embargo, las APIs que satisfacen nuestras necesidades son de pago, por lo que hemos optado por una solución alternativa que no implique costos adicionales.

- **Firebase Realtime Database:** Implementaremos una base de datos en Firebase donde almacenaremos información detallada sobre lugares turísticos, incluyendo su clasificación, imágenes y descripciones. Al desarrollar nosotros mismos esta base de datos, podemos personalizar el contenido y asegurarnos de que se ajuste a las necesidades de nuestros usuarios.

- **Firebase Authentication con Google Sign-In:** Para el manejo de la autenticación de usuarios, utilizaremos Firebase Authentication en combinación con Google Sign-In. Esto permitirá a los usuarios registrarse y acceder a la aplicación utilizando sus cuentas de Google, facilitando el proceso de inicio de sesión y mejorando la seguridad.

**Nota:** Actualmente, Firebase aún no está implementado en la aplicación. Por el momento, estamos utilizando una base de datos local para manejar la información de los lugares y un repositorio local para la autenticación de usuarios con credenciales predefinidas (usuario: `1`, contraseña: `1`). Planeamos migrar a Firebase en la siguiente versión.

### Servicios Propios (Desarrollados por Nosotros)

- **Base de Datos Local:** Mientras completamos la integración con Firebase, utilizamos una base de datos local integrada en la aplicación para manejar los datos de los lugares turísticos y usuarios. Esto nos permite desarrollar y probar funcionalidades sin depender de una conexión a internet, esta información se encuentra en los archivos 'PlaceDb.kt' y 'SearchDb.kt'.

- **Repositorio Local para Autenticación:** Actualmente, para fines de prueba, tenemos un repositorio local que verifica el inicio de sesión con credenciales predefinidas. Este enfoque nos permite validar la funcionalidad de autenticación mientras implementamos la solución basada en Firebase.

## Librerías

Para desarrollar TripUp y asegurar su correcto funcionamiento, hemos incorporado varias librerías externas que nos proporcionan funcionalidades clave. A continuación, se detallan las librerías utilizadas y su propósito:

### Firebase SDKs (A Implementar)

Aunque todavía no hemos integrado Firebase, planeamos utilizar los siguientes SDKs:

- **Firebase BoM (Bill of Materials):** Gestionará las versiones de los diferentes SDKs de Firebase utilizados en la aplicación.

- **Firebase Realtime Database:** Permitirá la integración con la base de datos en tiempo real de Firebase para almacenar y sincronizar datos de lugares turísticos.

  ```kotlin
  implementation("com.google.firebase:firebase-database-ktx")
  ```
- **Firebase Realtime Database:** Permitirá la integración con la base de datos en tiempo real de Firebase para almacenar y sincronizar datos de lugares turísticos.

  ```kotlin
  implementation("com.google.firebase:firebase-database-ktx")
  ```
- **Firebase Authentication:** Facilitará la autenticación de usuarios, incluyendo la integración con proveedores de identidad como Google.

  ```kotlin
  implementation("com.google.firebase:firebase-auth-ktx")
  ```

- **Google Sign-In:** Librería necesaria para implementar el inicio de sesión con cuentas de Google.

  ```kotlin
  implementation("com.google.android.gms:play-services-auth:20.5.0")
  ```

## Nota Importante
A medida que avancemos en el desarrollo e integremos Firebase y Google Sign-In, actualizaremos las dependencias y adaptaremos la arquitectura de la aplicación para aprovechar al máximo estas tecnologías.

  
