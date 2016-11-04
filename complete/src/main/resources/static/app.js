var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#game").html("");
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));

        stompClient.subscribe('/topic/game', function (game) {
            showGame(JSON.parse(game.body));
        });
        stompClient.send("/app/start", {}, JSON.stringify({'name': $("#name").val()}));

    });
}

function showGame(game) {
    $("#game").html("");
    $("#game").append("<p> player " + game.playerList[0].name + " </p>")
    $("#game").append("<p> player " + game.playerList[1].name + " </p>")
    $("#game").append("<p> player " + game.playerList[2].name + " </p>")
}

function disconnect() {
    if (stompClient != null) {
        stompClient.send("/app/bye", {}, JSON.stringify({'name': $("#name").val()}));
        stompClient.send("/app/end", {}, JSON.stringify({'name': $("#name").val()}));
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});

