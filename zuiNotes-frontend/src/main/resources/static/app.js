
let stompClient = null;
let uiId;
let login;

var requestRoot = "app";
var responseRoot = "topic";

var loginMessageType = "LOGIN";
var logoutMessageType = "LOGOUT";
var userDataMessageType = "USER_DATA";

var addUserMessageType = "ADD_USER";
var delUserMessageType = "DEL_USER";
var groupDataMessageType = "GROUP_DATA";

function uuidv4() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
      (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}

$(() => {

    uiId = uuidv4();

    $("form").on('submit', event => event.preventDefault());
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);

    connect();
    fillEntryTopMenu();
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
            "/" + responseRoot + "/" + loginMessageType + "/" + uiId,
            message => loginResponse(message)
        );
        stompClient.subscribe(
            '/' + responseRoot + '/' + userDataMessageType + '/' + uiId,
            message => userDataResponse(message)
        )

        // stompClient.subscribe(
        //     "/" + responseRoot + "/" + addUserMessageType + "/" + uiId,
        //     rawData => handleAddUserResponse(rawData)
        // );
        // stompClient.subscribe(
        //     "/" + responseRoot + "/" + delUserMessageType + "/" + uiId,
        //     rawData => handleDelUserResponse(rawData)
        // );

        // stompClient.subscribe(
        //     "/" + responseRoot + "/" + userDataMessageType + "/" + uiId,
        //     data => userDateResponse(data)
        // );
        // stompClient.subscribe(
        //     '/' + responseRoot + '/' + groupDataMessageType + '/' + uiId,
        //     data => groupDataResponse(data)
        // );

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

const loginRequest = () => stompClient.send(    
    "/" + requestRoot + "/" + loginMessageType,
    {},
    JSON.stringify(
        {
            'login' : $("#login").val(),
            'password' : $('#password').val(),
            'uiId' : uiId
        }
    )
);

const loginResponse = message => {
    //<
    console.log(message);
    //<
    data = JSON.parse(message.body);
    login = data.login;
    group = data.group;
    errors = data.errors;

    if (Object.keys(errors).length == 0){
        if (group == 'admin'){
            fillAdminTopMenu(login);
        } else {
            fillUserTopMenu(login);
        }
    } else {
        fillErrorTopMenu(errors);
    }
};

const logoutRequest = () => {
    stompClient.send(
        '/' + requestRoot + '/'  + logoutMessageType,
        {},
        JSON.stringify(
            {
                'login' : login,
                'uiId' : uiId
            }
        )
    );
    fillEntryTopMenu();
};

const userDataRequest = () => {
    stompClient.send(
        '/' + requestRoot + '/' + userDataMessageType,
        {},
        JSON.stringify(
            {
                'uiId' : uiId
            }
        )
    );
};

const userDataResponse = (message) => {
    //<
    console.log("userDataResponse : " + message);
    //<

    data = JSON.parse(message.body);
    userData = data.users;
    errors = data.errors;

    if (Object.keys(errors).length == 0){
        fillDataUserInformationTable(userData);
    } else {
        fillErrorsUserInfornationData(errors);
    }

    //<
    // {"users":[{"id":1,"login":"admin","password":"qwerty","admin":true},{"id":2,"login":"user1","password":"user1pass","admin":false,"uiId":""},{"id":7,"login":"user6","password":"user6pass","admin":false,"uiId":""},{"id":8,"login":"user7","password":"user7pass","admin":false,"uiId":""},{"id":9,"login":"user8","password":"user8pass","admin":false,"uiId":""},{"id":10,"login":"user9","password":"user9pass","admin":false,"uiId":""}],"errors":[]}
}



//<
// const addUserAction = () => stompClient.send(
//     "/" + requestRoot + "/" + addUserMessageType,
//     {},
//     JSON.stringify(
//         {
//             'login' : $("#addLogin").val(),
//             'password' : $("#addPassword").val(),
//             'uiId' : uiId
//         }
//     )
// );

// const delUserAction = () => stompClient.send(
//     "/" + requestRoot + "/" + delUserMessageType,
//     {},
//     JSON.stringify(
//         {
//             'login' : $("#delLogin").val(),
//             'uiId' : uiId
//         }
//     )
// );



// const handleAdminAuthResponse = userData => {
//     fillStatusLine("User Information was load.");
//     fillUserInformationTable(userData);
//     fillAddUser();
//     fillDelUser();
// };

// const handleUserAuthResponse = userData => {
//     login = userData[0].login;
//     fillStatusLine("You are '" + login + "'.");
//     clearUserInformationTable();
//     clearAddUser();
//     clearDelUser();
// };

// const handleWrongAuthResponse = status => {
//     fillStatusLine(status);
//     clearUserInformationTable();
//     clearAddUser();
//     clearDelUser();
// };

// const handleAddUserResponse = rawData => {
//     data = JSON.parse(rawData.body);
//     fillStatusLine(data.status);
//     fillUserInformationTable(data.users);
// };

// const handleDelUserResponse = rawData => {
//     data = JSON.parse(rawData.body);
//     fillStatusLine(data.status);
//     fillUserInformationTable(data.users);
// }

// const fillUserInformationTable = userData => {
//     const userDataTableContainer = document.getElementById("userDataTableContainer");
//     userDataTableContainer.innerHTML = "<table id='conversation' class='table table-striped'><thead><tr><th>User Information</th></tr></thead><tbody id='userInformation'><tr><td>ID</td><td>Login</td><td>Password</td><td>Is Admin</td></tr></tbody></table>";

//     const userInfoNode = document.getElementById("userInformation");

//     for (var i in userData){
//         id = userData[i].id;
//         login = userData[i].login;
//         password = userData[i].password;
//         admin = userData[i].admin
//         $("#userInformation").append(
//             `<tr><td>${id}</td><td>${login}</td><td>${password}</td><td>${admin}</td></tr>`
//         );
//     }
// };

// const fillStatusLine = line => {
//     const statusLine = document.getElementById("statusLine");
//     statusLine.innerHTML = "<hr><p>"+ line +"</p><hr>";
// };

// const fillAddUser = () => {
//     const addUser = document.getElementById("addUser");
//     addUser.innerHTML = "<hr>"
//         + "<h5>User Addition</h5>"
//         + "<label for='addLogin'>Login</label>"
//         + "<input id='addLogin' type'text' class='form-control'>"
//         + "<label for='addPassword'>Password</label>"
//         + "<input id='addPassword' type'text' class='form-control'>"
//         + "<button id='addUserButton' onclick='addUserAction();' class='btn btn-default' type='submit'>Add</button";
// };

// const fillDelUser = () => {
//     const delUser = document.getElementById("delUser");
//     delUser.innerHTML = "<hr>"
//         + "<h5>User Deleting</h5>"
//         + "<label for='delLogin'>Login</label>"
//         + "<input id='delLogin' type'text' class='form-control'>"
//         + "<button id='delUserButton' onclick='delUserAction();' class='btn btn-default' type='submit'>Del</button";
// };

// const clearStatusLine = () => {
//     const statusLine = document.getElementById("statusLine");
//     statusLine.innerHTML = "";
// };

// const clearUserInformationTable = () => {
//     const userDataTableContainer = document.getElementById("userDataTableContainer");
//     userDataTableContainer.innerHTML = "";
// };

// const clearAddUser = () => {
//     const addUser = document.getElementById("addUser");
//     addUser.innerHTML = "";
// };

// const clearDelUser = () => {
//     const delUser = document.getElementById("delUser");
//     delUser.innerHTML = "";
// };

// --------------- TOP MENU FILLING BEGIN ------------------

const fillEntryTopMenu = () => {
    const top_menu = document.getElementById("top-menu");
    top_menu.innerHTML = "<label for='login'>Login</label>"
                        + "<input id='login', type='text' class='form-control'>"
                        + "<label for='password'>Password</label>"
                        + "<input id='password', type='text' class='form-control'>"
                        + "<button onclick='loginRequest();' class='btn btn-default' type='submit'>Login</button>";
}

const fillErrorTopMenu = (errors) => {
    //<
    console.log('fillErrorTopMenu errors : ' + errors);
    //<
};

const fillAdminTopMenu = (login) => {
    //<
    console.log('fillAdminTopMenu login : ' + login);
    //<

    const top_menu = document.getElementById('top-menu');
    top_menu.innerHTML = "<label class='form-control'>"+ login +"</label>"
                        + "<button onclick='userDataRequest();' class='btn btn-default' type='submit'>User Settings</button>"
                        + "<button onclick='groupDataRequest();' class='btn btn-default' type='submit'>Group Settings</button>"
                        + "<button onclick='logoutRequest();' class='btn btn-default' type='submit'>Logout</button>";
};

const fillUserTopMenu = (login) => {
    //<
    console.log('fillUserTopMenu login : ' + login);
    //<

    const top_menu = document.getElementById('top-menu');
    top_menu.innerHTML = "<label class='form-control'>"+ login +"</label>"
                        + "<button onclick='logoutRequest();' class='btn btn-default' type='submit'>Logout</button>";
};

// --------------- TOP MENU FILLING END --------------------

// --------------- MAIN FIELD FILLING USER DATA BEGIN ------------------

const fillDataUserInformationTable = userData => {

    //<
    console.log("users : " + userData);
    //<

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

const fillErrorsUserInfornationData = errors => {
    //<
    console.log("errors : " + errors);
    //<
};

const clearUserInformationTable = () => {
    const userDataTableContainer = document.getElementById("userDataTableContainer");
    userDataTableContainer.innerHTML = "";
};

// --------------- MAIN FIELD FILLING USER DATA END ------------------