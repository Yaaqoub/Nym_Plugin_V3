package edu.amu.nym.protege.plugin.tree.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class MyJTree extends JTree implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3605346745433659217L;
	
	private JPopupMenu popup = new JPopupMenu();
	
	private JMenuItem menuItems = new JMenuItem("Add Individual");
	
	private Icon classImg = new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\class.png");
	
	private Icon individualImg = new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\individual.png");
		
	private FillJTree fillJTree = new FillJTree();
	  
	public MyJTree (TreeModel mutableTreeNode) {
		super(mutableTreeNode);
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) this.getCellRenderer();
        renderer.setLeafIcon(individualImg);
        renderer.setClosedIcon(classImg);
        renderer.setOpenIcon(classImg);
        
		menuItems.addActionListener(this);
		menuItems.setActionCommand("add");
		popup.add(menuItems);
		
		menuItems = new JMenuItem("Remove Individual");
		menuItems.addActionListener(this);
		menuItems.setActionCommand("remove");
	    popup.add(menuItems);
	    
	    addMouseListener(new MouseAdapter() {
	        public void mouseReleased(MouseEvent e) {
	          if (e.isPopupTrigger()) {
	            popup.show((JComponent) e.getSource(), e.getX(), e.getY());
	          }
	        }
	      });
	}
	
	public void actionPerformed(ActionEvent ae) {
	    DefaultMutableTreeNode dmtn, node;

	    TreePath path = this.getSelectionPath();
	    dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
	    
	    if (ae.getActionCommand().equals("add")) {
	    	DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
	    	new AddIndividual(FrameTree.modelManager.getActiveOntology(), FrameTree.manager, selectedElement.getUserObject().toString());
	    	//FrameTree.classHierarchyTree.setModel(fillJTree.fillJTree());
	    	
	    	
	    	
	    	//JOptionPane.showMessageDialog(null, selectedElement.getUserObject());
	    	
	    	//DefaultTreeModel model = (DefaultTreeModel)this.getModel();
	    	//DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
	    	
	    	//model.reload(root);
	    }
	    
	    if (ae.getActionCommand().equals("remove")) {
	    	node = (DefaultMutableTreeNode) dmtn.getParent();
	    	int nodeIndex = node.getIndex(dmtn);
	    	dmtn.removeAllChildren();
	    	node.remove(nodeIndex);
	    	((DefaultTreeModel) this.getModel()).nodeStructureChanged((TreeNode) dmtn);
	    }
	}
}
