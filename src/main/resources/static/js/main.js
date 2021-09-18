(function(){
    'use strict';
    console.log("Application started...");
})();

// activate toasts
//document.getElementById("toast-btn").onclick = function() {
//    var toastElList = [].slice.call(document.querySelectorAll('.toast'))
//    var toastList = toastElList.map(function(toastEl) {
//        // creates an array of toasts with default options
//        return new bootstrap.Toast(toastEl)
//    });
//    toastList.forEach(toast => toast.show());
//
//    console.log(toastList);
//};

window.addEventListener('load', function() {
// Fetch all the forms we want to apply custom Bootstrap validation styles to
var forms = document.getElementsByClassName('needs-validation');
// Loop over them and prevent submission
var validation = Array.prototype.filter.call(forms, function(form) {
  form.addEventListener('submit', function(event) {
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }
    form.classList.add('was-validated');
  }, false);
});
}, false);
