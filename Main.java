package tpjdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import data.fieldType;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = Connexion.connectR();
            Gestion g = new Gestion(conn);
            
            //g.execute("CREATE TABLE test (id INT4)");
            //System.out.println("Table test créée");
            
            //HashMap<String, fieldType> struct = g.structTable("test", true);
            //System.out.println(struct);
            
            /*
            g.execute("CREATE TABLE personne2 (nom VARCHAR, age INT4)"); // creer table
            HashMap<String, fieldType> s = g.structTable("personne1", true); // affiche STRUTURE DE TABLE
            
            g.execute("INSERT INTO personne2 VALUES ('Alice', 25)");
            g.execute("INSERT INTO personne2 VALUES ('Bob', 30)");
            g.displayTable("personne2");
            */
            
            //g.execute("CREATE TABLE product (id INT4, lot INT4, nom VARCHAR, description VARCHAR, categorie VARCHAR, prix FLOAT8)");
            Produit p = new Produit(1, 100, "Laptop", "Ordinateur portable", "Electronique", 899.99);
            g.insert(p, "product");
            g.displayTable("product");
            
            Produit p1 = new Produit(1, 100, "Laptop", "Ordinateur portable", "Electronique", 899.99);
            g.insert(p1, "product");

            Produit p2 = new Produit(1, 200, "Laptop", "Reconditionné", "Electronique", 100.0);
            g.insert(p2, "product"); // Même id → mise à jour

            g.displayTable("product");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}