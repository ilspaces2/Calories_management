const mealsAjaxUrl = "ui/meals/";
const mealsFilterAjaxUrl = "ui/meals/filter";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealsAjaxUrl,
    filterAjaxUrl: mealsFilterAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
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
            ]
        })
    );
});

function filter() {
    $.get(ctx.filterAjaxUrl, $("#filterForm").serialize(), function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function cleanFilter() {
    updateTable();
    $("#filterForm")[0].reset();
}
