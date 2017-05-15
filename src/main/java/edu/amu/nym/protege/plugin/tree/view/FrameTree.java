package edu.amu.nym.protege.plugin.tree.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class FrameTree extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3155394581640508282L;


	public static OWLModelManager modelManager;
	
	public static OWLOntologyManager manager;
	
	
	
	private FillJTree fillJTree = new FillJTree();
	
	public static MyJTree classHierarchyTree;
	
	

    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
            //recalculate();
        }
    };
    
    @SuppressWarnings("static-access")
	public FrameTree(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
    	
    	manager = OWLManager.createOWLOntologyManager();
    	
        modelManager.addListener(modelListener);

        classHierarchyTree = new MyJTree(fillJTree.fillJTree());
        setLayout(new BorderLayout());
        add(new JScrollPane((JTree) classHierarchyTree), "Center");
    }
    
    public void dispose() {
        modelManager.removeListener(modelListener);
    }
    

}
