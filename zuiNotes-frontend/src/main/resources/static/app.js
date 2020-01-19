let stompClient = null;

$(() => {
    $("form").on('submit', event => event.preventDefault());
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#authorization").click(authorization);
});

const setConnected = connected => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
};

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, frame => {
        setConnected(true);
        console.log(`Connected: ${frame}`);
        stompClient.subscribe(
            "/topic/authResponse",
            rawData => handleAuthResponse(rawData)
        );
        stompClient.subscribe(
            "/topic/addUserResponse",
            rawData => handleAddUserResponse(rawData)
        );
        stompClient.subscribe(
            "/topic/delUserResponse",
            rawData => handleDelUserResponse(rawData)
        );
    });
};

const disconnect = () => {
    clearStatusLine();
    clearUserInformationTable();
    clearAddUser();
    clearDelUser();
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
};

const authorization = () => stompClient.send(
    "/app/authUserRequest",
    {},
    JSON.stringify(
        {
            'login' : $("#login").val(),
            'password' : $('#password').val()
        }
    )
);

const addUserAction = () => stompClient.send(
    "/app/addUserRequest",
    {},
    JSON.stringify(
        {
            'login' : $("#addLogin").val(),
            'password' : $("#addPassword").val()
        }
    )
);

const delUserAction = () => stompClient.send(
    "/app/delUserRequest",
    {},
    JSON.stringify(
        {
            'login' : $("#delLogin").val()
        }
    )
);

const handleAuthResponse = rawData => {
    data = JSON.parse(rawData.body);
    status = data.status;
    userData = data.users;

    switch (status){
        case "admin":
            handleAdminAuthResponse(userData);
            break;
        case "user":
            handleUserAuthResponse(userData);
            break;
        default:
            handleWrongAuthResponse(status);
            break;
    }
};

const handleAdminAuthResponse = userData => {
    fillStatusLine("User Information was load.");
    fillUserInformationTable(userData);
    fillAddUser();
    fillDelUser();
};

const handleUserAuthResponse = userData => {
    login = userData[0].login;
    fillStatusLine("You are '" + login + "'.");
    clearUserInformationTable();
    clearAddUser();
    clearDelUser();
};

const handleWrongAuthResponse = status => {
    fillStatusLine(status);
    clearUserInformationTable();
    clearAddUser();
    clearDelUser();
};

const handleAddUserResponse = rawData => {
    data = JSON.parse(rawData.body);
    fillStatusLine(data.status);
    fillUserInformationTable(data.users);
};

const handleDelUserResponse = rawData => {
    data = JSON.parse(rawData.body);
    fillStatusLine(data.status);
    fillUserInformationTable(data.users);
}

const fillUserInformationTable = userData => {
    const userDataTableContainer = document.getElementById("userDataTableContainer");
    userDataTableContainer.innerHTML = "<table id='conversation' class='table table-striped'><thead><tr><th>User Information</th></tr></thead><tbody id='userInformation'><tr><td>ID</td><td>Login</td><td>Password</td><td>Is Admin</td></tr></tbody></table>";

    const userInfoNode = document.getElementById("userInformation");

    for (var i in userData){
        id = userData[i].id;
        login = userData[i].login;
        password = userData[i].password;
        admin = userData[i].admin
        $("#userInformation").append(
            `<tr><td>${id}</td><td>${login}</td><td>${password}</td><td>${admin}</td></tr>`
        );
    }
};

const fillStatusLine = line => {
    const statusLine = document.getElementById("statusLine");
    statusLine.innerHTML = "<hr><p>"+ line +"</p><hr>";
};

const fillAddUser = () => {
    const addUser = document.getElementById("addUser");
    addUser.innerHTML = "<hr>"
        + "<h5>User Addition</h5>"
        + "<label for='addLogin'>Login</label>"
        + "<input id='addLogin' type'text' class='form-control'>"
        + "<label for='addPassword'>Password</label>"
        + "<input id='addPassword' type'text' class='form-control'>"
        + "<button id='addUserButton' onclick='addUserAction();' class='btn btn-default' type='submit'>Add</button";
};

const fillDelUser = () => {
    const delUser = document.getElementById("delUser");
    delUser.innerHTML = "<hr>"
        + "<h5>User Deleting</h5>"
        + "<label for='delLogin'>Login</label>"
        + "<input id='delLogin' type'text' class='form-control'>"
        + "<button id='delUserButton' onclick='delUserAction();' class='btn btn-default' type='submit'>Del</button";
};

const clearStatusLine = () => {
    const statusLine = document.getElementById("statusLine");
    statusLine.innerHTML = "";
};

const clearUserInformationTable = () => {
    const userDataTableContainer = document.getElementById("userDataTableContainer");
    userDataTableContainer.innerHTML = "";
};

const clearAddUser = () => {
    const addUser = document.getElementById("addUser");
    addUser.innerHTML = "";
};

const clearDelUser = () => {
    const delUser = document.getElementById("delUser");
    delUser.innerHTML = "";
};

