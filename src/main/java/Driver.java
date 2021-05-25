import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Driver {

    public static SQLInjection sqlInjection = null;

    public static void main(String[] args) {

        sqlInjection = new SQLInjection();

        ArrayList<String> emails = sqlInjection.getEmails();
        Collections.reverse(emails);

        System.out.println("Email ids from credential table are");
        for (String email: emails){
            System.out.println(email);
        }

        //List<String> names = sqlInjection.getNamesUnsafe(emails);

        //List<String> names2 = sqlInjection.getNamesSafe(emails);

        detect();

    }

    public static void detect(){
        sqlInjection = new SQLInjection();

        ArrayList<String> emails = sqlInjection.getEmails();
        Collections.reverse(emails);

        System.out.println("Email ids from credential table are");
        for (String email: emails){
            System.out.println(email);
        }

        List<String> names = sqlInjection.detectandgetNames(emails);
    }
}
