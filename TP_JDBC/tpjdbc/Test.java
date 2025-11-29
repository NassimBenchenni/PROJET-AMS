package tpjdbc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        try {
            Connection conn = Connexion.connectR();
            Gestion g = new Gestion(conn);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("=== Distributeur de produits ===");
            System.out.println("Commandes: CREATE, INSERT, DISPLAY, STRUCT, REMOVE, DROP, EXIT");
            
            while (true) {
                System.out.print("\n> ");
                String cmd = br.readLine().trim().toUpperCase();
                
                if (cmd.equals("EXIT")) {
                    break;
                }
                // comande CREATE 
                if (cmd.equals("CREATE")) {
                    g.execute("CREATE TABLE product (id INT4, lot INT4, nom VARCHAR, description VARCHAR, categorie VARCHAR, prix FLOAT8)");
                    System.out.println("Table product créée");
                }
                // Commande Display
                else if (cmd.equals("DISPLAY")) {
                    g.displayTable("product");
                }
                // Comande Struct (affiche la structure de ma table)
                else if (cmd.equals("STRUCT")) {
                    g.structTable("product", true);
                }
                // Commande DROP
                else if (cmd.equals("DROP")) {
                    g.execute("DROP TABLE product");
                    System.out.println("Table product supprimée");
                }
                // Commande REMOVE
                else if (cmd.startsWith("REMOVE")) {
                    String[] parts = cmd.split(" ");
                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[1]);
                        g.execute("DELETE FROM product WHERE id = " + id);
                        System.out.println("Produit " + id + " supprimé");
                    } else {
                        System.out.println("Usage: REMOVE <id>");
                    }
                }
        // ------------------------- Commande INSERT ----------------------------------
                else if (cmd.equals("INSERT")) {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(br.readLine().trim());
                    
                    System.out.print("Lot: ");
                    int lot = Integer.parseInt(br.readLine().trim());
                    
                    System.out.print("Nom: ");
                    String nom = br.readLine().trim();
                    
                    System.out.print("Description: ");
                    String description = br.readLine().trim();
                    
                    System.out.print("Catégorie: ");
                    String categorie = br.readLine().trim();
                    
                    System.out.print("Prix: ");
                    double prix = Double.parseDouble(br.readLine().trim());
                    
                    Produit p = new Produit(id, lot, nom, description, categorie, prix);
                    g.insert(p, "product");
                }
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }	
    }
}
