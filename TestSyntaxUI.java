
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import jsyntaxpane.DefaultSyntaxKit;

class IDEObject {
	public static int UNKNOWN = 0;
	public static int PROJECT = 1;
	public static int DIR = 2;
	public static int JAVAFILE = 3;
	public static int PROJECTLIST = 4;

	public int node_type;
	public String name;
}

class IDEProjectList extends IDEObject {
	public IDEProjectList() {
		node_type = IDEObject.PROJECTLIST;
	}
	public String toString() {
		return "Projects";
	}
}

class IDEProject extends IDEObject {
	public String basedir;

}

class IDEJavaFile extends IDEObject {
}

class IDEDir extends IDEObject {
}


class IDETreeNode extends DefaultMutableTreeNode {
	public IDETreeNode() {
		super();
	}
	public IDETreeNode(IDEProjectList pl) {
		super(pl,true);
	}
	public IDETreeNode(IDEProject proj) {
		super(proj,true);
	}
}

public class TestSyntaxUI {

	JFrame mf = null;

	public IDETreeNode makeTree() {
		IDETreeNode root =
			new IDETreeNode(new IDEProjectList());
		return root;
	}

	public void run() {
		mf = new JFrame("Test");
		Container c = mf.getContentPane();
		c.setLayout(new BorderLayout());
		DefaultSyntaxKit.initKit();
		JEditorPane edit = new JEditorPane();
		JScrollPane scroller = new JScrollPane(edit);
		JSplitPane mainp = new JSplitPane();
		mainp.setRightComponent(scroller);
		c.add(mainp, BorderLayout.CENTER);
		edit.setContentType("text/java");

		JTree proj = new JTree(makeTree());
		MouseAdapter tree_right_click = new MouseAdapter() {
			public void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				JTree tree = (JTree)e.getSource();
				TreePath path = tree.getPathForLocation(x,y);
				if (path==null) {
					System.out.println("null path");
					return;
				}
				tree.setSelectionPath(path);
				// fancy cast
				// component is an IDETreeNode
				// get the user object (it is an IDEObject)
				IDEObject o = (IDEObject)((IDETreeNode)path
					.getLastPathComponent())
					.getUserObject();
				
				if (o.node_type == IDEObject.PROJECTLIST) {
					JPopupMenu popup = new JPopupMenu();
					popup.add(new JMenuItem("New Project..."));
					popup.show(tree,x,y);
				}
			}
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					myPopupEvent(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					myPopupEvent(e);
				}
			}
		};
		proj.addMouseListener(tree_right_click);
		mainp.setLeftComponent(proj);
		c.doLayout();

		mf.setSize(500,400);
		mf.setVisible(true);
		mf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					new TestSyntaxUI().run();
				}
			}
		);
	}
}
