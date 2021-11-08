document.getElementById('recipe-delete-btn')
    .addEventListener('click', openDeleteRecipeDialog);

function openDeleteRecipeDialog() {
    let action = confirm('Delete recipe? All changes will be lost.');
    if (action) {
        let request = new XMLHttpRequest();
        request.open('POST', 'http://localhost:8080/another/recipe_editing?delete=1&id=' + form['delete'].value, true);
        request.send();
    }
}