<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Welcome to KatChat</title>
		<link rel="stylesheet" type="text/css" href="CSS/common.css"/>
        <link rel="stylesheet" type="text/css" href="CSS/login.css"/>
    </head>
    <body>
        <div class=head>
            <h1>Log ind</h1>
            <br>
        </div>
		<% if (session.getAttribute("error") != null){ %>
		<div class=error>
			<%= session.getAttribute("error") %>
		</div>
		<% }%>
        <div class=content>
            <form method="POST" action="LoginServlet">
                <fieldset>
					<label>Brugernavn:</label><input name="userID" type="text" >
                    <label>Kodeord:</label><input name="password" type="password">
                    <input type="submit">
				</fieldset>   
			</form>
		</div>
	</body>
</html>