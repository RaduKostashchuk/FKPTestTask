# FKPTestTask

## О проекте

Приложение производит периодический поиск дубликатов файлов в целевой директории.

Результаты поиска сохраняются в базе данных и доступны через REST.

Через REST результаты доступны как в виде списка всех файлов имеющих дубли,

так и в виде списка объектов дубликатов.

## Как использовать
### REST API 
| Операция | Метод запроса | Содержание запроса | Статус ответа | Содержание ответа |
|--|--|--|--|--|
| Список запусков сканирования | GET | **Url**: '/taskexecution/' | OK | ![](/image/tasks.png)|
| Список файлов дубликатов | GET | **Url**: '/fileentry/' | OK | ![](/image/files.png) |
| Список объектов дуликатов | GET | **Url**: '/duplicate' | OK | ![](/image/duplicates.png)|

## Настройка и сборка

Настройки приложения содержатся в файле /src/main/resources/application.properties,

в котором необходимо указать параметры подключения к базе данных.

Сборка приложения осуществляется командой: mvn install.

Запуск приложения осуществляется командой:

java -jar FKPTestTask-1.0.jar --target-folder=c:\temp --run-interval=60

Приложение принимает два аргумента, один из которых обязательный.

    1. --target-folder - обязательный аргумент, содержит абсолютный путь к целевой папке.
    2. --run-interval - необязательный аргумент, определяет интервал сканирования целевой папки в секундах.
    (значение по умолчанию - 1 час)

Перед запуском приложения следует создать базу данных и настроить ее в соответсвии с файлом application.properties.

## Контакты

Email: kostasc@mail.ru
Telegram: @rkostashchuk