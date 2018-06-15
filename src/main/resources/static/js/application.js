function populateQueryResults() {
    var template = $('#spark-results-template').html();
    var templateScript = Handlebars.compile(template);
    var context = {"name": "Ritesh Kumar", "occupation": "developer"};
    var html = templateScript(context);
    $('#results').append(html);
}

$(document).ready(function () {
    $('#sparksqlbtn').click(function (event) {
        var sql = $('#sparksql').val();
        var json = {query: sql};
        $.ajax({
            url: 'sql',
            data: JSON.stringify(json),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                var template = $('#spark-results-template').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(data);
                $('#results').html(html);
            }
        });
    });
});
