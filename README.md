# Spring-MVC-thymeleaf-heroku

**[Demo](https://ad09.herokuapp.com/)**, Heroku turns off the app after 30 min of inactivity.. so, that link works and you just have to wait for about 20sg bc it is getting ready.

## TL;DR
Simple app, No Vue, good luck.

<a href="https://i.ibb.co/xMv4n70/Sin-t-tulo.jpg"><img src="https://i.ibb.co/2j2bzkQ/Sin-t-tulo.jpg" alt="Sin-t-tulo" border="0"></a>


## About
The main requirement was implementing the ``view/front layer`` in Spring, meaning that each **user state** ``Model`` & ``Session`` will be managed on server side, the use of ``@Controller`` instead ``@RestController`` and **DOM** generated thx to Thymeleaf.

|  | | 
| ------------- | ------------- | 
| <img width="50" src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/java/java.png" /> | [<img width="50" src="https://avatars0.githubusercontent.com/u/317776?s=200&v=4" />](https://github.com/spring-projects/spring-boot) [<img width="50" src="https://avatars0.githubusercontent.com/u/1492367?s=200&v=4" />](https://github.com/thymeleaf) | 
| <img width="50" src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/javascript/javascript.png"> | [**canvasJs**](https://canvasjs.com/) |
| <img width="35%" src="https://avatars0.githubusercontent.com/u/1525981?s=200&v=4"> |  <img width="50" src="https://avatars0.githubusercontent.com/u/983927?s=200&v=4" />  |
|  **CSS** | [<img width="50" src="https://avatars1.githubusercontent.com/u/2918581?s=200&v=4">](https://github.com/twbs) |
|  |  |

### Key concepts about deploying heroku

- [read this](https://www.callicoder.com/deploy-host-spring-boot-apps-on-heroku/).
- [read this too](https://devcenter.heroku.com/articles/deploying-java#verify-that-your-pom-xml-file-is-set-up-correctly)
- Build jar.
  - Write [Procfile](https://github.com/addUsername/Spring-MVC-thymeleaf-heroku/blob/main/app/Procfile) .
  - Write [system.propeties](https://github.com/addUsername/Spring-MVC-thymeleaf-heroku/blob/main/app/system.properties) .
  - Look for potentials errors ../main/resources/application.propeties and in pom.xml
  - ``mvn clean``
  - ``mvn package``
  - Run .jar locally `` java -jar myJar.jar ``
  - copy jar to root folder (or the same folder as  [Procfile](https://github.com/addUsername/Spring-MVC-thymeleaf-heroku/blob/main/app/Procfile) and[system.propeties](https://github.com/addUsername/Spring-MVC-thymeleaf-heroku/blob/main/app/system.properties) .
- Deploy .jar file (no git)
  - by installing ``heroku plugins:install heroku-cli-deploy``
  - create app ``heroku create myApp --no-remote`` 
  - deploy ``heroku deploy:jar myJar.jar --app myApp``
  - profit
