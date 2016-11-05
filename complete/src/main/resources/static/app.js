var stompClient = null;
var gameOn = false;

var up = false;
var right = false;
var down = false;
var left = false;

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


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        setConnected(true);

        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.send("/app/hello", {}, JSON.stringify({'content': $("#name").val()}));

        stompClient.subscribe('/topic/game', function (game) {
            showGame(JSON.parse(game.body));
        });
        stompClient.send("/app/start", {}, JSON.stringify({'content': $("#name").val()}));

    });
}


function move(move) {
    //TODO !!! continuous move ???
    stompClient.subscribe('/topic/game', function (game) {
        showGame(JSON.parse(game.body));
    });
    stompClient.send("/app/move", {}, JSON.stringify({'playerName': $("#name").val(), "move": move}));
}

function showGame(game) {
    $("#game").html("");
    var nrOfPlayers = game.playerList.length;
    if(nrOfPlayers < 4 && gameOn == false) {
        $("#game").append("<p> Wait for the other players to enter the game </p>")
    } else {
        gameOn = true;
        $("#game").append("<p> The game has started. </p>")
        //TODO draw game.board and players with game.playerList[i].positionOx and Oy
    }

    for (var i = 0; i < nrOfPlayers; i++) {
        $("#game").append("<p> player " + game.playerList[i].name + " </p>");
    }

    $("#game").append("<p> test -----> score 1st player " + game.playerList[0].score + " </p>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });

    //TODO - change the events that call move function
    $("#up").click(function() { move("up"); });
    $("#right").click(function() { move("right"); });
    $("#down").click(function() { move("down"); });
    $("#left").click(function() { move("left"); });
});

function disconnect() {
    if (stompClient != null) {
        stompClient.send("/app/bye", {}, JSON.stringify({'content': $("#name").val()}));
        stompClient.send("/app/end", {}, JSON.stringify({'content': $("#name").val()}));
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
