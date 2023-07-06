
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


function getBooks() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
           // document.getElementById("ajaxContent").innerHTML=(createTableFromJSON(JSON.parse(xhr.responseText)));
            const jsonArray = JSON.parse(xhr.responseText);
            var container = document.getElementById("ajaxContent");
            for (var i = 0; i < jsonArray.length; i++) {
                var element = jsonArray[i];
                container.innerHTML += "ISBN: " + element.isbn + ", Title: " + element.title +", Authors: " + element.authors +", Genre: " + element.genre +", Pages: " + element.pages +", Publication Year: " + element.publicationyear + "<br>";
            }
            //$("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
            localStorage.genre=$('#genre').val();
            localStorage.from=$('#fromParam').val();
            localStorage.to=$('#toParam').val();
            //$("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("Incorect Parameters");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'GetBooks?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function UpdateBookPages() {
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
    var data = $('#updateForm').serialize();
    xhr.open('POST', 'updateBook?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
    getBooks();
}


function deleteBook() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
            localStorage.genre=$('#genre').val();
            localStorage.from=$('#fromParam').val();
            localStorage.to=$('#toParam').val();
            //$("#ajaxContent").html("Successful Login");
        } else if (xhr.status == 403) {
            $("#ajaxContent").html("Wrong ISBN");
        }else if (xhr.status !== 406) {
            $("#ajaxContent").html("Book is unavailable in this library");
        }else if (xhr.status !== 200) {
        $("#ajaxContent").html("Incorect Parameters");
        }
    };
    var data = $('#updateForm').serialize();
    xhr.open('POST', 'deleteBook?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
    getBooks();
}

function Logout(){
    localStorage.genre='';
    localStorage.from='';
    localStorage.to='';
    document.getElementById('genre').value='';
    document.getElementById('fromParam').value='';
    document.getElementById('toParam').value='';
    getBooks();
}

$(function(){
       // alert("ininininin");
        $('#genre').val(localStorage.genre);
        $('#fromParam').val(localStorage.from);
        $('#toParam').val(localStorage.to);
        getBooks();
    
});


