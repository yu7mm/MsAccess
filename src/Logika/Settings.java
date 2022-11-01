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
    public boolean developing = false;
    public String sistem;
    public String putanja;
    
    public String tabAdmin = "admin";

    public static String dbUser = "root";
    public static String dbPass = "root";
    public static String dbUrl = "localhost:3306/" + "edibot" + "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&rewriteBatchedStatements=true";
    public static String dbUrl2 = "localhost:3306/" + "edibot" + "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&rewriteBatchedStatements=true";

    public static boolean debug = true;
    public static boolean advancedOptions = false;
    public static String glavnaBaza = "edibot";

    
    public Settings() {
        traziPutanju();
        Properties prop = System.getProperties();
        String sist;
        sist = prop.get("os.name").toString();
        if (sist.contains("Windows")) {
            this.sistem = "windows";
            dbPass = "edi";
        } else {
            this.sistem = "linux";
            dbPass = "edi";
        }

        if (sist.contains("Windows 10")) {
            //dbPass="root";
            dbPass = "edi";  // Za remote bazu
        }


        if (developing) {
            dbUser = "root";
            dbPass = "root";
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
                if (line.toUpperCase().contains("<BAZA>")) {
                    temp = line.replace("<BAZA>", "").replace("</BAZA>", "");
                    glavnaBaza = temp;
                    System.out.println("Učitana glavna baza: " + temp);
                }
                if (line.toUpperCase().contains("BAZAPASS")) {
                    temp = line.toUpperCase().replace("<BAZAPASS>", "").replace("</BAZAPASS>", "");
                    temp = temp.toLowerCase();
                    dbPass = temp;
                }
                if (line.toUpperCase().contains("BAZAUSER")) {
                    temp = line.toUpperCase().replace("<BAZAUSER>", "").replace("</BAZAUSER>", "");
                    temp = temp.toLowerCase();
                    dbUser = temp;
                }
                if (line.contains("BAZAURL")) {
                    temp = line.replace("<BAZAURL>", "").replace("</BAZAURL>", "");
                    dbUrl = temp;
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
