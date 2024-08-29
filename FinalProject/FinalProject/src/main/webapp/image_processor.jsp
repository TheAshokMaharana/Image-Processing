<!DOCTYPE html>
<html>
<head>
    <title>Simple Image Encryption/Decryption</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script>
        function updateImagePreview() {
            var fileInput = document.getElementById('file');
            var file = fileInput.files[0];
            var preview = document.getElementById('image-preview');

            if (file) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block'; // Show the image element
                }
                reader.readAsDataURL(file);
            } else {
                preview.src = '';
                preview.style.display = 'none'; // Hide the image element if no file is selected
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1 class="mt-5">Simple Image Encryption/Decryption</h1>
        <form action="image" method="post" enctype="multipart/form-data">
            <input type="hidden" name="operation" value="simple">
            <div class="form-group">
                <label for="file">Select Image:</label>
                <input type="file" class="form-control-file" id="file" name="file" onchange="updateImagePreview()" required>
                <div class="mt-3">
                    <img id="image-preview" style="display:none; max-width: 100%; height: auto;" alt="Image Preview">
                </div>
            </div>
            <div class="form-group">
                <label for="key">Enter Key:</label>
                <input type="text" class="form-control" id="key" name="key" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary" name="encrypt" value="true">Encrypt</button>
                <button type="submit" class="btn btn-secondary" name="encrypt" value="false">Decrypt</button>
            </div>
            <div class="form-group">
                <a href="index.jsp" class="btn btn-secondary">Back to Home</a>
            </div>
        </form>
    </div>
</body>
</html>
