<!DOCTYPE html>
<html>
<head>
    <title>Hello WebSocket</title>
    <script src="http://code.jquery.com/jquery-2.1.0.js"></script>
    <%--<script src="sockjs-0.3.4.js"></script>--%>
    <%--<script src="stomp.js"></script>--%>
    <script type="text/javascript">

      var host = "localhost:8080";
      var ws = new WebSocket("ws://" + host + "/ws/hello");

      ws.onopen = function(){
        console.log("onopen")
      };
      ws.onmessage = function(message){
        $( "#response" ).append( JSON.parse(message.data).content )
      };
      
      function sendUsingWebSocket(){
        var name = document.getElementById('name').value;
        ws.send(JSON.stringify({ 'name': name }));
      }
      
      function sendUsingHttp(){
        var name = document.getElementById('name').value;
        $.ajax({
          type: "POST",
          url: "http://" + host + "/hello",
          data: JSON.stringify({ 'name': name }),
          dataType: "json",
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        }).done(function (message) {
          $( "#response" ).append( message.content )
        });      
      }
      
    </script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name" />
        <button id="sendName_websocket">Send (WebSocket)</button>
        <button id="sendName_http">Send (Http)</button>
        <p id="response"></p>
    </div>
</div>
<script>
  $( "#sendName_websocket" ).click(function() {
    sendUsingWebSocket();
  });
  $( "#sendName_http" ).click(function() {
    sendUsingHttp();
  });
</script>
</body>
</html>