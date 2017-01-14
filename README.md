# SiteswapFactory

Siteswap Factory is intended to be a java module that anyone can import into their project and gain easy access to siteswap creation & validation.

Goals:
* Support multiple types of siteswap (e.g. Four Handed Siteswaps (FHS), Multiplex Siteswaps, Takeout Patterns).
* Provide an easy interface to create these patterns with automatic validation.
* Easy access to pattern properties (e.g. prime, grounded, number of jugglers, throws), and convert between representations (i.e. FHS and Prechac).
* Allow for generation of siteswaps.
* Keep it as generic as possible/reasonable so users can implement interfaces and use them (e.g. create their own custom sorter) (but also provide sensible defaults).

## Usage/Import

Use https://jitpack.io to import the project.

## Contribute

If you would like to contribute please clone the repository and either pick something off of the TODO list or find something you want to add and make a pull request. If you have any questions please send me an email.

### TODO

* Low Hanging Fruit:
  * Add more unit tests all over the place (Throws, Sorters).
  * Add i18n support (see hefflish of FourHandedSiteswapThro).
  * Add automatic text descriptions (with i18n support, and perhaps in a similar fashion to sorters)
  * Add difficulty estimators (in a similar fashion to sorters).
  * Improve documentation.
  * Add logging?
  * Add categorization, e.g. Four
* More effort:
  * Find a good way to deal with transitions between patterns, and entries into excited patterns.
  * Generation of joepass files.
  * Generic siteswap generator (I've already started this).
* Long Long term goals:
  * Add ability to generate diagrams (Ladder diagrams, Causal Diagrams), perhaps multiple formats (svg, png, some latex supported drawing).
  * Add ability to generate gifs of patterns.
  * Threaded siteswap generation.

## Version History

### 0.0.1

* Supports TwoHandedSiteswap and FourHandedSiteswap.
* Two sorting strategies: HighestThrowFirst and FourHandedPassingStrategy