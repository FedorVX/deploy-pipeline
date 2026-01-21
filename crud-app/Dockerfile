# =========================
# Этап 1: Сборка приложения
# =========================
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости (для кэширования слоёв)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходники
COPY src ./src

# Собираем jar файл
RUN mvn clean package -DskipTests

# =========================
# Этап 2: Создание финального образа
# =========================
FROM eclipse-temurin:17-jdk-alpine

# Создаем директорию для приложения
WORKDIR /app

# Копируем jar файл из предыдущего этапа
COPY --from=build /app/target/*.jar app.jar

# Открываем порт приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java","-jar","app.jar"]
