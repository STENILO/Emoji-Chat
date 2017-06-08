<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <title>Kat Chat</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="CSS/common.css">
		<link rel="stylesheet" type="text/css" href="CSS/chat.css">
		<script type="text/javascript" src="Javascript/scripts.js"></script>

	</head>
	<body onload="getMessages()">
		<script type="text/javascript">
			var id = <%= session.getAttribute("JavaSessionID") %>
		</script>
		<div class=head>
            <h1>Velkommen til chatten <%= session.getAttribute("userID") %></h1>
        </div>
		<textarea id="messages" readonly></textarea>
		<BR>
		<input type="text" id="input" onkeypress="if (event.keyCode === 13)
					sendMessage();">
		<button onclick="sendMessage()">Send</button>
	</body>
</html>
