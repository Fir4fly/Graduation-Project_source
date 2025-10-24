let userdata = [
    {loginID: "testuser", loginPass: "test"}
];

function login() {
    let loginuser = ""
    let loginID = document.getElementById("loginID").value;
    let loginPass = document.getElementById("loginPass").value;

    let found = false;
    let i = 0;

    while(!found & i < userdata.length){
        loginuser = userdata[i];
        if (loginuser.hasOwnProperty("loginID") && loginuser.hasOwnProperty("loginPass")){
            if (loginuser.loginID === loginID && loginuser.loginPass === loginPass) {
                found = true;
            }
        }
        i++;
    }

    if(found){
        window.location.href = "home.html";
    }else{
        alert("ログインに失敗しました。もう一度入力してください")
    }
}
