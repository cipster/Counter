var $counter = $('.counter'),
    writeCountToLocalStorage = function (count) {
        storage.push(count);
    },
    DIRECTION = {
        IN: 'IN',
        OUT: 'OUT'
    },
    GATE = {
        G1: 'G1',
        G2: 'G2',
        G3: 'G3',
        G4: 'G4',
        U1: 'U1',
        U2: 'U2',
        U3: 'U3',
        U4: 'U4',
        U5: 'U5',
        U6: 'U6',
        U7: 'U7',
        U8: 'U8',
        U9: 'U9',
        U10: 'U10',
        U11: 'U11'
    },
    poarta,
    direction,
    storage = [],
    increaseCount = function () {
        var $currentCountLabel = $(document).find('.current-count');
        var currentCount = parseInt($currentCountLabel.text()) + 1;
        if (!poarta) {
            bootbox.alert('Nu ai ales poarta');
            $('#counter').hide();
            return false;
        }
        if (!direction) {
            bootbox.alert('Nu ai ales ce numeri');
            $('#counter').hide();
            return false;
        }
        var data = {
            recordDate: new Date(),
            direction: direction,
            poarta: poarta,
            currentCount: currentCount
        };

        writeCountToLocalStorage(data);
        $currentCountLabel.text(currentCount);
        if (navigator.onLine) {
            var token = $("meta[name='_csrf']").prop("content");
            var header = $("meta[name='_csrf_header']").prop("content");

            $.ajax({
                type: 'POST',
                url: '/count',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(data)
            })
                .done(function (response) {

                })
                .fail(function (error) {

                });
        }
    };

function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('ceas').innerHTML =
        h + ":" + m + ":" + s;
    var t = setTimeout(startTime, 500);
}
function checkTime(i) {
    if (i < 10) {
        i = "0" + i
    }  // add zero in front of numbers < 10
    return i;
}

$(document).ready(function () {
    $(".chosen-select").chosen({
        disable_search_threshold: 16,
        width: "95%",
        placeholder_text_single: "Alege poarta"
    });

    $(document).on('change', '#poarta-select', function (event) {
        poarta = $('#poarta-select').val();
        $('#poarta').text(poarta);
        $('#display-poarta').show();
        $('#direction-select-container').show();
        $('#poarta-select-container').hide();
    });

    $(document).on('change', '#direction-select', function (event) {
        direction = $('#direction-select').val();
        $('#direction-select-container').hide();
        $('#count-container').html(
            '<div style="margin: auto;" class="counter ' + (direction == DIRECTION.IN ? 'round-enter' : 'round-exit') + ' pointer text-center unselectable">' +
            (direction == DIRECTION.IN ? '<p>Au intrat</p>' : '<p>Au ie&#x219;it</p>') +
            '<span class="current-count">0</span>' +
            '<br/>' +
            '<span>persoane</span>' +
            '</div>'
        );
        $('#counter').show();
    });

    $(document).on('click tap', '.counter', function (event) {
        event.preventDefault();
        increaseCount();
    });
});
