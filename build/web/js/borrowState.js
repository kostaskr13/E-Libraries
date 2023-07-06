
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


function getRequests() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
        document.getElementById("ajaxContent").innerHTML+=(createTableFromJSON(JSON.parse(xhr.responseText)));
        //$("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
        } else if (xhr.status == 403) {
            $("#ajaxContent").html("Libary doesnt have any requested books");
        }else if (xhr.status !== 200) {
            $("#ajaxContent").html("Incorect Parameters");
   }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'borrowState?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function grantRequests() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
        document.getElementById("ajaxContent").innerHTML+=(createTableFromJSON(JSON.parse(xhr.responseText)));
        
        localStorage.borrowing_id=$('#borrowing_id').val();
        //$("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
        } else if (xhr.status == 403) {
            $("#ajaxContent").html("Libary doesnt have any requested books");
        }else if (xhr.status !== 200) {
            $("#ajaxContent").html("Incorect Parameters");
   }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'borrowState?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

$(function(){
    // alert("ininininin");
     $('#username').val(localStorage.uname);
     $('#password').val(localStorage.pwd);
     $('#borrowing_id').val(localStorage.borrowing_id);
});