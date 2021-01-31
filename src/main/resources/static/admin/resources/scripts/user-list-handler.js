class UserList {
    static getElement = function() {
        return window.document.body.querySelector("#user-list");
    }

    static getSelectedUserCount = function() {
        return UserList.getSelectedUsers().length;
    }

    static getSelectedUsers = function() {
        let table = UserList.getElement().querySelector("table");
        let getSelectedUsers = table.querySelectorAll("tr.selected");
        return getSelectedUsers;
    }
}