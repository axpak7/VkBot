# VkBot

## VK bot for a test task in Just-AI.

В рамках задания нужно создать бота который при его упоминании будет цитировать
присланный ему текст. Пример взаимодействия с подобным ботом см. на картинке:

![screen](https://github.com/axpak7/VkBot/blob/master/src/screenshots/screenshot4.PNG)


Далее будет описан подробный процесс запуска приложения.

### 1. Ngrok

Прежде всего вам понадобится использование внешних http адресов, поэтому запускаем ngrok
```$xslt
ngrok http 8080
```
![screen](https://github.com/axpak7/VkBot/blob/master/src/screenshots/screenshot2.PNG)

Здесь вам понадобится http адрес, например http://fb7347381f28.ngrok.io

### 2. Настройки сообщества

Для подключения чат-бота Вы можете использовать любое своё сообщество ВКонтакте - группу,
встречу или публичную страницу.

Первым делом нужно указать, что в вашем сообществе будет работать бот. Для этого перейдите
в "Управление сообществом" → "Сообщения" → "Настройки для бота" и включите пунк 
"Возможности бота".

Не забудьте включить сообщения в Вашем сообществе ("Управление сообществом" → "Сообщения"), 
когда бот будет готов к использованию, чтобы ему можно было написать.

### 3. Получение ключа доступа

Ключ доступа потребуется вам для работы с API.

Откройте раздел «Управление сообществом» («Управление страницей», если у 
Вас публичная страница), выберите вкладку «Работа с API» и нажмите «Создать 
ключ доступа».

Отметьте необходимые права доступа и подтвердите свой выбор. (Для этого чат бота
обязательно **Разрешить приложению доступ к сообщениям сообщества**)

![screen](https://github.com/axpak7/VkBot/blob/master/src/screenshots/screenshot3.PNG)

### 4. Подключение Callback API

Для подключения Callback API нужно открыть раздел «Управление сообществом» («Управление страницей»,
 если у Вас публичная страница), перейти во вкладку «Работа с API».
Далее необходимо указать и подтвердить конечный адрес сервера, куда будут направлены все запросы.

![screen](https://github.com/axpak7/VkBot/blob/master/src/screenshots/screenshot1.PNG)

В поле "Адрес" вставляем http адрес, который мы получили в ngrok

### 5. Подключение бота

Далее в классе CallbackController необходимо изменить следующие параметры:

- ACCESS_TOKEN (ключ доступа, полученный на 3 шаге)
- CALLBACK_API_CONFIRMATION_TOKEN (строка, которую должен вернуть сервер, 
полученная на 4 шаге)

Запускаем debug и всё готово.
