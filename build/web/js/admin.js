
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

var successfulLogin=false;
function getUser() {

  var xhr = new XMLHttpRequest();
  xhr.onload = function () {
    if(document.getElementById("username").value == "admin" && document.getElementById("password").value == "admin12*"){
        document.getElementById('error').style.display = "none";
        successfulLogin=true;
       
    } else if (xhr.status = 200) {
      document.getElementById('error').style.display = "contents";
      successfulLogin=false;
    }
  };
  var data = $('#loginForm').serialize();
  xhr.open('GET', 'Admin?'+data);
  xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
  xhr.send();
}

function nextWindow(){
  if(successfulLogin)
    window.location.href = "nini.html";
}

function viewDelete(){
  var xhr = new XMLHttpRequest();
  alert(xhr);
  xhr.onreadystatechange = function () {
      alert("onload");
      if (1==1){//xhr.readyState === 4 && xhr.status === 200) {
        alert("1==1");
          //$("#ajaxContentStudents").html(createTableFromJSON(JSON.parse(xhr.responseText)));
          const jsonArray = JSON.parse(xhr.responseText);
          alert(jsonArray.length);
          var container = document.getElementById("ajaxContent");
          alert("get container");
          for (var i = 0; i < jsonArray.length; i++) {
              var element = jsonArray[i];
              container.innerHTML += "ISBN: " + element.isbn + ", Title: " + element.title +", Authors: " + element.authors +", Genre: " + element.genre +", Pages: " + element.pages +", Publication Year: " + element.publicationyear + "<br>";
          }
      } else if (xhr.status !== 200) {
           $("#ajaxContent").html("Database Error");
      }
  };
  var data = $('#loginForm').serialize();
  xhr.open('GET', 'Admin?'+data);
  xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
  xhr.send();
}