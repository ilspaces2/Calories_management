const mealsAjaxUrl = "user/meals/";
const mealsFilterAjaxUrl = "user/meals/filter";

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
    $.ajax({
        type: "GET",
        url: ctx.filterAjaxUrl,
        data: {
            startDate: "2020-01-30",
            endDate: "2020-01-30",
            startTime: "00:00",
            endTime: "23:23",
        }
    })
    //TODO
        // .done(function (data) {
        // ctx.datatableApi.clear().rows.add(data).draw();
    // });
}
