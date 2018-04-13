$(function(){

    $.ajax({
        url: "/users",
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
});