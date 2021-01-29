[![LinkedIn][linkedin-shield]][linkedin-url]

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
        <li><a href="#build">Build</a></li>
        <li><a href="#run">Run</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This was the final project on the [Udacity Java Web Developer Nanodegree](https://www.udacity.com/course/java-developer-nanodegree--nd035)

The brief was to be able to:
- Demonstrate correct handling of authorization with proper security using JWT.
- Write tests and meet an acceptable code coverage level.
- Identify the correct metrics for logging, in order to monitor a system.
- Index metrics to Splunk.
- Demonstrate configuration and automation of a CI/CD pipeline.

### Built With

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Jenkins](https://www.jenkins.io/)
* [Splunk](https://www.splunk.com/)


## Getting Started

### Build

To build the project run the command:
  ```sh
  mvn clean install
  ```

### Run

Run the application with:

  ```sh 
  java -jar target/auth-course-0.0.1-SNAPSHOT.jar
  ```
## Usage
Included is a [Postman collection](src/main/resources/ecommerce_app.postman_collection.json) that can be imported to Postman desktop


[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/danrhjones/
