package workshop13.app;

//mvn spring-boot:run -Dspring-boot.run.arguments="--port=8080 --dataDir=./opt/tmp/data"

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
//@RequestMapping(path="/addressBook")
public class addressBookController {

    private static final Logger logger = LoggerFactory.getLogger(AppApplication.class);


    @Autowired
    ContactsWriter ctcz;
    @Autowired
    ApplicationArguments appArgs;

    @GetMapping("/")
    public String showPage(Model model, @ModelAttribute Contact contact) {
        model.addAttribute("contact", contact);
        File file = new File("./src/main/resources/opt/tmp/data/");
        String absolute = file.getAbsolutePath();
        System.out.println("Directory: " + absolute + "Exists: " + file.exists());
        File[] listFiles = file.listFiles();
        String[] dir;
        String dirStr = "";
        for (int i = 0; i < listFiles.length; i++) {
            System.out.println("File: " + listFiles[i].getName());
            dirStr += listFiles[i].getName() + ",";
        }
        dir = dirStr.split(",");
        model.addAttribute("fileList", dir);

        return "index"; //  Thymeleaf must put in templates, not static folder
    }

    @PostMapping("/addressBook")
    public String showForm(
            @ModelAttribute Contact contactInfo,
            Model model)   
                                    {
        //model.addAttribute("contact", new Contact());
        System.out.println("********Checking form input data...");
        System.out.println("********NAME: " + contactInfo.getName());
        System.out.println("********EMAIL: " + contactInfo.getEmail());
        System.out.println("********PHONENUMBER: " + contactInfo.getPhoneNumber());
        
        ctcz.saveContact(contactInfo, model, appArgs);
        return "addBookConfPage";

    }

    @GetMapping("/contact")
    public String returnForm(
        Model model,
        @RequestParam(name="id") String id, //passes in http://localhost:8080/contact?id=test   id which is mapped to test
        @ModelAttribute Contact ctc) { 
        
        File file = new File("./src/main/resources/opt/tmp/data/" + id);
        String absolute = file.getAbsolutePath();
        System.out.println("Directory: " + absolute + "Exists: " + file.exists());
        
        String[] fileContents;
        String data = "";
        
        try {
            Scanner myReader = new Scanner(file);
            while(myReader.hasNextLine()) {
                String line = myReader.nextLine();
                data = data + line + ",";
                System.out.println("READING FORM FILE: " + line);
            }
            myReader.close();
            System.out.println(data);
            fileContents = data.split(",");

            ctc.setName(fileContents[0].trim());
            ctc.setEmail(fileContents[1].trim());
            ctc.setPhoneNumber(Integer.parseInt(fileContents[2].trim()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "<<< contact info not found >>>");
        }

        //Deprecated: Already have inside the above try catch block
        // if(file.exists()) {
        //     System.out.println(data);
        //     fileContents = data.split(",");

        //     ctc.setName(fileContents[0].trim());
        //     ctc.setEmail(fileContents[1].trim());
        //     ctc.setPhoneNumber(Integer.parseInt(fileContents[2].trim()));
        // }

        System.out.println("INSIDE RETURNFORM METHOD, ID: " + id);
        return "showContact";
    
    }
}