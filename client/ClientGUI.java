import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame 
{
    private JTextField commandField;
    private JTextArea outputArea;
    private JButton sendButton, quitButton;
    private ClientConnection clientConnection;
    private CommandHandler commandHandler;

    public ClientGUI() 
    {
        setTitle("Machine Client");
        setSize(500, 700);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 650, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();

        clientConnection = new ClientConnection("127.0.0.1", 1234);
        commandHandler = new CommandHandler(clientConnection, outputArea);

        if (!AuthenticationManager.authenticate(clientConnection)) 
        {
            System.exit(0);
        }

        sendButton.addActionListener(e -> commandHandler.sendCommand(commandField));
        commandField.addActionListener(e -> commandHandler.sendCommand(commandField));
        quitButton.addActionListener(e -> clientConnection.disconnect());
    }

    private void initUI() 
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 150, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(0, 150, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        commandField = new JTextField();
        sendButton = createStyledButton("Envoyer", new Color(0, 200, 0));
        quitButton = createStyledButton("Quitter", Color.RED);
        quitButton.setPreferredSize(new Dimension(100, 30));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(Color.DARK_GRAY);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(commandField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        JPanel quitPanel = new JPanel();
        quitPanel.setBackground(new Color(200, 255, 200));
        quitPanel.add(quitButton);

        contentPanel.add(panel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        contentPanel.add(quitPanel, BorderLayout.SOUTH);

        innerPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(innerPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color bgColor) 
    {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

}
