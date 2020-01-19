# 6. Database-клиент

Database-клиент:
* Принимает запрос от frontend-клиента.
* Производит работу с БД на основе запроса.
* Отправляет ответ в frontend-клиент.
* database-клиент работает с таблицами:
  * Таблица пользователей
  * Таблица групп прав
  * Таблица узлов дерева (плоский список)
  * Таблица соотношений родитель - ребенок


## 6.1. Запросы

### 6.1.1. АВТОРИЗАЦИЯ
* Проверяет валидность логина и пароля
* В случае успешной проверки : выгружает данные для формирования деррева.
* Формирует и отправляет ответ.

### 6.1.2. НАСТРОЙКИ
* Проверяет валидность логина и пароля.
* В случае успешной проверки : выгружает данные для пользователей и групп прав.
* Формирует и отправляет ответ.

### 6.1.3. ДОБАВЛЕНИЕ УЗЛА
* Проверяет может ли данный пользователь добавлять узел
* Если может, то модифицирует таблицы.
* Формирует и отправляет ответ.

### 6.1.4. ПРОСМОТР УЗЛА
* Проверяет может ли данный пользователь просматривать узел
* Если может, то выгружаеи узел
* Формирует и отправляет ответ.

### 6.1.5. УДАЛЕНИЕ УЗЛА
* Проверяет может ли данный пользователь удалять узел
* Если может, то модифицируем таблицы
* Формирует и отправляет ответ.

### 6.1.6. РЕДАКТИРОВАНИЕ УЗЛА
* Проверяет может ли данный пользователь редактировать узел
* Если может, то модифицируем таблицы
* Формирует и отправляет ответ.

### 6.1.7. ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
* Добавляем пользователя в БД
* Формирует и отправляет ответ.

### 5.2.8. УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
* Удаляем пользователя из БД
* Формирует и отправляет ответ.

### 5.2.9. РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ
* Редактируем пользователя в БД
* Формирует и отправляет ответ.
  
### 5.2.10. ДОБАВЛЕНИЕ ГРУППЫ ПРАВ
* Добавляем группу прав в БД
* Формирует и отправляет ответ.

### 5.2.11. УДАЛЕНИЕ ГРУППЫ ПРАВ
* Удаляем группу прав из БД
* Формирует и отправляет ответ.

### 5.2.12. РЕДАКТИРОВАНИЕ ГРУППЫ ПРАВ
* Редактируем группу прав в БД
* Формирует и отправляет ответ.

## 5.2.13 I AM
database-клиент после своего запуска, с определенной периодичностью, шлёт в messageSystem-клиент, регистрирующее его сообщение в messageSystem-клиенте, сообщение. Отправка данного сообщения прекращается после приема ответа.