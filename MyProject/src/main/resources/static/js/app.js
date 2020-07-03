var stompClient = null;
var num = 0;

if(sender!==null){
	connect();
}



function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	num = 0;
	stompClient.connect({}, function (frame) {
		console.log('Connected: ' + frame);
		if(who=='방문자'){
			sendHello();
		}
		// topic 뒤에 식별번호 sender (보내는 사람)
		stompClient.subscribe('/topic/'+ sender, function (chatLog) {
			//보낼때말고 받을 때도 시간필요
			d = serverToday();
			time = moment(d).format('h:mm a | MMMM DD');
			showChat(JSON.parse(chatLog.body));
		});
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	console.log("Disconnected");
}

/* Chat과 관련된 메서드 추가 */
function sendChat() {
	num++;
	// /app/hello로 JSON 파라미터를 메세지 body로 전송.
	d = serverToday();
	time = moment(d).format('h:mm a | MMMM DD');
	data = {'roomId':roomId, 'receiver':receiver,'sender':sender,'sendTime':d,'content':$("#Q").val(),'who':who, 'num':num};
	stompClient.send("/app/chat/send", {}, JSON.stringify(data));
	showMe(); 
}
function sendHello() {
	d = serverToday();
	time = moment(d).format('h:mm a | MMMM DD');
	data = {'roomId':roomId,'receiver':receiver, 'sender':sender,'content':receiver+'님이 입장하셨습니다.', 'who':who,'num':0};
	stompClient.send("/app/chat/send", {}, JSON.stringify(data));
}
function showChat(chatLog) {
	if(chatLog.num==0){
		alert(receiver+'님이 들어오셨습니다.');
		location.reload();
	}else{
	$("#chat").append("<li class='you'>"
			+ "<div class='entete'>"
			+ "<span class='status green'></span>"
			+ "<h2>"+ chatLog.sender +"</h2>&nbsp;&nbsp;"
			+ "<h3>"+ time +"</h3>&nbsp;&nbsp;"
			+ "</div>"
			+ "<div class='triangle'></div>"
			+ "<div class='message'>"+ chatLog.content +"</div>"
			+ "</li>");
	$('#chat').scrollTop($('#chat')[0].scrollHeight);
	}
}
function showMe() {
	$('#chat').append(
			"<li class='me'>" 
			+ "<div class='entete'><span class='status blue'>" 
			+ "</span><h2>" 
			+ sender+"</h2&nbsp;&nbsp;><h3>&nbsp;"+ time 
			+"</h3&nbsp;&nbsp;></div><div class='triangle'></div><div class='message'>"
			+ $('#Q').val() + "</div></li>");
	$('#chat').scrollTop($('#chat')[0].scrollHeight);
}

$(function () {

	$( "#back" ).click(function(e) { 
		sendBye();
		disconnect();});
	$( "#chatSend" ).click(function(){ 
		if($('#Q').val().trim()==""){
			alert("입력해주세요"); return;
			} 
		sendChat(); $('#Q').val(""); });
});