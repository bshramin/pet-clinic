Feature: PetService
  Background: We have some owners and some pets
    Given There is an owner with first name "Amin"
    And There is an owner with first name "SomeoneElse"
    And There is pet type "Parrot"
    And There is a pet called "Fandogh" with owner "Amin"
    And There is a pet called "Pesteh" with owner "Amin"

  Scenario: finding owner
    When Using find owner service to find "Amin"
    Then Founded owner first name is "Amin"
    Then Founded owner first name is not "SomeoneElse"

  Scenario: finding owner 2
    When Using find owner service to find "SomeoneElse"
    Then Founded owner first name is "SomeoneElse"
    Then Founded owner first name is not "Amin"

  Scenario: finding pet
    When Using find pet service to find "Pesteh"
    Then Founded pet name is "Pesteh"
    Then Founded pet name is not "Fandogh"

  Scenario: finding pet 2
    When Using find pet service to find "Fandogh"
    Then Founded pet name is "Fandogh"
    Then Founded pet name is not "Pesteh"
