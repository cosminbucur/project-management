(function(){
    console.log("Application started...");
})();

// activate toasts
document.getElementById("toast-btn").onclick = function() {
    var toastElList = [].slice.call(document.querySelectorAll('.toast'))
    var toastList = toastElList.map(function(toastEl) {
        // creates an array of toasts with default options
        return new bootstrap.Toast(toastEl)
    });
    toastList.forEach(toast => toast.show());

    console.log(toastList);
};
