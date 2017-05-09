package edu.amu.nym.protege.plugin.get.view;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.protege.editor.core.ui.error.ErrorLogPanel;
import org.protege.editor.core.ui.util.LinkLabel;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;

public class FrameGet extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5411711042737977576L;
	
    public static final URI Classes_IRI_DOCUMENTATION = URI.create("https://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Classes");

    public static OWLModelManager modelManager;
    
    
	private final String classesNameFieldLabel = "Classes Name";
	
	private JComboBox<Object> classesComboBox = new JComboBox<Object>();

	private FillCombo fillCombo = new FillCombo();
	
	private FillIndividualsTable fillIndividualsTable = new FillIndividualsTable();
	
	private JTable individualsTable = new JTable(){
    	/**
		 * 
		 */
		private static final long serialVersionUID = 2667265216208579839L;
		
		@Override
		public boolean isCellEditable(int row, int column) {                
            return false;               
    	}; 
    };
	
    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
        	fillComboBox();
        }
    };
    
    public FrameGet(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
    	fillComboBox();
    	printIndividualsByclass();

        modelManager.addListener(modelListener);
                
        Insets insets = new Insets(0, 4, 2, 0);
        
        add(new LinkLabel(classesNameFieldLabel , e -> {
        	showOntologyIRIDocumentation();
        }), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.BASELINE_TRAILING,
									GridBagConstraints.NONE,
									insets,
									0, 0));
        
        add(classesComboBox,
				 new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, 
				 GridBagConstraints.BASELINE_LEADING, 
				 GridBagConstraints.HORIZONTAL, 
				 insets, 
				 0, 0));
        
        add(new JScrollPane(individualsTable),
				 new GridBagConstraints(1, 1, 1, 1, 100.0, 0.0, 
				 GridBagConstraints.BASELINE_LEADING, 
				 GridBagConstraints.HORIZONTAL, 
				 insets, 
				 0, 0));
    }
    
    public void dispose() {
        modelManager.removeListener(modelListener);
    }
    
    private void showOntologyIRIDocumentation() {
    	try {
            Desktop.getDesktop().browse(Classes_IRI_DOCUMENTATION);
        }
        catch (IOException ex) {
            ErrorLogPanel.showErrorDialog(ex);
        }
    }
    
    private void fillComboBox() {
    	if (classesComboBox.getItemCount() > 1){
    		classesComboBox.removeAllItems();
		}
    	classesComboBox.setModel(fillCombo.fillComboBox());
    	repaint();
        revalidate();
    }
    
    private void printIndividualsByclass() {
    	classesComboBox.addActionListener(new ActionListener() {
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				Object selected = comboBox.getSelectedItem();
				
				individualsTable.setModel(fillIndividualsTable.buildTableModel(modelManager.getActiveOntology(), selected.toString()));
				repaint();
		        revalidate();
			}
		});
    }
}
