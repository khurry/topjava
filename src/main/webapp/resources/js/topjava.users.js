var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "admin/users/",
        datatableApi: $("#userDatatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    };

    $("#userDatatable").first().find("input[type=checkbox]").click(function () {
        var checked = $(this).prop("checked");
        var tr = $(this).closest("tr");
        var id = tr.attr("id");
        debugger
        $.ajax({
            type: "POST",
            url: ctx.ajaxUrl + "enable",
            data: {
                id: id,
                enabled: checked
            }
        }).done(function () {
            updateTable();
            successNoty("Changed");
        });
    })
    makeEditable();
});
