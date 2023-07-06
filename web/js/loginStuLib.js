
function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}

let isStudent;

function getUser() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
            var ret=createTableFromJSON(JSON.parse(xhr.responseText));
            //$("#ajaxContent").html(ret);
            //alert(ret);
            localStorage.uname=$('#username').val();
            localStorage.pwd=$('#password').val();
            if(ret.includes("library") ){
                isStudent=0;
                document.getElementById('error').style.display = "none";
                //alert("is lib");
            }else if(ret.includes("student")){
                isStudent=1;
                document.getElementById('error').style.display = "none";
                //alert("is stu");
            }else{
                //alert("not loged in");
                isStudent=2;
            }
            //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
            isStudent=2;
             $("#ajaxContent").html("User not exists or incorrect password");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'GetStudent?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
    //getBooks()
}
function goForward(){
    if(localStorage.uname==$('#username').val() && localStorage.pwd==$('#password').val()){    
        if(isStudent==1 ){
            window.location.href ='Student.html';
        }else if(isStudent==0){
            window.location.href ='Librarian.html';
        }else if(isStudent==2){
            document.getElementById('error').style.display = "contents";
            window.location.href ='index.html';
        }
    }else{ 
        document.getElementById('error').style.display = "contents";
        localStorage.uname=$('#username').val();
        localStorage.pwd=$('#password').val();
        window.location.href ='index.html';
    }
}
function Register(){    
        window.location.href ='registerStuLib.html';
}
function Update(){    
        window.location.href ='viewBooks.html';
}
function BorrowBook(){    
        window.location.href ='borrowBook.html';
}
function ReviewBook(){    
        window.location.href ='reviewBook.html';
}
function UpdateInfo(){    
    window.location.href ='updateStInfo.html';
}

function Logout(){
    localStorage.uname='';
    localStorage.pwd='';
    document.getElementById('username').value='';
    document.getElementById('password').value='';
    getUser();
}

$(function(){
    // alert("ininininin");
     $('#username').val(localStorage.uname);
     $('#password').val(localStorage.pwd);
     getUser();
 
});



function UpdateStudent() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
            localStorage.genre=$('#genre').val();
            localStorage.from=$('#fromParam').val();
            localStorage.to=$('#toParam').val();
            //$("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("Incorect Parameters");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'updateStudent?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
    getUser();
}

function getBooks() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent2").html(createTableFromJSON(JSON.parse(xhr.responseText)));
            localStorage.uname=$('#username').val();
            localStorage.pwd=$('#password').val();
            //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent2").html("User not exists or incorrect password");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'loadBooks?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}
