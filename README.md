# 🐳 Guía de Docker - AlgoHoot

Esta guía explica cómo funciona el sistema Dockerizado de AlgoHoot, incluyendo desarrollo local y despliegue en producción.

## 📁 Estructura del Proyecto

algohoot/\
├── docker-compose.yml # 🛠️ Desarrollo local\
├── docker-compose.prod.yml # 🚀 Producción\
├── backend/ # 📡 API Java Spring Boot\
├── frontend/ # ⚛️ Aplicación React/TypeScript\
├── ingress/ # 🔀 Servidor Nginx (Reverse Proxy)\
└── .github/workflows/\
└── ci-cd.yml # ⚡ CI/CD Automático\

## 🎯 Servicios Docker

### 1. **🌐 Base de Datos PostgreSQL** (`db`)
- **Imagen:** `postgres:15-alpine`
- **Puerto:** `5433` (local) → `5432` (contenedor)
- **Base de datos:** `algohoot_db`
- **Credenciales:** usuario `postgres`, password `dev-password`

### 2. **📡 Backend API** (`backend`)
- **Tecnología:** Java 21 + Spring Boot
- **Puerto interno:** `8080`
- **Health Check:** `http://localhost:8080/actuator/health`

### 3. **⚛️ Frontend React** (`frontend`)
- **Tecnología:** React + TypeScript + Vite
- **Puerto interno:** `80` (servido por el servidor web del build)

### 4. **🔀 Ingress/Nginx** (`ingress`)
- **Función:** Reverse Proxy y servidor web
- **Puerto expuesto:** `80`
- **Configuración:** Sirve el frontend y redirige API calls al backend

### 5. **📊 Adminer** (`adminer`)
- **Función:** Interfaz web para administrar PostgreSQL
- **Puerto:** `8081`

## 🌐 URLs de Acceso

Cuando los contenedores están ejecutándose, puedes acceder a:

### **🔗 Desarrollo Local** (con `docker compose up -d --build`)
| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Aplicación Principal** | http://localhost | Frontend completo via Nginx |
| **API Backend Directo** | http://localhost:8080 | Backend Spring Boot |
| **Endpoint API** | http://localhost/api | API a través de Nginx |
| **Adminer (BD)** | http://localhost:8081 | Gestor de base de datos |
| **PostgreSQL** | `localhost:5433` | Conexión directa a BD |

### **🔄 Flujo de Peticiones:**

Navegador → http://localhost (Nginx:80)\
├── /api/* → Backend:8080 (Spring Boot)\
└── /* → Frontend:80 (Archivos estáticos React)

## 🛠️ Comandos de Desarrollo

### **Levantar entorno completo:**
```bash
docker compose up -d --build
```

### **Comandos de producción:**
```bash
docker compose -f docker-compose.prod.yml pull
docker compose -f docker-compose.prod.yml up -d
```
