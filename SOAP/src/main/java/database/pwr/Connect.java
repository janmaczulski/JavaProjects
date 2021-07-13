package database.pwr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JOptionPane;

@WebService
public class Connect {

	@WebMethod
    private static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:provider.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public static void addClient(String imie, String nazwisko)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("INSERT INTO Klienci (Imie,Nazwisko) VALUES (?,?);");
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.execute();
            System.out.println("added");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @WebMethod
    public static void addInstallation(String adres, String typ, int idklienta)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("INSERT INTO Instalacje (Adres,TypUslugi,IdKlienta) VALUES (?,?,?);");
            stmt.setString(1, adres);
            stmt.setString(2, typ);
            stmt.setInt(2, idklienta);
            stmt.execute();
            System.out.println("added");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @WebMethod
    public static void getRachunki()
    {
        String sql = "SELECT NaliczoneNaleznosci.IdRachunku, NaliczoneNaleznosci.Kwota, Klienci.Imie, Klienci.Nazwisko, NaliczoneNaleznosci.Oplacone FROM Klienci INNER JOIN NaliczoneNaleznosci ON Klienci.Id=NaliczoneNaleznosci.IdKlienta;";
        System.out.println("Id rachunku" +  "\t" +"Kwota" + "\t" +"Imie" + "\t" + "Nazwisko" + "\t" + "Oplacone?");

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getInt("IdRachunku") +  "\t" +
                        rs.getDouble("Kwota") + "\t" +
                        rs.getString("Imie") + "\t" +
                        rs.getString("Nazwisko") + "\t" +
                        rs.getInt("Oplacone"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @WebMethod
    public static void selectClients(){
        String sql = "SELECT * FROM Klienci";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getInt("Id") +  "\t" +
                        rs.getString("Imie") + "\t" +
                        rs.getString("Nazwisko"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @WebMethod
    public static void selectInstallations(){
        String sql = "SELECT * FROM Instalacje";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getInt("Router") +  "\t" +
                        rs.getString("TypUslugi") + "\t" +
                        rs.getString("Adres") + "\t" +
                        rs.getInt("Id Klienta"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getDateAndTime()
    {
        try {
            Connection conn = connect();
            String sql = "SELECT DATETIME('now');";
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return (rs.getString(1));
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void deleteClient(int Id)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("DELETE FROM Klienci WHERE Id=?");
            stmt.setInt(1, Id);
            stmt.execute();
            System.out.println("deleted");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterClient(String imie, String nazwisko, int id)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("UPDATE Klienci SET Imie=?,Nazwisko=? WHERE Id=?");
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setInt(3, id);
            stmt.execute();
            System.out.println("changed");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteInstallation(int Id)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("DELETE FROM Instalacje WHERE Id=?");
            stmt.setInt(1, Id);
            stmt.execute();
            System.out.println("deleted");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getNaleznosci(){
        String sql = "SELECT Klienci.Nazwisko,Klienci.Imie, SUM(NaliczoneNaleznosci.Kwota) FROM Klienci INNER JOIN NaliczoneNaleznosci ON Klienci.Id=NaliczoneNaleznosci.IdKlienta GROUP BY Klienci.Nazwisko;";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getString("Imie") +  "\t" +
                        rs.getString("Nazwisko") + "\t" +
                        rs.getDouble("SUM(NaliczoneNaleznosci.Kwota)"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getWplaty(){
        String sql = "SELECT DokonaneWplaty.IdWplaty, Klienci.Nazwisko,Klienci.Imie, DokonaneWplaty.Kwota, DokonaneWplaty.TerminWplaty FROM Klienci INNER JOIN DokonaneWplaty ON Klienci.Id=DokonaneWplaty.IdKlienta;";
        System.out.println("Id wplaty" + "\t" + "Imie" +  "\t" +"Nazwisko" + "\t" +"Kwota" + "\t" + "TerminWplaty");

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getInt("IdWplaty") + "\t\t" +
                        rs.getString("Imie") +  "\t" +
                        rs.getString("Nazwisko") + "\t" +
                        rs.getDouble("Kwota") + "\t" +
                        rs.getString("TerminWplaty"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addWplata(int rachunek, double kwota){

        String time = getDateAndTime();
        int id = 0;
        double doOplacenia = 0;
        int oplacone = 0;

        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("SELECT IdKlienta,Kwota,Oplacone FROM NaliczoneNaleznosci WHERE IdRachunku=?;");
            stmt.setInt(1, rachunek);
            ResultSet rs    = stmt.executeQuery();{

                while (rs.next()) {
                    id = rs.getInt("IdKlienta");
                    doOplacenia = rs.getDouble("Kwota");
                    oplacone = rs.getInt("Oplacone");
                }
            }

            stmt  = conn.prepareStatement("INSERT INTO DokonaneWplaty(TerminWplaty,Kwota,IdKlienta,IdRachunku) VALUES (?,?,?,?);");
            stmt.setString(1, time);
            stmt.setDouble(2, kwota);
            stmt.setInt(3, id);
            stmt.setInt(4, rachunek);
            stmt.execute();

            if(doOplacenia<=kwota && oplacone==0)
            {
                oplacone = 1;
                stmt  = conn.prepareStatement("UPDATE NaliczoneNaleznosci SET Oplacone = ? WHERE IdRachunku=?;");
                stmt.setInt(1, oplacone);
                stmt.setInt(2, rachunek);
                stmt.execute();
            }

            System.out.println("Zarejestrowano wplate!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNaleznosc(int idklienta, double kwota, String termin){

        try {
            Connection conn = connect();

            PreparedStatement stmt  = conn.prepareStatement("INSERT INTO NaliczoneNaleznosci(IdKlienta,Kwota,TerminPlatnosci, Oplacone) VALUES (?,?,?,0);");
            stmt.setInt(1, idklienta);
            stmt.setDouble(2, kwota);
            stmt.setString(3, termin);
            stmt.execute();

            System.out.println("Zarejestrowano naleznosc!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateWplata(int idwplaty, double kwota, String time){

        try{
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("UPDATE DokonaneWplaty SET Kwota = ?, TerminWplaty = ? WHERE IdWplaty = ?;");
            stmt.setDouble(1, kwota);
            stmt.setString(2, time);
            stmt.setInt(3, idwplaty);
            stmt.execute();

        System.out.println("Zaktualizowano wplate!");
    }
        catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }

    public static void updateNaleznosc(double kwota, String time, int idrachunku, int oplacone){
        try{
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("UPDATE NaliczoneNaleznosci SET Kwota = ?, TerminPlatnosci = ?, Oplacone=? WHERE IdRachunku = ?;");
            stmt.setDouble(1, kwota);
            stmt.setString(2, time);
            stmt.setInt(3, oplacone);
            stmt.setInt(4, idrachunku);
            stmt.execute();

            System.out.println("Zaktualizowano naleznosc!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectCeny(){
        String sql = "SELECT * FROM Cennik";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getString("TypUslugi") + "\t" +
                        rs.getDouble("Cena"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCena(String typ, double cena)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("INSERT INTO Cennik (TypUslugi,Cena) VALUES (?,?);");
            stmt.setString(1, typ);
            stmt.setDouble(2, cena);
            stmt.execute();
            System.out.println("Dodano!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteCena(String typ)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("UPDATE Cennik SET Cena=-1 WHERE TypUslugi=?");
            stmt.setString(1, typ);
            stmt.execute();
            System.out.println("Usunieto cene uslugi!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void alterCena(String typ, double cena)
    {
        try {
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement("UPDATE Cennik SET Cena=? WHERE TypUslugi=?");
            stmt.setDouble(1, cena);
            stmt.setString(2, typ);
            stmt.execute();
            System.out.println("Zmieniono cene!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
