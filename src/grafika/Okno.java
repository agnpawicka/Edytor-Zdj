package grafika;

import javax.swing.*;
import java.awt.*;

public class Okno extends JFrame {
    private PasekNarzędzi pasek;
    private JScrollPane suwaki;
    private JScrollBar vertykalny;
    private JScrollBar horyzontalny;
    private ZdjPanel panel;
    private JLabel kursor;
    private JPanel panelPomocniczy;
    public Okno(String tytuł){
        setTitle(tytuł);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panelPomocniczy=new JPanel();
        panelPomocniczy.setPreferredSize(new Dimension(600, 600));
        add(panelPomocniczy, BorderLayout.CENTER);
        pasek=new PasekNarzędzi(this);
        panelPomocniczy.add(pasek, BorderLayout.NORTH);

        panel=new ZdjPanel(this, pasek);
        suwaki=new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        suwaki.setBounds(0, 800, 400, 400);
        suwaki.setPreferredSize(new Dimension(600, 450));
        panelPomocniczy.add(suwaki, BorderLayout.CENTER);
        vertykalny = suwaki.getVerticalScrollBar();
        horyzontalny = suwaki.getHorizontalScrollBar();

        vertykalny.addAdjustmentListener(e -> {
            Graphics2D g = (Graphics2D) panel.getGraphics();
            g.drawImage(panel.getZdjęcie(),0,0,panel);
        });

        horyzontalny.addAdjustmentListener(e -> {
            Graphics2D g = (Graphics2D) panel.getGraphics();
            g.drawImage(panel.getZdjęcie(), 0, 0, panel);
        });


        kursor=new JLabel();
        kursor.setPreferredSize(new Dimension(600, 50));
        kursor.setText("Współrzędna x: "+ MouseInfo.getPointerInfo().getLocation().getX()+"     Współrzędna y: "+MouseInfo.getPointerInfo().getLocation().getY());
        panelPomocniczy.add(kursor, BorderLayout.SOUTH);



        pack();
        setVisible(true);

    }

    public void pozycjaMyszy(PointerInfo pi){
        kursor.setText("Współrzędna x: "+ pi.getLocation().getX()+"     Współrzędna y: "+pi.getLocation().getY());
    }
    public JScrollPane getSuwaki(){
        return suwaki;
    }

    public void setVertykalny(int ver) {
        this.vertykalny.setValue(ver);
    }
    public void setHoryzontalny(int hor){
        this.horyzontalny.setValue(hor);
    }
    public JScrollBar getVertykalny(){
        return vertykalny;
    }
    public JScrollBar getHoryzontalny(){
        return horyzontalny;
    }
}
