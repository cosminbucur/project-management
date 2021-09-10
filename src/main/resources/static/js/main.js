(function(){
    console.log("Hello World!");
})();

$(document).ready(function(){
    $(".show-toast").click(function(){
        $("#myToast").toast('show');
    });
});

//function showToast(name, message) {
//  $("#myToast").toast('show');
//}

function showToast(toast) {
    console.log('in toast');
    M.toast({html: toast});
}
