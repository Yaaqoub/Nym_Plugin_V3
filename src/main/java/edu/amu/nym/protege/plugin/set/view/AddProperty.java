package edu.amu.nym.protege.plugin.set.view;

import java.util.Set;

import javax.swing.JOptionPane;

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

public class AddProperty {
	
	private String prefix = "";
	
	public AddProperty() {
		
	}
	
	public static boolean save(OWLOntology ontology, OWLOntologyManager manager, String fileName) {
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
	}
	
	public void addDataProperty(OWLOntology ontology, OWLOntologyManager manager, String dataProperty, String individualName, int dataPropertyValue, String ontologyFile) {
		
		for (OWLClass clazz : ontology.getClassesInSignature()) {
			prefix = clazz.getIRI().getNamespace();
		}
		
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLDataProperty hasDataProperty = factory.getOWLDataProperty(IRI.create(prefix + dataProperty));
		OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + individualName));
		OWLAxiom axiom = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, classIndName, dataPropertyValue);
		
		manager.applyChange(new AddAxiom(ontology, axiom));
		AddProperty.save(ontology, manager, ontologyFile);
	}
}
