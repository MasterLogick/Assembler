import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class Tab extends Canvas {
    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);
    private Dimension size = new Dimension();

    public Color getBoundsColor() {
        return boundsColor;
    }

    public void setBoundsColor(Color boundsColor) {
        this.boundsColor = boundsColor;
    }

    private Color boundsColor=new Color(75, 75, 75);
    private String name="";
    private Color textColor=new Color(187, 187, 187);
    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }


    public Tab(String name){
        this.name=name;
        TextLayout tl = new TextLayout(name, font, new FontRenderContext(null, true, true));
        size=new Dimension((int) ((tl.getBounds().getWidth())+9+26),22);
    }

    @Override
    public void paint(Graphics g) {
        //System.out.println(g.getClipBounds().x+" "+g.getClipBounds().y+" "+g.getClipBounds().width+" "+g.getClipBounds().height);
        g.setColor(getBackground());
        g.fillRect(0,0,(int)size.getWidth(),(int)size.getHeight());
        g.setColor(getForeground());
        g.setFont(font);
        g.drawString(name,9,14);
        g.setColor(boundsColor);
        g.drawLine(0,0,(int)size.getWidth(),0);
        g.drawLine(0,0,0,(int)size.getHeight());
        /*System.out.println((int)size.getWidth()+" "+0+" "+(int)size.getWidth()+" "+(int)size.getHeight());
        System.out.println(0+" "+(int)size.getHeight()+" "+(int)size.getWidth()+" "+(int)size.getHeight());*/
        g.setColor(boundsColor);
        g.drawLine((int)size.getWidth(),0,(int)size.getWidth(),(int)size.getHeight());
        g.drawLine(0,(int)size.getHeight(),(int)size.getWidth(),(int)size.getHeight());
    }
}
