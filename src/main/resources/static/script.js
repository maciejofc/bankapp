
const contacts = document.getElementsByClassName("contact-data");
for (let i = 0; i < contacts.length; i++) {
    contacts[i].addEventListener("click", function () {

        const name = contacts[i].querySelector(".contact-name").innerHTML;
        const number = contacts[i].querySelector(".contact-account-number").innerHTML;
        document.getElementById("account-number-input").value = number;
        document.getElementById("name-input").value = name;


    })
}



