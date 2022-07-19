package workshop13.app;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;



@Component("contacts")
public class ContactsWriter {
    private static final Logger logger = LoggerFactory.getLogger(ContactsWriter.class);

    public void saveContact(Contact ctc, Model model, ApplicationArguments appArgs) {
        String dataFileName = ctc.getId();
        Set<String> opsNames = appArgs.getOptionNames();
        String[] optNamesArr = opsNames.toArray(new String[opsNames.size()]);
        List<String> optValues = appArgs.getOptionValues(optNamesArr[0]);
        String[] optValuesArr = optValues.toArray(new String[optValues.size()]);

        PrintWriter pWriter = null;
    
        try {
            File myObj = new File("C:/Users/vans_/VISA NUS-ISS VTTP/sdf-workshop13/app/src/main/resources" + appArgs.getOptionValues("dataDir").get(0) + "/" + dataFileName + ".txt");
            myObj.createNewFile();
            System.out.println(myObj.getAbsolutePath());

            FileWriter fWriter = new FileWriter("C:/Users/vans_/VISA NUS-ISS VTTP/sdf-workshop13/app/src/main/resources" + (appArgs.getOptionValues("dataDir").get(0)) + "/" + dataFileName + ".txt");
            System.out.println("FILE PATH TO SAVE TO: " + fWriter.toString());
            
            pWriter = new PrintWriter(fWriter);
            pWriter.println(ctc.getName());
            pWriter.println(ctc.getEmail());
            pWriter.println(ctc.getPhoneNumber());
            pWriter.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}