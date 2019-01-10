package grafika;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ZdjPanel extends JPanel implements MouseMotionListener, MouseListener {
    private BufferedImage zdjęcie;
    private PointerInfo pi;
    private Okno oknogłówne;
    private Color kolor1 = Color.BLACK;
    private Color kolor2=Color.RED;
    public int zoom=1;
    private PasekNarzędzi pasek;

    public ZdjPanel(Okno okno, PasekNarzędzi pasek) {
        this.pasek=pasek;
        pasek.setPanel(this);
        oknogłówne = okno;
        addMouseMotionListener(this);
        setBounds(0, 100, 600, 400);
        try {
            zdjęcie = ImageIO.read(new File("src/grafika/tło.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(zdjęcie, 0, 0, this);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseMoved(mouseEvent);
        narysuj(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        pi = MouseInfo.getPointerInfo();
        oknogłówne.pozycjaMyszy(pi);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
       narysuj(mouseEvent);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        narysuj(mouseEvent);

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    public BufferedImage getZdjęcie(){
        return zdjęcie;
    }


    public void setZdjęcie(BufferedImage zdj) {
        zdjęcie = zdj;
        paintComponent(getGraphics());
    }

    public void ustawKolor(Color color) {
        kolor2=kolor1;
       kolor1=color;
    }


    private void narysuj(MouseEvent mouseEvent){
        Graphics2D g = (Graphics2D) zdjęcie.getGraphics();

        if(SwingUtilities.isLeftMouseButton(mouseEvent))      {
            g.setColor(kolor1);
        }
        else g.setColor(kolor2);
        g.fillRect(pos_x(mouseEvent), pos_y(mouseEvent),  zoom,  zoom);
        g = (Graphics2D) getGraphics();
        g.drawImage(zdjęcie, 0, 0, this);
    }


    private int pos_x(MouseEvent e) {

        return (e.getX() - e.getX() % zoom);
    }

    private int pos_y(MouseEvent e) {
        return (e.getY() - e.getY() % zoom);
    }
}
