# 9. Обмен сообщений между клиентами

Функционал приема/передачи между клиентами реализуется в SockethandlerImpl, схематично показан ниже.

![](/description/img/messaging/common.svg)
* 9.1. handleInProcessor - метод, выполняемый в отдельном потоке, "слушает" порт, передает, полеченное сообщение в соответствующий типу сообщения обработчик (H)
* 9.2. H - обработчик сообщения; для каждого типа клиента реализуется свой набор обработчиков сообщений.
* 9.3. handlers - контейнер содержащий пары : "тип сообщения" - "обработчик сообщения"
* 9.4. send - метод, передающий отправляемое сообщение в SSH
* 9.5. SSH (socketSendingHandler) - обработчик отправки сообщения; свой SSH реализуется для кждого типа клиента.