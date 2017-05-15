package edu.amu.nym.protege.plugin.set.view;

import javax.swing.DefaultComboBoxModel;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import edu.amu.nym.protege.plugin.get.view.FrameGet;

public class FillIndividualsComboBox {
	
	private DefaultComboBoxModel<Object> individualsComboBoxModel = new DefaultComboBoxModel<Object>();
	
	public FillIndividualsComboBox() {
		
	}
	
	public DefaultComboBoxModel<Object> fillComboBox() {
		OWLOntology ontology = FrameSet.modelManager.getActiveOntology();

		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		OWLDataFactory factory = FrameGet.modelManager.getOWLDataFactory();
		
		int instanceCount = 0;
		
		for (OWLClass c : ontology.getClassesInSignature()) {
			String prefix = c.getIRI().getNamespace();
			NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
			instanceCount = instances.getFlattened().size();
			Object[] test = instances.getFlattened().toArray();
			
			for (int i = 0; i < instanceCount; i++) {
				OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + test[i]));
				individualsComboBoxModel.addElement(classIndName);
			}
			
		}
		return individualsComboBoxModel;
	}
}
