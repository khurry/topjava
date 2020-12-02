var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "meals/",
        datatableApi: $("#mealsDatatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    filterForm = $("#filterForm").first();
    makeEditable();
});

function filter() {
    $.get(ctx.ajaxUrl + "filter", filterForm.serialize(), function(data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    }, "json");
}

function clearFilter() {
    filterForm.find("input[name=startDate]").val("");
    filterForm.find("input[name=endDate]").val("");
    filterForm.find("input[name=startTime]").val("");
    filterForm.find("input[name=endTime]").val("");
    updateTable();
}