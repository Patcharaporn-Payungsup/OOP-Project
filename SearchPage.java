import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SearchPage extends JFrame implements ActionListener {
    private JTextField jobCodeTxt;
    private JTextField plateTxt;
    private JTextField partNameTxt;
    private JTextArea searchResults;
    private JButton searchBtn;
    private JButton backMainBtn;

    public SearchPage() {
        super("Garage Repair Search Module");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(550, 550);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Search Module", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        JLabel jobCodeLbl = new JLabel("Job Code contains:");
        JLabel plateLbl = new JLabel("Car Plate contains:");
        JLabel partNameLbl = new JLabel("Part Name contains:");

        jobCodeTxt = new JTextField(20);
        plateTxt = new JTextField(20);
        partNameTxt = new JTextField(20);

        searchResults = new JTextArea(12, 45);
        searchResults.setEditable(false);
        searchResults.append("Results:\n");
        JScrollPane scrollPane = new JScrollPane(searchResults);

        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        backMainBtn = new JButton("Back to Main Page");
        backMainBtn.addActionListener(this);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        formPanel.add(jobCodeLbl); formPanel.add(jobCodeTxt);
        formPanel.add(plateLbl); formPanel.add(plateTxt);
        formPanel.add(partNameLbl); formPanel.add(partNameTxt);

        JPanel btnPanel = new JPanel();
        btnPanel.add(searchBtn);
        btnPanel.add(backMainBtn);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(btnPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private List<RepairRecord> searchByJobCode(List<RepairRecord> list, String code) {
        List<RepairRecord> results = new ArrayList<>();
        for (RepairRecord r : list) {
            if (r.getJobCode().toLowerCase().contains(code.toLowerCase().trim())) {
                results.add(r);
            }
        }
        return results;
    }

    private List<RepairRecord> searchByPlate(List<RepairRecord> list, String plate) {
        List<RepairRecord> results = new ArrayList<>();
        for (RepairRecord r : list) {
            if (r instanceof CarRepairJob) {
                if (((CarRepairJob) r).getCarPlate().toLowerCase().contains(plate.toLowerCase().trim())) {
                    results.add(r);
                }
            }
        }
        return results;
    }

    private List<RepairRecord> searchByPartName(List<RepairRecord> list, String partSubstr) {
        List<RepairRecord> results = new ArrayList<>();
        for (RepairRecord r : list) {
            if (r instanceof EngineRepairJob) {
                EngineRepairJob ej = (EngineRepairJob) r;
                if (ej.getPartName().toLowerCase().contains(partSubstr.toLowerCase().trim())) {
                    results.add(r);
                }
            }
        }
        return results;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Search")) {
            List<RepairRecord> records = Database.repairs;

            if (!jobCodeTxt.getText().trim().isEmpty()) {
                records = searchByJobCode(records, jobCodeTxt.getText().trim());
            }

            if (!plateTxt.getText().trim().isEmpty()) {
                records = searchByPlate(records, plateTxt.getText().trim());
            }

            if (!partNameTxt.getText().trim().isEmpty()) {
                records = searchByPartName(records, partNameTxt.getText().trim());
            }

            searchResults.setText("");
            searchResults.append("Results:\n");
            if (records.isEmpty()) {
                searchResults.append("Not Found\n");
            } else {
                for (RepairRecord r : records) {
                    searchResults.append(r.toString());
                }
            }
        } else if (cmd.equals("Back to Main Page")) {
            dispose();
        }
    }
}