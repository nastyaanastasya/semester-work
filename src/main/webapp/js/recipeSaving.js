document.getElementById('save-changes').addEventListener('click', sendData);

function sendData() {
    let ingredients = document.getElementsByName('ingredient');
    let amounts = document.getElementsByName('amount');
    let units = document.getElementsByName('unit');

    let json = '';
    for (let i = 0; i < ingredients.length; i++) {
        json += json + '{"name":' + '"' + ingredients[i].value + '",' +
            '"amount":' + '"' + amounts[i].value + '",' +
            '"unit":' + '"' + units[i].value + '"}';
        if (i !== ingredients.length - 1) {
            json = json + ',';
        }
    }
    json = "[" + json + "]";
    document.getElementsByName('save-changes')[0].value=json;
}