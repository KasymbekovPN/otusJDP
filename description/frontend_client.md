# 5. Frontend-клиент

Frontend-клиент:
* Принимает запрос от GUI.
* На основе запроса от GUI формирует и отправляет запрос в database-клиент.
* Принимает ответ от database-клиента, на его основе формирует и отправляет ответ в GUI.
* Принятые от database-клиента данные сохраняются в хэшах данного клинта.
* Если запрос от GUI не требует модификации данных, а требуемые данные есть в хэшах, то данные беруться из соответствующего хэша.

## 5.1. ХЭШ
Frontend-клиент включает в себя хэши:
* Хэш с данными пользователей.
* Хэш с данными групп прав.
* Хэш с данными узлов.

## 5.2. Запросы
### 5.2.1. АВТОРИЗАЦИЯ
Frontend-клиент по запросу GUI запрашивает у соответствующего database-клиента данные необходимые для авторизации и перенаправляет ответ в GUI.

Frontend-клиент передает в database-клиент:
* Логин пользователя
* пароль пользователя

Frontend-клиент принимает от database-клиента:
* Логин пользователя
* Статус запроса
* Данные для формирования дерева заметок.

### 5.2.2. НАСТРОЙКИ
Frontend-клиент по запросу GUI запрашивает у соответствующего database-клиента данные необходимые для окна настроек и перенаправляет ответ в GUI.

Frontend-клиент передает в database-клиент:
* Логин пользователя
* пароль пользователя

Frontend-клиент принимает от database-клиента:
* Логин пользователя
* Статус запроса
* Данные пользователей
* Данные групп

### 5.2.3. ДОБАВЛЕНИЕ УЗЛА
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для добавления нового узла, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин пользователя, создающего узел
* Данные узла

Frontend-клиент принимает от database-клиента:
* Логин пользователя, создающего узел
* Статус запроса
* Данные узла


### 5.2.4. ПРОСМОТР УЗЛА
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые дляпростотра содержимого узла, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин пользователя
* Данные узла (только идентификатор)

Frontend-клиент принимает от database-клиента:
* Логин пользователя
* Данные узла (только идентификатор)
* Данные узла

### 5.2.5. УДАЛЕНИЕ УЗЛА
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для удаления узла, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин пользователя, удаляющего узел
* Данные узла (только идентификатор узла)

Frontend-клиент принимает от database-клиента:
* Логин пользователя, удаляющего узел
* Статус запроса
* Данные узла (только идентификатор узла)


### 5.2.6. РЕДАКТИРОВАНИЕ УЗЛА
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для редактирования узла, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин пользователя, редактирующего узел
* Измененные данные узла

Frontend-клиент принимает от database-клиента:
* Логин пользователя, редактирующего узел
* Статус запроса
* Измененные данные узла


### 5.2.7. ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для добавления нового пользователя, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин, добавляемого пользователя
* Пароль, добавляемого пользователя
* Группа прав, добавляемого пользователя.

Frontend-клиент принимает от database-клиента:
* Логин, добавляемого пользователя
* Пароль, добавляемого пользователя
* Группа прав, добавляемого пользователя.
* Статус запроса

### 5.2.8. УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для удаления пользователя, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин, удаляемого пользователя

Frontend-клиент принимает от database-клиента:
* Логин, удаляемого пользователя
* Статус запроса


### 5.2.9. РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для редактирования пользователя, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Логин, редактируемого пользователя пользователя
* Пароль
* Группа прав

Frontend-клиент принимает от database-клиента:
* Логин, редактируемого пользователя пользователя
* Пароль
* Группа прав
* Статус запроса
  
### 5.2.10. ДОБАВЛЕНИЕ ГРУППЫ ПРАВ
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для добавления новой группы прав, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Группа прав
* Возможность просмотра заметок для данной группы
* Возможность редактирования заметок для данной группы
* Возможность добавления заметок для данной группы
* Возможность удаления заметок для данной группы

Frontend-клиент принимает от database-клиента:
* Группа прав
* Возможность просмотра заметок для данной группы
* Возможность редактирования заметок для данной группы
* Возможность добавления заметок для данной группы
* Возможность удаления заметок для данной группы
* Статус запроса

### 5.2.11. УДАЛЕНИЕ ГРУППЫ ПРАВ
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для удаления группы прав, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Группа прав

Frontend-клиент принимает от database-клиента:
* Группа прав
* Статус запроса

### 5.2.12. РЕДАКТИРОВАНИЕ ГРУППЫ ПРАВ
Frontend-клиент по запросу GUI передает в соответствующий database-клиент данные необходимые для редактирования группы прав, полученный ответ перенаправляет в GUI.

Frontend-клиент передает в database-клиент:
* Группа прав
* Возможность просмотра заметок для данной группы
* Возможность редактирования заметок для данной группы
* Возможность добавления заметок для данной группы
* Возможность удаления заметок для данной группы

Frontend-клиент принимает от database-клиента:
* Группа прав
* Возможность просмотра заметок для данной группы
* Возможность редактирования заметок для данной группы
* Возможность добавления заметок для данной группы
* Возможность удаления заметок для данной группы
* Статус запроса


## 5.2.13 I AM
Frontend-клиент после своего запуска, с определенной периодичностью, шлёт в messageSystem-клиент, регистрирующее его сообщение в messageSystem-клиенте, сообщение. Отправка данного сообщения прекращается после приема ответа.