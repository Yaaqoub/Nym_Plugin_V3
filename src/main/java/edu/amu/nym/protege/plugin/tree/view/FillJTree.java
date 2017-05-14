package edu.amu.nym.protege.plugin.tree.view;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class FillJTree {

	
	private DefaultTreeModel tree;
	
	public FillJTree() {
		
	}
	
	@SuppressWarnings("deprecation")
	public TreeModel fillJTree() {
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("owl:Thing");
		
		OWLOntology ontology = FrameTree.modelManager.getActiveOntology();
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		OWLDataFactory factory = FrameTree.modelManager.getOWLDataFactory();

		int instanceCount = 0;
		int ClassesCount = FrameTree.modelManager.getActiveOntology().getClassesInSignature().size();
		
		Object[] test = FrameTree.modelManager.getActiveOntology().getClassesInSignature().toArray();
		
		for (int i = 0; i < ClassesCount; i++){
			
			DefaultMutableTreeNode rep = new DefaultMutableTreeNode(test[i]);

			for (OWLClass c : ontology.getClassesInSignature()){
				String prefix = c.getIRI().getNamespace();
				NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
				instanceCount = instances.getFlattened().size();
				Object[] test2 = instances.getFlattened().toArray();
		    	for (int x = 0; x < instanceCount; x++){	
		    		OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + test2[x]));
		    		if(c.getIRI().getFragment().toString().equals(test[i].toString()))
		    			rep.add(new DefaultMutableTreeNode(classIndName));
		    	}
			}

			racine.add(rep);
    	}
		
		tree = new DefaultTreeModel(racine);
		return tree;
	}
}
