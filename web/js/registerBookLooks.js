  var checkIsbn=0;
  var checkTitle=0;
  var checkAuthors=0;
  var checkGenre=0;
  var checkPages=0;
  var checkPublicationYear=0;
  var checkURL=0;
  var checkPhoto=0;
  
  
  //======================Check Submit=============================
  function check(){
    var ti=document.getElementById('title').value;
    var au=document.getElementById('authors').value;
    var gn=document.getElementById('genre').value;
    var page=document.getElementById('pages').value;
    var pY=document.getElementById('publicationYear').value;
    if(ti!=0){checkTitle=1}
    if(au!=0){checkAuthors=1}
    if(gn!=0){checkGenre=1}
    if(page>0){checkPages=1}
    if(pY>=1200){checkPublicationYear=1}
    //if(checkIsbn&&checkTitle&&checkAuthors&&checkGenre&&checkPages&&checkPublicationYear&&checkURL&&checkPhoto){
      getBook();return false;
      document.getElementById('overSubmit').innerHTML='';
    //}else{
    //  document.getElementById('overSubmit').innerHTML='Sorry Wrong Field or Fields';
    //}
  }

  let numsOnly = new RegExp('(?=.*[0-9])')
//===========================ISBN===================================
  function isbnFunc(){
    var isbn=document.getElementById('isbn');
    var err=document.getElementById('isbnErr');
    err.style.float="right";
    var error=0;
    err.innerHTML="";
    if(isbn.value.length<10 || isbn.value.length>13){
      isbn.style.backgroundColor='red';
      err.innerHTML="Length";
      error=1;
    }
    if(!numsOnly.test(isbn.value)){
      isbn.style.backgroundColor='red';
      err.innerHTML+=" Numbers";
      error=1;
    }
    if(error==0){
      isbn.style.backgroundColor='white';
      checkIsbn=1;
      err.innerHTML="";
  }
  }
//=============================URL===================================
function urlCheck(){
  var url=document.getElementById('url');
  if(!url.value.startsWith('https')){
    url.style.backgroundColor='red';
  }else{
    url.style.backgroundColor='white';
    checkURL=1;
  }
}
//=============================Photo==================================
function photoCheck(){
  var urlP=document.getElementById('photo');
  if(!urlP.value.startsWith('https')){
    urlP.style.backgroundColor='red';
  }else{
    urlP.style.backgroundColor='white';
    checkPhoto=1;
  }
}
