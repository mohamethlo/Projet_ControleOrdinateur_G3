import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientConnection 
{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // Constructeur
    public ClientConnection(String serverAddress, int port) 
    {
        try 
        {
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, "Erreur de connexion au serveur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // Methode qui permet d'envoyer des messages
    public void send(String message) 
    {
        out.println(message);
    }

    // Methode qui permet de recevoir les messages 
    public String receive() throws IOException 
    {
        return in.readLine();
    }

    // Methode qui permet de se doconnecter
    public void disconnect() 
    {
        try 
        {
            send("exit");
            out.close();
            in.close();
            socket.close();
            System.exit(0);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    // Getters
    public BufferedReader getReader() 
    {
        return in;
    }

    public PrintWriter getWriter() 
    {
        return out;
    }
}
