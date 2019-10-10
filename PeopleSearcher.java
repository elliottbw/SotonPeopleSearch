import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PeopleSearcher {
    public String getInput() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String output = in.readLine();
        in.close();

        return output;
    }

    public String getSource(URL address) throws Exception {
        // Create BufferedReader to get source from address.
        BufferedReader urlReader = new BufferedReader(new InputStreamReader(address.openStream()));
        
        StringBuilder source = new StringBuilder();
        String line;

        // Read source line by line and append to a string using StringBuilder object.
        while ((line = urlReader.readLine()) != null ) {
            source.append(line);
        }

        return source.toString();
    }

    public String getNameFromSource(String source) {
        // Positive lookbehind for "name" property, non-greedy search for any number of characters,
        // then positive lookahead for a open tag. 
        final Pattern NAME_SEARCH = Pattern.compile("(?<=\"name\">).*?(?=<)");
        
        Matcher nameMatcher = NAME_SEARCH.matcher(source);
        nameMatcher.find();
        
        // Handle no match error slightly more gracefully
        try {
            return nameMatcher.group(0);
        }
        
        catch (java.lang.IllegalStateException e) {
            return "No match";
        }
    }

    public static void main(String[] args) throws Exception {
        // Create PeopleSearcher instance.
        PeopleSearcher directory = new PeopleSearcher();
        String initials = directory.getInput();

        URL webAddress = new URL("https://www.ecs.soton.ac.uk/people/" + initials);
        
        // Pass URL formatted with user input to get the source.
        String page = directory.getSource(webAddress);
        
        // Print resulting name.
        System.out.println(directory.getNameFromSource(page));
    }
}

// Authentication - POST request with params
// https://secure.ecs.soton.ac.uk/login/now/index.php?ecslogin_username=XXX&ecslogin_password=XXX
