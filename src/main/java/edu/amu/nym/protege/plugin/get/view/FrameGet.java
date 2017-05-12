package edu.amu.nym.protege.plugin.get.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.protege.editor.core.ui.error.ErrorLogPanel;
import org.protege.editor.core.ui.util.LinkLabel;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;

import edu.amu.nym.protege.plugin.set.view.FrameSet;

public class FrameGet extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5411711042737977576L;
	
    public static final URI Classes_IRI_DOCUMENTATION = URI.create("https://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Classes");

    public static OWLModelManager modelManager;
    
    public static boolean isIndividualsTableClicked = false;
    
    public static String individualSelected = "";
    
    public static JComboBox<Object> classesComboBox = new JComboBox<Object>();
    
    public static JTable individualsTable = new JTable(){
    	/**
		 * 
		 */
		private static final long serialVersionUID = 2667265216208579839L;
		
		@Override
		public boolean isCellEditable(int row, int column) {                
            return false;               
    	}; 
    };
    
        
	private final String classesNameFieldLabel = "Classes Names:  ";

	private FillCombo fillCombo = new FillCombo();
	
	private FillIndividualsTable fillIndividualsTable = new FillIndividualsTable();
	
	private FrameSet frameSet = new FrameSet();

    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
        	fillComboBox();
        }
    };
    
    @SuppressWarnings("static-access")
	public FrameGet(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
    	fillComboBox();
    	printIndividualsByclass();

        modelManager.addListener(modelListener);
                
        setLayout(new BorderLayout());
        add(createComboBox(), BorderLayout.NORTH);
        add(new JScrollPane(individualsTable), BorderLayout.CENTER);
        
        individualsTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  if (e.getClickCount() == 1) {
					  isIndividualsTableClicked = true;
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      String rowValue = individualsTable.getModel().getValueAt(row, 0).toString();
				      individualSelected = rowValue;
				      
				      frameSet.printDataPropertyByIndividual(modelManager.getActiveOntology(), individualSelected);
				      frameSet.printObjectPropertyByIndividual(modelManager.getActiveOntology(), individualSelected);
				      
				      FrameSet.selectedPropertyTableRowValue = "";
			    }
			  }
      	});
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
    
    private JToolBar createComboBox() {
    	JToolBar panel = new JToolBar();
    	panel.add(new LinkLabel(classesNameFieldLabel , e -> {
        	showOntologyIRIDocumentation();
        }));
    	
    	panel.add(classesComboBox);
    	return panel;
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