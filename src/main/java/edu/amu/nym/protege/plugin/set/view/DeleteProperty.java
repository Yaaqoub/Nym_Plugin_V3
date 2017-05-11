package edu.amu.nym.protege.plugin.set.view;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.OWLEntityRemover;

public class DeleteProperty {
	
	public DeleteProperty() {
		
	}
	
	public void deleteDataProperty(OWLOntology ontology, OWLOntologyManager manager, String indvName, String deletedDataProperty) {
		Object[] options = { "Yes", "No" };
		
		OWLEntityRemover remover = new OWLEntityRemover(Collections.singleton(ontology));
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		OWLDataFactory factory = manager.getOWLDataFactory();
		for (OWLClass c : ontology.getClassesInSignature()){
			NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
			
			for (OWLNamedIndividual i : instances.getFlattened()){
				if(i.getIRI().getFragment().equals(indvName)) {
					OWLNamedIndividual input = factory.getOWLNamedIndividual(IRI.create(i.getIRI().toString()));
					Set<OWLDataPropertyAssertionAxiom> properties = ontology.getDataPropertyAssertionAxioms(input);
					
					for (OWLDataPropertyAssertionAxiom ax: properties) {
						for (OWLLiteral propertyLit :  EntitySearcher.getDataPropertyValues(i, ax.getProperty(), ontology)){
							if(ax.getProperty().toString().equals(deletedDataProperty)){
								//JOptionPane.showMessageDialog(null, "Property: " + deletedDataProperty + " Value: " + propertyLit.getLiteral());
								int clickedButton = JOptionPane.showOptionDialog(null, "Do you want to Delete " +  deletedDataProperty + " : "
																		+ propertyLit.getLiteral() + " ?",
																		"Confirm to Delete?",
																		JOptionPane.YES_NO_CANCEL_OPTION,
																		JOptionPane.QUESTION_MESSAGE,
																		null, options, options[1]);
								if (clickedButton == 0) {
									manager.removeAxiom(ontology, ax);
									JOptionPane.showMessageDialog(null, "Delete Data Property Successfully");
								}
								
							}
						 }
					}
				}
			}
		}
	}
}
