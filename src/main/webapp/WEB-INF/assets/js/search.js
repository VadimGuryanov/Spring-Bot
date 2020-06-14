$(document).ready(function () {
    let page = 0;
    var sity = document.getElementById("sity");
    var country = document.getElementById("country");
    var query = document.getElementById("query");
    var summaries = document.getElementById("summaries");
    console.log("ready", "go");
    var $loading = $('#loading').hide();
    $(document)
        .ajaxStart(function () {
            $loading.show();
        })
        .ajaxStop(function () {
            $loading.hide();
        });
    $("#query").change(function () {
        page = -1;
        console.log("val", $(this).val());
        console.log("val", $("#sity").val());
        console.log("val", $("#country").val());
        if ($("#query").val() === "" || $("#query").val() == null) {
            if ($("#country").val() !== "-" && $("#sity").val() !== "-") {
                console.log("country and sity not query", $("#country").val() + " " + $("#sity").val());
                searchBySityAndCountry($("#country").val(), $("#sity").val())
            } else if ($("#country").val() !== "-") {

                console.log("country not query", $("#country").val());
                searchByCountry($("#country").val())

            }

        } else {
            if ($("#country").val() !== "-" && $("#sity").val() !== "-") {

                console.log("country and sity", $("#country").val() + " " + $("#sity").val());
                searchByQueryAndSityAndCountry($(this).val(), $("#country").val(), $("#sity").val())

            } else if ($("#country").val() !== "-") {

                console.log("country", $("#country").val());
                searchByQueryAndCountry($(this).val(), $("#country").val())

            } else if ($("#sity").val() !== "-") {

                console.log("sity", $("#sity").val());
                searchByQueryAndSity($(this).val(), $("#sity").val())

            } else {
                console.log("only query", $(this).val());
                getNewPageQuery($(this).val())
            }
        }
    });
    $("#btn-search").click(function () {
        page = -1;
        console.log("val", $("#query").val());
        console.log("val", $("#sity").val());
        console.log("val", $("#country").val());
        if ($("#query").val() === "" || $("#query").val() == null) {
            if ($("#country").val() !== "-" && $("#sity").val() !== "-") {
                console.log("country and sity not query", $("#country").val() + " " + $("#sity").val());
                searchBySityAndCountry($("#country").val(), $("#sity").val())
            } else if ($("#country").val() !== "-") {

                console.log("country not query", $("#country").val());
                searchByCountry($("#country").val())

            } else {
                document.getElementById('summaries-html').innerHTML='';
                ajaxGetNewPage(0)
            }
        } else if ($("#country").val() !== "-" && $("#sity").val() !== "-") {

            console.log("country and sity", $("#country").val() + " " + $("#sity").val());
            searchByQueryAndSityAndCountry($("#query").val(), $("#country").val(), $("#sity").val())

        } else if ($("#country").val() !== "-") {

            console.log("country", $("#country").val());
            searchByQueryAndCountry($("#query").val(), $("#country").val())

        } else if ($("#sity").val() !== "-") {

            console.log("sity", $("#sity").val());
            searchByQueryAndSity($("#query").val(), $("#sity").val())

        } else {
            console.log("only query", $("#query").val());
            getNewPageQuery($("#query").val())
        }
    });

    window.addEventListener("scroll", function () {
        if ($("#country").val() === "-" && $("#sity").val() === "-" && $("#query").val() === "") {
            if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
                page++;
                ajaxGetNewPage(page)
            }
        }
    })

});

function ajaxGetNewPage(page) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/getNewPage",
        datatype: "json",
        data: "page=" + page,
        contentType : "application/json",
        success: function (data) {
            if (data.length === 0) {
                $('#loading').hide()
            }
            page++;
            console.log("--------", "----------");
            console.log("data", data);
            console.log("page", page);
            console.log("--------", "----------");
            for (i in data) {
                createSummary(data[i])
            }
        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function getNewPageQuery(query) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/getNewPageQuery",
        datatype: "json",
        // contentType : "application/json",
        data: "query=" + query,
        success: function (data) {
            console.log("--------", "----------");
            console.log("data", data);
            console.log("--------", "----------");
            document.getElementById('summaries-html').innerHTML='';
            for (i in data) {
                createSummary(data[i])
            }
        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function searchByQueryAndCountry(query, country) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/searchByQueryAndCountry",
        // contentType : "application/json",
        datatype: "json",
        data: "query=" + query + "&&country=" + country,
        success: function (data) {
            console.log("--------", "----------");
            console.log("data", data);
            console.log("--------", "----------");
            document.getElementById('summaries-html').innerHTML='';
            for (i in data) {
                createSummary(data[i])
            }
        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function searchByQueryAndSityAndCountry(query, country, sity) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/searchByQueryAndCountry",
        datatype: "json",
        // contentType : "application/json",
        data: "query=" + query + "&&country=" + country + "&&sity=" + sity,
        success: function (data) {
            console.log("--------", "----------");
            console.log("data", data);
            console.log("--------", "----------");
            document.getElementById('summaries-html').innerHTML='';
            for (i in data) {
                createSummary(data[i])
            }
        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function searchBySityAndCountry(country, sity) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/searchBySityAndCountry",
        datatype: "json",
        // contentType : "application/json",
        data: "country=" + country + "&&sity=" + sity,
        success: function (data) {
            console.log("--------", "----------");
            console.log("data", data);
            console.log("--------", "----------");
            document.getElementById('summaries-html').innerHTML='';
            for (i in data) {
                createSummary(data[i])
            }
        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function searchByCountry(country) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/searchByCountry",
        datatype: "json",
        // contentType : "application/json",
        data: "country=" + country,
        success: function (data) {
            console.log("--------", "----------");
            console.log("data", data);
            console.log("--------", "----------");
            document.getElementById('summaries-html').innerHTML='';
            for (i in data) {
                createSummary(data[i])
            }
        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function searchByQueryAndSity(query, sity, summaries) {
    let token = $('input[name="_csrf"]').attr('value');
    $.ajax({
        headers: {'X-CSRF-Token': token},
        type: "POST",
        url: "/searchByQueryAndSity",
        datatype: "json",
        // contentType : "application/json",
        data: "query=" + query + "&&sity=" + sity,
        success: function (data) {
            console.log("--------", "----------");
            console.log("data", data);
            console.log("--------", "----------");
            document.getElementById('summaries-html').innerHTML='';
            for (i in data) {
                createSummary(data[i], summaries)
            }

        },
        error: function () {
            console.log("error", "error")
        }
    });
}

function createSummary(data) {
    let container = document.createElement("div");
    let name = document.createElement("div");
    let categories = document.createElement("div");
    let secinfo = document.createElement("div");
    let secinfo1 = document.createElement("div");
    let secinfo2 = document.createElement("div");
    let content = document.createElement("div");
    let actions = document.createElement("div");
    let span = document.createElement("span");

    container.setAttribute("class", "fir-info container d-flex flex-column justify-content-start summaries");
    name.setAttribute("class", "name");
    categories.setAttribute("class", "categories");
    secinfo.setAttribute("class", "sec-info");
    content.setAttribute("class", "content");
    span.setAttribute("class", "d-flex flex-row");

    if (data != null) {

        let a_title = document.createElement("a");
        a_title.setAttribute("href", window.location.protocol + "/summary/" + data["id"]);
        a_title.textContent = data["title"];
        name.appendChild(a_title);

        for (let i = 0; i < data["summaryCategories"].length; i++) {
            let a_category = document.createElement("a");
            a_category.textContent = "#" + data["summaryCategories"][i]["name"];
            categories.appendChild(a_category);
        }
        console.log("data", data["user"]);
        let profile = document.createElement("a");
        let location = document.createElement("b");
        let username = document.createElement("b");
        profile.setAttribute("href", window.location.protocol + "/profile/" + data["user"]);
        location.textContent = data["country"] + "," + data["sity"];
        // username.textContent = data["user"] + " " + data["user"];
        profile.appendChild(username);
        secinfo1.appendChild(profile);
        secinfo2.appendChild(location);
        secinfo.appendChild(secinfo1);
        secinfo.appendChild(secinfo2);

        let description = document.createElement("p");
        if (data["description"].length > 50) {
            description.textContent = data["description"].slice(0, 50) + "...";
        } else {
            description.textContent = data["description"]
        }
        content.appendChild(description);



        container.appendChild(name);
        container.appendChild(categories);
        container.appendChild(secinfo);
        container.appendChild(content);
        document.getElementById('summaries-html').appendChild(container)

    } else {
        let nan = document.createElement("h5");
        nan.textContent = "Данные не найденно";
        name.appendChild(nan);
        container.appendChild(name);
        document.getElementById('summaries-html').appendChild(container)
    }

}

