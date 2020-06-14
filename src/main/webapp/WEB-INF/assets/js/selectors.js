$(document).ready(function () {
    var sity = document.getElementById("sity");
    console.log("ready", "go");
    if ($("#country").val() !== "" && $("#country").val() !== "-Страна-" ) {
        sity.hidden = false;
    }
    $("#country").change(function(){
        console.log("val", $(this).val());
        if ($(this).val() !== "-Страна-") {
            sity.hidden = false;
            console.log("hidden", sity.hidden);
            ajax($(this).val())
        }
    });
});

function ajax(val) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/cities",
        data: "name=" + val,
        datatype: "json",
        success: function (data) {
            remove();
            console.log("data", data);
            for (i in data) {
                var option = document.createElement("option");
                option.text = data[i];
                option.value = data[i];
                sity.add(option);
            }
        },
        error: function () {
            console.log("mes", "error")
        }
    })
}

function remove() {
    document.getElementById('sity').innerHTML='';
    var o = document.createElement("option");
    o.text = "-Город-";
    o.value = "-";
    document.getElementById('sity').add(o)
}