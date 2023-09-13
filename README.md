# job4j_url_shortcut

## Описание проекта
Учебный проект, представляющий собой RESTful веб-сервис для сокращения url-ссылок.

Общие возможности сервиса:
- Регистрация и авторизация пользователей для получения сокращенных ссылок и просмотра общего количества вызовов зарегистрированных url-адресов
- Переход по сокращенной ссылке на исходный адрес без необходимости авторизации в сервисе

## Стек технологий
- **Java 17.0.4.1**
- **Spring Boot 2.7.12**
- **Spring Data JPA 2.7.12**
- **Spring Web 5.3.27**
- **Spring Security 5.7.8**
- **Spring Test 5.3.27**
- **Java JWT 4.3.0**
- **Lombok 1.18.26**
- **Hashids 1.0.3**
- **PostgreSQL**
- **H2 Database 2.2.220**
- **Liquibase 4.22.0**
- **Springdoc 1.7.0**
- **Jacoco 0.8.10**
- **GitHub Action**

## Требования к окружению
- **Java 17.0.4.1**
- **Maven 3.6.2**
- **PostgreSQL 13.0**
- **cURL 8.0.1**

## Запуск проекта
- Создание БД  
  _create database url_shortcut;_
- Компиляция проекта  
  _maven package_
- Запуск проекта  
  _java -jar target\job4j_url_shortcut-0.0.1-SNAPSHOT.jar_

## Взаимодействие с приложением
**Описание API сервиса**
http://localhost:8080/swagger-ui/index.html

Примеры команд для тестирования сервиса с помощью утилиты cURL:
1. **Регистрация сайта**  
Запрос: _curl -H "Content-Type:application/json" -X POST -d"{\"site\":\"job4j.ru\"}" http://localhost:8080/registration_  
Ответ: _{"registration":true,"login":"yThfQVTy","password":"xuj3uFWP?X6n"}_
2. **Авторизация**  
Запрос: _curl -H "Content-Type:application/json" -X POST -d"{\"login\":\"yThfQVTy\",\"password\":\"xuj3uFWP?X6n\"}" -v http://localhost:8080/login_  
Ответ: _*** Authorization: Bearer eyJ0eXAiOi***_
3. **Регистрация URL**  
Запрос: _curl -H "Authorization: Bearer Bearer eyJ0eXAiOi***" -H "Content-Type:application/json"  -X POST -d"{\"url\":\"https://job4j.ru/profile/exercise/106/task-view/532\"}" http://localhost:8080/convert_  
Ответ: _{"code":"EOgLxE1"}_
4. **Переадресация. Выполняется без авторизации**  
Запрос: _curl -i http://localhost:8080/redirect/EOgLxE1_  
Ответ: _HTTP/1.1 302 Location: https://job4j.ru/profile/exercise/106/task-view/532_
5. **Получение статистики**  
Запрос: _curl -i http://localhost:8080/statistic -H "Authorization: Bearer Bearer eyJ0eXAiOi***"_  
Ответ: _[{"url":"https://job4j.ru/profile/exercise/106/task-view/532","total":2}]_

## Контакты
telegram: https://t.me/yuriy_litvinenko
