const contacts = document.getElementsByClassName("contact-data");
for (let i = 0; i < contacts.length; i++) {
    contacts[i].addEventListener("click", function () {

        const name = contacts[i].querySelector(".contact-name").innerHTML;
        const number = contacts[i].querySelector(".contact-account-number").innerHTML;
        document.getElementById("account-number-input").value = number;
        document.getElementById("name-input").value = name;

    })
}

/*POPUPS*/


// const announcementsShortened = document.getElementsByClassName("announcement-shortened");
//
// for (const element of announcementsShortened) {
//     const announcementFullText = element.getElementsByClassName("announcement-full-text")[0].innerHTML;
//
//     const readMoreButton = element.getElementsByClassName("read-more-button")[0].firstElementChild;
//     readMoreButton.addEventListener("click", function () {
//         alert(announcementFullText)
//     });
//
// }
let popups = document.getElementsByClassName("popup");
console.log(popups);



for (const popup of popups) {

    console.log(popup.getElementsByTagName("button")[0]);
    let openPopupButton = popup.getElementsByClassName("read-more-button")[0];
    let closePopupButton = popup.getElementsByClassName("close-btn")[0];
    closePopupButton.addEventListener("click", function () {

        popup.classList.toggle("active");

    });
    openPopupButton.addEventListener("click", function () {

        popup.classList.toggle("active");

    })
}




