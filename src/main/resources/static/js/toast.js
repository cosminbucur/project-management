(function(){
    'use strict';
    document.getElementById("toast-btn").onclick = function() {
    console.log('activating toasts...');
        var toastElList = [].slice.call(document.querySelectorAll('.toast'))
        var toastList = toastElList.map(function(toastEl) {
            console.log(toastList);
            // creates an array of toasts with default options
            return new bootstrap.Toast(toastEl)
        });
        toastList.forEach(toast => toast.show());

        console.log(toastList);
    };
})();


