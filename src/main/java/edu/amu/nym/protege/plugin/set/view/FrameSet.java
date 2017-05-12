package edu.amu.nym.protege.plugin.set.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import edu.amu.nym.protege.plugin.get.view.FillIndividualsTable;
import edu.amu.nym.protege.plugin.get.view.FrameGet;

public class FrameSet extends JPanel {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1732484583274649432L;


	public static OWLModelManager modelManager;
    
    public static JTable dataPropertyTable = new JTable();
    
    public static JTable objectPropertyTable = new JTable();
    
    public static FillPropertiesTable fillPropertiesTable = new FillPropertiesTable();
    
    public static String selectedPropertyTableRowValue = "";

    
    private OWLOntologyManager manager;
    
    private DeleteProperty deleteProperty = new DeleteProperty();
    
    private FillIndividualsTable fillIndividualsTable = new FillIndividualsTable();
    
    //private AddProperty addProperty = new AddProperty();
    
    private JButton saveButton = new JButton(new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\save.png"));
    
    private JButton addButton = new JButton(new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\add.png"));
    
    private JButton deleteButton = new JButton(new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\delete.png"));
    
    private boolean isDataProperty = false;
    
    private boolean isObjectProperty = false;
    
    
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
        
        manager = OWLManager.createOWLOntologyManager();
        
        setLayout(new BorderLayout());
        add(createButtons(), BorderLayout.NORTH);
        add(new JScrollPane(objectPropertyTable), BorderLayout.CENTER);
        add(new JScrollPane(dataPropertyTable), BorderLayout.SOUTH);
        
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, selectedPropertyTableRowValue);
			}
		});
        
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new AddProperty();
			}
		});
        
        deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				if (isDataProperty == true) {
					deleteProperty.deleteDataProperty(modelManager.getActiveOntology(), manager, FrameGet.individualSelected, selectedPropertyTableRowValue);
					printDataPropertyByIndividual(modelManager.getActiveOntology(), FrameGet.individualSelected);
					
				} else if (isObjectProperty == true) {
					deleteProperty.deleteObjectProperty(modelManager.getActiveOntology(), manager, FrameGet.individualSelected, selectedPropertyTableRowValue);
					printObjectPropertyByIndividual(modelManager.getActiveOntology(), FrameGet.individualSelected);
				}else {
					JOptionPane.showMessageDialog(null, "Select a property to delete !!",
													"Property not selected",
													JOptionPane.WARNING_MESSAGE);
				}

				String selectedClass = FrameGet.classesComboBox.getSelectedItem().toString();
				FrameGet.individualsTable.setModel(fillIndividualsTable.buildTableModel(modelManager.getActiveOntology(), selectedClass));
			}
		});
        
        dataPropertyTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  if (e.getClickCount() == 1) {
					  isDataProperty = true;
					  isObjectProperty = false;
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      selectedPropertyTableRowValue = dataPropertyTable.getModel().getValueAt(row, 0).toString();
				      objectPropertyTable.getSelectionModel().clearSelection();
			    }
			  }
    	});
        
        objectPropertyTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  if (e.getClickCount() == 1) {
					  isObjectProperty = true;
					  isDataProperty = false;
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      selectedPropertyTableRowValue = objectPropertyTable.getModel().getValueAt(row, 0).toString();
				      dataPropertyTable.getSelectionModel().clearSelection();
			    }
			  }
  	});
    }
    
    public void dispose() {
        modelManager.removeListener(modelListener);
    }
    
    private JToolBar createButtons() {
    	JToolBar panel = new JToolBar();
    	panel.setLayout(new GridLayout(1,7));
    	
    	for(int i = 0; i < 3; i++)
    		panel.add(new JLabel(""));
    	
    	panel.add(saveButton);
    	panel.add(new JToolBar.Separator());
    	panel.add(addButton);
    	panel.add(new JToolBar.Separator());
    	panel.add(deleteButton);
    	
    	for(int i = 0; i < 3; i++)
    		panel.add(new JLabel(""));
    	
    	return panel;
    }
    
    public static void printDataPropertyByIndividual(OWLOntology ontology, String indvName) {
    	dataPropertyTable.setModel(fillPropertiesTable.buildDataTableModel(ontology, indvName));
    }
    
    public static void printObjectPropertyByIndividual(OWLOntology ontology, String indvName) {
    	objectPropertyTable.setModel(fillPropertiesTable.buildObjectTableModel(ontology, indvName));
    }
}
