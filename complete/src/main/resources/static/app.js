var stompClient = null;
var gameOn = false;

var mapLenght = 1000;
var mapHeight = 1000;
var wallLat = 6;
var charLat = 6 * wallLat;
var halfCharLat = charLat / 2;
var DIMENSION_SCALE = 1000;

var gameInfo;
var colors = {'0': 'red', '1': 'green', '2': 'blue', '3': 'GoldenRod'};
var hydeIndex = 0;
var hydeImage = {};
var jekyllImages = [];
var mapImage = {};

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
            //draw only the map
            showGame(JSON.parse(game.body));
        });
        stompClient.send("/app/start", {}, JSON.stringify({'content': $("#name").val()}));

        stompClient.subscribe('/topic/movement', function (infoPlayers) {
            if (gameInfo)
                gameInfo.infoPlayers = JSON.parse(infoPlayers.body);
        });

    });
}

function isHyde(index) {
  return index == hydeIndex;
}

// Response from server
function showGame(game) {
    gameInfo = game;
    console.log(gameInfo);

    $("#game").html("");
    var nrOfPlayers = game.infoPlayers.playerList.length;
    if(nrOfPlayers < 4 && gameOn == false) {
        $("#game").append("<p><i> Wait for the other players to enter the game </p>")
    } else {
        // show the scores; display them only once
        if (!gameOn) {
            for (var i = 0; i < nrOfPlayers; i++) {
                $("#scores").append("<div class=\"col-xs-6 col-sm-3\" style=\"color:" + colors[i] + "\"><div>"
                    + game.infoPlayers.playerList[i].name
                    + "</div><div>Score: " + game.infoPlayers.playerList[i].score + "</div> </div>");
            }
            $("#greetings").html("");
        }

        gameOn = true;
        $("#game").append("<p> The game has started. </p>")
    }

    mapImage = createImage(gameInfo.board.length * wallLat, gameInfo.board.length * wallLat);
    mapImage.loadPixels();
    for (x = 0; x < gameInfo.board.length; x++) {
        for (y = 0; y < gameInfo.board.length; y++) {
            var actualColor = color(255, 255, 255);
            if (gameInfo.board[x][y]) {
                actualColor = color(0, 29, 178)
            }
            for (i = x * wallLat ; i < (x + 1) * wallLat ; i++) {
                for (j = y * wallLat ; j < (y + 1) * wallLat ; j++) {
                    mapImage.set(i, j, actualColor);
                }
            }
        }
    }
    mapImage.updatePixels();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
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

// Setup for drawing
function setup() {
	var canvas = createCanvas(mapLenght, mapHeight);
	canvas.parent('sketch-holder');
	frameRate(60);
	hydeImage = loadImage("hyde_player_36.png");
	for(var i = 0; i < 4; i++) {
        jekyllImages[i] = loadImage(colors[i] + "_player_36.png");
    }


}

// Draw on canvas; this is called continuously
function draw() {
    if (gameInfo != undefined) {
        image(mapImage, 0, 0);
        if (gameOn) {
            drawPlayers();
        }
    }

}

function drawPlayers() {
    var nrOfPlayers = gameInfo.infoPlayers.playerList.length;
    hydeIndex = gameInfo.infoPlayers.hydeIndex;
    for (var i = 0; i < nrOfPlayers; i++) {
        if (isHyde(i)) {
            image(  hydeImage,
                    gameInfo.infoPlayers.playerList[i].absoluteX * wallLat / DIMENSION_SCALE - halfCharLat,
                    gameInfo.infoPlayers.playerList[i].absoluteY * wallLat / DIMENSION_SCALE - halfCharLat);
        }
        else {
            image(  jekyllImages[i],
                    gameInfo.infoPlayers.playerList[i].absoluteX * wallLat / DIMENSION_SCALE - halfCharLat,
                    gameInfo.infoPlayers.playerList[i].absoluteY * wallLat / DIMENSION_SCALE - halfCharLat);
        }
    }
}

// Send the move to the server
function move(move) {
    stompClient.send("/app/move", {}, JSON.stringify({'playerName': $("#name").val(), "move": move}));
}

// Key events for moving on the map
document.onkeydown = function(e) {
    switch (e.keyCode) {
        case 65: //'a'
            console.log('left');
            move('left');
            break;
        case 87: //'w'
            console.log('up');
            move('up');
            break;
        case 68: //'d'
            console.log('right');
            move('right');
            break;
        case 83: //'s'
            console.log('down');
            move('down');
            break;
    }
};
