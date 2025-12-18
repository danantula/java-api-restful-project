Feature: To delete the object

  @DeleteNonExistingObject
  Scenario: Delete the object that doesn't exist
    When the request to delete the item by "abcd" is made
    Then a 404 response code is returned
    And error message containing "doesn't exist" is returned

  @DeleteReservedObject
  Scenario: Delete the reserved object
    When the request to delete the item by "2" is made
    Then a 405 response code is returned
    And error message containing "reserved id and the data object of it cannot be deleted" is returned

  @DeleteCreatedObject
  Scenario: Verify the deletion of non-reserved object
    Given item to create is from a file
    When the request to add the item is made
    Then a 200 response code is returned
    When the request to delete the item by '' is made
    Then a 200 response code is returned
    And verify the object is deleted