package tpjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connexion {
    public static Connection connectR() {
    	Connection connection = null;
        try {
            String url = "jdbc:postgresql://pedago.univ-avignon.fr:5432/etd";

            Properties props = new Properties();
            props.setProperty("user", "uapv*****");    // mettre votre uapv
            props.setProperty("password", "mdp");  // mettre votre mdp personnel 

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, props);
            System.out.println("Connexion Réussi");
        } catch (Exception e) {
        	System.out.println("Connexion échouée");
        }
        return connection;
    }
}

