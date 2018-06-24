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

    $('#patient-search').click(function (event) {
        var tableName = 'patient';
        var patientSearch = new Object();
        patientSearch.tableName = tableName;
        patientSearch.fields = [];
        patientSearch.rangeFields = [];

        var fieldVal = $('#patient-id').val();
        var fieldCotains = $('#patient-id-contains').is(":checked");
        var fieldOperator = $('#patient-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-identifier-id').val();
        fieldCotains = $('#patient-identifier-id-contains').is(":checked");
        fieldOperator = $('#patient-identifier-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-identifier-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-identifier-id-use').val();
        fieldCotains = $('#patient-identifier-id-use-contains').is(":checked");
        fieldOperator = $('#patient-identifier-id-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-identifier-use';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-identifier-id-value').val();
        fieldCotains = $('#patient-identifier-id-value-contains').is(":checked");
        fieldOperator = $('#patient-identifier-id-value-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-identifier-value';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-identifier-system').val();
        fieldCotains = $('#patient-identifier-system-contains').is(":checked");
        fieldOperator = $('#patient-identifier-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-identifier-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-name-id').val();
        fieldCotains = $('#patient-name-id-contains').is(":checked");
        fieldOperator = $('#patient-name-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-name-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-name-use').val();
        fieldCotains = $('#patient-name-use-contains').is(":checked");
        fieldOperator = $('#patient-name-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-name-use';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-name-family').val();
        fieldCotains = $('#patient-name-family-contains').is(":checked");
        fieldOperator = $('#patient-name-family-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-name-family';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-name-given').val();
        fieldCotains = $('#patient-name-given-contains').is(":checked");
        fieldOperator = $('#patient-name-given-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-name-family';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-active').is(":checked").toString();
        fieldCotains = $('#patient-active-contains').is(":checked");
        fieldOperator = $('#patient-active-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-active';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'bool';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        fieldVal = $('#patient-bday').val();
        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-bday';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'boolean';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            patientSearch.fields.push(field);
        }

        $.ajax({
            url: 'patient-search',
            data: JSON.stringify(patientSearch),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                var template = $('#spark-results-template').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(data);
                $('#patient-results').html(html);
            }
        });
    });
});

function load(type) {
    data = {};
    var template = $('#patient-search-template').html();
    var templateScript = Handlebars.compile(template);
    var html = templateScript(data);
    $('#patientattribute-content').html(html);
}