import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OknoMain extends JFrame {
    private JButton predchoziButton;
    private JButton dalsiButton;
    private JTextField txt2;
    private JTextField txt1;
    private JCheckBox checkBox1;
    private JTextField txt3;
    private JPanel panelMain;
    private File selectedFile;
    private int currentIndex = 0;

    public OknoMain(){
        setSize(300,200);
        setTitle("Menu");
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenu();

        predchoziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateFields();
                }
            }
        });

        dalsiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < 2) { // Předpokládám, že v souboru je 3 řádky
                    currentIndex++;
                    updateFields();
                }
            }
        });

        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });

        updateFields();
    }

    public void chooser() {
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            currentIndex = 0; // Resetujeme index při načtení nového souboru
            updateFields();
        }
    }

    public void updateFields() {
        if (selectedFile != null) {
            try (Scanner sc = new Scanner(selectedFile)) {
                for (int i = 0; i < currentIndex; i++) {
                    sc.nextLine(); // Přeskočíme řádky, které již byly zpracovány
                }
                if (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] values = line.split(":");
                    if (values.length == 4) {
                        String jmeno = values[0];
                        String prijmeni = values[1];
                        boolean pojisteni = Boolean.parseBoolean(values[2]);
                        String datum = values[3];
                        txt1.setText(jmeno);
                        txt2.setText(prijmeni);
                        checkBox1.setSelected(pojisteni);
                        txt3.setText(datum);
                    } else {
                        JOptionPane.showMessageDialog(this, "Špatný formát vstupních dat.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Konec souboru.");
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Soubor nebyl nalezen: " + e.getLocalizedMessage());
            }
        }
    }

    public void initMenu(){
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu soubor = new JMenu("Soubor");
        menubar.add(soubor);

        JMenuItem nacti = new JMenuItem("Načti");
        soubor.add(nacti);
        nacti.addActionListener(e->{
            chooser();
        });
    }

    public static void main(String[] args) {
        OknoMain main = new OknoMain();
        main.setVisible(true);
    }
}