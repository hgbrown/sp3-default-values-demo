client.test("Request executed successfully", function() {
    client.assert(response.status === 200, "Response status is not 200");
});

client.test("Should get expected API name", function() {
    console.log(response);
    client.assert(response.body['name'] === 'sp3-default-values-demo', "Should have API name: sp3-default-values-demo");
});

client.test("Should have a version", function() {
    console.log(response);
    client.assert(response.body['version'] != null, "Should have a version");
});

client.test("Should have a time", function() {
    console.log(response);
    client.assert(response.body['time'] != null, "Should have a time");
});

client.test("Should have profiles", function() {
    console.log(response);
    client.assert(response.body['profiles'] != null, "Should have profiles");
});
