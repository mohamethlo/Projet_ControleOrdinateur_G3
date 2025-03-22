import java.sql.*;

public class ConnectionBD 
{
    private final String url = "jdbc:mysql://localhost:3306/ordinateur_distant";

    // Methode qui permet d'authentifier les clients
    public boolean authentierClient(String username, String password) 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM clients WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            boolean isValid = rs.next();

            rs.close();
            stmt.close();
            conn.close();
            return isValid;
        } 
        catch (ClassNotFoundException e) 
        {
            System.err.println("Driver JDBC non trouvé !");
        } 
        catch (SQLException e) 
        {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return false;
    }
}
