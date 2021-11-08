function showRecipeDescription(description) {
    let parts = description.split('\s');
    let output = $('#description');
    for (let i = 0; i < parts.length; i++) {
        let html = '<p>' + parts[i] + '</p>';
        output.append(html);
    }
}

function showTimeOfCooking(minutes) {
    return parseInt(minutes/60) + 'h ' + minutes%60 + 'm';
}