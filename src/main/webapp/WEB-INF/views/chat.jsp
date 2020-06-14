<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Чат</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script>
        var webSocket;
        function connect() {
            webSocket = new WebSocket('ws://localhost:8080/update');

            webSocket.onmessage = function receiveMessage(response) {
                console.log("data", response);
                let data = response['data'];
                let json = JSON.parse(data);
                let mess = json['message'].split("\n");
                if (mess.length == 1) {
                    $('#messagesList').first().after("<li>" + json['message'] +"</li>")
                } else {
                    var m = "";
                    for (var i = 0; i < mess.length; i++) {
                        m += mess[i] + "</br>"
                    }
                    $('#messagesList').first().after("<li>" + m +"</li>")
                }
            }
        }

        function sendMessage(text, pageId) {
            let message = {
                "message": text,
                "from": pageId
            };

            webSocket.send(JSON.stringify(message));
        }
    </script>
</head>
<body onload="connect()">
<div>
    <label for="message">Текст сообщения</label>
    <input name="message" id="message" placeholder="Сообщение">
    <button onclick="sendMessage($('#message').val(), '${pageId}')">Отправить</button>
    <h3>Сообщения</h3>
    <ul id="messagesList">

    </ul>
</div>
</body>
</html>