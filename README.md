<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
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

<a href="https://github.com/talentedasian/Todo-App/actions/workflows/maven.yml/">
  <img src="![example workflow](https://github.com/github/docs/actions/workflows/main.yml/badge.svg)"/>
</a>



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

A Todo App made with various Spring Framework Modules. Initially, I thought of letting the users make a decision whether to send email notifications if 
the deadline of their Todos would soon meet. After some research and trials, I guess I could do it a little later than I had plan.

### Built With


* [Spring](https://spring.io/)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Hateoas](https://spring.io/projects/spring-hateoas)
* [Spring Data Jpa](https://spring.io/projects/spring-data-jpa)
* [Jjwt](https://github.com/jwtk/jjwt)



<!-- GETTING STARTED -->
## Getting Started

1. Install your database of choice. Mine was Postgresql.
2. Find the `application.properties` file inside the `/src/main/resources` directory and change the settings to your appropriate database of choice.
3. In this step, you could either choose to create keys for JWT Signing via the library or create a keystore. If you wanna create a keystore, you're on your own but if you choose to just create it during application startup, follow through.
    1. Create a class or just simply pick a class that would be responsible for creating and holding your keys.
    2. Point the keys needed for the `JwtProvider` class and `JwtAuthFilter` to the class needed for the JWT to work.

### Prerequisites

## Usage
1. This backend currently requires a JWT to be present in an authorization header. Since this backend generates keys on application startup and does not provide a keystore when the backend generates a JWT, that JWT is invalid when the application starts up or restarts.

<!-- ROADMAP -->
## Roadmap

1. Email API Service to send email notifications for nearing deadline Todos
2. Test Cases


<!-- CONTRIBUTING -->
## Contributing


1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<!-- CONTACT -->
## Contact

Discord - [757516400716939266]

Project Link: [https://github.com/talentedasian/Todo-App/]
