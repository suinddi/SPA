// HTML, CSS, JavaScript, Java, Spring Boot, Spring, DBMS, React(Vue.js, Angular.js)

window.addEventListener("load", function() {
    function attachEvents() {
        function attachLoginEvents() {}

        function attachRegisterEvents() {
            let register = window.document.body.querySelector("#register");
            if (register !== null) {
                register.querySelector("div.buttons-item.cancel").addEventListener("click", function() {
                    register.classList.remove("visible");
                });
                // 회원 추가 버튼 클릭시 데이터 전송 및 정규화 검사
                register.querySelector("div.buttons-item.add").addEventListener("click", function() {

                    let form = register.querySelector("form");

                    if (form.value === "") {
                        alert("빈 칸을 채워주세요.")
                    } else {
                        function callback(response) {
                            if (response === "NORMALIZATION_FAILURE") {
                                alert("올바른 값을 입력해 주세요.");
                            } else if (response === "EMAIL_DUPLICATE") {
                                alert("이미 사용 중인 이메일 입니다.");
                            } else if (response === "NICKNAME_DUPLICATE") {
                                alert("이미 사용 중인 닉네임 입니다.");
                            } else if (response === "FAILURE") {
                                alert("알 수 없는 이유로 회원추가를 못했습니다. 잠시 후 다시 시도해주세요.");
                            } else if (response === "SUCCESS") {
                                alert("회원을 추가하였습니다.");
                            }
                        }

                        function fallback(status) {
                            alert("회원을 추가하는 도중 예상치 못한 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
                        }

                        let formData = new FormData(form);
                        xhr("POST", "/admin/apis/register", callback, fallback, formData);
                    }
                });
            }
        }

        function attachUserListEvents() {
            let userList = window.document.body.querySelector("#user-list");
            if (userList !== null) {
                let addButton = userList.querySelector("div.add.object-button");
                addButton.addEventListener("click", function() {
                     let register = window.document.body.querySelector("#register");
                     if(!register.classList.contains("visible")) {
                        register.classList.add("visible");
                     }
                });

                let deleteButton = userList.querySelector("div.delete.object-button");
                deleteButton.addEventListener("click", function() {
                    let userListTable = userList.querySelector("table.table");
                    let userListTableBody = userListTable.querySelector("tbody");
                    let rows = userListTableBody.querySelectorAll("tr");
                    let selectedCount = 0;
                    let toDelete = "";
                    for (let i =0; i < rows.length; i++) {
                        if(rows[i].classList.contains("selected")) {
                            selectedCount += 1;
                            // 0,3,5
                            // 1,4,6,
                            toDelete += `${rows[i].querySelector("td:first-child").innerText},`;
                        }
                    }
                    if(selectedCount === 0) {
                        alert("삭제할 회원을 선택해주세요.");
                    } else {
                        if (confirm(`정말로 선택한 ${selectedCount} 명의 회원을 삭제할까요?`)) {
                            toDelete = toDelete.substring(0, toDelete.length - 1);

                            function callback(response) {
                                if(response == "SUCCESS") {
                                    alert("선택한 회원을 삭제하였습니다.");
                                } else {
                                    fallback();
                                }
                            }

                            function fallback(status) {
                                alert("삭제 실패");
                            }

                            let formData = new FormData();
                            formData.append("index", toDelete);
                            xhr("POST", "/admin/apis/delete-user", callback, fallback, formData);

                        }

                        // 1,2,3,
                        // length = 6
                        // length - 1 = 5
                        // alert(toDelete);
                    }
                });
                    let modifyButton = userList.querySelector("div.modify.object-button");
                    modifyButton.addEventListener("click", function() {
                        if (UserList.getSelectedUserCount() !== 1) {
                            alert("한 명의 회원만 선택해주세요.");
                        } else {
                            Modify.show(UserList.getSelectedUsers()[0]);  // 배열의 길이가 1
                        }
                    });
            }
        }
        attachLoginEvents();
        attachUserListEvents();
        attachRegisterEvents();

if(typeof(Modify) !== "undefined") {
    Modify.attachEvents();
}

   }

   function loadCounterValues() {
       let counterWrapper = window.document.body.querySelector("#counter-wrapper");
       if (counterWrapper !== null) {
           let total = counterWrapper.querySelector("div.counter.total");
           let register = counterWrapper.querySelector("div.counter.register");
           let deleteValue = counterWrapper.querySelector("div.counter.delete");
           let sum = counterWrapper.querySelector("div.counter.sum");

           function callback(response) {
               let responseArray = response.split(",");
               total.querySelector("div.value").innerText = responseArray[0];
               register.querySelector("div.value").innerText = responseArray[1];
               deleteValue.querySelector("div.value").innerText = responseArray[2];
               sum.querySelector("div.value").innerText = responseArray[3];
           }

           function fallback(status) {
               total.querySelector("div.value").innerText = "#";
               register.querySelector("div.value").innerText = "#";
               deleteValue.querySelector("div.value").innerText = "#";
               sum.querySelector("div.value").innerText = "#";
           }

           xhr("POST", "/admin/apis/counter/get-member-count",callback,fallback);
       }
   }
   attachEvents();

   if (window.document.body.querySelector("#user-list") !== null) {
        getUsers();
   }

   function getUsers(pageNumber) {
       if(typeof(pageNumber) !== "number") {
           pageNumber =1;
       }
       function callback(response) {
           let userList = window.document.body.querySelector("#user-list");
           let userListTable = userList.querySelector("table.table");
           let userListTableBody = userListTable.querySelector("tbody");
           userListTableBody.innerHTML = "";

           let jsonUsers = JSON.parse(response);
           let startPage = parseInt(jsonUsers["startPage"]);
           let endPage = parseInt(jsonUsers["endPage"]);
           let requestPage = parseInt(jsonUsers["requestPage"]);
           let userListPages = userList.querySelector("div.center");
           userListPages.innerHTML = "";
           for(let i = startPage; i <= endPage; i++) {
                let page = window.document.createElement("div");
                page.classList.add("object-page-number-button");
                page.innerText = i;
                if (i == requestPage) {
                    page.classList.add("selected");
                } else {
                    page.addEventListener("click", function() {
                        getUsers(i);
                    });
                }
                userListPages.append(page);
           }


           for (let i=0; i < jsonUsers["users"].length; i++) {
                let userStatus = jsonUsers["users"][i]["status"];
                if (userStatus === "OKY") {
                    userStatus = "정상";
                } else if (userStatus === "DEL") {
                    userStatus = "탈퇴";
                } else if (userStatus === "SUS") {
                    userStatus = "정지";
                } else {
                    userStatus = "오류";
                }

                let tr = window.document.createElement("tr");
                let tdIndex = window.document.createElement("td");
                let tdEmail= window.document.createElement("td");
                let tdName = window.document.createElement("td");
                let tdNickname = window.document.createElement("td");
                let tdContact = window.document.createElement("td");
                let tdAddress = window.document.createElement("td");
                let tdBirth = window.document.createElement("td");
                let tdIsAdmin = window.document.createElement("td");
                let tdStatus = window.document.createElement("td");
                tdIndex.innerText = jsonUsers["users"][i]["index"];
                tdEmail.innerText = jsonUsers["users"][i]["email"];
                tdName.innerText = jsonUsers["users"][i]["name"];
                tdNickname.innerText = jsonUsers["users"][i]["nickname"];
                tdContact.innerText = jsonUsers["users"][i]["contact"];
                tdAddress.innerText = jsonUsers["users"][i]["address"];
                tdBirth.innerText = jsonUsers["users"][i]["birth"];
                tdIsAdmin.innerText = jsonUsers["users"][i]["isAdmin"] === "true" ? "여" : "부";
                tdStatus.innerText = userStatus;
                tr.append(tdIndex);
                tr.append(tdEmail);
                tr.append(tdName);
                tr.append(tdNickname);
                tr.append(tdContact);
                tr.append(tdAddress);
                tr.append(tdBirth);
                tr.append(tdIsAdmin);
                tr.append(tdStatus);

                tr.addEventListener("click", function() {
                    if (tr.classList.contains("selected")) {
                        tr.classList.remove("selected");
                    } else {
                        tr.classList.add("selected");
                    }
                });

                userListTableBody.append(tr);

//                userListTableBody.innerHTML += `
//                            <tr>
//                                <td>${jsonUsers["users"][i]["index"]}</td>
//                                <td>${jsonUsers["users"][i]["email"]}</td>
//                                <td>${jsonUsers["users"][i]["name"]}</td>
//                                <td>${jsonUsers["users"][i]["nickname"]}</td>
//                                <td>${jsonUsers["users"][i]["contact"]}</td>
//                                <td>${jsonUsers["users"][i]["address"]}</td>
//                                <td>${jsonUsers["users"][i]["birth"]}</td>
//                                <td>${jsonUsers["users"][i]["isAdmin"] === "true" ? "여" : "부"}</td>
//                                <td>${userStatus}</td>
//                            </tr>`;

           }
       }
       function fallback(status) {

       }

       let formData = new FormData();
       formData.append("page", pageNumber);
       xhr("POST", "/admin/apis/get-user", callback, fallback, formData);
   }

   loadCounterValues();

});

let login = window.document.body.querySelector("#button-login");
if(login !== null) {
    login.addEventListener("click", function() {
        let inputLoginEmail = window.document.body.querySelector("#input-login-email");
        let inputLoginPassword = window.document.body.querySelector("#input-login-password");
        let emailRegex = new RegExp("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");
        let passwordRegex = new RegExp("^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$");
        // 자바 : [문자열].matches([정규식]) <boolean>
        // JS : [정규식].test([문자열]) <boolean>

        if(!emailRegex.test(inputLoginEmail.value)) {
            alert("올바른 이메일을 입력해주세요.");
            inputLoginEmail.focus();
        } else if (!passwordRegex.test(inputLoginPassword.value)) {
            alert("올바른 비밀번호를 입력해주세요.");
            inputLoginPassword.focus();
        } else {
            function callback(responseText) {
                // INTERNAL_SERVER_ERROR
                // NORMALIZATION_FAILURE
                // FAILURE
                // SUCCESS
                // LIMIT_EXCEEDED
                // IP_BLOCKED

                if (responseText === "IP_BLOCKED") {
                    alert("해당 IP는 접근이 차단된 상태입니다. 잠시 후 시도해 주세요.");
                }
                else if (responseText === "LIMIT_EXCEEDED") {
                    alert("로그인 횟수를 초과하였습니다. 잠시 후 다시 시도해주세요.");
                }
                else if (responseText === "INTERNAL_SERVER_ERROR") {
                    alert("올바른 이메일 혹은 비밀번호를 입력해주세요.");
                } else if (responseText === "SUCCESS") {
                    window.location.reload();
                }
            }
            function fallback() {
                // 주로 500 Internal Server Error
                alert("로그인 도중 예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
            let formData = new FormData();
            formData.append("email",inputLoginEmail.value);
            formData.append("password",inputLoginPassword.value);
            xhr("POST", "/admin/apis/login", callback, fallback, formData)
    }
});
}

function xhr(method, url, callback, fallback, formData) {
    let xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                callback(xhr.responseText)
            } else {
                fallback();
            }
        }
    };
    if (typeof(formData) == "unsigned") {
        xhr.send();
    } else {
        xhr.send(formData);
    }
}