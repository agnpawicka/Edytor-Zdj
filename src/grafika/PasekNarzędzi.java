package grafika;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

public class PasekNarzędzi extends JToolBar implements ActionListener {
    private JButton powiększenie;
    private JButton zmiejszenie;
    private JButton innyKolor;
    private JButton wybierzZdjęcie;
    private JButton zapisz;
    private JButton[] koloryPodstawowe=new JButton[16];
    private Color[] koloryRGB=new Color[16];
    private JButton right;
    private JButton left;
    private JButton down;
    private JButton up;
    private Okno oknogłówne;
    private ZdjPanel panel;
    private Zapis zapis;
    public PasekNarzędzi(Okno okno) {
        oknogłówne=okno;
        setLayout(new GridLayout(2, 13));
        setRollover(true);
        setPreferredSize(new Dimension(600, 100));


        powiększenie = new JButton(new ImageIcon("src/grafika/size++.png"));
        zmiejszenie = new JButton(new ImageIcon("src/grafika/size--.png"));
        innyKolor = new JButton(new ImageIcon("src/grafika/kolory.png"));
        wybierzZdjęcie=new JButton(new ImageIcon("src/grafika/nowy.png"));
        zapisz=new JButton(new ImageIcon("src/grafika/zapisz.png"));
        right=new JButton(">");
        left=new JButton("<");
        up=new JButton("^");
        down=new JButton("v");
        powiększenie.addActionListener(this);
        zmiejszenie.addActionListener(this);
        innyKolor.addActionListener(this);
        wybierzZdjęcie.addActionListener(this);
        zapisz.addActionListener(this);
        add(powiększenie);
        add(zmiejszenie);
        add(innyKolor);
        add(wybierzZdjęcie);
        add(zapisz);
        add(left);
        add(right);
        add(down);
        add(up);
        right.addActionListener(e -> oknogłówne.setHoryzontalny(oknogłówne.getHoryzontalny().getMaximum()));
        left.addActionListener(e -> oknogłówne.setHoryzontalny(0));
        up.addActionListener(e -> oknogłówne.setVertykalny(0));
        down.addActionListener(e -> oknogłówne.setVertykalny((oknogłówne.getVertykalny()).getMaximum()));
        koloryRGB[0]=new Color(0, 0, 0);
        koloryRGB[1]=new Color(52, 52, 52);
        koloryRGB[2]=new Color(157, 168, 180);
        koloryRGB[3]=new Color(255, 255, 255);
        koloryRGB[4]=new Color(204, 199, 37);
        koloryRGB[5]=new Color(188, 122, 32);
        koloryRGB[6]=new Color(168, 29, 24);
        koloryRGB[7]=new Color(159, 46, 91);
        koloryRGB[8]=new Color(226, 73, 219);
        koloryRGB[9]=new Color(139, 57, 218);
        koloryRGB[10]=new Color(94, 56, 238);
        koloryRGB[10]=new Color(94, 56, 238);
        koloryRGB[11]=new Color(30, 64, 189);
        koloryRGB[12]=new Color(34, 161, 190);
        koloryRGB[13]=new Color(39, 241, 156);
        koloryRGB[14]=new Color(23, 174, 28);
        koloryRGB[15]=new Color(99, 168, 10);
        for(int i=0; i<16; i++) {
            koloryPodstawowe[i] = new JButton();
            koloryPodstawowe[i].setBackground(koloryRGB[i]);
            add(koloryPodstawowe[i]);
            koloryPodstawowe[i].addActionListener(this);
        }
    }
    public void setPanel(ZdjPanel panel){
        this.panel=panel;
    }

    @Override

    public void actionPerformed(ActionEvent actionEvent) {
       Object kliknięty=actionEvent.getSource();
       if(kliknięty==powiększenie){
           if(panel.zoom<8.1) {
               panel.zoom *= 2;
               //setText("panel.zoom: x" + panel.zoom_x);
               panel.setPreferredSize(new Dimension(panel.getWidth() * 2, panel.getHeight() * 2));
               panel.setSize(panel.getWidth() * 2, panel.getHeight() * 2);
               BufferedImage img = panel.getZdjęcie();
               img = resize(img, img.getWidth() * 2, img.getHeight() * 2);
               panel.setZdjęcie(img);
               Graphics2D g = (Graphics2D) panel.getGraphics();
               g.drawImage(img, 0, 0, panel);
           }
       }
       else if(kliknięty==zmiejszenie){
           if(panel.zoom>1.1){
               panel.zoom/=2;
               //setText("panel.zoom: x" + panel.zoom_x);
               panel.setPreferredSize(new Dimension(panel.getWidth()/2,panel.getHeight()/2));
               panel.setSize(panel.getWidth()/2,panel.getHeight()/2);
               BufferedImage img=panel.getZdjęcie();
               img=resize(img, img.getWidth()/2,img.getHeight()/2);
               panel.setZdjęcie(img);

               Graphics2D g =  (Graphics2D) panel.getGraphics();
               g.drawImage(img,0,0, panel);
           }
           //System.out.println("--");
       }
        else if(kliknięty==innyKolor){
           Color initialcolor=Color.GREEN;
           Color kolor=JColorChooser.showDialog(this,"Select a color",initialcolor);
           panel.ustawKolor(kolor);
       }
       else if(kliknięty==zapisz){


         zapis=new Zapis(this);

       }
       else if(kliknięty==wybierzZdjęcie){
           JFileChooser fc=new JFileChooser();
          fc.setDialogTitle("Please choose an image...");
           FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
           fc.addChoosableFileFilter(filter);

           if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
               File f = fc.getSelectedFile();
               try {
                   BufferedImage zdj = ImageIO.read(f);
                   panel.setPreferredSize(new Dimension(zdj.getWidth(),zdj.getHeight()));
                   Graphics2D g = (Graphics2D) panel.getGraphics();
                   g.drawImage(zdj,0,0,panel);
                   panel.zoom=1;
                   panel.setZdjęcie(zdj);
                   oknogłówne.getSuwaki().updateUI();
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
           }

       }
       else {
            for(int i=0; i<16; i++){
                if(kliknięty==koloryPodstawowe[i]){
                    panel.ustawKolor(koloryRGB[i]);
                    //System.out.println("bgiqbi"+i);
                    break;
                }
            }
       }
    }


    private BufferedImage resize(BufferedImage zdj, int wys, int szer) {
        BufferedImage resizedImage = new BufferedImage(szer, wys, zdj.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(zdj, 0, 0, szer, wys, null);
        g.dispose();
        return  resizedImage;
    }

    public void ZapiszZdjęcie(String nazwa){
        //System.out.println(nazwa);
        String format;
        switch (zapis.rozszerzenie){
            case 0:
                nazwa+=".png";
                format="png";
                break;
            case 1:
                nazwa+=".jpg";
                format="jpg";
                break;
            default:
                nazwa+=".gif";
                format="gif";
                break;
        }

        RenderedImage zdj=panel.getZdjęcie();
        try {
            File plik = new File(nazwa);
            boolean fvar =plik.createNewFile();
            //System.out.println(fvar);
            if(fvar)ImageIO.write(zdj, format, plik);
            else{
                ImageIO.write(zdj, "png", new File("poprawionezdjęcie.png"));
                System.out.println("nie udało się utworzyć pliku, zdjęcie zapisano w pliku poprawionezdjęcie.png");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
