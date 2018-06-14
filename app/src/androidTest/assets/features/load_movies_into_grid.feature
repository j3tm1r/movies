Feature: LoadMoviesIntoGrid
    Load and show the movies in a grid

    Scenario Outline: Movies are loaded and filtered
        Given I have a SingleFragmentActivity
        And I create a MainView
        And The movies have been loaded
        When I type into the searchbar <filter>
        Then I should see <count> movies in the grid

    Examples:
        |filter     | count| visible|
        |action     | 6    | false  |
        |nonono     | 0    | false  |
        |jumanji    | 1    | false  |

    Scenario Outline: No movies are loaded with error
        Given I have a SingleFragmentActivity
        And I feed to the MainView a bad repository
        Then The error is <visible>

    Examples:
        |visible|
        |true   |

    Scenario Outline: No movies are loaded loading is shown
        Given I have a SingleFragmentActivity
        And I create a MainView with a loading repository
        Then The loading message is <visible>

    Examples:
        |visible|
        |true   |