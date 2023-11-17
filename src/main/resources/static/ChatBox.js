// var hostname = location.hostname;
// var brokerURL;
// if (hostname === 'localhost' || hostname === '127.0.0.1' || hostname === 'localhost:8080' || hostname === '127.0.0.1:8080')
// {brokerURL = 'ws://localhost:8080/gs-guide-websocket'; }
// else { brokerURL = 'ws://' + hostname + ':8080/gs-guide-websocket'; }
//
//     const stompClient = new StompJs.Client({ brokerURL: brokerURL });

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8000/gs-guide-websocket'
});

stompClient.activate();

$(document).ready(function () {
    var $chatHistory = $(".chat .chat-history");
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting();

    });
};


stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function connect() {
    stompClient.activate();
}

// function disconnect() {
//     stompClient.deactivate();
//     setConnected(false);
//     console.log("Disconnected");
// }

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'text': $("#text").val()})
    });
}

function showGreeting() {
    var chatDataDiv = $("#chat-history");
    chatDataDiv.innerHTML='';
    chatDataDiv.load('/add #chat-history', function(response, status, xhr) {
        if (status === "success") {
            var $chatHistory = $(".chat .chat-history");
            $chatHistory.scrollTop($chatHistory[0].scrollHeight);
        } else {
        }
    });
}


$(function () {
    $( "#connect" ).click(() => connect());
    // $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
    $( "#sendimage" ).click(() => sendName());
});

