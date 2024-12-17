const dropArea = document.getElementById('drop-area');
const fileInput = document.getElementById('fileInput');
const messageDiv = document.getElementById('message');
const fileName = document.getElementById('image_name').value;

dropArea.addEventListener('click', () => {
    fileInput.click();
});

fileInput.addEventListener('change', handleFileSelect);

dropArea.addEventListener('dragover', (event) => {
    event.preventDefault();
    dropArea.classList.add('hover');
});

dropArea.addEventListener('dragleave', () => {
    dropArea.classList.remove('hover');
});

dropArea.addEventListener('drop', (event) => {
    event.preventDefault();
    dropArea.classList.remove('hover');
    const files = event.dataTransfer.files;
    if (files.length > 0) {
        handleFileSelect({ target: { files } });
    }
});

function handleFileSelect(event) {
    const file = event.target.files[0];

    if (!file || file.type !== 'image/jpeg') {
        messageDiv.textContent = "Invalid file type. Only JPEG files are allowed.";
        messageDiv.className = "error-message";
        return;
    }

    if (file.size > 1_000_000) { // 1 MB
        messageDiv.textContent = "File is too large. The size limit is 1 MB.";
        messageDiv.className = "error-message";
        return;
    }


    uploadFile(file, fileName);
}

function uploadFile(file, fileName) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("img_UUID", fileName);

    fetch('http://localhost:8089/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(errorMessage => {
                throw new Error(errorMessage);
            });
        }
        return response.text();
    })
    .then(successMessage => {
        messageDiv.textContent = file.name;
        messageDiv.className = "success-message";
    })
    .catch(error => {
        messageDiv.textContent = "Error: " + error.message;
        messageDiv.className = "error-message";
    });
}