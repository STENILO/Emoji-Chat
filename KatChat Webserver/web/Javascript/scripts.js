/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getMessages() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (xhttp.readyState === 4 && xhttp.status === 200) {
			updateText(xhttp.responseText);
		}
	};
	xhttp.open("GET", "messages?id=" + id, true);
	xhttp.send();
}

function updateText(text) {
	var messages = document.getElementById("messages");
	messages.value = messages.value + text;
	messages.scrollTop = messages.scrollHeight;

	getMessages();
}

function sendMessage() {
	var input = document.getElementById("input");
	var message = input.value;
	input.value = "";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (xhttp.readyState === 4 && xhttp.status === 200) {
			updateText(xhttp.responseText);
		}
	};
	xhttp.open("POST", "messages", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("message=" + message + "&id=" + id);
}