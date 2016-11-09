var stompClient = null;
var gameOn = false;

var up = false;
var right = false;
var down = false;
var left = false;

var mapLenght = 1000;
var mapHeight = 1000;
var wallLat = 10;
var charLat = 4 * wallLat;

var gameInfo;

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

// Send the move to the server
function move(move) {
    stompClient.subscribe('/topic/game', function (game) {
        showGame(JSON.parse(game.body));
    });
    stompClient.send("/app/move", {}, JSON.stringify({'playerName': $("#name").val(), "move": move}));
}

// Response from server
function showGame(game) {
    gameInfo = game;
    console.log(gameInfo);

    $("#game").html("");
    var nrOfPlayers = game.playerList.length;
    if(nrOfPlayers < 4 && gameOn == false) {
        $("#game").append("<p> Wait for the other players to enter the game </p>")
    } else {
        // show the scores; display them only once
        if (!gameOn) {
            for (var i = 0; i < nrOfPlayers; i++) {
                $("#scores").append("<div class=\"col-xs-6 col-sm-3\"><div>" + game.playerList[i].name +
                        "</div><div>Score: " + game.playerList[i].score + "</div> </div>");
            }
            $("#greetings").html("");
        }

        gameOn = true;
        $("#game").append("<p> The game has started. </p>")
    }

    for (var i = 0; i < nrOfPlayers; i++) {
        if (allSprites.length <= i) {
            var img = loadImage("pumpkin_40x40.png");
            var s = createSprite((game.playerList[i].positionOx - 2) * wallLat,
                                 (game.playerList[i].positionOy - 2) * wallLat,
                                 charLat, charLat);
            s.addImage(img);
        }
        else {
            allSprites[i].position.x = (game.playerList[i].positionOx - 2) * wallLat;
            allSprites[i].position.y = (game.playerList[i].positionOy - 2) * wallLat;
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
	createCanvas(mapLenght, mapHeight);
}

// Draw on canvas; this is called continously
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

document.onkeydown = function(e) {
    switch (e.keyCode) {
        case 37:
            console.log('left');
            move('left');
            break;
        case 38:
            console.log('up');
            move('up');
            break;
        case 39:
            console.log('right');
            move('right');
            break;
        case 40:
            console.log('down');
            move('down');
            break;
    }
};
