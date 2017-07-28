# SentRep

[![Project](https://img.shields.io/badge/Project-SentRep-4B0082.svg)](https://github.com/kariminf/sentrep)
[![License](https://img.shields.io/badge/License-Apache_2-4B0082.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Travis](https://img.shields.io/travis/kariminf/sentrep.svg)](https://travis-ci.org/kariminf/sentrep)
[![codecov](https://img.shields.io/codecov/c/github/kariminf/sentrep.svg)](https://codecov.io/gh/kariminf/sentrep)
[![jitpack](https://jitpack.io/v/kariminf/sentrep.svg)](https://jitpack.io/#kariminf/sentrep)

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

You can download the jar from "release" section and link it to your project;
Or you can use https://jitpack.io to manage dependency.
Replace "tag" with the release tag; for example "1.0.1".

### Gradle

Add this to your "build.gradle":
```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.kariminf:sentrep:tag'
}
```

### Maven

```xml
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
</repositories>

<dependency>
	    <groupId>com.github.kariminf</groupId>
	    <artifactId>sentrep</artifactId>
	    <version>tag</version>
</dependency>
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
