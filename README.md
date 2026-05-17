# Валидатор данных

Java-библиотека для проверки данных по настраиваемым схемам. Валидатор поддерживает строки, числа и `Map`, включая вложенную проверку значений по ключам.

Пример работы: создаётся `Validator`, затем схема (`string()`, `number()` или `map()`), после чего к ней добавляются ограничения вроде `required()`, `minLength()`, `positive()`, `range()`, `sizeof()` и `shape()`. Метод `isValid()` возвращает `true`, если значение проходит все добавленные проверки.

---

### Тесты Hexlet и линтер

[![Actions Status](https://github.com/necasper/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/necasper/java-project-78/actions/workflows/hexlet-check.yml)

### Сборка CI

[![build](https://github.com/necasper/java-project-78/actions/workflows/build.yml/badge.svg)](https://github.com/necasper/java-project-78/actions/workflows/build.yml)

### SonarCloud

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=necasper_java-project-78&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=necasper_java-project-78)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=necasper_java-project-78&metric=coverage)](https://sonarcloud.io/summary/new_code?id=necasper_java-project-78)

---

## Как запускать проект

Проект находится в каталоге [`app`](app/). Это библиотека, поэтому у неё нет отдельной точки входа для запуска приложения. Основной способ проверить проект — собрать его и запустить тесты.

### Linux / macOS

```bash
cd app
chmod +x gradlew
./gradlew build
```

### Windows PowerShell

```powershell
cd app
.\gradlew.bat build
```

## Полезные команды

| Задача | Команда |
|--------|---------|
| Собрать проект | `./gradlew build` |
| Запустить тесты | `./gradlew test` |
| Запустить Checkstyle | `./gradlew checkstyleMain checkstyleTest` |
| Сформировать отчёт покрытия | `./gradlew jacocoTestReport` |
