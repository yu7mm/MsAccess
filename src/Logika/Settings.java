package Logika;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Settings {

    public int id;
    public static String sistem;
    public static String putanja;
    public static boolean developing = false;
    public static boolean debug = true;
    public static boolean advancedOptions = false;

    public static String dbUrlLoc = "D:/Java/bazazgrada.mdb";
    public static String dbUrlRemote = "localhost:3306/bitsoft_rs_db_5?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&rewriteBatchedStatements=true";
    public static String dbUserRemote = "root";
    public static String dbUserLoc;
    public static String dbPassRemote = "root";
    public static String dbPassLoc;
    public static String bazaRemote = "bitsoft_rs_db_5";
    public static String bazaLoc = "bitsoft_rs_db_5";

    
    public Settings() {
        traziPutanju();
        Properties prop = System.getProperties();
        String sist;
        sist = prop.get("os.name").toString();
        if (sist.contains("Windows")) {
            this.sistem = "windows";
            dbPassLoc = "edi";
        } else {
            this.sistem = "linux";
            dbPassLoc = "edi";
        }

        if (sist.contains("Windows 10")) {
            //dbPass="root";
            dbPassLoc = "edi";  // Za remote bazu
        }


        if (developing) {
            dbUserLoc = "root";
            dbPassLoc = "root";
        }

        settings("settings.xml");

        //this.tempPutanja = this.putanja + "temp/";
        //makdir(this.tempPutanja);
        //   Set<Object> keys = properties.keySet();  
        //   for(Object key : keys){  
        //      System.out.println(key + ": " + properties.get(key));  
        //   }
        //   System.out.println("OS name: " + prop.get("os.name"));  
    }

    public void makdir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public void settings(String naziv) {
        String line, temp;
        try {
            BufferedReader br = new BufferedReader(new FileReader(putanja + naziv));
            while ((line = br.readLine()) != null) {
                if (line.toUpperCase().contains("DEVELOPING")) {
                    temp = line.toUpperCase().replace("<DEVELOPING>", "").replace("</DEVELOPING>", "");
                    this.developing = (temp.contains("TRUE") || temp.contains("D") || temp.contains("Y"));
                }
                if (line.toUpperCase().contains("DEBUG")) {
                    temp = line.toUpperCase().replace("<DEBUG>", "").replace("</DEBUG>", "");
                    debug = (temp.contains("TRUE") || temp.contains("D") || temp.contains("Y") || temp.contains("1"));
                }
                if (line.contains("<BAZALOCAL>")) {
                    temp = line.replace("<BAZALOCAL>", "").replace("</BAZALOCAL>", "");
                    bazaLoc = temp;
                    System.out.println("Učitana glavna baza local: " + temp);
                }
                if (line.contains("<BAZAREMOTE>")) {
                    temp = line.replace("<BAZAREMOTE>", "").replace("</BAZAREMOTE>", "");
                    bazaRemote = temp;
                    System.out.println("Učitana glavna baza remote: " + temp);
                }
                if (line.contains("BAZALOCALPASS")) {
                    temp = line.replace("<BAZALOCALPASS>", "").replace("</BAZALOCALPASS>", "");
                    dbPassLoc = temp;
                }
                if (line.contains("BAZAREMOTEPASS")) {
                    temp = line.replace("<BAZAREMOTEPASS>", "").replace("</BAZAREMOTEPASS>", "");
                    dbPassRemote = temp;
                }
                if (line.contains("BAZALOCALUSER")) {
                    temp = line.replace("<BAZALOCALUSER>", "").replace("</BAZALOCALUSER>", "");
                    dbUserLoc = temp;
                }
                if (line.contains("BAZAREMOTEUSER")) {
                    temp = line.replace("<BAZAREMOTEUSER>", "").replace("</BAZAREMOTEUSER>", "");
                    dbUserRemote = temp;
                }
                if (line.contains("BAZALOCALURL")) {
                    temp = line.replace("<BAZALOCALURL>", "").replace("</BAZALOCALURL>", "");
                    dbUrlLoc = temp;
                }
                if (line.contains("BAZAREMOTEURL")) {
                    temp = line.replace("<BAZAREMOTEURL>", "").replace("</BAZAREMOTEURL>", "");
                    dbUrlRemote = temp;
                }

                if (line.contains("ADVOPT")) {
                    temp = line.replace("<ADVOPT>", "").replace("</ADVOPT>", "");
                    advancedOptions = temp.toLowerCase().contains("true");
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void traziPutanju() {
        putanja = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        putanja = putanja.replace("file:/", "");
        putanja = putanja.replace("classes/", "");
        putanja = putanja.replace("build/", "");
        putanja = putanja.replace("%20", " ");
        if (putanja.endsWith(".jar")) {
            int slashPos = putanja.lastIndexOf("/");
            if (slashPos <= 0) {
                slashPos = putanja.lastIndexOf("\\");
            }
            slashPos++;      // Ovako nam u putanji ostaje / na kraju putanje
            putanja = putanja.substring(0, slashPos);
        }
    }

    public static void shutdown() throws RuntimeException, IOException {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");
        System.err.println(operatingSystem);

        if (operatingSystem.contains("Linux") || operatingSystem.contains("Mac")) {
            shutdownCommand = "shutdown -r now";
        } else if (operatingSystem.contains("Windows")) {
            shutdownCommand = "shutdown.exe -r -t 0";
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }

        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }

}
