Feature: To add the object

  @CreateObject
  Scenario: Verify an item can be created
    Given a "Apple MacBook Pro 16" item is created
    And is a "Intel Core i9" CPU model with hard disk "1 TB"
    And has a price of "1849.99" manufactured in "2019"
    When the request to add the item is made
    Then a 200 response code is returned
    And an item having name "Apple MacBook Pro 16" is created

  @CreateObjectFromFile
  Scenario: Verify an item can be created from input file
    Given item to create is from a file
    When the request to add the item is made
    Then a 200 response code is returned
    And an item having name "Microsoft Notebook" is created

  @createBookingDataTable
  Scenario Outline: Verify an item can be created using cucumber Data Table
    Given item to create is from cucumber data table
      | name   | model   | year   | harddisk   | price   |
      | <name> | <model> | <year> | <harddisk> | <price> |
    When the request to add the item is made
    Then a 200 response code is returned
    And an item having name "<name>" is created

    Examples:
      | name | model    | year | harddisk | price |
      | Dell | Inspiron | 2020 | 2 TB     | 2000  |
      | HP   | Elite    | 2023 | 1 TB     | 1799  |