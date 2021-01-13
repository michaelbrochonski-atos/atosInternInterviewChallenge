Narrative:
As an user
I want to handle heros on the hero store

Scenario: Add a hero to store

Given I am on a hero store with model 'loadTemplate(hero)'
When I set the hero's name to 'Hulk2'
And I set the hero's superpower to 'Limitless Strength2'
And I add the hero to store
Then I should not see any error