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

        $.ajax({
            url: 'encounter-search',
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

    $('#medication-request-search').click(function (event) {

        $.ajax({
            url: 'medication-request-search',
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
    })

    $('#diagnostic-report-search').click(function (event) {

        $.ajax({
            url: 'diagnostic-report-search',
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