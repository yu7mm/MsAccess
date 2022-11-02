
package Io;

import Logika.Settings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbBrokerLocal {

    public String greska = "";
    private String address = "D:/Java/bazazgrada.mdb";
    private String user;
    private String pass;
    private Connection conn;

    public DbBrokerLocal() {
        this.user = Settings.dbUserLoc;
        this.pass = Settings.dbPassLoc;
        this.address = Settings.dbUrlLoc;
    }

    public DbBrokerLocal(String address, String user, String pass) {
        this.address = address;
        this.user = user;
        this.pass = pass;
    }


    public boolean conn() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //address += ";keepMirror=C:/bazaZgMirror";
            System.out.println("Adresa baze: " + address);
            conn = DriverManager.getConnection("jdbc:ucanaccess://" + address);
            conn.setAutoCommit(false);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Problem kod učitavanja ili registrovanja MS Access JDBC drivera\n" + e);
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.commit();
                conn.close();                
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int scalar(String query) {
        try {
            int res = -1;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                res = rs.getInt(1);
            }
            // System.out.println("Izvrsen scalar, upit: " + query);
            return res;
        } catch (SQLException ex) {
            System.out.println("Izvrsen scalar, greska: " + ex);
            System.out.println("Upit glasi: " + query);
            return -1;
        }
    }

    public String string(String query) {
        try {
            String res = "";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                res = rs.getString(1);
            }
            return res;
        } catch (SQLException ex) {
            return "";
        }
    }

    public ArrayList<String> stringArr(String query) {
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            //ResultSetMetaData rsMdata = rs.getMetaData();
            ArrayList<String> resultList = new ArrayList<String>();
            while (rs.next()) {
                resultList.add(rs.getString(1));
            }
            return resultList;
        } catch (SQLException ex) {
            ArrayList<String> r = new ArrayList<String>();
            r.add("");
            return r;
        }
    }

    public ArrayList<String[]> getArr(String query) {
        try {
            Statement st = conn.createStatement();
            //System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsMdata = rs.getMetaData();
            int colNo = rsMdata.getColumnCount();
            ArrayList<String[]> resultList = new ArrayList<>();
            // System.out.println("Upit iz klase DbBrokerLocal: " + query);
            while (rs.next()) {
                String[] rw = new String[colNo];
                for (int i = 1; i <= colNo; i++) {
                    rw[i - 1] = rs.getString(i);
                    // System.out.println("Odgovor na upit: " +rw[i-1]);
                }
                resultList.add(rw);
            }
            return resultList;
        } catch (SQLException ex) {
            ArrayList<String[]> r = new ArrayList<String[]>();
            String[] s = new String[1];
            s[0] = ex.getMessage();
            System.err.println("<DbBroker> Greska kod getArr: " + s[0] + ", \n Upit: " + query);
            r.add(s);
            return r;
        }
    }
    
    
    /**
     * Mapira rezultat u HashMap<key, String[]> 
     * pri čemu je key prva kolona koju upit vrati a ostali elementi smeštaju se u niz.
     * @param query
     * @return 
     */
    public Map<String, String[]> getMap(String query) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsMdata = rs.getMetaData();
            int colNo = rsMdata.getColumnCount();
            Map<String, String[]> resultList = new HashMap<>();
            rs.next();
            String key = rs.getString(1);
            while (rs.next()) {
                String[] rw = new String[colNo];
                for (int i = 2; i <= colNo; i++) {
                    rw[i - 1] = rs.getString(i);
                }
                if (resultList.containsKey(key)) {
                    System.out.println("Dupliran ključ kof getMap!!! " + key);
                }
                resultList.put(key, rw);
            }
            return resultList;
        } catch (SQLException ex) {
            Map<String, String[]> r = new HashMap<>();
            String[] s = new String[1];
            s[0] = ex.getMessage();
            System.err.println("<DbBroker> Greska kod getMap: " + s[0] + ", \n Upit: " + query);
            r.put("GRESKA", s);
            return r;
        }
    }

    
    /**
     * Mapira rezultat u dvostruku HashMapu u formatu <key1, HashMap<key2, Strin[]>>
     * Pri čemu je key1 prva kolona iz upita, a key2 druga. Ostale kolone prebacuju se u niz.
     * @param query - očekivano je da vrati najmanje 3 kolone.
     * @return 
     */
    public Map<String, LinkedHashMap<String, String[]>> getMap2Key(String query) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsMdata = rs.getMetaData();
            int colNo = rsMdata.getColumnCount();
            Map<String, LinkedHashMap<String, String[]>> resultList = new HashMap<>();
            String key1, key2;
            while (rs.next()) {
                key1 = rs.getString(1);
                key2 = rs.getString(2);
                String[] rw = new String[colNo];
                for (int i = 3; i <= colNo; i++) {
                    rw[i - 3] = rs.getString(i);
                }

                //System.out.println("key1: " +key1 + ", key2: " + key2 + ", loc: " + rw[1]);

                if (resultList.containsKey(key1)) {
                    if (resultList.get(key1).containsKey(key2)) {
                        String key3 = key2 + "_dup1";
                        int s=2;
                        while (resultList.get(key1).containsKey(key3)) {
                            key3 = key2 + "_dup" + s;
                            s++;
                        }
                        // System.out.println("Dupliranje kod unutrašnje HashMape!!! key2: " + key2 + ". Upisano: " + key3);
                        key2 = key3;
                    }
                    resultList.get(key1).put(key2, rw);
                }
                else {
                    LinkedHashMap<String, String[]> unutra = new LinkedHashMap<>();
                    unutra.put(key2, rw);
                    resultList.put(key1, unutra);
                }
            }
            return resultList;
        } catch (SQLException ex) {
            Map<String, LinkedHashMap<String, String[]>> r1 = new HashMap<>();
            LinkedHashMap<String, String[]> r2 = new LinkedHashMap<>();
            String[] s = new String[1];
            s[0] = ex.getMessage();
            System.err.println("<DbBroker> Greska kod getMap2Key: " + s[0] + ", \n Upit: " + query);
            r2.put("GRESKA", s);
            r1.put("GRESKA", r2);
            return r1;
        }
    }

    
    public String simpleQuery(String string) {
        boolean uspesno = false;
        String greska = "OK";
        try {
            Statement st = conn.createStatement();
            st.execute(string);
            uspesno = true;
        } catch (SQLException ex) {
            System.out.println("Greska kod simpleQuery: " + ex);
            System.out.println("Komanda: " + string);
            uspesno = false;
            greska = ex.toString();
        }
        //System.out.println("Izvrsen simpleQuery");
        //System.out.println(string);
        return greska;
    }


    public void commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DbBrokerLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean batchedQuery(ArrayList<String> batchSt) {
        conn();
        try {
            int i=0;
            Statement st = conn.createStatement();
            for (String s : batchSt) {
                i++;
                st.addBatch(s);
                if (i % 900 == 0 || i == batchSt.size()) {
                    System.out.println(" Ubacujem " + i + " redova iz batch u bazu...");
                    st.executeBatch();
                    st.clearBatch();
                    conn.commit();
                }
            }
            st.executeBatch();
            st.clearBatch();
            st.close();
            conn.commit();
            close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DbBrokerLocal.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
        return false;
    }

}
