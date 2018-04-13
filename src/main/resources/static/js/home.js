$(function(){

    var urlPersons = "/persons";
    var urlFolks = "/folks";
    var urlUsers = "/users";
    var urlPeople = "/people";

    $.ajax({
        url: urlPersons,
        contentType: "json",
        success: function(users) {
            $(".list-of-users").html(PlaySQL.listOfUsers({
                users: users
            }).content);
        },
        error: function(jqXHR){
            AJS.flag({
                type: "error",
                title: "Error while retrieving the list of users",
                body: AJS.escapeHtml(jqXHR.responseText)
            });
        }
    });

    $("body").on("click", ".status", function(e) {
        var $this = $(this);
        var value = $this.text();
        var id = $this.closest("tr").attr("data-user-id");
        var newValue;
        if (value == "ACTIVE") {
            newValue = "INACTIVE";
        } else {
            newValue = "ACTIVE";
        }
        // Post it to the server
        $.ajax({
            url: urlFolks + "/" + id,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                id: id,
                status: newValue
            }),
            success: function(data) {
                // We don't care about the lozenge color.
                $this.text(data.status);
            }
        });

    });
});