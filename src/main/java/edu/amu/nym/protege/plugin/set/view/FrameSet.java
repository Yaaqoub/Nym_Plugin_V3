package edu.amu.nym.protege.plugin.set.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.semanticweb.owlapi.model.OWLOntology;

public class FrameSet extends JPanel {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1732484583274649432L;


	public static OWLModelManager modelManager;
    
    public static JTable propertyTable = new JTable();
    
    public static FillPropertiesTable fillPropertiesTable = new FillPropertiesTable();

    
    private JButton saveButton = new JButton("Save");
    
    private JButton addButton = new JButton("Add");
    
    private JButton deleteButton = new JButton("Delete");
    
    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
            //recalculate();
        }
    };
    
    public FrameSet(){
    	
    }
    
    @SuppressWarnings("static-access")
	public FrameSet(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
        
        modelManager.addListener(modelListener);
        
        setLayout(new BorderLayout());
        add(createButtons(), BorderLayout.NORTH);
        add(new JScrollPane(propertyTable), BorderLayout.CENTER);
        
    }
    
    public void dispose() {
        modelManager.removeListener(modelListener);
    }
    
    private JToolBar createButtons() {
    	JToolBar panel = new JToolBar();
    	panel.add(saveButton);
    	panel.add(addButton);
    	panel.add(deleteButton);
    	return panel;
    }
    
    public static void printPropertyByIndividual(OWLOntology ontology, String indvName) {
    	propertyTable.setModel(fillPropertiesTable.buildTableModel(ontology, indvName));
    }
}
