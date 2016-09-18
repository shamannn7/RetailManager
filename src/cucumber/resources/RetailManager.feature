Feature: Retail Manager Shop API
  Retail Manager should be able to submit a POST request to a web service to add a shop.
  Scenario:Adding shop
    Given Shop API started
    When Retail Manager adds a shop
    Then the server should handle it and return a success status