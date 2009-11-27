import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


abstract class ParentRunnable implements Runnable {
	IDE parent;
	public ParentRunnable(IDE parent) {
		this.parent = parent;
	}
	abstract public void run();
}

public class IDE {

	JFrame mf = null;
	DefaultListModel projectFiles = new DefaultListModel();
	JList projectList = null;

	DefaultListModel classpathModel = new DefaultListModel();
	JList classPathList = null;

	public void loadProjectFile(File f) {
		try {
			projectFiles.clear();
			Properties props = new Properties();
			FileInputStream inf = new FileInputStream(f);
			props.load(inf);
			int count = Integer.parseInt(
				props.getProperty("sourcefiles"));
			for(int i=0;i<count;i++) {
				projectFiles.addElement(
					props.getProperty("f"+i)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showCompileResults(String s) {
		System.out.println(s);
	}

	public void compileFile(String filename) {
		System.out.println("compiling " + filename);
		String[] args = new String[4];
		args[0] = "-verbose";
		args[1] = "-d";
		args[2] = "./classes";
		args[3] = filename;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		com.sun.tools.javac.Main.compile(args, pw);
		System.out.println(sw.toString());
	}

	public String loadFile(File f) {
		return null;
	}

	public void gui() {
		mf = new JFrame("ide");
		mf.setSize(500,500);
		mf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JEditorPane editor = new JEditorPane();
		editor.setEditable(true);
		editor.setSize(400,300);
		JScrollPane scroller = new JScrollPane(editor);
		scroller.setMinimumSize(new Dimension(200,200));
		mf.getContentPane().setLayout(new BorderLayout());
		mf.getContentPane().add(scroller, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		mf.getContentPane().add(bottom, BorderLayout.SOUTH);
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		JButton open = new JButton("open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(".");
				int ret = fc.showOpenDialog(mf);
				if (ret==JFileChooser.APPROVE_OPTION) {
					File inf = fc.getSelectedFile();
					loadProjectFile(inf);
				}
			}
		});
		top.add(open);
		JButton save = new JButton("save");
		top.add(save);
		JButton compile = new JButton("compile");
		final ParentRunnable runner = new ParentRunnable(this) {
			public void run() {
					int idx = projectList.getSelectedIndex();
					String filename = (String)projectFiles
					.get(idx);
					compileFile(filename);
				}
		};
	
		compile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(runner);
			}
		});
		top.add(compile);
		mf.getContentPane().add(top, BorderLayout.NORTH);
		JPanel left = new JPanel();
		projectList = new JList(projectFiles);
		projectList.setSelectionMode(
			ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		left.add(projectList);
		mf.getContentPane().add(left,BorderLayout.WEST);
		mf.setVisible(true);
	}

	public void run() {
		gui();
	}

	public static void main(String[] args) {
		IDE mainline = new IDE();
		mainline.run();
	}

}
