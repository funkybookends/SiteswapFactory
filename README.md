# SiteswapFactory

Siteswap Factory is intended to be a java module that anyone can import into their project and gain easy access to siteswap creation & validation.

## Usage/Import

Use https://jitpack.io to import the project.

#### Create

Create siteswaps using `SiteswapFactory`'s static methods.

```java
final TwoHandedSiteswap fiveThreeFour = TwoHandedSiteswapFactory.getTwoHandedSiteswap("534");
final FourHandedSiteswap funkyBookends = SiteswapFactory.getFourHandedSiteswap("77786");
```

Create using the `SiteswapGenerator`, or one of it's convenient helpers.

```java
final int numObjects = 7;
final int maxThro = 10;
final int maxPeriod = 3;
final Stream<FourHandedSiteswap> patterns = FourHandedSiteswapGenerator.all(numObjects, maxThro, maxPeriod)
final SiteswapGenerator<FourHandedSiteswap> periodThreePatterns = FourHandedSiteswapGenerator.allBuilder(numObjects, maxThro, maxPeriod)
            .andResultPredicate(PeriodPredicate.anyOf(maxPeriod))
            .create();
```

#### Customize Creation

For more custom configuration, or extending the siteswaps, create your own `SiteswapFactory` by injecting a list of `SiteswapConstructor`s.
 
 ```java
final SiteswapFactory myCustomSiteswapFactory = new SiteswapFactory<>(myOrderedSiteswapConstructors);
final Siteswap myPrefferedSitewap = myCustomSiteswapFactory.get(myConstructorObject);
```

Further configure by providing a `SiteswapRequestBuilder` object, or pass a new custom one in per request.

#### Extend

To extend, simply implement any of the provided interfaces:
* `Siteswap` - to define your own siteswap types.
  * `State` - to define your own type of state
  * `Thro` - to define your own transitions between states.
* `SiteswapConstructor` - to take an object and create a siteswap from it.
* `StartingStrategy` - to request your siteswaps to be sorted how you prefer.


#### Reuse

* The converters package contains many converters that can be chained to convert from almost any type to any other type.
* Any new `Siteswap` that fully implements the `State` methods should be able to take advantage of the `StateSearcher` implementation of `SiteswapGenerator`.

## Contribute

If you would like to contribute please clone the repository and either pick something off of the TODO list or find something you want to add and make a pull request. If you have any questions please send me an email.

Project Goals:
* Support multiple types of siteswap (e.g. Four Handed Siteswaps (FHS), Multiplex Siteswaps, Takeout Patterns).
* Provide an easy interface to create these patterns with validation.
* Easy access to pattern properties (e.g. prime, grounded, number of jugglers, throws), and convert between representations (i.e. FHS and Prechac).
* Allow for generation of siteswaps.
* Keep it as generic as possible/reasonable so users can implement interfaces and use them (e.g. create their own custom sorter) (but also provide sensible defaults).

### TODO

* Easier:
  * Improve this document.
  * Add more unit tests all over the place (Throws, Sorters, Generators).
  * Add automatic text descriptions (with i18n support, and perhaps in a similar fashion to sorters)
  * Add difficulty estimators (in a similar fashion to sorters).
  * Improve documentation.
  * Add better logging.
  * More predicates for categorization, e.g. 3 Count, PPS
* More effort:
  * Find a good way to deal with transitions between patterns, and entries into excited patterns.
  * Generation of joepass files.
  * Add ability to deserialse json into siteswap.
  * Add proper support for Prechacs in FourHandedSiteswapThrow
* Long Long term goals:
  * Add ability to generate diagrams (Ladder diagrams, Causal Diagrams), perhaps multiple formats (svg, png, some latex supported drawing).
  * Add ability to generate gifs of patterns.
  * Threaded siteswap generation.

## Version History

### 0.0.1

* Supports TwoHandedSiteswap and FourHandedSiteswap.
* Two sorting strategies: HighestThrowFirst and FourHandedPassingStrategy