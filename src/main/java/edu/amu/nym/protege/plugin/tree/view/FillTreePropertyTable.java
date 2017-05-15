package edu.amu.nym.protege.plugin.tree.view;

import java.util.Set;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;

public class FillTreePropertyTable {
	
	public FillTreePropertyTable() {
		
	}
	
	public DefaultTableModel  buildTableModel(OWLOntology ontology, String indvName) {
		Vector<String> columnNames = new Vector<String>();
		 columnNames.addElement("Data Property");
		 columnNames.addElement("Value");
		 
		 Vector<Object> rows = null;
		 Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		 
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		int instanceCount = 0;
		OWLDataFactory factory = FrameTree.modelManager.getOWLDataFactory();
		
		for (OWLClass c : ontology.getClassesInSignature()){
			NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
			
			for (OWLNamedIndividual i : instances.getFlattened()){
				if(i.getIRI().getFragment().equals(indvName)) {
					OWLNamedIndividual input = factory.getOWLNamedIndividual(IRI.create(i.getIRI().toString()));
					Set<OWLDataPropertyAssertionAxiom> properties = ontology.getDataPropertyAssertionAxioms(input);

					for (OWLDataPropertyAssertionAxiom ax: properties) {
						for (OWLLiteral propertyLit :  EntitySearcher.getDataPropertyValues(i, ax.getProperty(), ontology)){
							//JOptionPane.showMessageDialog(null, "Property: " + ax.getProperty() + " Value: " + propertyLit.getLiteral());
							rows = new Vector<Object>();
			    			rows.add(ax.getProperty());
			    			rows.add(propertyLit.getLiteral());
						 }
						rowData.add(rows);
					}
					
				}
			}
		}
		
		return new DefaultTableModel(rowData, columnNames);
	}
}
