# CULTIVUS-CO

## Resumen del Proyecto

**Cultivus Co** es una aplicación web desarrollada en Java con Spring Boot que funciona como una plataforma para conectar productores locales con consumidores. La aplicación está diseñada para facilitar la venta y compra de productos agrícolas y locales.

### Características Principales

- **Plataforma de Productos Locales**: Enfocada en productos agrícolas y artesanales de productores locales
- **Sistema de Búsqueda**: Funcionalidad para buscar productos disponibles en la plataforma
- **Gestión de Usuarios**: Sistema de registro e inicio de sesión para productores y compradores
- **Interfaz Web Responsiva**: Diseño moderno y limpio con navegación intuitiva

### Tecnologías Utilizadas

- **Backend**: Java 17 con Spring Boot 3.5.6
- **Frontend**: HTML5, CSS3 con Thymeleaf como motor de plantillas
- **Gestión de Dependencias**: Maven
- **Base de Datos**: Configurada para GraphQL (con plugin de generación de código)
- **Estilo**: CSS personalizado con diseño minimalista y profesional

### Estructura del Proyecto

# CULTIVIS-CO

## Resumen

Cultivus Co es una aplicación web (Spring Boot + Thymeleaf) pensada para conectar productores locales con consumidores. Ofrece páginas de catálogo, registro de usuarios y APIs mínimas para productos y ubicación.

Este README se ha actualizado para reflejar el estado actual del repo (noviembre 2025): rutas disponibles, cómo ejecutar, y soluciones rápidas a problemas comunes que surgieron durante el desarrollo.

## Tecnologías

- Java 17+ (se recomienda JDK 17 o 21)
- Spring Boot 3.5.x
- Thymeleaf (plantillas del lado servidor)
- Maven (wrapper incluido: `./mvnw`)
- MySQL (configurado en `application.properties` por defecto)

## Estructura relevante

```
demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java
│   ├── Controller/            # Controladores web y REST
│   ├── Model/                 # Entidades JPA
│   ├── repository/            # Repositorios Spring Data
│   └── services/              # Lógica de negocio
├── src/main/resources/
│   ├── templates/             # Plantillas Thymeleaf (HTML)
│   └── static/                # JS/CSS/imagenes
└── pom.xml
```

## Rutas principales (vistas)

- GET / -> `index.html`
- GET /registro -> `register.html`
- GET /login -> `login.html`
- GET /product -> `product.html`
- GET /product-detall -> `product-detall.html`
- GET /usuarios -> `usuarios.html`
- GET /usuarios/nuevo -> `nuevoUsuario.html` (formulario)
- GET /usuarios/editar/{id} -> `editarUsuario.html`

Además se añadieron aliases para compatibilidad con el header:
- /newregister -> redirect:/registro
- /loginnew -> redirect:/login
- /categorias -> redirect:/product
- /carrito -> redirect:/product (temporal)

## Endpoints API relevantes

- GET /api/ubicacion/departamentos -> lista departamentos
- GET /api/ubicacion/ciudades/{idDepartamento} -> ciudades por departamento (path)
- GET /api/departamentos and /api/ciudades etc. (expuestas en controladores auxiliares según el código)
- Productos: `/api/productos` (endpoints REST para CRUD de productos)

Puedes probar las APIs con curl o Postman.

## Cómo ejecutar (desarrollo)

1. Asegúrate de tener JDK instalado y `JAVA_HOME` configurado. Ejemplo (Linux):

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

2. Configura la base de datos en `demo/src/main/resources/application.properties` (por defecto apunta a `jdbc:mysql://localhost:3306/cultivus`).

3. Ejecuta desde la carpeta `demo`:

```bash
# Compilar (opcional)
./mvnw -DskipTests package

# O arrancar directamente
./mvnw spring-boot:run
```

Accede a la app en `http://localhost:8080`.

## Notas importantes / Troubleshooting

- JAVA_HOME: si `./mvnw` falla con "The JAVA_HOME environment variable is not defined correctly", define `JAVA_HOME` apuntando a una instalación JDK válida y vuelve a intentarlo.

- DataInitializer / errores al insertar ciudades: durante la inicialización de datos se detectó un `Data truncation: Data too long for column 'nombre'` porque la columna `nombre` de la tabla `ciudad` era demasiado corta para algunos nombres. Dos opciones:
	- Aumentar la longitud de la columna en la base de datos (ejemplo):

		```sql
		ALTER TABLE ciudad MODIFY nombre VARCHAR(100);
		```

	- O ajustar la entidad `Ciudad` y dejar que `spring.jpa.hibernate.ddl-auto=update` aplique el cambio (revisar `application.properties`).

- Si ves 404 en vistas referenciadas por los enlaces del header (ej. `/newregister`, `/loginnew`, `/categorias`, `/carrito`) ahora existen alias en `RoutesController` que hacen redirect a las vistas reales.

## Formularios y comportamiento cliente

- El formulario de registro y los formularios de usuario (`nuevoUsuario.html`, `editarUsuario.html`) usan dos selects dependientes: `departamento` y `ciudad`.
- El filtrado de ciudades se hace en el cliente (JS) usando el archivo `static/js/register.js`. Cada option de ciudad incluye un atributo `data-departamento` que permite ocultar/mostrar ciudades según el departamento seleccionado.
- También existe un endpoint REST para obtener ciudades por departamento si se prefiere cargar dinámicamente (AJAX).

## Archivos que añadimos / actualizamos recientemente

- `src/main/java/com/example/demo/Controller/CiudadesController.java` — API para departamentos/ciudades (GET /api/ubicacion/...)
- `src/main/java/com/example/demo/Controller/UbicacionController.java` — (si está presente) controller REST alternativo
- `src/main/resources/templates/nuevoUsuario.html` — plantilla creada para nuevo usuario
- `src/main/resources/templates/editarUsuario.html` — plantilla creada para editar usuario
- `src/main/resources/static/js/register.js` — manejo de pasos del formulario y filtrado de ciudades

## Próximos pasos recomendados

1. Verificar que la base de datos `cultivus` existe y que el usuario configurado en `application.properties` tiene permisos.
2. Ejecutar la aplicación localmente y probar las rutas mencionadas.
3. (Opcional) Cambiar el filtrado de ciudades a carga por AJAX si la lista crece mucho.
4. Añadir pruebas unitarias para servicios y controladores.

Si quieres, puedo:
- (A) intentar arrancar la aplicación aquí y comprobar endpoints (necesito que `JAVA_HOME` sea válido en el entorno del runner), o
- (B) crear una versión AJAX del select de ciudades para reducir la carga inicial.

---

Si necesitas que agregue más documentación (diagramas, endpoints completos, ejemplos curl o configuración Docker), dime cuál prefieres y lo añado.

