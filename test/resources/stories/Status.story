Meta:

Scenario: Application is up and running
Given the server is started
When I call get application status
Then the response code should be 200
Then the response text should contain Success

