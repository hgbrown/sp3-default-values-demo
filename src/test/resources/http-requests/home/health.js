client.test("Request executed successfully", function() {
    client.assert(response.status === 200, "Response status is not 200");
});

client.test("Status should be UP", function() {
    console.log(response);
    client.assert(response.body['status'] === 'UP', "Status should be UP");
});

