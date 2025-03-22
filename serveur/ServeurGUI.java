import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ServeurGUI extends JFrame 
{
    private JTextArea logArea;
    private DefaultTableModel tableModel;
    private JTable clientsTable;
    private Serveur serveur;

    // Construction
    public ServeurGUI() 
    {
        serveur = new Serveur(this);
        initUI();
        new Thread(() -> serveur.demarrerServeur()).start();
    }

    // Getter qui permet de recuperer le tableau
    public DefaultTableModel getTableModel() 
    {
        return tableModel;
    }

    // Methode qui permet de d'initialiser les composants
    private void initUI() 
    {
        // Initialisation de la fenetre
        setTitle("Machine Serveur");
        setSize(800, 700);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Premiere backgroud
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 150, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Deuxieme backgroud
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dernier panel qui contient les toutes les composantes
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(0, 150, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Zone de texte qui permet de stocker les clients connecter
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(Color.DARK_GRAY);
        logArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Tableau qui permet de stocker les historiques des commandes avec les adresse ip et l'heure d'execution
        String[] columnNames = {"Adresse IP", "Commande", "Date & Heure"};
        tableModel = new DefaultTableModel(columnNames, 0);
        clientsTable = new JTable(tableModel);
        clientsTable.setBackground(new Color(200, 255, 200));
        clientsTable.setForeground(Color.BLACK);
        clientsTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JTableHeader header = clientsTable.getTableHeader();
        header.setBackground(new Color(0, 100, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Coloration des lignes du tableau
        clientsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() 
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) 
                {
                    cell.setBackground(Color.WHITE);
                } 
                else 
                {
                    cell.setBackground(new Color(220, 255, 220));
                }
                return cell;
            }
        });

        contentPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        contentPanel.add(new JScrollPane(clientsTable), BorderLayout.EAST);

        // Mis en place du bouton qui permet d'arreter le serveur
        JButton stopButton = new JButton("Arreter le serveur");
        stopButton.setBackground(Color.RED);
        stopButton.setForeground(Color.WHITE);
        stopButton.setFont(new Font("Arial", Font.BOLD, 12));
        stopButton.setPreferredSize(new Dimension(150, 30));
        stopButton.addActionListener(e -> serveur.stopServer());

        // Mis en place du bouton qui permet d'afficher les logs
        JButton logsButton = new JButton("Afficher les logs");
        logsButton.setBackground(new Color(0, 100, 200));
        logsButton.setForeground(Color.WHITE);
        logsButton.setFont(new Font("Arial", Font.BOLD, 12));
        logsButton.setPreferredSize(new Dimension(150, 30));
        logsButton.addActionListener(e -> serveur.AfficherLogs());

        // Ajout des deux boutons dans le panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 255, 200));
        buttonPanel.add(stopButton);
        buttonPanel.add(logsButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        innerPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(innerPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void logMessage(String message) 
    {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }
}
