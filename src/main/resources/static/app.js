var currentSubscribedRoom = 0;
var stompSubscription;

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function sendMsgInChatRoom() {
    stompClient.publish({
        destination: "/app/hello/" + currentSubscribedRoom,
        body: JSON.stringify({'name': $("#chat_msg").val()})
    });
}

function showChatMsg(message) {
    $("#chat_room_texts").append("<tr><td>" + message + "</td></tr>");
}

window.onload = function () {
    fetch('/rooms') // Replace with your Spring REST API endpoint
        .then(response => response.json())
        .then(data => {
            const roomList = document.getElementById('room-list');
            roomList.textContent = null
            data.forEach(room => {
                const li = document.createElement('li');
                li.textContent = room.name;
                li.addEventListener('click', function () {
                    // disconnect from current websocket
                    if (currentSubscribedRoom !== room.id) {
                        const chatRoomTexts = document.getElementById('chat_room_texts');
                        chatRoomTexts.textContent = null
                    }
                    currentSubscribedRoom = room.id;
                    if (stompSubscription !== undefined) {
                        stompSubscription.unsubscribe();
                    }

                    stompSubscription = stompClient.subscribe('/topic/room/' + currentSubscribedRoom, (greeting) => {
                        showChatMsg(JSON.parse(greeting.body).content);
                    });
                });
                roomList.appendChild(li);
            });
        });
};


$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#chat_msg_send").click(() => sendMsgInChatRoom());

    document.getElementById('room-form').addEventListener('submit', function (event) {
        event.preventDefault();
        const roomId = document.getElementById('room-id').value;
        const roomName = document.getElementById('room-name').value;
        fetch('/create/room', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({id: roomId, name: roomName}),
        })
            .then(data => {
                window.onload();
            });
    });

    document.getElementById('refresh-button').addEventListener('click', function () {
        window.onload();
    });

    stompClient.activate();
});

