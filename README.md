[![Maven Central](https://img.shields.io/maven-central/v/de.team-holycow/acc-result-parser.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.team-holycow%22%20AND%20a:%acc-result-parser%22)
[![Licence](https://img.shields.io/github/license/team-holycow/acc-result-parser)](./LICENSE)

# ACC Result Parser

This lib analyzes the json result logs of assetto corsa competizione and parses the data into a more usable format.

This includes the following data:

- Server Meta Data
- Leaderboard
- Driver Information
  - Car
  - Lap time (real / best / avg / optimal)
  - Slow laps
  - Invalid laps
  - PitStops with possible damage (the damage is approximately)
  - Consistency

Open Points:

- Tests
- Penalties
- Better Leaderboard implementation
  - Leaderboard by round and sector
- Better return Class/Interface
- Interface with json input and/or file
- Better description in the README.md how to use this lib

## Usage

Maven

 ```xml

<dependency>
  <groupId>de.team-holycow</groupId>
  <artifactId>acc-result-parser</artifactId>
  <version>0.1</version>
</dependency>
 ```

Gradle

 ```
 implementation 'de.team-holycow:acc-result-parser:0.1'
 ```