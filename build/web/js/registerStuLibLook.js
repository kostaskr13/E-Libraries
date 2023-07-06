let password=document.querySelector('#pwd');
let verifyPassword=document.querySelector('#verpwd');
let result=document.querySelector('#val');

let timeout;
let strongPassword = new RegExp('(?=.{8,})(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*?[#?!@$%^&*-]{2})')
let mediumPassword = new RegExp('(?=.{8,})')
let weakPassword   = new RegExp('(?=.{8,})(?=.*[0-9])')
let strengthBadge= document.querySelector('#strdsp');
const eyeIcon= document.querySelector("#eye");

var input =document.getElementById('due').value;    
var pasoLife=0;
var valid;
//Password Valid
function checkPassword(){
    if(password.value== verifyPassword.value){
        valid=1;
        result.innerText ='Valid';
        result.style.color='white';
        result.style.backgroundColor="green";
        result.style.paddingRight='5px';
        result.style.paddingLeft='5px';
    }else{
        valid=0;
        result.innerText ='Invalid Passwords dont match';
        result.style.color='white';
        result.style.backgroundColor="red";
        result.style.paddingRight='5px';
        result.style.paddingLeft='5px';
    }
}
password.addEventListener('keyup',()=>{
    if(verifyPassword.value.length!=0){
        checkPassword();
    }
})
verifyPassword.addEventListener('keyup',checkPassword);

//Password eye show/hide
eye.addEventListener("click", function(){
    this.classList.toggle("fa-eye-slash");
    const type = password.getAttribute("type") === "password" ? "text" : "password";
    password.setAttribute("type", type);
})
eyever.addEventListener("click", function(){
    this.classList.toggle("fa-eye-slash");
    const type = verifyPassword.getAttribute("type") === "password" ? "text" : "password";
    verifyPassword.setAttribute("type", type);
})
var pwdStr;
//Password strength
function StrengthChecker(PasswordParameter){
    if(PasswordParameter.includes('helmepa')||PasswordParameter.includes('uoc')||PasswordParameter.includes('tuc')){
        strengthBadge.style.backgroundColor = 'red';
        strengthBadge.innerText = 'Invalid contains special words';
    }else{
        //search if nums are more than 50%
        if(weakPassword.test(PasswordParameter)&&PasswordParameter.replace(/\D/g, '').length >= PasswordParameter.length/2){ 
            strengthBadge.style.backgroundColor = "orange";
            pwdStr=0;
            strengthBadge.innerText = 'Weak';
        } else if(strongPassword.test(PasswordParameter)) {
            strengthBadge.style.backgroundColor = 'Green';
            pwdStr=1;
            strengthBadge.innerText = 'Strong';
        } else if(mediumPassword.test(PasswordParameter) ) {
            strengthBadge.style.backgroundColor = 'blue';
            pwdStr=1;
            strengthBadge.innerText = 'Medium';
        } else {
            strengthBadge.style.backgroundColor = 'red';
            pwdStr=0;
            strengthBadge.textContent = 'Password Invalid';
        }
    }
}

password.addEventListener("keyup", () => {
    strengthBadge.style.display = 'block';
    clearTimeout(timeout);
    timeout = setTimeout(() => StrengthChecker(password.value), 500);
    if(password.value.length !== 0) {
        strengthBadge.style.display != 'block';
    } else {
        strengthBadge.style.display = 'none';
    }
});
//////Email check
var mail;
email.addEventListener("input", (e) => {
    if( /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(email.value) == true ){
        if(email.value.endsWith('uoc.gr')) {
            email.style.border = "thin solid purple"
            mail='uoc';
        } else if( email.value.endsWith('tuc.gr') ) {
            email.style.border = "thin solid purple"
            mail='tuc';
        } else if(email.value.endsWith('helmepa.gr') ) {
            email.style.border = "thin solid purple"
            mail='helmepa';
        } else{
            email.style.border = "thin solid blue"
        }
    } else {
        email.style.border = "thin solid red"
    }
  })

  //Date Not working
  function setMaxDate(){
    var date =document.getElementById('startDate').value;
    var type=document.getElementById('dropStudent').value;
    const d=new Date(date);
    var year=d.getFullYear();
    if(type=='Undergraduate'){
        pasoLife=6+year;
    }else if(type=='Postgraduate'){
        pasoLife=2+year;
    }else {
        pasoLife=5+year;
    }
  }

  function getMaxDate(){
    var date=document.getElementById('due');
    var input=document.getElementById('startDate');
    const d=new Date(document.getElementById('startDate').value);
    var dd=d.getDay();
    var mm=d.getMonth();
    date.setAttribute('min',document.getElementById('startDate').value);
    date.setAttribute('max',pasoLife+"-"+mm+"-"+dd);
    //alert(pasoLife+"-"+mm+"-"+dd);
  }

 //User Type
  function userT(){
    if(document.getElementById('dropUser').value=='Librarian'){
        //disappear
        document.getElementById('dropStudent').style.display='none';
        document.getElementById('dropStudentt').style.display='none';
        document.getElementById('AcademicID').style.display='none';
        document.getElementById('AcademicIDd').style.display='none';
        document.getElementById('startDate').style.display='none';
        document.getElementById('startL').style.display='none';
        document.getElementById('due').style.display='none';
        document.getElementById('dueL').style.display='none';
        document.getElementById('studentAt').style.display='none';
        document.getElementById('studentAtt').style.display='none';
        document.getElementById('dep').style.display='none';
        document.getElementById('depp').style.display='none';
        //appear
        document.getElementById('lib').style.visibility='visible';
        document.getElementById('libName').style.visibility='visible';
        document.getElementById('linfo').style.visibility='visible';
        document.getElementById('libInfo').style.visibility='visible';
        //change
        document.getElementById('address').innerHTML="Library Address:";
    }else{
        //disappear
        document.getElementById('lib').style.display='initial';
        document.getElementById('libName').style.display='initial';
        document.getElementById('linfo').style.display='initial';
        document.getElementById('libInfo').style.display='initial';
        document.getElementById('lib').style.visibility='hidden';
        document.getElementById('libName').style.visibility='hidden';
        document.getElementById('linfo').style.visibility='hidden';
        document.getElementById('libInfo').style.visibility='hidden';
        //appear
        document.getElementById('dropStudent').style.display='initial';
        document.getElementById('dropStudentt').style.display='initial';
        document.getElementById('AcademicID').style.display='initial';
        document.getElementById('AcademicIDd').style.display='initial';
        document.getElementById('startDate').style.display='initial';
        document.getElementById('startL').style.display='initial';
        document.getElementById('due').style.display='initial';
        document.getElementById('dueL').style.display='initial';
        document.getElementById('studentAt').style.display='initial';
        document.getElementById('studentAtt').style.display='initial';
        document.getElementById('dep').style.display='initial';
        document.getElementById('depp').style.display='initial';
        //change
        document.getElementById('address').style.display='initial';
    }
  }
  var sfrom=0;
  function studentFrom(){
    sfrom=document.getElementById('studentAt').value;
  }

  var term=0;
  function terms(){
    if(document.getElementById('termNC').checked==true){
        term=1;
    }else{
        term=0;
    }
  }

  function check(){
    if(term==1 && valid==1 && pwdStr==1 && mail==sfrom){
        document.getElementById('submit').style.backgroundColor="green";
        document.getElementById('submsg').style.visibility="hidden";
        alert("checks submit is correct");
        getUser();return false;
    }else if (term==1){
        document.getElementById('submsg').style.visibility="hidden";
        alert("acc termns");
        getUser();return false;
    }else{
        if(term==0){
            document.getElementById('submsg').style.visibility="visible"
        }    
        document.getElementById('submit').style.backgroundColor="red";
    }
  }



  