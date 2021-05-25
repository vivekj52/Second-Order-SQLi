import java.util.ArrayList;
import java.util.Collections;

public class InjectionDetection {

    private static final String query[] = {"SELECT", "FROM", "WHERE", "ORDER BY",
            "GROUP BY", "HAVING", "UNION", "OR", "AND"};

    private static final String insert[] = {"INSERT", "INTO", "VALUES"};

    private static final String delete[] = {"DELETE", "FROM", "WHERE"};

    private static final String update[] = {"UPDATE", "SET", "WHERE"};

    private static final String tableOp[] = {"CREATE", "DROP", "ALTER", "TABLE"};

    private static final String random = "123";

    private ArrayList<String> keywords;

    public InjectionDetection(){
        keywords = new ArrayList<>();

        Collections.addAll(keywords, query);
        Collections.addAll(keywords, insert);
        Collections.addAll(keywords, delete);
        Collections.addAll(keywords, update);
        Collections.addAll(keywords, tableOp);
    }

    public String randomise(String query){
        String randomisedQuery = query;

        String tokens[] = randomisedQuery.split(" ");
        for (String token: tokens){
            if(keywords.contains(token)){
                randomisedQuery = randomisedQuery.replaceAll(token, token + random);
            }
        }
        return  randomisedQuery;
    }

    public void detectAttack(String randomised_query){

        String tokens[] = randomised_query.split(" ");
        for (String token: tokens){
            if (keywords.contains(token)){
                System.out.println("WARNING: SQL Injection attack detected!!");
            }
        }
    }

    public String deRandomise(String query){
        return query.replaceAll(random, "");
    }
}
