import javax.swing.*;

public class Screen extends JFrame
{
    private Logic panel;
    public Screen()
    {
        this.setTitle("Gameish");
        this.setBounds(0,0,512,512);
        panel = new Logic();
        panel.setBounds(0,0,512,512);
        this.add(panel);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        panel.start();
    }
}
