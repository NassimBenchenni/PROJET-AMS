package tpjdbc;

import java.sql.Connection;
import java.sql.SQLException;

// lire le ReadMe si erreur

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = Connexion.connectR();
            Gestion g = new Gestion(conn);
            
            g.execute("DROP TABLE IF EXISTS product");
            System.out.println("\n=== TEST 1 : Création de la table ===");
            g.execute("CREATE TABLE product (id INT4, lot INT4, nom VARCHAR, description VARCHAR, categorie VARCHAR, prix FLOAT8)");
            System.out.println("Table créée avec succès");
            
            System.out.println("\n=== TEST 2 : Structure de la table ===");
            g.structTable("product", true);
            
            System.out.println("\n=== TEST 3 : Insertion d'un produit ===");
            Produit p1 = new Produit(1, 100, "Laptop", "Ordinateur portable", "Electronique", 899.99);
            g.insert(p1, "product");
            g.displayTable("product");
            
            System.out.println("\n=== TEST 4 : Insertion d'un autre produit ===");
            Produit p2 = new Produit(2, 200, "Souris", "Sans fil", "Accessoire", 29.99);
            g.insert(p2, "product");
            g.displayTable("product");
            
            System.out.println("\n=== TEST 5 : Mise à jour (même id) ===");
            Produit p3 = new Produit(1, 150, "Laptop", "Reconditionné", "Electronique", 100.0);
            g.insert(p3, "product");
            g.displayTable("product");
            
            System.out.println("\n=== TEST 6 : Suppression d'un produit ===");
            g.execute("DELETE FROM product WHERE id = 2");
            System.out.println("Produit supprimé");
            g.displayTable("product");
            
            System.out.println("\n=== TEST 7 : Suppression de la table ===");
            g.execute("DROP TABLE product");
            System.out.println("Table supprimée avec succès");
            
            conn.close();
            System.out.println("\nTous les tests fonctionnent bien. ");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}