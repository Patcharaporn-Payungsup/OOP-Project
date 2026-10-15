import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EnrollPage extends JFrame implements ActionListener {
    private JTextField ownerTxt;
    private JTextField plateTxt;
    private JComboBox<String> severityCombo;
    private JComboBox<String> typeCombo;

    private JLabel partNameLbl;
    private JTextField partNameTxt;
    private JLabel partCostLbl;
    private JTextField partCostTxt;

    private JTextArea recordListTxtArea;
    private JButton addBtn;
    private JButton backMainBtn;

    public EnrollPage() {
        super("Auto Garage Repair Intake");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(550, 580);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Add Repair Job", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));

        JLabel ownerLbl = new JLabel("Owner:");
        JLabel plateLbl = new JLabel("Car Plate:");
        JLabel severityLbl = new JLabel("Severity:");
        JLabel typeLbl = new JLabel("Repair Type:");

        ownerTxt = new JTextField();
        plateTxt = new JTextField();
        severityCombo = new JComboBox<>(new String[]{"Light", "Medium", "Heavy"});
        
        typeCombo = new JComboBox<>(new String[]{
            "Engine Repair", 
            "Paint Repair"
        });

        partNameLbl = new JLabel("Part Name:");
        partNameTxt = new JTextField();
        partCostLbl = new JLabel("Parts Cost (ฺB.):");
        partCostTxt = new JTextField("0.0");

        typeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleEngineFields();
            }
        });

        recordListTxtArea = new JTextArea(8, 40);
        recordListTxtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordListTxtArea);

        addBtn = new JButton("Add Repair Job");
        addBtn.addActionListener(this);
        backMainBtn = new JButton("Back to Main Page");
        backMainBtn.addActionListener(this);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        formPanel.add(ownerLbl); 
        formPanel.add(ownerTxt);
        
        formPanel.add(plateLbl); 
        formPanel.add(plateTxt);
        
        formPanel.add(severityLbl); 
        formPanel.add(severityCombo);
        
        formPanel.add(typeLbl); 
        formPanel.add(typeCombo);
        
        formPanel.add(partNameLbl); 
        formPanel.add(partNameTxt);
        
        formPanel.add(partCostLbl); 
        formPanel.add(partCostTxt);

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(backMainBtn);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(btnPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        loadExistingRecords();
        toggleEngineFields();

        setVisible(true);
    }

    private void toggleEngineFields() {
        boolean isEngine = typeCombo.getSelectedIndex() == 0;
        partNameLbl.setVisible(isEngine);
        partNameTxt.setVisible(isEngine);
        partCostLbl.setVisible(isEngine);
        partCostTxt.setVisible(isEngine);
    }

    private void loadExistingRecords() {
        recordListTxtArea.setText("=== System Repair Records ===\n");
        for (RepairRecord r : Database.repairs) {
            recordListTxtArea.append(r.toString());
        }
    }

    private void clearTextField() {
        ownerTxt.setText("");
        plateTxt.setText("");
        partNameTxt.setText("");
        partCostTxt.setText("0.0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Add Repair Job")) {
            try {
                String owner = ownerTxt.getText().trim();
                String plate = plateTxt.getText().trim();
                String severity = (String) severityCombo.getSelectedItem();

                if (owner.isEmpty() || plate.isEmpty()) {
                    throw new IllegalArgumentException("Please enter the owner and plate");
                }

                int jobId = Database.nextJobId++;
                RepairRecord record;

                if (typeCombo.getSelectedIndex() == 0) {
                    String partName = partNameTxt.getText().trim();
                    if (partName.isEmpty()) {
                        partName = "-";
                    }
                    double partCost = Double.parseDouble(partCostTxt.getText().trim());
                    record = new EngineRepairJob(jobId, owner, plate, severity, partName, partCost);
                } else {
                    record = new PaintRepairJob(jobId, owner, plate, severity, 0);
                }

                Database.repairs.add(record);
                recordListTxtArea.append(record.toString());

                JOptionPane.showMessageDialog(this, "Recorded successfully: " + record.getJobCode());
                clearTextField();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter invalid parts cost",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "ข้อผิดพลาด", JOptionPane.WARNING_MESSAGE);
            }

        } else if (cmd.equals("Back to Main Page")) {
            dispose();
        }
    }
}