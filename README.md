<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/StreetLess/OmniFlow">
    <img src="https://avatars.githubusercontent.com/u/116024069?s=200&v=4" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">OmniFlow</h3>

  <p align="center">
    OmniFlow <br />
    <br />
    <a href="#documentation"><strong>Explore the documentation »</strong></a>
    <br />
    <br />
    <a href="https://github.com/StreetLess/OmniFlow/issues">Report Bug</a>
    ·
    <a href="https://github.com/StreetLess/OmniFlow/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#uses">Uses</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#documentation">Documentation</a></li>
    <li>
        <a href="#setup">Setup</a>
        <ul>
            <li><a href="#environment-variables">Environment variables</a></li>
        </ul>
    </li>
    <li>
        <a href="#usage">Usage</a>
        <ul>
            <li><a href="#running">Running</a></li>
        </ul>
    </li>
    <li>
        <a href="#build">Build</a>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#authors">Authors</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

[//]: # ([![Product Name Screen Shot][product-screenshot]]&#40;https://github.com/StreetLess/OmniFlow&#41;)

Omniflow is a tool to create updater files, for EnVRonment

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Uses

* [![kotlin][kotlin]][kotlin-url]
* [![java][java]][java-url]
* [![docker][docker]][docker-url]
* [![GA][GA]][GA-url]
* [![idea][idea]][idea-url]
* [![git][git]][git-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

You will need to install some tools to run the project.

- You will need [infisical-cli](https://infisical.com/docs/cli/overview) and an account on https://infisical.envronment.com, to fetch environment variables.
- You will also need [Docker][docker-url] and [docker-compose][docker-compose-url] to run the database and the cache.
- You will need to install [Git][git-url] to clone the project.
- You will need to install [Intellij Idea][idea-url] to edit the project.
- You will need to install [Java][java-url] v21 to run the project.

### Installation

Clone the project and install the dependencies.

#### Clone & install

```sh
git clone git@github.com:StreetLess/OmniFlow.git
cd OmniFlow
./gradlew build --refresh-dependencies
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Documentation -->
## Documentation

No documentation available yet.

<!-- SETUP -->
## Setup

### Environment variables

You will need to login to https://infisical.envronment.com and fetch the environment variables.

```sh
infisical login
# Choose self-hosted, type the url of the server
```

To fetch the environment variables, run the following command:

```sh
# --env is optional, by default it will fetch the dev environment. If you are on main or release/* branch it will fetch production environment.
infisical export --env dev > .env

# If you want more information about the environment variables run the following command to generate the example file
infisical secrets generate-example-env > .env.sample
```

<!-- USAGE -->
## Usage

### Running

You can run the project with the following command:

```sh
# You'll need to pass the cli arguments in --args=""
./gradlew run --args=""
```

### CLI

A CLI is available to run some commands.
You will need to configure the `.env`.
You can run it with the following command:

```sh
usage: [-h] [-d DIRECTORY] -v VERSION --editor [--new] [--clear] [--release]

required arguments:
  -v VERSION,             The version to push
  --version VERSION

  --editor, --simulator   The project type


optional arguments:
  -h, --help              show this help message and exit

  -d DIRECTORY,           The directory to push
  --directory DIRECTORY

  --new, --temporary      The mode to use

  --clear, -c             Clear directory version before uploading

  --release, --debug      The build type
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Build -->
## Build

If you need to build the project:

````bash
./gradlew shadowJar
ls -L /build/libs
````

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

Read the [contributing guide][contributing-url] to learn how to contribute to the project.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the License. See [LICENSE][license-url] for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Authors

<table>
    <tbody>
        <tr>
            <td align="center"><a href="https://github.com/alwyn974/"><img src="https://avatars.githubusercontent.com/u/47529956?v=4?s=100" width="100px;" alt="Alwyn974"/><br /><sub><b>Alwyn974</b></sub></a><br /></td>
        </tr>
    </tbody>
</table>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/StreetLess/Backend.svg?style=for-the-badge
[contributors-url]: https://github.com/StreetLess/Backend/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/StreetLess/Backend.svg?style=for-the-badge
[forks-url]: https://github.com/StreetLess/Backend/network/members
[stars-shield]: https://img.shields.io/github/stars/StreetLess/Backend.svg?style=for-the-badge
[stars-url]: https://github.com/StreetLess/Backend/stargazers
[issues-shield]: https://img.shields.io/github/issues/StreetLess/Backend.svg?style=for-the-badge
[issues-url]: https://github.com/StreetLess/Backend/issues
[license-shield]: https://img.shields.io/github/license/StreetLess/Backend.svg?style=for-the-badge
[license-url]: https://github.com/StreetLess/Backend/blob/master/LICENSE
[product-screenshot]: https://avatars.githubusercontent.com/u/116024069?s=200&v=4
[contributing-url]: CONTRIBUTING.md

[GA]: https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white
[GA-url]: https://github.com/features/actions
[docker]: https://img.shields.io/badge/docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white
[docker-url]: https://www.docker.com/
[docker-compose]: https://img.shields.io/badge/docker%20compose-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white
[docker-compose-url]: https://docs.docker.com/compose/
[git]: https://img.shields.io/badge/git-%23F05032.svg?style=for-the-badge&logo=git&logoColor=white
[git-url]: https://git-scm.com/
[kotlin]: https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white
[kotlin-url]: https://kotlinlang.org/
[idea]: https://img.shields.io/badge/intellij%20idea-000000?style=for-the-badge&logo=intellij-idea&logoColor=white
[idea-url]: https://www.jetbrains.com/idea/
[java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[java-url]: https://www.java.com/