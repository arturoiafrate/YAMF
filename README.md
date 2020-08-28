[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![GPL-3 License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<br />
<p align="center">
  <h3 align="center">YAMF</h3>

  <p align="center">
    Yet Another Mapping Framework
    <br />
    <a href="https://github.com/arturoiafrate/YAMF/issues">Report Bug</a>
    ·
    <a href="https://github.com/arturoiafrate/YAMF/issues">Request Feature</a>
  </p>
</p>

## What is YAMF
YAMF - Yet Another Mapping Framework is a framework that allows you to map an object of a specific class to a second object of another class with the usage of the Java Reflection API.
It works like most popular framework (like <a href="http://dozer.sourceforge.net">dozer</a> or <a href="https://mapstruct.org">MapStruct</a>), but it provides a fluent interface to define and build the mapping.
The idea is to provide additional ways to map classes in upcoming releases.

## Getting Started
Download the <a href="https://github.com/arturoiafrate/YAMF/releases/latest">latest release</a> and import in your project workspace.

## Usage
Starting from a "ClassA" object, you can simply map to a "ClassB" object with the following syntax:
```java
ClassA classAObj = new ClassA();//Do other stuff on the object...
ClassB classBObj = new MappingFactory()
    .fromObject(classAObj)
    .toClass(ClassB.class)
    .doConvert();
```
By default, all fields with the same name on source and target are mapped.
If you want to change this behavior and you want to map a field from the source to a specific field on target, you can do it in this way:
```java
ClassA classAObj = new ClassA();//Do other stuff on the object...
ClassB classBObj = new MappingFactory()
    .fromObject(classAObj)
    .toClass(ClassB.class)
    .mapFieldsWithSameName(false)
    .mapAs("integerValue", "itg")
    .mapAs("internalObj.stringValue", "str")
    .doConvert();
```

You can also use a json to load profiles and mapping objects with one of them:
```java
ClassA classAObj = new ClassA();//Do other stuff on the object...
ClassB classBObj = new MappingFactory()
                .loadProfiles(jsonString, ProfilesEncoding.JSON)
                .fromObject(classAObj)
                .toClass(ClassB.class)
                .useProfile("profileToUse")
                .doConvert();
```
The JSON format is like this:
```json
{
    "profiles": [{
        "profileName": "profileA",
        "profile": {
            "mapFieldsWithSameName": false,
            "associations": [{
                    "source": "integer",
                    "target": "itg"
                },
                {
                    "source": "aBoolean",
                    "target": "bln"
                },
                {
                    "source": "string",
                    "target": "str"
                }
            ]
        }
    }, {
        "profileName": "profileB",
        "profile": {
            "mapFieldsWithSameName": true,
            "associations": [{
                    "source": "string",
                    "target": "internal.string"
                }
            ]
        }
  }]
}
```

## Roadmap
This is the list of all the features that will be implemented with the next releases.
* An alternative way to mapping between classes with an annotation form.
* An alternative way to mapping between classes with ~~json~~/xml form.
* Upload next releases to a maven repository.

You can always <a href="https://github.com/arturoiafrate/YAMF/issues">request a feature</a> if you want.

## Dependencies
* <a href="https://junit.org/junit5/">JUnit Framework 5.6.2</a>
* <a href="https://commons.apache.org/proper/commons-lang/">Apache Commons Lang 3.8.1</a>
* <a href="https://github.com/google/gson">Gson 2.8.6</a>

[contributors-shield]: https://img.shields.io/github/contributors/arturoiafrate/YAMF.svg?style=flat-square
[contributors-url]: https://github.com/arturoiafrate/YAMF/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/arturoiafrate/YAMF.svg?style=flat-square
[forks-url]: https://github.com/arturoiafrate/YAMF/network/members
[stars-shield]: https://img.shields.io/github/stars/arturoiafrate/YAMF.svg?style=flat-square
[stars-url]: https://github.com/arturoiafrate/YAMF/stargazers
[issues-shield]: https://img.shields.io/github/issues/arturoiafrate/YAMF.svg?style=flat-square
[issues-url]: https://github.com/arturoiafrate/YAMF/issues
[license-shield]: https://img.shields.io/github/license/arturoiafrate/YAMF.svg?style=flat-square
[license-url]: https://github.com/arturoiafrate/YAMF/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/arturoiafrate/
