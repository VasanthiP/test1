Feature: Promotions

  @Smoke
  Scenario: To verify the promotion details
    Given I perform "GET" operation for "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false"
    Then I verify Response http response should be "200"
    And I verify Response HEADER "Content-Type" should be "application/json"
    Then I should see the "Name" field value as "Carbon credits"
    And I should see the "CanRelist" field value as "true"
    And I should see "Promotions" with the key "Name,Description" with values "Gallery,2xlarger image"
