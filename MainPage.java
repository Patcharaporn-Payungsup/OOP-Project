import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPage extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel titleLabel;
    private JButton enrollButton;
    private JButton searchButton;
    private JButton exitButton;

    public MainPage() {
        setTitle("Auto Garage Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 360);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Garage Main Menu", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));

        enrollButton = new JButton("Repair");
        enrollButton.addActionListener(this);
        searchButton = new JButton("Search Records");
        searchButton.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(enrollButton);
        panel.add(searchButton);
        panel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Repair")) {
            new EnrollPage();
        } else if (cmd.equals("Search Records")) {
            new SearchPage();
        } else if (cmd.equals("Exit")) {
            int option = JOptionPane.showConfirmDialog(this, 
                    "Do you want to exit the program?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}