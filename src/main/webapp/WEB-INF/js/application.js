function populateQueryResults() {
    var template = $('#spark-results-template').html();
    var templateScript = Handlebars.compile(template);
    var context = {"name": "Ritesh Kumar", "occupation": "developer"};
    var html = templateScript(context);
    $('#results').append(html);
}

function submitForm() {
    $.ajax({
        url: "/upload",
        type: "POST",
        data: new FormData($("#uploadForm")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function () {
            $("#uploadModal").modal('hide');
        },
        error: function () {
            $("#uploadModal").modal('hide');
        }
    });
} // function uploadFile

/**
 * Hides "Please wait" overlay. See function showPleaseWait().
 */
function hidePleaseWait() {
    $("#pleaseWaitDialog").modal("hide");
}

function showPleaseWait() {
    $("#pleaseWaitDialog").modal("show");
}

$(document).ready(function () {

    $('#sparksqlbtn').click(function (event) {
        showPleaseWait();
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
                hidePleaseWait();
            }
        });
    });

    $( "#uploadForm").submit(function( event ) {
        event.preventDefault();
    });


    $(function(){
        load('patient');
    });

    $('#observation-search').click(function (event) {
        var tableName = 'observation';
        var observationSearch = new Object();
        observationSearch.tableName = tableName;
        observationSearch.fields = [];
        observationSearch.rangeFields = [];

        var fieldVal = $('#observation-id').val();
        var fieldCotains = $('#observation-id-contains').is(":checked");
        var fieldOperator = $('#observation-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-identifier-id').val();
        fieldCotains = $('#observation-identifier-id-contains').is(":checked");
        fieldOperator = $('#observation-identifier-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-identifier-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-identifier-id-use').val();
        fieldCotains = $('#observation-identifier-id-use-contains').is(":checked");
        fieldOperator = $('#observation-identifier-id-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-identifier-use';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-identifier-id-value').val();
        fieldCotains = $('#observation-identifier-id-value-contains').is(":checked");
        fieldOperator = $('#observation-identifier-id-value-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-identifier-value';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-identifier-system').val();
        fieldCotains = $('#observation-identifier-system-contains').is(":checked");
        fieldOperator = $('#observation-identifier-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-identifier-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-basedon-reference').val();
        fieldCotains = $('#observation-basedon-reference-contains').is(":checked");
        fieldOperator = $('#observation-basedon-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-basedon-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-status').val();
        fieldCotains = $('#observation-status-contains').is(":checked");
        fieldOperator = $('#observation-status-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-status';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-category-code').val();
        fieldCotains = $('#observation-category-code-contains').is(":checked");
        fieldOperator = $('#observation-category-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-category-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }


        fieldVal = $('#observation-category-system').val();
        fieldCotains = $('#observation-category-system-contains').is(":checked");
        fieldOperator = $('#observation-category-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-category-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-type-code').val();
        fieldCotains = $('#observation-type-code-contains').is(":checked");
        fieldOperator = $('#observation-type-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-type-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }


        fieldVal = $('#observation-type-system').val();
        fieldCotains = $('#observation-type-system-contains').is(":checked");
        fieldOperator = $('#observation-type-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-type-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-subject-reference').val();
        fieldCotains = $('#observation-subject-reference-contains').is(":checked");
        fieldOperator = $('#observation-subject-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-subject-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-context-reference').val();
        fieldCotains = $('#observation-context-reference-contains').is(":checked");
        fieldOperator = $('#observation-context-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-context-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-performer-reference').val();
        fieldCotains = $('#observation-performer-reference-contains').is(":checked");
        fieldOperator = $('#observation-performer-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-performer-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-value-codeable-code').val();
        fieldCotains = $('#observation-value-codeable-code-contains').is(":checked");
        fieldOperator = $('#observation-value-codeable-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-codeable-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-value-codeable-system').val();
        fieldCotains = $('#observation-value-codeable-system-contains').is(":checked");
        fieldOperator = $('#observation-value-codeable-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-codeable-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-value-boolean').is(":checked").toString();
        fieldOperator = $('#observation-value-boolean-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-boolean';
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

        fieldVal = $('#observation-value-quantity').val();
        fieldOperator = $('#observation-value-quantity-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-quantity';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-value-quantity').val();
        fieldOperator = $('#observation-value-quantity-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-quantity';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-value-quantity-range-low').val();
        fieldOperator = $('#observation-value-quantity-range-low-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-quantity-range-low';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }


        fieldVal = $('#observation-value-quantity-range-high').val();
        fieldOperator = $('#observation-value-quantity-range-high-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-value-quantity-range-high';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }


        fieldVal = $('#observation-bodysite-code').val();
        fieldCotains = $('#observation-bodysite-code-contains').is(":checked");
        fieldOperator = $('#observation-bodysite-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-bodysite-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-bodysite-system').val();
        fieldCotains = $('#observation-bodysite-contains').is(":checked");
        fieldOperator = $('#observation-bodysite-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-bodysite-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-method-code').val();
        fieldCotains = $('#observation-method-code-contains').is(":checked");
        fieldOperator = $('#observation-method-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-method-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-method-system').val();
        fieldCotains = $('#observation-method-system-contains').is(":checked");
        fieldOperator = $('#observation-method-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-method-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-specimen-reference').val();
        fieldCotains = $('#observation-specimen-reference-contains').is(":checked");
        fieldOperator = $('#observation-specimen-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-specimen-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-device-reference').val();
        fieldCotains = $('#observation-device-reference-contains').is(":checked");
        fieldOperator = $('#observation-device-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-device-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }

        fieldVal = $('#observation-reference-range-low').val();
        fieldOperator = $('#observation-reference-range-low-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-reference-range-low';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }


        fieldVal = $('#observation-reference-range-high').val();
        fieldOperator = $('#observation-reference-range-high-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'observation-reference-range-high';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            observationSearch.fields.push(field);
        }
        showPleaseWait();
        $.ajax({
            url: 'observation-search',
            data: JSON.stringify(observationSearch),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                var template = $('#spark-results-template').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(data);
                $('#observation-results').html(html);
                hidePleaseWait();
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

        fieldVal = $('#patient-deceased').is(":checked").toString();
        fieldOperator = $('#patient-deceased-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-deceased';
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
        fieldOperator = $('#patient-bday-combined').is(":checked");
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

        fieldVal = $('#patient-gender').val();
        fieldOperator = $('#patient-gender-combined').is(":checked");
        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-gender';
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

        fieldVal = $('#patient-telecom-use').val();
        fieldCotains = $('#patient-telecom-use-contains').is(":checked");
        fieldOperator = $('#patient-telecom-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-telecom-use';
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

        fieldVal = $('#patient-telecom-system').val();
        fieldCotains = $('#patient-telecom-system-contains').is(":checked");
        fieldOperator = $('#patient-telecom-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-telecom-system';
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

        fieldVal = $('#patient-telecom-value').val();
        fieldCotains = $('#patient-telecom-value-contains').is(":checked");
        fieldOperator = $('#patient-telecom-value-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-telecom-value';
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

        fieldVal = $('#patient-address-use').val();
        fieldCotains = $('#patient-address-use-contains').is(":checked");
        fieldOperator = $('#patient-address-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-use';
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

        fieldVal = $('#patient-address-line').val();
        fieldCotains = $('#patient-address-line-contains').is(":checked");
        fieldOperator = $('#patient-address-line-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-line';
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

        fieldVal = $('#patient-address-city').val();
        fieldCotains = $('#patient-address-city-contains').is(":checked");
        fieldOperator = $('#patient-address-city-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-city';
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

        fieldVal = $('#patient-address-district').val();
        fieldCotains = $('#patient-address-district-contains').is(":checked");
        fieldOperator = $('#patient-address-district-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-district';
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

        fieldVal = $('#patient-address-state').val();
        fieldCotains = $('#patient-address-state-contains').is(":checked");
        fieldOperator = $('#patient-address-state-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-state';
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

        fieldVal = $('#patient-address-postalcode').val();
        fieldCotains = $('#patient-address-postalcode-contains').is(":checked");
        fieldOperator = $('#patient-address-postalcode-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-postalcode';
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

        fieldVal = $('#patient-address-country').val();
        fieldCotains = $('#patient-address-country-contains').is(":checked");
        fieldOperator = $('#patient-address-country-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-address-country';
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

        fieldVal = $('#patient-martialstatus-code').val();
        fieldOperator = $('#patient-martialstatus-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-martialstatus-code';
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

        fieldVal = $('#patient-martialstatus-system').val();
        fieldCotains = $('#patient-martialstatus-system-contains').is(":checked");
        fieldOperator = $('#patient-martialstatus-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-martialstatus-system';
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

        fieldVal = $('#patient-generalpractitioner-reference').val();
        fieldCotains = $('#patient-generalpractitioner-reference-contains').is(":checked");
        fieldOperator = $('#patient-generalpractitioner-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'patient-generalpractitioner-reference';
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
        showPleaseWait();
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
                hidePleaseWait();
            }
        });
    });

    $('#encounter-search').click(function (event) {

        var tableName = 'observation';
        var encounterSearch = new Object();
        encounterSearch.tableName = tableName;
        encounterSearch.fields = [];
        encounterSearch.rangeFields = [];

        var fieldVal = $('#encounter-id').val();
        var fieldCotains = $('#encounter-id-contains').is(":checked");
        var fieldOperator = $('#encounter-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-identifier-id').val();
        fieldCotains = $('#encounter-identifier-id-contains').is(":checked");
        fieldOperator = $('#encounter-identifier-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-identifier-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-identifier-id-use').val();
        fieldCotains = $('#encounter-identifier-id-use-contains').is(":checked");
        fieldOperator = $('#encounter-identifier-id-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-identifier-use';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-identifier-id-value').val();
        fieldCotains = $('#encounter-identifier-id-value-contains').is(":checked");
        fieldOperator = $('#encounter-identifier-id-value-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-identifier-value';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-identifier-system').val();
        fieldCotains = $('#encounter-identifier-system-contains').is(":checked");
        fieldOperator = $('#encounter-identifier-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-identifier-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-status').val();
        fieldCotains = $('#encounter-status-contains').is(":checked");
        fieldOperator = $('#encounter-status-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-status';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-class-code').val();
        fieldCotains = $('#encounter-class-code-contains').is(":checked");
        fieldOperator = $('#encounter-class-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-class-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-class-system').val();
        fieldCotains = $('#encounter-class-system-contains').is(":checked");
        fieldOperator = $('#encounter-class-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-class-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-type-code').val();
        fieldCotains = $('#encounter-type-code-contains').is(":checked");
        fieldOperator = $('#encounter-type-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-type-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-type-system').val();
        fieldCotains = $('#encounter-type-system-contains').is(":checked");
        fieldOperator = $('#encounter-type-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-type-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-priority-code').val();
        fieldCotains = $('#encounter-priority-code-contains').is(":checked");
        fieldOperator = $('#encounter-priority-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-priority-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-priority-system').val();
        fieldCotains = $('#encounter-priority-system-contains').is(":checked");
        fieldOperator = $('#encounter-priority-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-priority-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-subject-reference').val();
        fieldCotains = $('#encounter-subject-reference-contains').is(":checked");
        fieldOperator = $('#encounter-subject-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-subject-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }


        fieldVal = $('#encounter-appointment-reference').val();
        fieldCotains = $('#encounter-appointment-reference-contains').is(":checked");
        fieldOperator = $('#encounter-appointment-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-appointment-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-length').val();
        fieldOperator = $('#encounter-length-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-length';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'int';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-reason-code').val();
        fieldCotains = $('#encounter-reason-code-contains').is(":checked");
        fieldOperator = $('#encounter-reason-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-reason-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-reason-system').val();
        fieldCotains = $('#encounter-reason-system-contains').is(":checked");
        fieldOperator = $('#encounter-reason-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-reason-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-diagnosis-condition-reference').val();
        fieldCotains = $('#encounter-diagnosis-condition-reference-contains').is(":checked");
        fieldOperator = $('#encounter-diagnosis-condition-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-diagnosis-condition-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-diagnosis-condition-role-code').val();
        fieldCotains = $('#encounter-diagnosis-condition-role-code-contains').is(":checked");
        fieldOperator = $('#encounter-diagnosis-condition-role-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-diagnosis-condition-role-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-diagnosis-condition-role-system').val();
        fieldCotains = $('#encounter-diagnosis-condition-role-system-contains').is(":checked");
        fieldOperator = $('#encounter-diagnosis-condition-role-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-diagnosis-condition-role-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        fieldVal = $('#encounter-location-reference').val();
        fieldCotains = $('#encounter-location-reference-contains').is(":checked");
        fieldOperator = $('#encounter-location-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-location-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }


        fieldVal = $('#encounter-serviceprovider-reference').val();
        fieldCotains = $('#encounter-serviceprovider-reference-contains').is(":checked");
        fieldOperator = $('#encounter-serviceprovider-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'encounter-serviceprovider-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            encounterSearch.fields.push(field);
        }

        showPleaseWait();
        $.ajax({
            url: 'encounter-search',
            data: JSON.stringify(encounterSearch),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                var template = $('#spark-results-template').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(data);
                $('#encounter-results').html(html);
                hidePleaseWait();
            }
        });
    });

    $('#medication-request-search').click(function (event) {

        var tableName = 'medicationrequest';
        var medicationRequest = new Object();
        medicationRequest.tableName = tableName;
        medicationRequest.fields = [];
        medicationRequest.rangeFields = [];

        var fieldVal = $('#medication-request-id').val();
        var fieldCotains = $('#medication-request-id-contains').is(":checked");
        var fieldOperator = $('#medication-request-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-identifier-id').val();
        fieldCotains = $('#medication-request-identifier-id-contains').is(":checked");
        fieldOperator = $('#medication-request-identifier-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-identifier-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-identifier-id-use').val();
        fieldCotains = $('#medication-request-identifier-id-use-contains').is(":checked");
        fieldOperator = $('#medication-request-identifier-id-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-identifier-use';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-identifier-id-value').val();
        fieldCotains = $('#medication-request-identifier-id-value-contains').is(":checked");
        fieldOperator = $('#medication-request-identifier-id-value-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-identifier-value';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-identifier-system').val();
        fieldCotains = $('#medication-request-identifier-system-contains').is(":checked");
        fieldOperator = $('#medication-request-identifier-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-identifier-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-basedon-reference').val();
        fieldCotains = $('#medication-request-basedon-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-basedon-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-basedon-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-definition-reference').val();
        fieldCotains = $('#medication-request-definition-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-definition-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-definition-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-status').val();
        fieldCotains = false;
        fieldOperator = $('#medication-request-status-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-status';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-intent').val();
        fieldCotains = false;
        fieldOperator = $('#medication-request-intent-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-intent';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-category-code').val();
        fieldCotains = $('#medication-request-category-code-contains').is(":checked");
        fieldOperator = $('#medication-request-category-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-category-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }


        fieldVal = $('#medication-request-category-system').val();
        fieldCotains = $('#medication-request-category-system-contains').is(":checked");
        fieldOperator = $('#medication-request-category-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-category-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-priority').val();
        fieldCotains = false;
        fieldOperator = $('#medication-request-priority-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-priority';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-medication-codeable-code').val();
        fieldCotains = $('#medication-request-medication-codeable-code-contains').is(":checked");
        fieldOperator = $('#medication-request-medication-codeable-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-medication-codeable-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-medication-codeable-system').val();
        fieldCotains = $('#medication-request-medication-codeable-system-contains').is(":checked");
        fieldOperator = $('#medication-request-medication-codeable-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-medication-codeable-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-medication-reference').val();
        fieldCotains = $('#medication-request-medication-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-medication-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-medication-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-subject-reference').val();
        fieldCotains = $('#medication-request-subject-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-subject-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-subject-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-context-reference').val();
        fieldCotains = $('#medication-request-context-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-context-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-context-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-support-information-reference').val();
        fieldCotains = $('#medication-request-support-information-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-support-information-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-support-information-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-requester-agent-reference').val();
        fieldCotains = $('#medication-request-requester-agent-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-requester-agent-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-requester-agent-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-requester-recorder-reference').val();
        fieldCotains = $('#medication-request-requester-recorder-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-requester-recorder-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-requester-recorder-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-requester-onbehalfof-reference').val();
        fieldCotains = $('#medication-request-requester-onbehalfof-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-requester-onbehalfof-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-requester-onbehalfof-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-requester-reason-code-system').val();
        fieldCotains = $('#medication-request-requester-reason-code-system-contains').is(":checked");
        fieldOperator = $('#medication-request-requester-reason-code-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-requester-reason-code-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-requester-reason-code-code').val();
        fieldCotains = $('#medication-request-requester-reason-code-code-contains').is(":checked");
        fieldOperator = $('#medication-request-requester-reason-code-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-requester-reason-code-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-reason-reference').val();
        fieldCotains = $('#medication-request-reason-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-reason-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-reason-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-dosing-instructions-site-system').val();
        fieldCotains = $('#medication-request-dosing-instructions-site-system-contains').is(":checked");
        fieldOperator = $('#medication-request-dosing-instructions-site-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-dosing-instructions-site-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-dosing-instructions-site-code').val();
        fieldCotains = $('#medication-request-dosing-instructions-site-code-contains').is(":checked");
        fieldOperator = $('#medication-request-dosing-instructions-site-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-dosing-instructions-site-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-dosing-instructions-method-system').val();
        fieldCotains = $('#medication-request-dosing-instructions-method-system-contains').is(":checked");
        fieldOperator = $('#medication-request-dosing-instructions-method-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-dosing-instructions-method-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-dosing-instructions-method-code').val();
        fieldCotains = $('#medication-request-dosing-instructions-method-code-contains').is(":checked");
        fieldOperator = $('#medication-request-dosing-instructions-method-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-dosing-instructions-method-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-dosing-instructions-route-system').val();
        fieldCotains = $('#medication-request-dosing-instructions-route-system-contains').is(":checked");
        fieldOperator = $('#medication-request-dosing-instructions-route-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-dosing-instructions-route-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-dosing-instructions-route-code').val();
        fieldCotains = $('#medication-request-dosing-instructions-route-code-contains').is(":checked");
        fieldOperator = $('#medication-request-dosing-instructions-route-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-dosing-instructions-route-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-substitution-system').val();
        fieldCotains = $('#medication-request-substitution-system-contains').is(":checked");
        fieldOperator = $('#medication-request-substitution-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-substitution-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-substitution-code').val();
        fieldCotains = $('#medication-request-substitution-code-contains').is(":checked");
        fieldOperator = $('#medication-request-substitution-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-substitution-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#medication-request-prior-prescription-reference').val();
        fieldCotains = $('#medication-request-prior-prescription-reference-contains').is(":checked");
        fieldOperator = $('#medication-request-prior-prescription-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'medication-request-prior-prescription-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        showPleaseWait();
        $.ajax({
            url: 'medication-request-search',
            data: JSON.stringify(medicationRequest),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                var template = $('#spark-results-template').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(data);
                $('#medication-request-results').html(html);
                hidePleaseWait();
            }
        });
    })

    $('#diagnostic-report-search').click(function (event) {
        var tableName = 'diagnosticreport';
        var medicationRequest = new Object();
        medicationRequest.tableName = tableName;
        medicationRequest.fields = [];
        medicationRequest.rangeFields = [];

        var fieldVal = $('#diagnostic-report-id').val();
        var fieldCotains = $('#diagnostic-report-id-contains').is(":checked");
        var fieldOperator = $('#diagnostic-report-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-identifier-id').val();
        fieldCotains = $('#diagnostic-report-identifier-id-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-identifier-id-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-identifier-id';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-identifier-id-use').val();
        fieldCotains = $('#diagnostic-report-identifier-id-use-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-identifier-id-use-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-identifier-use';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-identifier-id-value').val();
        fieldCotains = $('#diagnostic-report-identifier-id-value-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-identifier-id-value-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-identifier-value';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-identifier-system').val();
        fieldCotains = $('#diagnostic-report-identifier-system-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-identifier-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-identifier-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.operator = 'AND';
            field.type = 'string';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-basedon-reference').val();
        fieldCotains = $('#diagnostic-report-basedon-reference-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-basedon-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-basedon-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-status').val();
        fieldCotains = false;
        fieldOperator = $('#diagnostic-report-status-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-status';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-category-code').val();
        fieldCotains = $('#diagnostic-report-category-code-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-category-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-category-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }


        fieldVal = $('#diagnostic-report-category-system').val();
        fieldCotains = $('#diagnostic-report-category-system-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-category-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-category-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-code-code').val();
        fieldCotains = $('#diagnostic-report-code-code-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-code-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-code-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }


        fieldVal = $('#diagnostic-report-code-system').val();
        fieldCotains = $('#diagnostic-report-code-system-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-code-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-code-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-subject-reference').val();
        fieldCotains = $('#diagnostic-report-subject-reference-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-subject-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-subject-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-context-reference').val();
        fieldCotains = $('#diagnostic-report-context-reference-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-context-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-context-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-performer-role-code').val();
        fieldCotains = $('#diagnostic-report-performer-role-code-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-performer-role-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-performer-role-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-performer-role-system').val();
        fieldCotains = $('#diagnostic-report-performer-role-system-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-performer-role-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-performer-role-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-specimen-reference').val();
        fieldCotains = $('#diagnostic-report-specimen-reference-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-specimen-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-specimen-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-result-reference').val();
        fieldCotains = $('#diagnostic-report-result-reference-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-result-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-result-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-image-study-reference').val();
        fieldCotains = $('#diagnostic-report-image-study-reference-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-image-study-reference-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-image-study-reference';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-conclusion').val();
        fieldCotains = $('#diagnostic-report-conclusion-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-conclusion-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-conclusion';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-coded-diagnosis-code').val();
        fieldCotains = $('#diagnostic-report-coded-diagnosis-code-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-coded-diagnosis-code-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-coded-diagnosis-code';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        fieldVal = $('#diagnostic-report-coded-diagnosis-system').val();
        fieldCotains = $('#diagnostic-report-coded-diagnosis-system-contains').is(":checked");
        fieldOperator = $('#diagnostic-report-coded-diagnosis-system-combined').is(":checked");

        if (fieldVal != null && fieldVal != "") {
            var field = new Object();
            field.column = 'diagnostic-report-coded-diagnosis-system';
            field.table = tableName;
            field.contains = fieldCotains;
            field.type = 'string';
            field.operator = 'AND';
            if (fieldOperator == true) {
                field.operator = 'OR';
            }
            field.value = fieldVal;
            medicationRequest.fields.push(field);
        }

        showPleaseWait();
        $.ajax({
            url: 'diagnostic-report-search',
            data: JSON.stringify(medicationRequest),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                var template = $('#spark-results-template').html();
                var templateScript = Handlebars.compile(template);
                var html = templateScript(data);
                $('#diagnostic-report-results').html(html);
                hidePleaseWait();
            }
        });
    });
});

function load(type) {
    var data = {};
    if (type == 'patient') {
        var template = $('#patient-search-template').html();
        var templateScript = Handlebars.compile(template);
        var html = templateScript(data);
        $('#patientattribute-content').html(html);
        $('#patient-bday').datepicker();
    } else if (type == 'observation') {
        var template = $('#observation-search-template').html();
        var templateScript = Handlebars.compile(template);
        var html = templateScript(data);
        $('#observation-content').html(html);
    } else if (type == 'encounter') {
        var template = $('#encounter-search-template').html();
        var templateScript = Handlebars.compile(template);
        var html = templateScript(data);
        $('#encounter-content').html(html);
    } else if (type == 'medication-request') {
        var template = $('#medication-request-search-template').html();
        var templateScript = Handlebars.compile(template);
        var html = templateScript(data);
        $('#medication-request-content').html(html);
    } else if (type == 'diagnostic-report') {
        var template = $('#diagnostic-report-search-template').html();
        var templateScript = Handlebars.compile(template);
        var html = templateScript(data);
        $('#diagnostic-report-content').html(html);
    }
}