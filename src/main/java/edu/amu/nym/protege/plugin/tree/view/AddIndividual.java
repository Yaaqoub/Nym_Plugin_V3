package edu.amu.nym.protege.plugin.tree.view;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AddIndividual {
	
	public AddIndividual(OWLOntology ontology, OWLOntologyManager manager, String className) {
		
		OWLDataFactory factory = manager.getOWLDataFactory();
		
		for (OWLClass c : ontology.getClassesInSignature()) {
			String prefix = c.getIRI().getNamespace();
			
			OWLClass clsPSS = factory.getOWLClass(IRI.create(prefix + ":" + className));
			
			OWLNamedIndividual maRoyaleSansOlive = factory.getOWLNamedIndividual(IRI.create(prefix + ":myIndividual"));
			
			OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(clsPSS, maRoyaleSansOlive);
			
			manager.addAxiom(ontology, classAssertion);
		}
	}
	
	
}
