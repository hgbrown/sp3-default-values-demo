client.test("Request executed successfully", function() {
    client.assert(response.status === 200, "Response status is not 200");
});

client.test("Response should contain expected body", function() {
    console.log(response);
    client.assert(response.body === 'Hello, World!', "Response should be: 'Hello, World!'");
});

