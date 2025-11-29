package tpjdbc;

import java.sql.*;
import java.util.HashMap;
import data.fieldType;
import data.IData;

@SuppressWarnings("unused")
public class Gestion {
    private Connection conn;
    
    public Gestion(Connection conn) {
        this.conn = conn;
    }
    
    public void execute(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        stmt.close();
    }
    
    public HashMap<String, fieldType> structTable(String table, boolean display) throws SQLException {
        HashMap<String, fieldType> struct = new HashMap<>();
        
        String query = "SELECT * FROM " + table + " LIMIT 0";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        ResultSetMetaData meta = rs.getMetaData();
        int nbCol = meta.getColumnCount();
        
        for (int i = 1; i <= nbCol; i++) {
            String colName = meta.getColumnName(i);
            String colType = meta.getColumnTypeName(i);
            
            fieldType type = convertType(colType);
            struct.put(colName, type);
            
            if (display) {
                System.out.println(colName + " : " + type);
            }
        }
        
        rs.close();
        pstmt.close();
        return struct;
    }
    
    public void displayTable(String table) throws SQLException {
        String query = "SELECT * FROM " + table;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        ResultSetMetaData meta = rs.getMetaData();
        int nbCol = meta.getColumnCount();
        
        // Afficher les noms de colonnes
        for (int i = 1; i <= nbCol; i++) {
            System.out.print(meta.getColumnName(i) + "\t");
        }
        System.out.println();
        
        // Afficher les données
        while (rs.next()) {
            for (int i = 1; i <= nbCol; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
        
        rs.close();
        pstmt.close();
    }
    
    public void insert(IData data, String table) throws SQLException {
        HashMap<String, fieldType> tableStruct = structTable(table, false);
        data.getStruct();
        
        if (!data.check(tableStruct)) {
            System.out.println("Erreur : structure incompatible");
            return;
        }
        
        // Récupérer l'id (on suppose que data est un Produit)
        Produit p = (Produit) data;
        int id = p.getId();
        
        if (idExists(table, id)) {
            // UPDATE : somme prix + concat description
            String query = "UPDATE " + table + 
                           " SET prix = prix + " + p.getPrix() + 
                           ", description = description || ' ' || '" + p.getDescription() + "'" +
                           " WHERE id = " + id;
            execute(query);
            System.out.println("Mise à jour réussie");
        } else {
            // INSERT normal
            String colonnes = String.join(", ", data.getMap().keySet());
            String query = "INSERT INTO " + table + " (" + colonnes + ") VALUES (" + data.getValues() + ")";
            execute(query);
            System.out.println("Insertion réussie");
        }
    }
    
    private boolean idExists(String table, int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + table + " WHERE id = " + id;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        pstmt.close();
        return count > 0;
    }
    
    private fieldType convertType(String sqlType) {
        switch(sqlType.toUpperCase()) {
            case "INT4":
            case "INTEGER":
                return fieldType.INT4;
            case "FLOAT8":
            case "DOUBLE":
                return fieldType.FLOAT8;
            case "VARCHAR":
            case "TEXT":
                return fieldType.VARCHAR;
            case "NUMERIC":
                return fieldType.NUMERIC;
            default:
                return fieldType.VARCHAR;
        }
    }
    
}