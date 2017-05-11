package edu.amu.nym.protege.plugin.set.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

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

    
    private OWLOntologyManager manager;
    
    private DeleteProperty deleteProperty = new DeleteProperty();
    
    //private AddProperty addProperty = new AddProperty();
    
    private JButton saveButton = new JButton(new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\save.png"));
    
    private JButton addButton = new JButton(new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\add.png"));
    
    private JButton deleteButton = new JButton(new ImageIcon("C:\\Users\\Yaaqoub\\workspace\\NYM_Plugin_V3\\src\\main\\resources\\delete.png"));
    
    
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
				JOptionPane.showMessageDialog(null, "save");
			}
		});
        
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new AddProperty();
			}
		});
        
        deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//JOptionPane.showMessageDialog(null, "delete");
				deleteProperty.deleteDataProperty(modelManager.getActiveOntology(), manager, FrameGet.individualSelected, "hasName");
				printDataPropertyByIndividual(modelManager.getActiveOntology(), FrameGet.individualSelected);
				repaint();
				revalidate();
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
