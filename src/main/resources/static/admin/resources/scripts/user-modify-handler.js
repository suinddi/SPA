class Modify {
    static getElement = function() {
        return window.document.body.querySelector("#modify");
    }

    static show = function(tr) {
        let modify = Modify.getElement();
        let form = modify.querySelector("form");
        let tds = tr.querySelectorAll("td");
        form.elements["index"].value = tds[0].innerText;
        form.elements["email"].value = tds[1].innerText;
        form.elements["name"].value = tds[2].innerText;
        form.elements["nickname"].value = tds[3].innerText;
        form.elements["contact"].value = tds[4].innerText;
        form.elements["address"].value = tds[5].innerText;
        form.elements["birth"].value = tds[6].innerText;
        form.elements["is_admin"].checked = (tds[7].innerText === "여");

        let status;
        if (tds[8].innerText === "정지") {
            status = "SUS";
        } else if (tds[8].innerText === "탈퇴") {
            status = "DEL";
        } else {
            status = "OKY";
        }
        form.elements["status"].value = status;

        if (!modify.classList.contains("visible")) {
            modify.classList.add("visible");
        }
    }

    static hide = function() {
        Modify.getElement().classList.remove("visible");
    }

    static attachEvents = function() {
        let modify = Modify.getElement();
        modify.querySelector("div.buttons-item.cancel").addEventListener("click", function() {
            Modify.hide();
        });
        modify.querySelector("div.buttons-item.modify").addEventListener("click", function() {
            // TODO: 정규화
            let form = modify.querySelector("form");
            let formData = new FormData(form);

            xhr("POST", "/admin/apis/modify-user", callback, fallback, formData);
        });
    }
}