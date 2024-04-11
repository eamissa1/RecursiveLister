import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecursiveListerGUI extends JFrame
{
    private JTextArea textArea;

    public RecursiveListerGUI()
    {
        setTitle("Recursive Lister");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // GUI Components
        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JLabel titleLabel = new JLabel("Recursive Directory Lister", SwingConstants.CENTER);

        startButton.addActionListener(this::startButtonAction);
        quitButton.addActionListener(e -> System.exit(0));

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(startButton);
        topPanel.add(quitButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void startButtonAction(ActionEvent e)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            textArea.setText("");
            listFiles(chooser.getSelectedFile().toPath());
        }
    }

    private void listFiles(Path path)
    {
        try
        {
            Files.walk(path)
                    .forEach(p -> textArea.append(p.toString() + "\n"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading directory.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
