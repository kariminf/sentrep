# SentRep

[![Project](https://img.shields.io/badge/Project-SentRep-4B0082.svg?style=plastic)](https://github.com/kariminf/SentRep)
[![License](https://img.shields.io/badge/License-Apache_2-4B0082.svg?style=plastic)](http://www.apache.org/licenses/LICENSE-2.0)
[![Travis](https://img.shields.io/travis/kariminf/SentRep.svg?style=plastic)](https://travis-ci.org/kariminf/SentRep)
[![codecov](https://img.shields.io/codecov/c/github/kariminf/SentRep.svg?style=plastic)](https://codecov.io/gh/kariminf/SentRep)
[![jitpack](https://jitpack.io/v/kariminf/SentRep.svg)](https://jitpack.io/#kariminf/SentRep)

A simple description of this project is: sentences (text) morphological and lexical annotation.
A more focus is on morphology such as verbs tense, modality, etc.

The project aims to afford:
* A universal mechanism to represent different morphological aspects of natural languages, and some lexical ones too.
* API's to transform from a language (formal or natural) to the universal representation and vis-versa.
* Parsers for formal languages designed for morphological and lexical representation.
These parsers use the universal morphology proposed in this project.
* Generators for the same formal languages.

## Universal types

Some mechanisms to reppresent:
* Comparison,
* Determiners,
* Pronouns,
* Verb modality
* verb tense
* Relations between clauses and phrases: relative clause (the man who walk), Adpositional phrases (prepositions) and adverbials (when I woke up).

## Supported representations

As for now the only focus of this project is to improve STON parser.
* STON (Sentence object notation)


## Using the project

### Gradle
To use the library, you can add this code to your "build.gradle" (replace 1.0.0 with any version you like from the release section)

```
repositories {
     jcenter()
     maven { url "https://jitpack.io" }
}
dependencies {
      compile 'com.github.kariminf:SentRep:1.0.0'
}
```

## License

Copyright (C) 2016-2017 Abdelkrime Aries

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
