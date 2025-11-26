🧬 Mutant Detector – Instrucciones de Ejecución

Este documento explica cómo ejecutar el proyecto, correr los tests y desplegarlo.

🚀 1. Requisitos previos

Para correr este proyecto necesitás tener instalado:

✔️ Java 21

Verificar versión:
java -version

✔️ Gradle Wrapper (ya incluido en el proyecto)

No hace falta instalar Gradle globalmente.

✔️ (Opcional) Docker

Solo necesario si querés ejecutar el proyecto en contenedor.

▶️ 2. Ejecutar la aplicación localmente

Abrí una terminal dentro de la carpeta del proyecto y ejecutá:

1️⃣ Compilar el proyecto
./gradlew clean build


En Windows (PowerShell):

.\gradlew clean build

2️⃣ Ejecutar la aplicación
./gradlew bootRun


Windows:

.\gradlew bootRun


Cuando arranca, deberías ver:

Tomcat started on port 8080
Started MutantDetectorApplication

🌐 3. Acceder a la API

La aplicación queda disponible en:

✔️ Swagger UI (documentación interactiva)

👉 http://localhost:8080/swagger-ui/index.html

✔️ Endpoint principal (POST /mutant)

Analiza ADN y devuelve:

200 OK si es mutante

403 Forbidden si no es mutante

Ejemplo:

{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

✔️ Endpoint de estadísticas (GET /stats)

Muestra:

count_mutant_dna

count_human_dna

ratio

🗃️ 4. Conectar a la base de datos (H2)

Consola H2 disponible en:

👉 http://localhost:8080/h2-console

Configurar así:

JDBC URL: jdbc:h2:mem:mutantsdb

User: sa

Password: (vacío)

Presioná Connect para ver la tabla dna_record.

🧪 5. Ejecutar los tests

Para correr todos los tests:

./gradlew test


En Windows:

.\gradlew test

✔️ Generar el reporte de cobertura Jacoco
./gradlew jacocoTestReport


Windows:

.\gradlew jacocoTestReport


Ver reporte en:

build/reports/jacoco/test/html/index.html


Abrilo en el navegador.

🐳 6. Ejecutar con Docker (opcional)
1️⃣ Construir la imagen
docker build -t mutant-detector .

2️⃣ Ejecutar el contenedor
docker run -p 8080:8080 mutant-detector


La app queda disponible en:

👉 http://localhost:8080/swagger-ui/index.html

☁️ 7. Desplegar en Render (opcional)

Subí el repo a GitHub.

Creá un servicio Web en Render.

Seleccioná "Deploy from Dockerfile".

Render construye la imagen y te da la URL pública.

Ejemplo:

https://mutant-detector.onrender.com/swagger-ui/index.html