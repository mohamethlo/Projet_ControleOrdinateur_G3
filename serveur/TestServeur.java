import javax.swing.SwingUtilities;

public class TestServeur 
{
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new ServeurGUI().setVisible(true));
    }    
}
