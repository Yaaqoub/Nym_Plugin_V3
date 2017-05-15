package edu.amu.nym.protege.plugin.graph.view;


import javax.swing.JLabel;
import javax.swing.JPanel;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;

public class FrameGraph extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7381253493633614448L;

	private JLabel textComponent = new JLabel();

    private OWLModelManager modelManager;

    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
            recalculate();
        }
    };
    
    public FrameGraph(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
        recalculate();
        
        modelManager.addListener(modelListener);
        
        add(textComponent);
    }
    
    public void dispose() {
        modelManager.removeListener(modelListener);
    }
    
    private void recalculate() {
        
        textComponent.setText("Graph Stream Place");
    }

}
