package edu.amu.nym.protege.plugin.set.view;

import java.util.Set;

import javax.swing.JOptionPane;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
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

public class ChangeValueTest {
	
	public ChangeValueTest(OWLOntology ontology, OWLOntologyManager manager, String indvName, String dataProperty, String value) {
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		OWLDataFactory factory = manager.getOWLDataFactory();
		
		//Delete DataProperty
		for (OWLClass c : ontology.getClassesInSignature()){
			NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
			
			for (OWLNamedIndividual i : instances.getFlattened()){
				if(i.getIRI().getFragment().equals(indvName)) {
					OWLNamedIndividual input = factory.getOWLNamedIndividual(IRI.create(i.getIRI().toString()));
					Set<OWLDataPropertyAssertionAxiom> properties = ontology.getDataPropertyAssertionAxioms(input);
					
					for (OWLDataPropertyAssertionAxiom ax: properties) {
						for (OWLLiteral propertyLit :  EntitySearcher.getDataPropertyValues(i, ax.getProperty(), ontology)){
							if(ax.getProperty().toString().equals(dataProperty)){								
								manager.removeAxiom(ontology, ax);
							}
						 }
					}
				}
			}
		}
		//End Delete DataProperty
		
		//Add the same one with new value
		for (OWLClass c : ontology.getClassesInSignature()) {
			String prefix = c.getIRI().getNamespace();
			OWLAxiom axiom = null;
			
			OWLDataProperty hasSomethingProperty = factory.getOWLDataProperty(IRI.create(prefix + dataProperty));
			OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + indvName));
			
			axiom = factory.getOWLDataPropertyAssertionAxiom(hasSomethingProperty, classIndName, value);
			
			manager.applyChange(new AddAxiom(ontology, axiom));
		}
		//End add
	}
	
	
}
