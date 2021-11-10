num = 2;

document.getElementById('loaded-img')
    .addEventListener('change', function (){
        $(".col.preview-image.preview-show").remove();
        readImage();
    });

$(document).on('click', '.delete-ingredient', function () {
    let no = $(this).data('no');
    $(".ingredient-item-" + no).remove();
});

$(document).on('click', '.new-ingredient', function () {
    let output = $('#ingredients-view');
    let html = addNextIngredient("", "", "");
    output.append(html);
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

function addNextIngredient(name, amount, unit) {
    let html =  '<div class="ingredient-item-' + num +' mb-2 d-flex justify-content-start">' +
        '<span class="ingredient-num">' + num + '.' + '</span>' +
        '<input class="form-control ingredient ms-3" type="text" id="ingredient" value="'+name+'" name="ingredient" placeholder="Ingredient" style="width: 250px;">' +
        '<input class="form-control amount ms-3" type="text" id="amount" value="'+amount+'" name="amount"  placeholder="Amount" style="width: 150px;">' +
        '<input class="form-control amount ms-3" type="text" id="unit" value="'+unit+'" name="unit" placeholder="Unit" style="width: 150px;">' +
        '<div class="delete-ingredient" data-no="' + num + '"><i class="fas fa-minus ms-3 mt-2" ></i></div>' +
        '</div>';
    num++;
    return html;
}

function addNextImage(path) {
    return '<div class="col preview-image preview-show">' +
        '<div class="image-zone"><img id="pro-img" src="' + path + '"></div>' +
        '</div>';
}