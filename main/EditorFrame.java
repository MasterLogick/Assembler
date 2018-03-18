package main;

import editor.Editor;
import editor.Parser;

import javax.swing.*;

public class EditorFrame extends JFrame {
    public static EditorFrame getInstance() {
        return instance;
    }

    public static Editor getEditor() {
        return editor;
    }

    private static EditorFrame instance;
    private static Editor editor;
    public static void main(String[] args) {
        Parser.init();
        instance=new EditorFrame();
        editor = new Editor();
        EditorFrame.init();
    }
    private static void init(){
        //todo init
    }
    private static JMenu menuInit(){
        //todo init JMenu
        return null;
    }
}
