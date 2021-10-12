$(document).ready(function() {
    $('select').select2({
        theme: "bootstrap-5",
        ajax: {
            url: 'http://localhost:8081/search',
            dataType: 'json',
            delay: 250,
            processResults: function(response) {
                console.log('response', response);
                return {
                    results: response
                };
            }
        }
    });
});
