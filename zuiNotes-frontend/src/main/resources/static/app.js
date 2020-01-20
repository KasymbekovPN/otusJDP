
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
var treeDataMessageType = 'TREE_DATA';

var containerIds = {
    top_menu : 'top-menu-container',
    user_data_table : 'user-data-table-container',
    add_user : 'add-user-container',
    del_user : 'del-user-container',
    error : 'error-container'
};

var entryTopMenuItems = [
    {
        type : 'label',
        attrs : [
            {key : 'for', value : 'login'}
        ],
        value : 'Login',
        ended : true
    },
    {
        type : 'input',
        attrs : [
            {key : 'id', value : 'login'},
            {key : 'type', value : 'text'},
            {key : 'class', value : 'form-control'}
        ],
        value : '',
        ended : false
    },
    {
        type : 'label',
        attrs : [
            {key : 'for', value : 'password'}
        ],
        value : 'Password',
        ended : true
    },
    {
        type : 'input',
        attrs : [
            {key : 'id', value : 'password'},
            {key : 'type', value : 'text'},
            {key : 'class', value : 'form-control'}
        ],
        value : '',
        ended : false
    },
    {
        type : 'button',
        attrs : [
            {key : 'onclick', value : 'loginRequest();'},
            {key : 'class', value : 'btn btn-default'},
            {key : 'type', value : 'submit'}
        ],   
        value : 'Login',
        ended : true
    }
];

var adminWorkAreaTopMenuItems = [
    {
        type : 'label',
        attrs : [
            {key:'class', value:'form-control'}
        ],
        value : '',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'userDataRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'User Settings',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'groupDataRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'Group Settings',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'logoutRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'Logout',
        ended : true
    }
];

var userWorkAreaTopMenuItems = [
    {
        type : 'label',
        attrs : [
            {key:'class', value:'form-control'}
        ],
        value : '',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'logoutRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'Logout',
        ended : true
    }
];

var userDataTopMenuItems = [
    {
        type : 'label',
        attrs : [
            {key:'class', value:'form-control'}
        ],
        value : '',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'treeDataRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'Work Area',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'groupDataRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'Group Settings',
        ended : true
    },
    {
        type : 'button',
        attrs : [
            {key:'onclick', value:'logoutRequest();'},
            {key:'class', value:'btn btn-default'},
            {key:'type', value:'submit'}
        ],
        value : 'Logout',
        ended : true
    }
];

var addUserItems = [
    {
        type : 'hr',
        attrs : [],
        ended : false,
    },
    {
        type : "h5",
        attrs : [],
        value : "User Addition",
        ended : true
    },
    {
        type : 'label',
        attrs : [
            {key:'for',value:'add-login'}
        ],
        value : 'Login',
        ended : true
    },
    {
        type : 'input',
        attrs : [
            {key:'id', value:"add-login"},
            {key:'type',value:'text'},
            {key:'class',value:'form-control'}
        ],
        ended : false
    },
    {
        type : 'label',
        attrs : [
            {key:'for',value:'add-password'}
        ],
        value : 'Password',
        ended : true
    },
    {
        type : 'input',
        attrs : [
            {key:'id', value:"add-password"},
            {key:'type',value:'text'},
            {key:'class',value:'form-control'}
        ],
        ended : false
    },
    {
        type : 'button',
        attrs : [
            {key:'id',value:'addUserButton'},
            {key:'onclick',value:'addUserRequest();'},
            {key:'class',value:'btn btn-default'},
            {key:'type',value:'submit'}
        ],
        value: 'Add',
        ended : true
    }
];

var delUserItems = [
    {
        type : 'hr',
        attrs : [],
        ended : false
    },
    {
        type : "h5",
        attrs : [],
        value : 'User Deleting',
        ended : true
    },
    {
        type : 'label',
        attrs : [
            {key:'for',value:'del-login'}
        ],
        value : 'Login',
        ended : true
    },
    {
        type : 'input',
        attrs : [
            {key:'id',value:'del-login'},
            {key:'type',value:'text'},
            {key:'class',value:'form-control'}
        ],
        ended : false
    },
    {
        type : 'button',
        attrs : [
            {key:'id',value:'del-user-button'},
            {key:'onclick',value:'delUserRequest();'},
            {key:'class',value:'btn btn-default'},
            {key:'type',value:'submit'}
        ],
        value : 'Del',
        ended : true
    }
];

function uuidv4() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
      (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}


$(() => {

    uiId = uuidv4();

    $("form").on('submit', event => event.preventDefault());

    connect();
    setElementText(containerIds.top_menu, generateContent(entryTopMenuItems));
});

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, frame => {
        console.log(`Connected: ${frame}`);
        stompClient.subscribe(
            "/" + responseRoot + "/" + loginMessageType + "/" + uiId,
            message => loginResponse(message)
        );
        stompClient.subscribe(
            '/' + responseRoot + '/' + userDataMessageType + '/' + uiId,
            message => userDataResponse(message)
        );
        stompClient.subscribe(
            '/' + responseRoot + '/' + addUserMessageType + '/' + uiId,
            message => addUserResponse(message)
        );
        stompClient.subscribe(
            '/' + responseRoot + '/' + delUserMessageType + '/' + uiId,
            message => delUserResponse(message)
        );
        stompClient.subscribe(
            '/' + responseRoot + '/' + treeDataMessageType + '/' + uiId,
            message => treeDataResponse(message)
        )
    });
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
    console.log('loginResponse : ' + message);
    data = JSON.parse(message.body);
    login = data.login;
    group = data.group;
    errors = data.errors;

    if (Object.keys(errors).length == 0){
        if (group == 'admin'){
            var items = adminWorkAreaTopMenuItems;
            items[0].value = login;
            setElementText(containerIds.top_menu, generateContent(items));
        } else {
            var items = userWorkAreaTopMenuItems;
            items[0].value = login;
            setElementText(containerIds.top_menu, generateContent(items));
        }
    } else {
        clearElementText([containerIds.add_user, containerIds.del_user, containerIds.user_data_table]);
        setElementText(containerIds.error, generateErrorContent(errors));
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

    setElementText(containerIds.top_menu, generateContent(entryTopMenuItems));
    clearElementText([containerIds.user_data_table, containerIds.add_user, containerIds.del_user, containerIds.error]);
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
    console.log("userDataResponse : " + message);

    data = JSON.parse(message.body);
    userData = data.users;
    errors = data.errors;

    if (Object.keys(errors).length == 0){
        var items = userDataTopMenuItems;
        items[0].value = login;
        setElementText(containerIds.top_menu, generateContent(items));
        setElementText(containerIds.user_data_table, generateUserDataTableContent(userData));
        setElementText(containerIds.add_user, generateContent(addUserItems));
        setElementText(containerIds.del_user, generateContent(delUserItems));
        clearElementText([containerIds.error]);
    } else {
        clearElementText([containerIds.add_user, containerIds.del_user, containerIds.user_data_table]);
        setElementText(containerIds.error, generateErrorContent(errors));
    }
}

const addUserRequest = () => {
    console.log('addUserRequest');

    var login = document.getElementById('add-login').value;
    var password = document.getElementById('add-password').value;
    stompClient.send(
        '/' + requestRoot + '/' + addUserMessageType,
        {},
        JSON.stringify(
            {
                'login' : login,
                'password' : password,
                'uiId' : uiId
            }
        )
    );
};

const addUserResponse = (message) => {
    console.log('addUserResponse : ' + message);

    data = JSON.parse(message.body);
    userData = data.users;
    errors = data.errors;

    if (Object.keys(errors).length == 0){
        setElementText(containerIds.user_data_table, generateUserDataTableContent(userData));
        clearElementText([containerIds.error]);
    } else {
        setElementText(containerIds.error, generateErrorContent(errors));
    }
};

const delUserRequest = () => {
    console.log('delUserRequest');

    var login = document.getElementById('del-login').value;
    stompClient.send(
        '/' + requestRoot + '/' + delUserMessageType,
        {},
        JSON.stringify(
            {
                'login' : login,
                'uiId' : uiId
            }
        )
    );
};

const delUserResponse = (message) => {
    console.log('delUserResponse : ' + message);

    data = JSON.parse(message.body);
    userData = data.users;
    errors = data.errors;

    if (Object.keys(errors).length == 0){
        setElementText(containerIds.user_data_table, generateUserDataTableContent(userData));
        clearElementText([containerIds.error]);
    } else {
        setElementText(containerIds.error, generateErrorContent(errors));
    }
};

const treeDataRequest = () => {
    console.log('treeDataRequest');

    stompClient.send(
        '/' + requestRoot + '/' + treeDataMessageType,
        {},
        JSON.stringify(
            {
                'uiId' : uiId
            }
        )
    );
};

const treeDataResponse = (message) => {
    console.log('treeDataResponse : ' + message);

    var items = adminWorkAreaTopMenuItems;
    items[0].value = login;
    setElementText(containerIds.top_menu, generateContent(items));
    clearElementText([containerIds.add_user, containerIds.del_user, containerIds.error, containerIds.user_data_table]);
};

const setElementText = (id, text) => {
    document.getElementById(id).innerHTML = text;
};

const clearElementText = (ids) => {
    for(i = 0; i < ids.length; i++){
        document.getElementById(ids[i]).innerHTML = "";
    }
};

const generateContent = (items) => {
    var itemNumber = items.length;
    var content = "";
    for(i = 0; i < itemNumber; i++){
        var line = "<" + items[i].type;
        for(j = 0; j < items[i].attrs.length; j++){
            line += " " + items[i].attrs[j].key + "='" + items[i].attrs[j].value + "'"
        }
        line += ">";

        if (items[i].ended){
            line += items[i].value + "</" + items[i].type + ">";
        }
        content += line;
    }

    return content;
};

const generateUserDataTableContent = (userData) => {

    var content = "<table id='conversation' class='table table-striped'><thead><tr><th>User Information</th></tr></thead><tbody><tr><td>ID</td><td>Login</td><td>Password</td><td>Is Admin</td></tr>";
    for(var i in userData){
        content += '<tr><td>' + userData[i].id + '</td><td>' + userData[i].login + "</td><td>" + userData[i].password + "</td><td>" + userData[i].admin + "</td></tr>";
    }
    content + "</tbody></table>";

    return content;
};

const generateErrorContent = (errors) => {
    var content = '';
    for(var i in errors){
        content += '<p>code : ' + errors[i].code + "; common : " + errors[i].common + "; entity : " + errors[i].entity + "; data : " + errors[i].data + "</p>"
    }
    return content;
};
