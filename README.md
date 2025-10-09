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

```
demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # Punto de entrada de la aplicación
│   └── RoutesController.java         # Controlador de rutas web
├── src/main/resources/
│   ├── templates/                    # Plantillas HTML
│   │   ├── index.html               # Página principal
│   │   ├── login.html               # Página de inicio de sesión
│   │   ├── register.html            # Página de registro
│   │   ├── search.html              # Página de búsqueda
│   │   └── product.html             # Página de producto
│   ├── static/
│   │   └── style.css                # Estilos CSS
│   └── image/                       # Recursos de imagen
└── pom.xml                          # Configuración de Maven
```

### Funcionalidades Implementadas

1. **Página Principal**: Interfaz de bienvenida con navegación y barra de búsqueda
2. **Sistema de Navegación**: Menú con opciones para Inicio, Productos, Productores y Autenticación
3. **Formulario de Registro**: Para nuevos usuarios/productores
4. **Búsqueda de Productos**: Funcionalidad para encontrar productos específicos

### Estado del Desarrollo

El proyecto se encuentra en fase de desarrollo inicial con:
- ✅ Estructura básica de Spring Boot configurada
- ✅ Plantillas HTML creadas
- ✅ Estilos CSS implementados
- ✅ Controlador de rutas básico
- 🔄 Funcionalidades de backend en desarrollo
- 🔄 Integración con base de datos pendiente

### Objetivo del Proyecto

Cultivus Co busca crear un ecosistema digital que conecte directamente a los productores locales con consumidores interesados en productos frescos y artesanales, promoviendo el comercio local y sostenible.

