Feature: To list/view the objects

  @GetItemById
  Scenario: Ability to return an item
    When the request to return the item by "1" is made
    Then a 200 response code is returned
    And an item having id "1" and name "Google Pixel 6 Pro" is returned

  @GetNotAvailableItem
  Scenario: Verify error scenario of getting not available item
    When the request to return the item by "abcde" is made
    Then a 404 response code is returned
    And error message containing "was not found" is returned

  @ListItems
  Scenario: Ability to list multiple items
    When the request to list multiple items is made
    Then a 200 response code is returned
    And all the list is returned