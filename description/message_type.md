# Типы сообщений

* type - тип запроса или ответа
* data - объект, содержащий данные запроса/ответа
* login - логин пользователя
* password - пароль пользователя
* from - объект, содержащий информацию об источнике сообщения
* to - объект, содержащий информацию об цели сообщения
* host - хост источника или цели
* post - порт источника или цели
* entity - сущность источника или цели сообщения : DATABASE или FRONTEND.
* status - статус выполнения запроса
* nodes - объект, содержащий узлы дерева
* users - массив, содержащий полезователей
* groups - массив, содержащий группы
* node - объект, содержащий информацию об узле
* group - имя группы
* view - право группы на просмотр узла
* edit - право группы на редактирование узла
* add - право группы на добавление узла
* del - право группы на удаление узла

## 8.1 Запрос АВТОРИЗАЦИЯ
```json
{
    "type" : "AUTH_USER_REQUEST",
    "data" : {
        "login" : "...",
        "password" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.2 Ответ АВТОРИЗАЦИЯ
```json
{
    "type" : "AUTH_USER_RESPONSE",
    "data" : {
        "login" : "...",
        "status" : "...",
        "nodes" : {...}
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.3 Запрос НАСТРОЙКИ
```json
{
    "type" : "SETTING_REQUEST",
    "data" : {
        "login" : "...",
        "password" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.4 Ответ НАСТРОЙКИ
```json
{
    "type" : "SETTING_RESPONSE",
    "data" : {
        "login" : "...",
        "status" : "...",
        "users" : [...],
        "groups" : [...]
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.5 Запрос ДОБАВЛЕНИЕ УЗЛА
```json
{
    "type" : "ADD_NODE_REQUEST",
    "data" : {
        "login" : "...",
        "node" : {...}
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.6 Ответ ДОБАВЛЕНИЕ УЗЛА
```json
{
    "type" : "ADD_NODE_RESPONSE",
    "data" : {
        "login" : "...",
        "status" : "...",
        "node" : {...}
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.7 Запрос ПРОСМОТР УЗЛА
```json
{
    "type" : "VIEW_NODE_REQUEST",
    "data" : {
        "login" : "...",
        "node" : {
            "id" : "..."
        }
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.8 Ответ ПРОСМОТР УЗЛА
```json
{
    "type" : "VIEW_NODE_REQUEST",
    "data" : {
        "login" : "...",
        "node" : {...},
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.9 Запрос УДАЛЕНИЕ УЗЛА
```json
{
    "type" : "DEL_NODE_REQUEST",
    "data" : {
        "login" : "...",
        "node" : {
            "id" : "..."
        }
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.10 Ответ УДАЛЕНИЕ УЗЛА
```json
{
    "type" : "DEL_NODE_RESPONSE",
    "data" : {
        "login" : "...",
        "status" : "...",
        "node" : {
            "id" : "..."
        }
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.11 Запрос РЕДАКТИРОВАНИЕ УЗЛА
```json
{
    "type" : "EDIT_NODE_REQUEST",
    "data" : {
        "login" : "...",
        "node" : {...}
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.12 Ответ РЕДАКТИРОВАНИЕ УЗЛА
```json
{
    "type" : "EDIT_NODE_RESPONSE",
    "data" : {
        "login" : "...",
        "status" : "...",
        "node" : {...}
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.13 Запрос ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
```json
{
    "type" : "ADD_USER_REQUEST",
    "data" : {
        "login" : "...",
        "password" : "...",
        "group" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.14 Ответ ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
```json
{
    "type" : "ADD_USER_RESPONSE",
    "data" : {
        "login" : "...",
        "password" : "...",
        "group" : "...",
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.15 Запрос УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
```json
{
    "type" : "DEL_USER_REQUEST",
    "data" : {
        "login" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.16 Ответ УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
```json
{
    "type" : "DEL_USER_RESPONSE",
    "data" : {
        "login" : "...",
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.17 Запрос РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ
```json
{
    "type" : "EDIT_USER_REQUEST",
    "data" : {
        "login" : "...",
        "password" : "...",
        "group" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.18 Ответ РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ
```json
{
    "type" : "EDIT_USER_RESPONSE",
    "data" : {
        "login" : "...",
        "password" : "...",
        "group" : "...",
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.19 Запрос ДОБАВЛЕНИЕ ГРУППЫ ПРАВ
```json
{
    "type" : "ADD_GROUP_REQUEST",
    "data" : {
        "group" : "...",
        "view" : bool,
        "edit" : bool,
        "add" : bool,
        "del" : bool
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.20 Ответ ДОБАВЛЕНИЕ ГРУППЫ ПРАВ
```json
{
    "type" : "ADD_GROUP_RESPONSE",
    "data" : {
        "group" : "...",
        "view" : bool,
        "edit" : bool,
        "add" : bool,
        "del" : bool,
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.21 Запрос УДАЛЕНИЕ ГРУППЫ ПРАВ
```json
{
    "type" : "DEL_GROUP_REQUEST",
    "data" : {
        "group" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.22 Ответ УДАЛЕНИЕ ГРУППЫ ПРАВ
```json
{
    "type" : "DEL_GROUP_RESPONSE",
    "data" : {
        "group" : "...",
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.23 Запрос РЕДАКТИРОВАНИЕ ГРУППЫ ПРАВ
```json
{
    "type" : "EDIT_GROUP_REQUEST",
    "data" : {
        "group" : "...",
        "view" : bool,
        "edit" : bool,
        "add" : bool,
        "del" : bool
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.24 Ответ РЕДАКТИРОВАНИЕ ГРУППЫ ПРАВ
```json
{
    "type" : "EDIT_GROUP_RESPONSE",
    "data" : {
        "group" : "...",
        "view" : bool,
        "edit" : bool,
        "add" : bool,
        "del" : bool,
        "status" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    },
    "to" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.25 Запрос I AM
```json
{
    "type" : "I_AM_REQUEST",
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```
## 8.26 Ответ I AM
```json
{
    "type" : "I_AM_RESPONSE",
    "data" : {
        "url" : "..."
    },
    "from" : {
        "host" : "...",
        "port" : 0,
        "entity" : "..."
    }
}
```