function getUser() {
    let myForm = document.getElementById('loginForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData=JSON.stringify(data);
    var xhr = new XMLHttpRequest();
   alert(jsonData);
    xhr.onload = function () {
        //alert('2')
        if (xhr.readyState === 4 && xhr.status === 200) {
          //  alert('3')
            const responseData = JSON.parse(xhr.responseText);
            //alert('4')
            $('#ajaxContent').html("Successful Registration. Now please log in!<br> Your Data");
            //alert('5')
            $('#ajaxContent').append(createTableFromJSON(responseData));
           // alert('6')
            setChoicesForRegisteredUser();
            //alert('7')
        } else if (xhr.status == 403) {
            //alert('8')
            $('#ajaxContent').html("Unsuccessful Registration <br>"+xhr.responseText);
            $('#ajaxContent').append(createTableFromJSON(responseData));
            setChoicesForRegisteredUser();
            
        } else if (xhr.status !== 200) {
            //alert('8')
            document.getElementById('ajaxContent') .innerHTML ='Request failed. Returned status of ' + xhr.status + "<br>";
        }
     //   alert('9')
    }
    xhr.open('POST', 'Register');
    xhr.setRequestHeader("Content-type", "application/json");
   // alert(jsonData);
    xhr.send(jsonData);
    alert("gets user;");
}
    
    

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