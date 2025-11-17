# bardzo lekka wersja Alpine Linux z JDK 21 od Temurina.
FROM eclipse-temurin:21-jdk-alpine
# Ustawia katalog roboczy wewnątrz obrazu.
WORKDIR /app
# Kopiujemy nasz build, który jest wykonany przez np. github actions
COPY build/libs/*.jar app.jar
# Deklarujemy na którym porcie kontener ma nasłuchiwać
EXPOSE 8080
# Uruchamiamy aplikacje
ENTRYPOINT [ "java","-jar","app.jar" ]
