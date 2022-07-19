package workshop13.app;

/*
Compile application
>>> mvn compile
• Package application including compile
• JAR file is in target directory
>>> mvn package
• Run application
>>> mvn spring-boot:run
• Run JAR file
java -jar day12-0.0.1-SNAPSHOT.jar
• Clean build artifacts
mvn clean

Run with port numbers specified
>>> mvn spring-boot:run -Dspring-boot.run.arguments="--port=8080 --dataDir=./opt/tmp/data"

//Publish to Github
git add .
git commit -m "new commit"
git push origin main

HOSTED ON
https://git.heroku.com/radiant-lake-35506.git
heroku login
heroku create (Then copy the heroku.git website url)
git init
git remote add heroku  <replace wif your heroku git url>
git add .
git commit -m "new"
git push heroku master 

TO START PROGRAM ON VS CODE
mvn compile
mvn package
mvn spring-boot:run
 */

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AppApplication {

	private static final Logger logger = LoggerFactory.getLogger(AppApplication.class);

	public static void main(String[] args) throws IOException {
		SpringApplication app = new SpringApplication(AppApplication.class);
		logger.info("Number Generator Web App");
		String port = "3000";
		ApplicationArguments cliOpts = new DefaultApplicationArguments(args);
		System.out.println(cliOpts.getSourceArgs());
		if(cliOpts.containsOption("port")){
			port = cliOpts.getOptionValues("port").get(0);
		}

		if(cliOpts.containsOption("dataDir") 
			&& !cliOpts.getOptionValues("dataDir").get(0).equals("")) {
			System.out.println("OPTION VALUE: " + cliOpts.getOptionValues("dataDir").get(0));
			Path path = Paths.get("./src/main/resources/" + cliOpts.getOptionValues("dataDir").get(0));
			System.out.println("Directory exists or no: " + Files.exists(path)); //Check whether directory exists
			if(!Files.exists(path)) {
				Files.createDirectories(path);
				System.out.println("New path created at " + path.toAbsolutePath());
			}
			File file = new File("./");
			String absolute = file.getAbsolutePath();
			System.out.println("Ori Path: " + file.getPath() + 
								"\n" + 
								"Absolute Path " + file.getAbsolutePath());
		} else {
			System.out.println(" ERROR ERROR ERROR \n dataDir should be in arguments and dataDir should not be empty \n EXITING THE APPLICATION");
			System.exit(0);
		}

		// 	System.out.println("TEST" + cliOpts.getOptionValues("--port").get(0));
		logger.info("cliOpts > " + port);
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		System.out.println("Application started on port: " + port);
		app.run(args);
		
		
		//Start without needing to specify ports in args
		//SpringApplication.run(Workshop11Application.class, args);
	}

}