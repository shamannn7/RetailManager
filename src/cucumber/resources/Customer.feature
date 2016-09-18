Feature: Customer Shop API
  Customer should be able to submit a GET request to a web service to find the nearest shop.
  Scenario: Finding the nearest shop
    Given Shop API started
    When Customer requests the nearest shop
    Then the server should handle it and return the nearest shop
