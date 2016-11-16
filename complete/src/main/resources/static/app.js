var stompClient = null;
var gameOn = false;

var mapLenght = 1000;
var mapHeight = 1000;
var wallLat = 6;
var charLat = 6 * wallLat;

var gameInfo;
var colors = {'0': 'red', '1': 'green', '2': 'blue', '3': 'GoldenRod'};
var hydeIndex = 0;

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
            //draw the players
            showPlayers(JSON.parse(infoPlayers.body));
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
        $("#game").append("<p> Wait for the other players to enter the game </p>")
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

    if(gameOn){
        hydeIndex = game.infoPlayers.hydeIndex;
        for(var i = 0; i < nrOfPlayers; i++){
            if(isHyde(i)) {
                var img = loadImage("hyde_player_36.png");
            } else {
                var img = loadImage(colors[i] + "_player_36.png");
            }
            var s = createSprite((game.infoPlayers.playerList[i].positionOx - 3) * wallLat,
                (game.infoPlayers.playerList[i].positionOy - 3) * wallLat,
                charLat, charLat);
            s.addImage(img);
        }
    }

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
}

// Draw on canvas; this is called continuously
function draw() {
  // draw map
  if (gameInfo != undefined) {
      for(x = 0; x < gameInfo.board.length; x++) {
        for (y = 0; y < gameInfo.board.length; y++) {
            if (gameInfo.board[y][x] == 0) {
                fill(255, 255, 255);
                noStroke();
                rect(x * wallLat, y * wallLat, wallLat, wallLat);
            } else {
                fill(0, 29, 178);
                noStroke();
                rect(x * wallLat, y * wallLat, wallLat, wallLat);
            }
        }
      }
  }
  drawSprites();
}

// Send the move to the server
function move(move) {
    stompClient.send("/app/move", {}, JSON.stringify({'playerName': $("#name").val(), "move": move}));
}

function showPlayers(infoPlayers) {
    var  nrOfPlayers = infoPlayers.playerList.length;
    if(gameOn) {
        hydeIndex = infoPlayers.hydeIndex;
        for (var i = 0; i < nrOfPlayers; i++) {
            if (isHyde(i)) {
                var img = loadImage("hyde_player_36.png");
            } else {
                var img = loadImage(colors[i] + "_player_36.png");
            }
            allSprites[i].addImage(img)
            allSprites[i].position.x = (infoPlayers.playerList[i].positionOx - 3) * wallLat;
            allSprites[i].position.y = (infoPlayers.playerList[i].positionOy - 3) * wallLat;
        }
    }
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
