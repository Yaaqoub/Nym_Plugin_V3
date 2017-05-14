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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;


import edu.amu.nym.protege.plugin.get.view.FrameGet;

public class AddProperty extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3835901111290787120L;

	private String prefix = "";
	
	private JTextField propertyNameField;
	
	private JTextField propertyValueField;
	
	private JComboBox<String> dataTypeCombo;
	
	String[] dataTypes = { "boolean", "byte", "decimal", "double",
							"float", "int", "integer", "long",
							"name", "short", "string"};
	
	
	public AddProperty(OWLOntology ontology, OWLOntologyManager manager) {
		chosingUI(ontology, manager);
	}
	
	private void chosingUI(OWLOntology ontology, OWLOntologyManager manager) {
		Object[] options = {"Add Data Property",
                			"Add Object Property"};
		
		int choosingOne = JOptionPane.showOptionDialog(this,
											    "What type of property you want to add ??",
											    "Chose",
											    JOptionPane.YES_NO_CANCEL_OPTION,
											    JOptionPane.QUESTION_MESSAGE,
											    null,
											    options,
											    options[1]);
		if (choosingOne == 0) {
			createDataPropertyUI(ontology, manager);
		} else if (choosingOne == 1) {
			createObjectPropertyUI();
		}
	}
	
	private void createDataPropertyUI(OWLOntology ontology, OWLOntologyManager manager) {
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
		
		addDataProperty(ontology, manager, propertyNameField.getText(), propertyValueField.getText(), dataTypeCombo.getSelectedItem().toString());
		
		/*JOptionPane.showMessageDialog(null, "first: " + propertyNameField.getText() 
										+ " 2nd " + propertyValueField.getText()
										+ " combo " + dataTypeCombo.getSelectedItem());*/
		
	}
	
	private void addDataProperty(OWLOntology ontology, OWLOntologyManager manager, String propertyName, String value, String comboBoxValue) {
		OWLDataFactory factory = manager.getOWLDataFactory();
		
		for (OWLClass c : ontology.getClassesInSignature()) {
			String prefix = c.getIRI().getNamespace();
			OWLAxiom axiom = null;
			OWLDataProperty hasSomethingProperty = factory.getOWLDataProperty(IRI.create(prefix + propertyName));
			OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + FrameGet.individualSelected));
			
			if (comboBoxValue.equals("boolean"))
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Boolean.parseBoolean(value));
			else if (comboBoxValue.equals("byte"))
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Byte.parseByte(value));
			else if (comboBoxValue.equals("decimal"))
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Float.parseFloat(value));
			else if (comboBoxValue.equals("double")) 
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Double.parseDouble(value));
			else if (comboBoxValue.equals("int")) 
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Integer.parseInt(value));
			else if (comboBoxValue.equals("integer")) 
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Integer.parseInt(value));
			else if (comboBoxValue.equals("long")) 
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Long.parseLong(value));
			else if (comboBoxValue.equals("short")) 
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, Short.parseShort(value));
			else
				axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, value);
			
			
			
			manager.applyChange(new AddAxiom(ontology, axiom));
		}
	}
	
	private void createObjectPropertyUI() {
		JOptionPane.showMessageDialog(null, "You are in add object property !!!");
	}
	
	private void addObjectProperty(OWLOntology ontology) {
		
	}
}
