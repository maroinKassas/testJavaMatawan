Feature: Manage Joueurs
  In order to manage football players
  As a client
  I want to create and list joueurs

  Background:
    Given an equipe exists with name "Nice FC" and acronym "NICE" and budget 1000000

  Scenario: Create a new joueur
    When I create a joueur with name "John Doe", position "Forward", equipeId stored
    Then the joueur creation response status should be 201
    And the returned joueur should have id and name "John Doe" and position "Forward" and equipeId stored

  Scenario: List joueurs with pagination
    Given there are at least 2 joueurs
    When I list joueurs page 0 size 2 sorted by "name"
    Then the response should contain 2 joueur