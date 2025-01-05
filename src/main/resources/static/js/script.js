const chatContainer = document.getElementById('chat-container');
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({},function (frame){
    stompClient.subscribe('/topic/messages', function (message){
        const receivedMessage = JSON.parse(message.body);
        addMessage(receivedMessage.content, 'agent', new Date(receivedMessage.time).toLocaleTimeString());
    })
});

function addMessage(content, sender, time) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('message', sender === 'user' ? 'user-message' : 'agent-message');
    messageElement.innerHTML = `
        <div>${content}</div>
        <div class="time">${time}</div>
    `;
    chatContainer.appendChild(messageElement);
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function sendMessage() {
    const input = document.getElementById('message-input');
    const message = input.value.trim();
    if (!message) return;


    // 사용자 메시지 추가
    const time = new Date().toLocaleTimeString();
    addMessage(message, 'user', time);

    // "상담원 연결중입니다." 메시지 (자동 응답)
    setTimeout(() => {
        addMessage('상담원 연결중입니다. 잠시만 기다려주세요.', 'agent', new Date().toLocaleTimeString());
    }, 1000);

    // 입력창 비우기
    input.value = '';
}
