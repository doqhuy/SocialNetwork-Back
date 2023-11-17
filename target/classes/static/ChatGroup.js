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
    //Luôn luôn cuộn xuống dưới cùng của chat
    var $chatHistory = $(".chat .chat-history");
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);

    //Ẩn các chức năng bên phải
    $(".form-trigger").click(function() {
        // Check if the button is disabled
        if ($(this).hasClass("disabled")) {
            return; // Do nothing if the button is disabled
        }
        // Get the ID of the clicked button
        var id = $(this).attr("id");
        // Show the corresponding form
        if ($("#" + id.replace("btn", "form")).is(":visible")) {
            // If visible, hide the form
            $("#" + id.replace("btn", "form")).hide();
        } else {
            // If hidden, show the form
            $("#" + id.replace("btn", "form")).show();
        }


    });




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
    chatDataDiv.load('/Chat/Groups #chat-history', function(response, status, xhr) {
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
    $( "#sendimage2" ).click(() => sendName());
    $( "#namechange" ).click(() => sendName());
});

