
import Io.DbBrokerLocal;
import Logika.Settings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class MsAccessDatabaseConnectionInJava8 {
    
    private static final Settings sett = new Settings();
    private static final DbBrokerLocal db = new DbBrokerLocal();

    public static void init() {
        //Settings sett = new Settings();
        //DbBrokerLocal db = new DbBrokerLocal();
        db.conn();
    }
    
    
    public static String prikaziZgrade() {
        String upit = "SELECT id, br, adresa, pib, tabelastanova, tabelabanka, tabelazaduzenja "
                + "FROM zgrade "
                + "WHERE brisano = false AND skriveno = false "
                + "ORDER BY adresa";
        ArrayList<String[]> rez = db.getArr(upit);

        StringBuilder sb = new StringBuilder();
        sb.append("R.br.\tID\tBr\tAdresa\t\tPIB\n");
        sb.append("======\t==\t====\t========================\t===========\n");
        int brojac = 0;
        for (String[] s : rez) {
            brojac++;
            String adresa = srediAdresu(s[2]);
            sb.append(brojac).append("\t").append(s[0]).append("\t").append(s[1]).append("\t").append(adresa).append("\t").append(s[3]).append("\n");
        }
        sb.append("\nPročitano redova: ").append(brojac).append("\n");
        System.out.println("\nPročitano redova: " + brojac);
        return sb.toString();
    }
    
    public static synchronized boolean sinhronizuj() {
        String upit = "SELECT id, br, adresa, pib, tabelastanova, tabelabanka, tabelazaduzenja "
                + "FROM zgrade "
                + "WHERE brisano = false AND skriveno = false "
                + "ORDER BY adresa";
        ArrayList<String[]> rez = db.getArr(upit);

        // Pocinjemo sinhronizaciju
        int brojac = 0;
        System.out.println("Pronađeno zgrada: " + rez.size());
        for (String[] s : rez) {
            brojac++;
            String tabelastanova = s[4];
            System.out.println("\nZGRADA " + brojac + ": " + s[2]);
            System.out.println();
            String kolone = "id, vlsifra, pib, zgrada, vlime, vlprez, vlfirma, brstana, tip, clanovakojiplacaju, povezano, datum";
            upit = "SELECT " + kolone + " FROM " + tabelastanova + " WHERE (synced=false) AND (skriveno=false) ORDER BY id ASC";
            ArrayList<String[]> stanovi = db.getArr(upit);
            salji_stanovi(stanovi);

            String tabelabanka = s[5];
            kolone = "id, sifrauplatioca, uplata, svrha, poznabr, brizvoda, datum, godina, mesec, datvreme, zgrada";
            upit = "SELECT " + kolone + " FROM " + tabelabanka + " WHERE synced=false AND brisano=false AND uplata<>0 ORDER BY id ASC";
            ArrayList<String[]> banka = db.getArr(upit);
            salji_banka(banka);

            String tabelazaduzenja = s[6];
            kolone = " id, racunbr, datum, rok, datsluge, godina, mesec, zgrada, sifrastana, uplatilac, ";
            kolone += "usluga, sumazg, suma, fiksni, merazg, subvenc, mera, kolicina, kolicinazg, ";
            kolone += "cenapojed, pretplata, stanjeracuna, brclanova, mesto, QRLabel25, QRLabel81, QRLabel16 ";
            upit = "SELECT " + kolone + " FROM " + tabelazaduzenja + " WHERE synced=false AND brisano=false ORDER BY id";
            ArrayList<String[]> zaduzenja = db.getArr(upit);
            salji_zaduzenja(zaduzenja);
        }
        System.out.println("Sinhronizacija završena");
        //ispisZaduzenja(db, 37);
        return true;
    }

    private static void ispisZaduzenja(DbBrokerLocal db, int zgrada) {
        String upit = "SELECT s.id, s.br, s.vlime, s.vlprez, s.vlsifra, z.datum, z.mesec, z.godina, z.usluga, z.suma "
                + "FROM stanovi_" + zgrada + " s "
                + "JOIN zaduzenja_37 z ON s.vlsifra = z.sifrastana "
                + "WHERE s.brisano = false AND s.SKRIVENO = false "
                + "ORDER BY s.br ASC, z.id ASC";
        ArrayList<String[]> rez = db.getArr(upit);
        int brojac = 0;
        System.out.println("Brojac\tID\tBr\tVlasnik  \t\t\tŠifra\tSuma\tUsluga\tGodina\tMesec");
        System.out.println("======\t===\t===\t===============================\t=======\t======\t======\t======\t=====");
        for (String[] s : rez) {
            brojac++;
            String ime = srediAdresu(s[2] + " " + s[3]);
            String usluga = (s[8].length() > 7) ? s[8].substring(0, 7) : s[8];
            System.out.println(brojac + "\t"
                    + s[0] + "\t"
                    + s[1] + "\t"
                    + ime + "\t"
                    + s[4] + "\t"
                    + s[9] + "\t"
                    + usluga + "\t"
                    + s[7] + "\t"
                    + s[6]);
        }
        System.out.println("\nPročitano redova: " + brojac);
    }

    private static void konekcija() {
        // variables
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        // Step 1: Loading or registering Oracle JDBC driver class
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {
            String msAccessDBName = "D:/Java/bazazgrada.mdb";
            // String dbURL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + msAccessDBName + ";DriverID=22;READONLY=true";
            String dbURL = "jdbc:ucanaccess://" + msAccessDBName;

            // Step 2.A: Create and get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL);

            // Step 2.B: Creating JDBC Statement 
            statement = connection.createStatement();

            // Step 2.C: Executing SQL & retrieve data into ResultSet
            String upit = "SELECT * FROM ZGRADE WHERE BRISANO = false AND SKRIVENO = false ORDER BY br";
            resultSet = statement.executeQuery(upit);

            System.out.println("Brojac\tID\tBr\tAdresa\t\t\t\tPIB");
            System.out.println("======\t==\t====\t===============================\t===========");

            // processing returned data and printing into console
            int brojac = 0;
            while (resultSet.next()) {
                brojac++;
                String adresa = srediAdresu(resultSet.getString(9));
                System.out.println(brojac + "\t"
                        + resultSet.getInt(1) + "\t"
                        + resultSet.getString(2) + "\t"
                        + adresa + "\t"
                        + resultSet.getString(13));
            }

            System.out.println("\nPročitano redova: " + brojac);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {

            // Step 3: Closing database connection
            try {
                if (null != connection) {

                    // cleanup resources, once after processing
                    resultSet.close();
                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }

    private static String srediAdresu(String adr) {
        String adresa = adr;
        if (adr.length() < 30) {
            for (int i = 0; i < 30 - adr.length(); i++) {
                adresa += " ";
            }
        } else {
            while (adresa.contains("   ")) {
                adresa = adresa.replace("   ", " ");
            }
            if (adr.length() < 30) {
                for (int i = 0; i < 30 - adr.length(); i++) {
                    adresa += " ";
                }
            }
        }
        return adresa;
    }

    private static boolean salji_stanovi(ArrayList<String[]> stanovi) {
        String id, tt, a, slog;
        String slogA;
        boolean uspelo;
        a = "', '";

        uspelo = true;
        System.out.println("Za ubacivanje iz tabele stanova: " + stanovi.size());
        for (String[] s : stanovi) {
            /*
            if (Sinhronizacija.odustani) then break;
            Sinhronizacija.Label2.Caption := 'Zapis ' + intToStr(i) + ' od ' + intToStr(slogova) ;
            Sinhronizacija.ProgressBar2.Position := i;
            Sinhronizacija.Repaint;
             */
            id = s[0];

            slog = " ('" + id + a + s[1] + a + s[2] + a + s[3];
            slog += a + s[4] + a + s[5];
            tt = s[6];
            if (tt != null && tt.length() > 2) {
                tt = tt.replace("''", "\"");
            }
            slog += a + tt + a + s[7];
            slog += a + s[8] + a + s[9] + a + s[10] + a + s[11] + "')";
            slog = " VALUES " + slog;
            slog = "(sourceid, sifrastana, pib, zgrada, ime, prezime, firma, brstana, tip, brstanara, povezano, datum) " + slog;
            slog = "INSERT INTO `bitsoft_rs_db_5`.`stanovi` " + slog;

            System.out.println(slog);

            //slogA = Utf8Encode(slog);
            //uspelo = ubaci(slog, tabela);
            if (uspelo) {
                // Markirati slog u bazi kao Synced!
                //if (not markiraj_synced(tabela, id)) then showMessage('Slog sa id=' + id + ' je sinhronizovan ali nije obeležen u tabeli pa će doći do dupliranja!');
            }
        }
        return uspelo;
    }

    private static boolean salji_banka(ArrayList<String[]> banka) {
        System.out.println("Za ubacivanje iz tabele banka: " + banka.size());
        String tt, t2, t3, t4, t5, t6, t7, t8, t9, u1, a;
        String slog;
        String slogA;
        boolean uspelo;
        Date datum;

        a = "', '";
        for (String[] s : banka) {
            tt = (s[10] != null) ? s[10] : "0";
            t2 = (s[1] != null) ? s[1] : "0";
            t3 = (s[2] != null) ? s[2] : "0";
            t4 = (s[3] != null) ? s[3] : "0";
            t5 = (s[5] != null) ? s[5] : "0";
            t6 = (s[4] != null) ? s[4] : "0";
            t7 = (s[6] != null) ? s[6] : "0";  // Formatirati datum formatdatetime('yyyy-mm-dd', ADOQuery2['datum']);
            //if (ADOQuery2['datum'] = null) then datum := StrToDate('1900-01-01') else datum := ADOQuery2['datum'];
            t8 = (s[7] != null) ? s[7] : "0";
            t9 = (s[8] != null) ? s[8] : "0";
            u1 = (s[9] != null) ? s[9] : "0";  // u1 := formatdatetime('yyyy-mm-dd', ADOQuery2['datvreme']);

            slog = " ('" + s[0] + a + t2 + a + t3;
            slog += a + t4 + a + t6 + a + t5;
            slog += a + t7 + a + t8 + a + t9;
            slog += a + u1 + a + tt;
            slog += a + "1' ) ";
            slog = " VALUES " + slog;
            slog = "(sourceid, sifrastana, uplata, svrha, poznabr, brizvoda, datumusluge, godina, mesec, datum, zgrada, uplzaduz) " + slog;
            slog = "INSERT INTO `bitsoft_rs_db_5`.`finansije` " + slog;

            //System.out.println(slog);
            /*
            slogA := Utf8Encode(slog);
            uspelo := true;
            uspelo := (uspelo) AND (ubaci(slog, tabela));
            if (uspelo) then 
              begin
                // Markirati slog u bazi kao Synced!
                markiraj_synced(tabela, intToStr(ADOQuery2['id']));
              end else
              begin
                ShowMessage('GREŠKA kod sinhronizacije!' + ' '#13#10' ' + slog + ' '#13#10' ' +  ' '#13#10' ' + ' ');  
              end;
            ADOQuery2.Next;
             */
        }
        uspelo = true;
        return uspelo;
    }

    private static boolean salji_zaduzenja(ArrayList<String[]> zaduzenja) {
        System.out.println("Za ubacivanje iz tabele zaduzenja: " + zaduzenja.size());

        int[] zaMarkiranje = new int[1000];
        int slogova, k, s, prolaza, p, pp, sl, maxSlogova, prol;
        String id1, tt, a;
        String t1, t2, t3, t4, t5, t6, t7, t8, t9, uplatilac;
        String u1, u2, u3, u4, u5, u6, u7, u8, u9;
        String v1, v2, v3, v4, v5, v6, v7, v8, v9;
        String slog, sviSlogovi;
        String slogA;
        boolean uspelo;
        //Stringlist MyText;

        p = -1;
        maxSlogova = 600;
        a = "', '";
        slogova = zaduzenja.size();
        prolaza = slogova / maxSlogova;
        if (slogova != maxSlogova) {
            prolaza++;
        }
        if (slogova < maxSlogova) {
            sl = slogova;
        } else {
            sl = maxSlogova;
        }
        // Sinhronizacija.ProgressBar2.Max := slogova+1;
        // Sinhronizaciju raditi u sekvencama od po 500-800 slogova od jednom
        // dakle sa ugnjezdenim petljama
        for (int j = 1; j <= prolaza; j++) {
            //if (Sinhronizacija.odustani) then break;
            System.out.println("PROLAZ: " + j);
            if (j == prolaza) {
                sl = slogova - ((prolaza - 1) * maxSlogova);
            }
            sviSlogovi = "";
            //MyText = TStringlist.create;
            //sqlcli.StartTransaction();
            for (int i = 1; i <= sl; i++) {
                //if (Sinhronizacija.odustani) break;
                //Sinhronizacija.Label2.Caption := 'Zapis ' + intToStr(p) + ' od ' + intToStr(slogova) ;
                //Sinhronizacija.ProgressBar2.Position := p;
                p++;
                //Sinhronizacija.Repaint;

                t1 = (zaduzenja.get(p)[1] != null) ? zaduzenja.get(p)[1] : "0";
                t2 = (zaduzenja.get(p)[2] != null) ? zaduzenja.get(p)[2] : "0000-00-00";  // t2 := formatdatetime('yyyy-mm-dd', ADOQuery2['datum']);
                t3 = (zaduzenja.get(p)[3] != null) ? zaduzenja.get(p)[3] : "0000-00-00";  // t3 := '0000-00-00' else t3 := formatdatetime('yyyy-mm-dd', ADOQuery2['rok']);
                t4 = (zaduzenja.get(p)[4] != null) ? zaduzenja.get(p)[4] : "0000-00-00";  // t4 := '0000-00-00' else t3 := formatdatetime('yyyy-mm-dd', ADOQuery2['rok']);
                t5 = (zaduzenja.get(p)[5] != null) ? zaduzenja.get(p)[5] : "0";
                t6 = (zaduzenja.get(p)[6] != null) ? zaduzenja.get(p)[6] : "0";
                t7 = (zaduzenja.get(p)[7] != null) ? zaduzenja.get(p)[7] : "0";
                t8 = (zaduzenja.get(p)[8] != null) ? zaduzenja.get(p)[8] : "0";
                t9 = (zaduzenja.get(p)[9] != null) ? zaduzenja.get(p)[9] : "0";
                t9 = t9.replace("''", "\"");
                u1 = (zaduzenja.get(p)[10] != null) ? zaduzenja.get(p)[10] : "0";
                u2 = (zaduzenja.get(p)[11] != null) ? zaduzenja.get(p)[11] : "0";
                u2 = u2.replace(",", ".");
                u3 = (zaduzenja.get(p)[12] != null) ? zaduzenja.get(p)[12] : "0";
                u3 = u3.replace(",", ".");
                u4 = (zaduzenja.get(p)[13] != null) ? zaduzenja.get(p)[13] : "0";
                u4 = u4.replace(",", ".");
                u5 = (zaduzenja.get(p)[14] != null) ? zaduzenja.get(p)[14] : "0";
                u6 = (zaduzenja.get(p)[15] != null) ? zaduzenja.get(p)[15] : "0";
                u6 = u6.replace(",", ".");
                u7 = (zaduzenja.get(p)[16] != null) ? zaduzenja.get(p)[16] : "0";
                u8 = (zaduzenja.get(p)[17] != null) ? zaduzenja.get(p)[17] : "0";
                u8 = u8.replace(",", ".");
                u9 = (zaduzenja.get(p)[18] != null) ? zaduzenja.get(p)[18] : "0";
                u9 = u9.replace(",", ".");
                v1 = (zaduzenja.get(p)[19] != null) ? zaduzenja.get(p)[19] : "0";
                v1 = v1.replace(",", ".");
                v2 = (zaduzenja.get(p)[20] != null) ? zaduzenja.get(p)[20] : "0";
                v2 = v2.replace(",", ".");
                v3 = (zaduzenja.get(p)[21] != null) ? zaduzenja.get(p)[21] : "0";
                v3 = v3.replace(",", ".");
                v4 = (zaduzenja.get(p)[22] != null) ? zaduzenja.get(p)[22] : "0";
                v5 = (zaduzenja.get(p)[23] != null) ? zaduzenja.get(p)[23] : "0";
                v6 = (zaduzenja.get(p)[24] != null) ? zaduzenja.get(p)[24] : "0";
                v7 = (zaduzenja.get(p)[25] != null) ? zaduzenja.get(p)[25] : "0";
                v8 = (zaduzenja.get(p)[26] != null) ? zaduzenja.get(p)[26] : "0";

                slog = " ('" + zaduzenja.get(p)[0] + a + t1 + a + t2;
                slog += a + t3 + a + t4 + a + t5;
                slog += a + t6 + a + t7 + a + t8;
                slog += a + t9 + a + u1 + a + u2;
                slog += a + u3 + a + u4 + a + u5;
                slog += a + u6 + a + u7 + a + u8;
                slog += a + u9 + a + v1 + a + v2;
                slog += a + v3 + a + v4 + a + v5;
                slog += a + v6 + a + v7 + a + v8;
                slog += a + "0' ); ";

                slog = " VALUES " + slog;
                slog = " usluga, sumazg, suma, fiksni, merazg, subvenc, mera, kolicina, kolicinazg, cenapojed, preplata, stanjeracuna, brclanova, mesto, qrlabel25, qrlabel81, qrlabel16, uplzaduz) " + slog;
                slog = " (sourceid, racunbr, datum, rok, datumusluge, godina, mesec, zgrada, sifrastana, uplatilac, " + slog;
                slog = "INSERT INTO `bitsoft_rs_db_5`.`finansije` " + slog;
                //slogA = Utf8Encode(slog);

                //sqlcli.ExecuteDirect(slogA);
                sviSlogovi += slog + ";\n" ;
                //sviSlogovi := slogA + '; '#13#10' ' ;
                //MyText.Add(slogA + '; '#13#10' ');
                zaMarkiranje[i] = Integer.parseInt(zaduzenja.get(p)[0]);
                //uspelo := ubaci(slog, tabela);
                /*
              if (uspelo) then 
                begin
                  // Markirati slog u bazi kao Synced!
                  markiraj_synced(tabela, intToStr(ADOQuery2['id']));
                end else
                begin
                  ShowMessage('GREŠKA kod sinhronizacije! u2=' + u2 + ', u3=' + u3 + ' '#13#10' ' + slog + ' '#13#10' ' +  ' '#13#10' ' + sviSlogovi);  
                end;
                 */
                
            }  // Zavrsena izrada slozenog upita
            //System.out.println(sviSlogovi);
            
            // Ovde poslati ceo paket za memorisanje u bazu
            //ShowMessage('Slog za slanje u bazu: u2=' + u2 + ', u3=' + u3 + ' '#13#10' ' + sviSlogovi + ' '#13#10' '); 
            //MyText.SaveToFile('d:\filename.txt');
            uspelo = true;

            /*
      try
      	sqlcli.Commit;
      Except
        MyText.SaveToFile('d:/upit_sa_greskom.txt');
      	showMessage('Greška kod kopiranja podataka iz tabele: ' + tabela + '. Pogledajte log fajl "upit_sa_greskom.txt" na D disku.');
        uspelo := false;
      end;
             */
 /*
      if (uspelo) then 
        begin
          // Markirati sve slogove u tabeli kao Synced! 
          pp := 1;         
  				prol := sl Div 80;
  				if sl <> 80 then prol := prol + 1;
  				if sl <  80 then s := sl else s := 80;
          for k := 1 to prol do
          	begin
              id1 := 'UPDATE ' + tabela + ' SET synced=true WHERE ';
              if k = prol then s := sl - ((prol - 1) * 80);
              for i := 1 to s do
                begin
                  //markiraj_synced(tabela, intToStr(zaMarkiranje[i]));
                  if i>1 then id1 := id1 + ' OR ';
                  id1 := id1 + 'id=' + intToStr(zaMarkiranje[pp]) ;
                  pp := pp + 1;
                end;
              try
                MyText:= TStringlist.create;
                MyText.Add('Tabela: ' + tabela + ', Slogova: ' + intToStr(slogova));
                MyText.Add('Upit: ' + id1);
                MyText.Add('sl=' + intToStr(sl) + ', prol=' + intToStr(prol) +  ', k=' + intToStr(k) + ', s=' + intToStr(s) + ', i=' + intToStr(i));
                MyText.SaveToFile('d:\sync_sa_greskom.txt');
              	ADOQuery3.SQL.Text := id1;
              	if s>0 then ADOQuery3.ExecSQL; // Uslov je za slučaj da sogova ima onoliki broj koji je deljiv sa 80, onda će prolaza biti jedan više, pa u poslednjem prolazu neće biti ni jedan slog pa dolazi do greske u WHERE
              	//ShowMessage('Potrebno markirati: '#13#10'' +   ''#13#10'' + id1); 
              Except
                MyText:= TStringlist.create;
                MyText.Add(id1);
                MyText.SaveToFile('d:\sync_sa_greskom.txt');
                ShowMessage('Greska kod markiranja.'#13#10'Pogledajte log fajl "sync_sa_greskom.txt" na D disku.');
              end;
          	end;
        end else
        begin
          ShowMessage('GREŠKA kod sinhronizacije! u2=' + u2 + ', u3=' + u3 + ' '#13#10' ' + slog + ' '#13#10' ' +  ' '#13#10' ' + sviSlogovi);  
        end;
        
        
        
        }
  ADOQuery2.Close;
  salji_zaduzenja := true;
             */
        }
        return true;
    }

    static void izlaz() {
        System.out.println("Zatvaram bazu");
        if (db != null) db.close();
    }
}
