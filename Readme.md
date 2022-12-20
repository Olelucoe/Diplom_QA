# Дипломный проект профессии «Тестировщик ПО»

## О проекте

### Веб-сервис "Путешествие дня"

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API
Банка.

Приложение предлагает купить тур по определённой цене с помощью двух способов:

1. Оплата по дебетовой карте
2. Оплата по кредитной карте

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

1. Сервису платежей - Payment Gate
2. Кредитному сервису - Credit Gate

Приложение в собственной СУБД сохраняет информацию о том, каким способом был совершён платёж и успешно ли он был
совершён.

---

## Документация
- [План автоматизации](https://github.com/Olelucoe/Diplom_QA/blob/main/Documents/Plan.md)
- [Отчет по итогам тестирования](https://github.com/Olelucoe/Diplom_QA/blob/main/Documents/Report.md)
- [Итоговый отчёт по автоматизации](https://github.com/Olelucoe/Diplom_QA/blob/main/Documents/Summary.md)

## **Перед началом работы**

Первое, что необходимо сделать - загрузить на свой ПК репозиторий с проектом. Для этого либо воспользуйтесь командой git clone, либо в IntelliJ IDEA нажмите "Get from Version Control" и вставьте ссылку https://github.com/Olelucoe/Diplom_QA в поле URL, нажмите "Clone".

Для запуска тестов на вашем ПК должно быть установлено следующее ПО:

- IntelliJ IDEA
- Git
- Docker Desktop
- Google Chrome (или другой браузер)

---
### Установка и запуск

1. Запускаем контейнеры из файла **docker-compose.yml** командой в терминале:

```
docker-compose up -d
```

и проверяем, что контейнеры запустились командой:

```
docker-compose ps
```
_Ожидаемый статус контейнеров - **UP**_

2. Запускаем SUT командой в терминале:

- для MySQL:

```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
```

- для PostgreSQL:

```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
```

3. Запускаем авто-тесты командой в терминале:

- для MySQL:

```
./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
```

- для PostgreSQL:

```
./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
```

Дождаться сообщения в терминале, которое будет означать, что приложение успешно запущено:
![Снимок cmd java jar](https://user-images.githubusercontent.com/106590777/206429142-1b43b471-cd30-4467-9e66-922b4c174810.JPG)


Сервис будет доступен в браузере по адресу: _http://localhost:8080/_

4. Генерируем отчёт по итогам тестирования с помощью **Allure**. Отчёт автоматически откроется в браузере с помощью команды в терминале:

```
./gradlew allureServe
```

После генерации и работы с отчётом, останавливаем работу **allureServe** в терминале сочетанием клавиш _CTRL + C_ и
подтверждаем действие в терминале вводом _Y_.

Если необходимо перезапустить контейнеры, приложение или авто-тесты, нужно остановить работу сервисов в терминале
сочетанием клавиш _CTRL + C_ и перезапустить их, повторив шаги 1-3.

---

