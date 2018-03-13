import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class TabsPane extends Canvas {
    static Color c = null;
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(Main.ep.getWidth(),22);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),22);
    }

    public Dimension getMaximumSize() {
        return new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),22);
    }
    static Vector<Tab> tabs = new Vector<>();

    public static Component add(Tab comp) {
        tabs.add(comp);
        return comp;
    }

    @Override
    public int getWidth() {
        return (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }

    @Override
    public int getHeight() {
        return 22;
    }
    public static void setBack(Color cs){
        c=cs;
    }
    @Override
    public void paint(Graphics g) {
        int size = 0;
        for (Tab t : tabs) {
            System.out.println(t.getWidth()+" "+ t.getHeight());
            BufferedImage bufferedImage = new BufferedImage(t.getWidth(), t.getHeight(), BufferedImage.TYPE_INT_RGB);
            t.paint(bufferedImage.getGraphics());
            g.drawImage(bufferedImage, size, 0, null);
            size += t.getWidth();
        }
    }
}
