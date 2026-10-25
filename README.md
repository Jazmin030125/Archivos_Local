# 📂 Gestor de Archivos Locales (Archivos_Local)

Este es un proyecto desarrollado en **Java** utilizando **JPA (Hibernate)** y **MySQL** para gestionar la subida, almacenamiento
y descarga de archivos (como documentos PDF) directamente en una base de datos local.

## 🚀 Características
- **Guardar archivos:** Convierte un archivo local (PDF) en un arreglo de bytes (`byte[]`) y lo almacena en la base de datos.
- **Recuperar archivos:** Busca el archivo por su ID en la base de datos y lo descarga nuevamente al disco duro.
- **Soporte para archivos grandes:** Utiliza el tipo de dato `LONGBLOB` en MySQL para evitar el límite de 64 KB de los `BLOB` normales.

## 🛠️ Tecnologías Utilizadas
- **Java (JDK 8)**
- **Maven** (Gestor de dependencias)
- **Hibernate 5.6** (Proveedor JPA)
- **MySQL / XAMPP** (Base de datos)
- **NetBeans IDE 8.2**

## ⚙️ Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalado y configurado:
1. **XAMPP** (con el módulo de MySQL iniciado).
2. **Java JDK 8** o superior.
3. **NetBeans** (o cualquier IDE compatible con Maven).

## 🗄️ Configuración de la Base de Datos
1. Abre **phpMyAdmin** (normalmente en `http://localhost/phpmyadmin`).
2. Crea una base de datos llamada exactamente: `gestor_archivos`.
3. Si la tabla `archivo` ya existe, asegúrate de que la columna `contenido` sea de tipo **`LONGBLOB`**:
   ```sql
   ALTER TABLE archivo MODIFY contenido LONGBLOB NOT NULL;
