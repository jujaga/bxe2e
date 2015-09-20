E2Everest [![Build Status](https://travis-ci.org/jujaga/e2everest.svg)](https://travis-ci.org/jujaga/e2everest) [![Coverage Status](https://coveralls.io/repos/jujaga/e2everest/badge.svg)](https://coveralls.io/r/jujaga/e2everest)
=========
A working Proof of Concept E2E Everest Implementation  
Data model emulates an [OSCAR EMR](https://github.com/scoophealth/oscar "OSCAR EMR") database  
Exported content should be E2E-DTC 1.4 compliant

Refer to **MANUAL.md** if you wish to use this framework in your own EMR environment.

Environment
-----------
E2Everest should work on Windows, OSX, and Linux

Dependencies
------------
* Oracle Java 6 or higher
* Maven 3.0.x or higher

Installation
------------
`mvn clean package`

Run
------------
`mvn exec:java`
