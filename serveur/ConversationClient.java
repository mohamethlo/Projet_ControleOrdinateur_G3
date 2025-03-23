import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.SwingUtilities;

public class ConversationClient implements Runnable 
{
    private Socket socket;
    private String clientIP;
    private ServeurGUI gui;
    private Serveur serveur;

    // Constructeur
    public ConversationClient(Socket socket, String clientIP, ServeurGUI gui, Serveur serveur) 
    {
        this.socket = socket;
        this.clientIP = clientIP;
        this.gui = gui;
        this.serveur = serveur;
    }

    @Override
    public void run() 
    {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
        {

            if (!authentifierClient(in, out)) 
            {
                out.println("Authentification échouée. Connexion refusée.");
                socket.close();
                return;
            }

            out.println("Authentification réussie !");

            String command;
            while ((command = in.readLine()) != null) 
            {
                if (command.equalsIgnoreCase("exit")) break;

                String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

                final String ip = clientIP;
                final String cmd = command;
                final String time = timestamp;

                String result = executerCommande(command);
                out.println(result);
                out.println("__END__");

                SwingUtilities.invokeLater(() -> gui.getTableModel().addRow(new Object[]{ip, cmd, time}));
                serveur.ajoutLogs(ip, cmd, result);
            }

        } 
        catch (IOException e) 
        {
            gui.logMessage("Erreur de communication avec un client.");
        } 
        finally 
        {
            try 
            {
                socket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    // Methode qui permet de determiner les commandes interdites
    private boolean estInterdit(String command) 
    {
        String[] forbiddenCommands = {"rm -rf", "shutdown", "reboot", "del", "poweroff"};
        for (String forbidden : forbiddenCommands) 
        {
            if (command.contains(forbidden)) 
                return false;
        }
        return true;
    }

    // Methode qui permet d'executer les commandes envoyees par le client
    private String executerCommande(String command) 
    {
        if (!estInterdit(command)) 
            return "Commande interdite pour des raisons de sécurité.";

        StringBuilder output = new StringBuilder();
        try 
        {
            ProcessBuilder builder = new ProcessBuilder(System.getProperty("os.name").toLowerCase().contains("win") ?
                    new String[]{"cmd.exe", "/c", command} :
                    new String[]{"/bin/sh", "-c", command});

            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) 
            {
                String line;
                while ((line = reader.readLine()) != null) 
                {
                    output.append(line).append("\n");
                }
            }
            process.waitFor();
        } 
        catch (Exception e) 
        {
            output.append("Erreur : ").append(e.getMessage());
        }
        return output.toString();
    }

    // Methode qui permet d'envoyer des instructions d'authentification au client
    private boolean authentifierClient(BufferedReader in, PrintWriter out) throws IOException 
    {
        out.println("Veuillez entrer votre nom d'utilisateur :");
        out.flush();
        String username = in.readLine();

        out.println("Veuillez entrer votre mot de passe :");
        out.flush();
        String password = in.readLine();

        boolean authSuccess = serveur.verifierAuthentification(username, password);
        out.println(authSuccess ? "Authentification réussie !" : "Authentification échouée !");
        out.flush();

        return authSuccess;
    }
}
