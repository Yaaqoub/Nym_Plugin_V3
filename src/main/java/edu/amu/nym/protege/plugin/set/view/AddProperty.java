package edu.amu.nym.protege.plugin.set.view;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.protege.editor.core.ui.util.ComponentFactory;
import org.protege.editor.core.ui.util.JOptionPaneEx;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class AddProperty extends JPanel{
	
	private String prefix = "";
	
	private JTextField propertyNameField;
	
	private JTextField propertyValueField;
	
	private JComboBox<String> dataTypeCombo;
	
	String[] dataTypes = { "boolean", "byte", "dateTime", "decimal", "double",
							"float", "hexBinary", "int", "integer", "long",
							"name", "short", "string", "unsignedByte", "unsignedLong", "unsignedShort"};
	
	
	public AddProperty() {
		createUI();
	}
	
	private void createUI() {
		setLayout(new BorderLayout());
		
		propertyNameField = new JTextField(45);
		JPanel propertyNameFieldHolder = new JPanel(new BorderLayout());
		propertyNameFieldHolder.setBorder(ComponentFactory.createTitledBorder("Data Property Name"));
		propertyNameFieldHolder.add(propertyNameField, BorderLayout.NORTH);
		add(propertyNameFieldHolder, BorderLayout.NORTH);
		
		propertyValueField = new JTextField(45);
		JPanel propertyValueFieldHolder = new JPanel(new BorderLayout());
		propertyValueFieldHolder.setBorder(ComponentFactory.createTitledBorder("Value"));
		propertyValueFieldHolder.add(propertyValueField, BorderLayout.CENTER);
		add(propertyValueFieldHolder, BorderLayout.CENTER);
		
		dataTypeCombo = new JComboBox<String>(dataTypes);
		JPanel propertyTypeFieldHolder = new JPanel(new BorderLayout());
		propertyTypeFieldHolder.setBorder(ComponentFactory.createTitledBorder("Value"));
		propertyTypeFieldHolder.add(dataTypeCombo, BorderLayout.SOUTH);
		add(propertyTypeFieldHolder, BorderLayout.SOUTH);
		
		JOptionPaneEx.showValidatingConfirmDialog(null,
                "Add Data Property",
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                this.propertyNameField);
		
		/*JOptionPane.showMessageDialog(null, "first: " + propertyNameField.getText() 
										+ " 2nd " + propertyValueField.getText()
										+ " combo " + dataTypeCombo.getSelectedItem());*/
		
	}
	
	/*public static boolean save(OWLOntology ontology, OWLOntologyManager manager, String fileName) {
		boolean ok = true;
		IRI ontoSavedIRI = IRI.create(fileName);
		
		try {
			manager.saveOntology(ontology, ontoSavedIRI);
		} catch (OWLOntologyStorageException e) {
			JOptionPane.showMessageDialog(null, "problem writting ontology " + e);
			ok = false;
		}
		
		if (ok)
			JOptionPane.showMessageDialog(null, "writting ontology done");
		
		return ok;
	}*/
	
	public void addDataProperty(OWLOntology ontology, OWLOntologyManager manager, String dataProperty, String individualName, int dataPropertyValue, String ontologyFile) {
		
		/*for (OWLClass clazz : ontology.getClassesInSignature()) {
			prefix = clazz.getIRI().getNamespace();
		}
		
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLDataProperty hasDataProperty = factory.getOWLDataProperty(IRI.create(prefix + dataProperty));
		OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + individualName));
		OWLAxiom axiom = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, classIndName, dataPropertyValue);
		
		manager.applyChange(new AddAxiom(ontology, axiom));
		AddProperty.save(ontology, manager, ontologyFile);*/
	}
}
