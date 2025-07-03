Feature: Manage Equipes
  In order to manage football teams
  As a client
  I want to create and list equipes

  Scenario: Create a new equipe
    Given no equipes exist
    When I create an equipe with name "Nice FC", acronym "NICE", budget 1000000
    Then the equipe creation response status should be 201
    And the returned equipe should have id and name "Nice FC" and acronym "NICE" and budget 1000000

  Scenario: List equipes with pagination
    Given there are at least 2 equipes
    When I list equipes page 0 size 2 sorted by "name"
    Then the response should contain 2 equipe