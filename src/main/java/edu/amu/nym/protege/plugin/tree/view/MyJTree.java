package edu.amu.nym.protege.plugin.tree.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import edu.amu.nym.protege.plugin.get.view.FillCombo;
import edu.amu.nym.protege.plugin.get.view.FrameGet;
import edu.amu.nym.protege.plugin.set.view.AddProperty;
import edu.amu.nym.protege.plugin.set.view.DeleteProperty;
import edu.amu.nym.protege.plugin.set.view.FrameSet;

public class MyJTree extends JTree implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3605346745433659217L;
	
	private JPopupMenu popup = new JPopupMenu();
	
	private JMenuItem menuItems = new JMenuItem("Add Class");
	
	private Icon classImg = new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\class.png");
	
	private Icon individualImg = new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\individual.png");
		
	private FillJTree fillJTree = new FillJTree();
	
	private AddIndividual addIndividual = new AddIndividual();
	
	private AddClass addClass = new AddClass();
	
	private FillCombo fillCombo = new FillCombo();
	
	private JTable dataPropertyTable = new JTable();
	
	private FillTreePropertyTable fillTreePropertyTable = new FillTreePropertyTable();
		
	private JButton deleteButton = new JButton("Delete Property");
	
	private JButton addButton = new JButton("Add Property");
	
	private Object[] options = {deleteButton, addButton};
	
	private String selectedTreeElementForProperty = "";
	
	private String dataPropertyTableRowValue = "";
	
	private DeleteProperty deleteProperty = new DeleteProperty();
	
	  
	public MyJTree (TreeModel mutableTreeNode) {
		super(mutableTreeNode);
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) this.getCellRenderer();
        renderer.setLeafIcon(individualImg);
        renderer.setClosedIcon(classImg);
        renderer.setOpenIcon(classImg);
        
        menuItems.addActionListener(this);
		menuItems.setActionCommand("addClass");
		popup.add(menuItems);
		
        menuItems = new JMenuItem("Add Individual");
		menuItems.addActionListener(this);
		menuItems.setActionCommand("addIndividual");
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
	        
	        public void mousePressed(MouseEvent e) {
	        	if (e.getClickCount() == 2) {
	        		DefaultMutableTreeNode selectedTreeElement = 
	        				(DefaultMutableTreeNode)getSelectionPath().getLastPathComponent();
	        		selectedTreeElementForProperty = selectedTreeElement.toString();

		        	  fillTreePropertyTable(selectedTreeElement.toString());
		        	  
		        	  JOptionPane.showOptionDialog(null,
				    		  new JScrollPane(dataPropertyTable), 
				    		  "Individual Data property",
				    		  JOptionPane.DEFAULT_OPTION,
				    		  JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		        }
	        }
	      });
	    
	    addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new AddProperty(FrameTree.modelManager.getActiveOntology(), FrameTree.manager, selectedTreeElementForProperty);
				fillTreePropertyTable(selectedTreeElementForProperty);
				dataPropertyTable.repaint();
			}
		});
	    
	    deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				deleteProperty.deleteDataProperty(FrameTree.modelManager.getActiveOntology(),
						FrameTree.manager, 
						selectedTreeElementForProperty, dataPropertyTableRowValue);
				fillTreePropertyTable(selectedTreeElementForProperty);
				dataPropertyTable.repaint();
			}
		});
	    
	    dataPropertyTable.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent e) {
				  if (e.getClickCount() == 1) {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      dataPropertyTableRowValue = dataPropertyTable.getModel().getValueAt(row, 0).toString();
				   }
			  }
    	});
	}
	
	public void actionPerformed(ActionEvent ae) {
	    DefaultMutableTreeNode dmtn, node;

	    TreePath path = this.getSelectionPath();
	    dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
	    	    	    
	    if (ae.getActionCommand().equals("addIndividual")) {
	    	DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
	    	addIndividual.addIndividualUI(FrameTree.modelManager.getActiveOntology(), 
							    			FrameTree.manager, 
							    			selectedElement.getUserObject().toString());

	    	FrameTree.classHierarchyTree.setModel(fillJTree.fillJTree());
	    }
	    
	    if (ae.getActionCommand().equals("addClass")) {
	    	
	    	addClass.addClassUI(FrameTree.modelManager.getActiveOntology(), FrameTree.manager);
	    	FrameTree.classHierarchyTree.setModel(fillJTree.fillJTree());
	    	FrameGet.classesComboBox.setModel(fillCombo.fillComboBox());
	    }
	    
	    if (ae.getActionCommand().equals("remove")) {
	    	DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
	    	OWLDataFactory factory = FrameTree.modelManager.getOWLDataFactory();
	    	for (OWLClass c : FrameTree.modelManager.getActiveOntology().getClassesInSignature()) {
	    		String prefix = c.getIRI().getNamespace();
	    		OWLNamedIndividual individualToDelete = factory.getOWLNamedIndividual(IRI.create(prefix + selectedElement.getUserObject().toString()));
	    		OWLEntityRemover remover = new OWLEntityRemover(Collections.singleton(FrameTree.modelManager.getActiveOntology()));
	    		remover.visit(individualToDelete);
	    		FrameTree.modelManager.applyChanges(remover.getChanges());
	    	}
	    	
	    	node = (DefaultMutableTreeNode) dmtn.getParent();
	    	int nodeIndex = node.getIndex(dmtn);
	    	dmtn.removeAllChildren();
	    	node.remove(nodeIndex);
	    	((DefaultTreeModel) this.getModel()).nodeStructureChanged((TreeNode) dmtn);
	    }
	}
	
	private void fillTreePropertyTable(String indvName) {
		dataPropertyTable.setModel(fillTreePropertyTable.buildTableModel(FrameTree.modelManager.getActiveOntology(), indvName));
	}
}
