import javax.swing.SwingUtilities;

public class TestClient 
{
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new ClientGUI().setVisible(true));
    }    
}
