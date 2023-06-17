# Используем базовый образ с установленной Java
FROM openjdk:11-jdk

# Установка переменных окружения
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/base
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

# Установка рабочей директории внутри контейнера
WORKDIR /app

# Копирование JAR-файла с приложением в контейнер
COPY target/myapp.jar /app

# Установка переменной окружения для указания пути к JAR-файлу
ENV JAR_FILE=myapp.jar

# Открытие порта, на котором будет работать приложение
EXPOSE 8080

# Запуск приложения при старте контейнера
CMD ["java", "-jar", "${JAR_FILE}"]
