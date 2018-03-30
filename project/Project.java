package project;

import main.EditorFrame;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Project {
	private static Document project;

	public static void createNew() {
		final boolean[] isDirChosen = {false};
		JFrame dialog = new JFrame("Create new");
		JPanel main = new JPanel();
		JPanel filler = new JPanel();
		JTextField name = new JTextField(60);
		JTextField path = new JTextField(49);
		JButton cancel = new JButton("Cancel");
		JButton create = new JButton("Create");
		JButton browse = new JButton("Browse...");
		JFileChooser fileChooser = new JFileChooser();

		cancel.addActionListener(e -> dialog.dispose());
		browse.addActionListener(e -> {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				path.setText(fileChooser.getSelectedFile().getAbsolutePath());
				isDirChosen[0] = true;
			}
		});
		name.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!isDirChosen[0]) {
					path.setText(path.getText().substring(0, path.getText().lastIndexOf("\\")) + "\\" + name.getText());
				}
			}
		});
		create.addActionListener(e -> {
			if ((!name.getText().isEmpty()) && (!path.getText().isEmpty())) {
				dialog.dispose();
				create(name.getText(), path.getText());
			}
		});
		name.setText("untitled");
		path.setText(FileSystemView.getFileSystemView().getRoots()[0].getPath() + File.separator + "untitled");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		main.setLayout(new GridLayout(3, 1));
		filler.setLayout(new FlowLayout(FlowLayout.LEFT));
		filler.add(new JLabel("Project name: "));
		filler.add(name);
		main.add(filler);
		filler = new JPanel();
		filler.add(new JLabel("Project path:  "));
		filler.add(path);
		filler.add(browse);
		main.add(filler);
		filler = new JPanel();
		filler.setLayout(new FlowLayout(FlowLayout.RIGHT));
		filler.add(create);
		filler.add(cancel);
		main.add(filler);
		dialog.add(main);
		dialog.setSize(dialog.getPreferredSize().width + 17, dialog.getPreferredSize().height + 28);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	private static void create(String name, String path) {
		//System.out.println("test " + name + " " + path);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		new File(path + File.separator + "src").mkdir();
		try {
			new File(path + File.separator + "src" + File.separator + "main.asm").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new File(path + File.separator + "out").mkdir();
		File project = new File(path + File.separator + "project.prj");
		try {
			project.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(path + File.separator + "project.prj");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
				"<project>\n" +
				"    <name>" + name + "</name>\n" +
				"    <work_dir>" + path + "</work_dir>\n" +
				"    <src>$[WORK_DIR]$\\src</src>\n" +
				"    <out>$[WORK_DIR]$" + File.separator + "out</out>\n" +
				"    <main>$[SRC]$" + File.separator + "main.asm</main>\n" +
				"</project>");
		pw.close();
		setProject(project);
		EditorFrame.getEditor().open(new File(path + File.separator + "src" + File.separator + "main.asm"));
	}

	public static void openProject() {
		/*for (File f:FileSystemView.getFileSystemView().getRoots()) {
			System.out.println(f.toString());
		}*/
		JFileChooser dialog = new JFileChooser(FileSystemView.getFileSystemView().getRoots()[0]);
		dialog.setDialogTitle("Open project");
		dialog.setMultiSelectionEnabled(false);
		dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dialog.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) return true;
				if (f.getName().toLowerCase().endsWith(".prj")) {
					return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "Project ASM project file *.prj";
			}
		});
		if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File project = dialog.getSelectedFile();
			setProject(project);
			//System.out.println(getProjectAtrib("main"));
			EditorFrame.getEditor().open(new File(getProjectAtrib("main")));
		}
	}

	private static String getProjectAtrib(String tag) {
		String ret = project.getElementsByTagName(tag).item(0).getTextContent();
		int start = 0, end = 0;
		while ((start = ret.indexOf("$[")) != -1) {
			end = ret.length() - new StringBuilder(ret).reverse().lastIndexOf("$]");
			String replace = project.getElementsByTagName(ret.substring(start + 2, end - 2).toLowerCase()).item(0).getTextContent();
			ret = new StringBuilder(ret).replace(start, end, replace).toString();
		}
		return ret;
	}

	private static void setProject(File f) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			Project.project = db.parse(f);
		} catch (SAXException | ParserConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}
}
