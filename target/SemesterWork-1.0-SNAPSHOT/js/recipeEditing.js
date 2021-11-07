document.getElementById('recipe-delete-btn')
    .addEventListener('click', openDeleteRecipeDialog);

function openDeleteRecipeDialog() {
    let action = confirm('Delete recipe? All changes will be lost.');
    if (action) {
        let request = new XMLHttpRequest();
        request.open('POST', 'http://localhost:8080/another/recipe_editing?delete=1', true);
        request.send();
    }
}

document.getElementById('loaded-img').addEventListener('change', readImage, false);

$(document).on('click', '.image-cancel', function() {
    let no = $(this).data('no');
    $(".col.preview-image.preview-show-"+no).remove();

});

function showImages(paths) {
    let num = 1;
    let output = $("#selected-img");
    for (let i = 0; i < paths.length; i++) {
        let path = paths[i];
        let html = addNextImage(path, num++);
        output.append(html);
    }
}

function readImage() {
    let num = 1;
    if (FileReader) {
        let source = event.target.files;
        let output = $("#selected-img");

        for (let i = 0; i < source.length; i++) {
            let file = source[i];
            let reader = new FileReader();

            reader.addEventListener('load', function (event) {
                let img = event.target;
                let html = addNextImage(img.result, num++);
                output.append(html);
            });
            reader.readAsDataURL(file);
        }
    }
}

function addNextImage(path, num) {
    return '<div class="col preview-image preview-show-' + num + '">' +
        '<div class="image-cancel" data-no="' + num + '">x</div>' +
        '<div class="image-zone"><img id="pro-img-' + num + '" src="' + path + '"></div>' +
        '</div>';
}