document.getElementById('btn-file-input-settings').addEventListener('click', chooseImage);
document.getElementById('delete-account-btn')
    .addEventListener('click', openDeleteAccountDialog);

function chooseImage(){
    let elem = document.getElementById('file-input-settings');
    elem.click();
    elem.addEventListener('change', function(event) {
        let tgt = event.target;
        let source = tgt.files;

        if(FileReader && source && source.length){
            let reader = new FileReader();
            reader.addEventListener('load', function(){
                document.getElementById('image-changed').src = reader.result;
            });
            reader.readAsDataURL(source[0]);
        }
    });
}

function openDeleteAccountDialog() {
    let action = confirm('Delete account? It will be impossible to undo this action.');
    if (action) {
        let request = new XMLHttpRequest();
        request.open('GET', 'http://localhost:8080/another/logout?fullLogout=1', true);
        request.send();
    }
}



