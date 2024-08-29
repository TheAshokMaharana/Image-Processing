<!DOCTYPE html>
<html>
<head>
    <title>Processed Image</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="mt-5">Processed Image</h1>
        <div class="text-center">
            <img src="<%= request.getAttribute("imageURL") %>" alt="Processed Image" class="img-fluid" />
        </div>
        <% String message = (String) request.getAttribute("message"); %>
        <% if (message != null && !message.isEmpty()) { %>
            <div class="text-center mt-3">
                <p><strong>Embedded Message:</strong> <%= message %></p>
            </div>
        <% } %>
        <div class="text-center mt-3">
            <a href="<%= request.getAttribute("imageURL") %>" class="btn btn-primary" download>Download Image</a>
        </div>
        <div class="text-center mt-3">
            <a href="index.jsp" class="btn btn-secondary">Back to Home</a>
        </div>
    </div>
</body>
</html>
