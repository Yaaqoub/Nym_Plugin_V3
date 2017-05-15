package edu.amu.nym.protege.plugin.tree.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.protege.editor.core.ui.util.ComponentFactory;
import org.protege.editor.core.ui.util.JOptionPaneEx;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AddClass extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -453441713782972782L;

	private JTextField classNameField;
	
	private JLabel iriLabel;
	
	
	public AddClass() {
		
	}
	
	private void addClassAlgo(OWLOntology ontology, OWLOntologyManager manager, String className, String firstIndividual) {
		OWLDataFactory factory = FrameTree.modelManager.getOWLDataFactory();
		
		for (OWLClass c : ontology.getClassesInSignature()) {
			String prefix = c.getIRI().getNamespace();
			
			//i7dyiwi rbi ri7 anzayd7 class ":" && l3akes adkiss7 ":"
			OWLClass clsPSS = factory.getOWLClass(IRI.create(prefix + ":" + className));
			
			OWLNamedIndividual indivName = factory.getOWLNamedIndividual(IRI.create(prefix + ":" + firstIndividual));
			
			OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(clsPSS, indivName);
						
			manager.addAxiom(ontology, classAssertion);
			
		}
	}
	
	public void addClassUI(OWLOntology ontology, OWLOntologyManager manager) {
		setLayout(new BorderLayout());
		
		classNameField = new JTextField(45);
		JPanel individualNameFieldHolder = new JPanel(new BorderLayout());
		individualNameFieldHolder.setBorder(ComponentFactory.createTitledBorder("Class Name"));
		individualNameFieldHolder.add(classNameField, BorderLayout.NORTH);
		add(individualNameFieldHolder, BorderLayout.NORTH);
		
		iriLabel = new JLabel();
		iriLabel.setText(getIRI(ontology));
		JPanel iriFieldHolder = new JPanel(new BorderLayout());
		iriFieldHolder.setBorder(ComponentFactory.createTitledBorder("IRI (auto-generated)"));
		iriFieldHolder.add(iriLabel, BorderLayout.CENTER);
		add(iriFieldHolder, BorderLayout.CENTER);
		
		classNameField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				changeText();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changeText();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changeText();
			}
			
			public void changeText() {
				iriLabel.setText(getIRI(ontology) + classNameField.getText());
			}
			
		});
		
		JOptionPaneEx.showValidatingConfirmDialog(null,
                "Create a new OWLClass",
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                this.classNameField);
		
		addClassAlgo(ontology, manager, classNameField.getText(), "IndividualTest");
		
		classNameField.setText("");
		iriLabel.setText(getIRI(ontology));
	}
	
	private String getIRI(OWLOntology ontology) {
		String prefix = "";
		for (OWLClass c : ontology.getClassesInSignature()) {
			prefix = c.getIRI().getNamespace();
		}
		
		return prefix;
	}
}
