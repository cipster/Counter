var $resultTable = $('#result-table'),
    results,
    template = $('#table-row-template').html(),
    loadTable = function (data) {
        var rendered = Mustache.render(template, data);
        $('#result-table').append(rendered);
    };

$(document).ready(function () {
    Mustache.parse(template);   // optional, speeds up future uses

    $.getJSON('/count')
        .done(function (response) {
            console.log(response);
            results = response;
            $.each(response, function (index, value) {
                loadTable(value);
            })
        })
});
