import javax.swing.*;
import java.io.*;

public class CommandHandler {
    private ClientConnection clientConnection;
    private JTextArea outputArea;

    public CommandHandler(ClientConnection clientConnection, JTextArea outputArea) {
        this.clientConnection = clientConnection;
        this.outputArea = outputArea;
    }

    public void sendCommand(JTextField commandField) {
        String command = commandField.getText();
        if (!command.isEmpty()) {
            clientConnection.send(command);
            commandField.setText("");
            try {
                outputArea.append("> " + command + "\n");
                String response;
                while ((response = clientConnection.receive()) != null) {
                    if (response.equals("__END__")) break;
                    outputArea.append(response + "\n");
                }
            } catch (IOException e) {
                outputArea.append("Erreur lors de la communication avec le serveur.\n");
            }
        }
    }
}
