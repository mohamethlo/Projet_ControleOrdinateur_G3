import java.awt.Dimension;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur 
{
    private ServerSocket serverSocket;
    private boolean estEnCours = true;
    private ExecutorService pool = Executors.newFixedThreadPool(10);
    private ServeurGUI gui;

    // Contructeur
    public Serveur(ServeurGUI gui) 
    {
        this.gui = gui;
    }

    // Methode qui permet de demarrer le serveur
    public void demarrerServeur() 
    {
        try 
        {
            serverSocket = new ServerSocket(1234);
            gui.logMessage("Serveur en attente de connexions...");

            while (estEnCours) 
            {
                Socket clientSocket = serverSocket.accept();
                if (!estEnCours) break;

                String clientAddress = clientSocket.getInetAddress().toString();
                gui.logMessage("Nouveau client connecté : " + clientAddress);

                pool.execute(new ConversationClient(clientSocket, clientAddress, gui, this));
            }
        } 
        catch (IOException e) 
        {
            gui.logMessage("Serveur arrêté.");
        }
    }

    // Methode qui permet d'arreter le serveur
    public void stopServer() 
    {
        estEnCours = false;
        try 
        {
            serverSocket.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        System.exit(0);
    }

    // Methode qui permet d'afficher les logs
    public void AfficherLogs() 
    {
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("commands.log"));
            JTextArea textArea = new JTextArea();
            textArea.read(reader, "commands.log");
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));

            JOptionPane.showMessageDialog(null, scrollPane, "Logs des commandes", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, "Impossible de lire les logs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Methode qui permet d'ajouter les logs 
    public void ajoutLogs(String ip, String cmd, String result) 
    {
        try (FileWriter writer = new FileWriter("commands.log", true)) 
        {
            writer.write(ip + " | " + cmd + " | " + result + "\n");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    // Methode qui permet de verifier les informations envoyer par le client
    public boolean verifierAuthentification(String username, String password) 
    {
        ConnectionBD db = new ConnectionBD();
        return db.authentierClient(username, password);
    }
}
