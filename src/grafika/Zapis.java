package grafika;

import javax.swing.*;
import java.awt.*;

public class Zapis extends JFrame {
    public int rozszerzenie = 0;
    private JButton png;
    private JButton jpg;
    private JButton gif;
    private JTextField nameField;
    public Zapis(PasekNarzędzi pasek) {

        setTitle("zapis");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameField = new JTextField("nazwa");

        png = new JButton("png");
        jpg = new JButton("jpg");
        gif = new JButton("gif");
        png.addActionListener(e -> {
            rozszerzenie = 0;
            dispose();
            pasek.ZapiszZdjęcie(getText());
        });
        jpg.addActionListener(e -> {
            rozszerzenie = 1;
            dispose();
            pasek.ZapiszZdjęcie(getText());
        });
        gif.addActionListener(e -> {
            rozszerzenie = 2;
            dispose();
            pasek.ZapiszZdjęcie(getText());
        });
        add(png, BorderLayout.LINE_START);
        add(jpg, BorderLayout.CENTER);
        add(gif, BorderLayout.LINE_END);
        add(nameField, BorderLayout.NORTH);
        setVisible(true);
    }

    public String getText() {
        return nameField.getText();
    }
}
