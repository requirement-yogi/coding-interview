$(function(){

    var urlPersons = "/persons";
    var urlFolks = "/folks";
    var urlUsers = "/users";
    var urlPeople = "/people";


    function loadUsers(searchQuery) {
        let url = urlPersons;
        if (searchQuery) {
            url += "?q=" + encodeURI(searchQuery);
        }
        $.ajax({
            url: url,
            contentType: "json",
            success: function (users) {

                // Generate the HTML using the `templates.soy` function
                var html = PlaySQL.listOfUsers({
                    users: users
                }).content;

                // Insert this HTML in the element '.list-of-users' in the page
                $(".list-of-users").html(html);
            },
            error: function (jqXHR) {
                AJS.flag({
                    type: "error",
                    title: "Error while retrieving the list of users",
                    body: AJS.escapeHtml(jqXHR.responseText)
                });
            }
        });
    }

    // Retrieve the list of users and display it
    loadUsers();

    // When the user types a name in the input field, perform a search
    $("body").on("keypress", ".search", function(e) {
        let searchQuery = $(this).val();
        loadUsers(searchQuery);
    });

    // When one clicks on the lozenge '.status', execute this function:
    $("body").on("click", ".status", function(e) {
        var $this = $(this);

        // Read the attribute "data-user-id" of the parent <tr>.
        var id = $this.closest("tr").attr("data-user-id");

        // Read the text displayed in the tag: <span class="status aui-lozenge"> the status text </span>
        var status = $this.text();

        // Post it to the server
        $.ajax({
            url: urlFolks + "/" + id,
            type: "POST",
            contentType: "application/json",
            data: status,
            success: function(data) {
                // 'data' is the answer from the server
                // We don't care about the lozenge color.
                $this.text(data.status);
            }
        });

    });
});