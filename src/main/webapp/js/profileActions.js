document.getElementById('add-new-recipe-btn')
    .addEventListener('click', function () {
        window.location.replace('http://localhost:8080/another/recipe_editing')
    });

document.getElementById('follow-btn').addEventListener("click", function () {
    setUserConnection(`${user.id}`);
});

function setUserConnection(id) {
    let request = new XMLHttpRequest();
    request.open("POST", 'http://localhost:8080/another/profile?followId=' + id, true);
    request.send();
}

