var $resultTable = $('#result-table'),
    results,
    template = $('#table-row-template').html(),
    loadTable = function (data) {
        var rendered = Mustache.render(template, data);
        $('#result-table').append(rendered);
    },
    $primaZi = $('#prima-zi');

$(document).ready(function () {
    $primaZi.datetimepicker({
        dayViewHeaderFormat: 'MMMM YYYY',
        format: 'YYYY-MM-DD',
        icons: {
            time: 'fa fa-time',
            date: 'fa fa-calendar',
            up: 'fa fa-chevron-up',
            down: 'fa fa-chevron-down',
            previous: 'fa fa-chevron-left',
            next: 'fa fa-chevron-right',
            today: 'fa fa-screenshot',
            clear: 'fa fa-trash',
            close: 'fa fa-remove'
        }
    });

    Mustache.parse(template);   // optional, speeds up future uses

    $primaZi.on('dp.hide', function (event) {
        $primaZi.data("DateTimePicker").disable();
        $('#metric-table').hide();
        $('#metric-table-loading').show();
        var primaZi = event.date.format('YYYY-MM-DD');
        $('#day-1-display').text(primaZi);
        $('#day-2-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));
        $('#day-3-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));
        $('#day-4-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));
        $('#day-5-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));
        $('#day-6-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));
        $('#day-7-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));
        $('#day-8-display').text(event.date.add(1, 'days').format('YYYY-MM-DD'));

        $('#result-table').empty();
        $.getJSON('/count/' + primaZi)
            .done(function (response) {
                $primaZi.data("DateTimePicker").enable();
                $('#metric-table-loading').hide();
                $('#metric-table').show();
                results = response;
                $.each(response, function (index, value) {
                    loadTable(value);
                })
            });
    });
});
