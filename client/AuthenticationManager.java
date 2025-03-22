import javax.swing.*;
import java.io.*;

public class AuthenticationManager 
{
    public static boolean authenticate(ClientConnection clientConnection) 
    {
        try 
        {
            BufferedReader in = clientConnection.getReader();
            PrintWriter out = clientConnection.getWriter();

            System.out.println("📥 Message serveur : " + in.readLine());
            String username = JOptionPane.showInputDialog(null, "Nom d'utilisateur :", "Authentification", JOptionPane.PLAIN_MESSAGE);
            if (username == null) return false;

            out.println(username);
            out.flush();

            System.out.println("📥 Message serveur : " + in.readLine());
            String password = JOptionPane.showInputDialog(null, "Mot de passe :", "Authentification", JOptionPane.PLAIN_MESSAGE);
            if (password == null) return false;

            out.println(password);
            out.flush();

            String response = in.readLine();
            System.out.println("📥 Réponse du serveur : " + response);
            if ("Authentification réussie !".equals(response)) 
            {
                return true;
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "Échec de l'authentification.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, "Erreur de communication avec le serveur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}

