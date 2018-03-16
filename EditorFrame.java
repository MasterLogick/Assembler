import javax.swing.*;

public class EditorFrame extends JFrame {
    public static EditorFrame getInstance() {
        return instance;
    }

    private static EditorFrame instance;
    public static void main(String[] args) {
        instance=new EditorFrame();
        EditorFrame.init();
    }
    public static void init(){
        //todo init
    }
}
