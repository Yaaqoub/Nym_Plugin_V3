package edu.amu.nym.protege.plugin.get.view;

import java.util.Set;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class FillIndividualsTable {

	public static Object[] storeIndiv = null;
	
	public FillIndividualsTable() {
		
	}
	
	@SuppressWarnings("deprecation")
	public DefaultTableModel  buildTableModel(OWLOntology ontology, String className) {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("Individual");
		columnNames.addElement("IDn");
		columnNames.addElement("Name");
		columnNames.addElement("Typology Name");
 
		Vector<Object> rows = null;
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		 
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		int instanceCount = 0;
		OWLDataFactory factory = FrameGet.modelManager.getOWLDataFactory();
		
		for (OWLClass c : ontology.getClassesInSignature()){
			if (c.getIRI().getFragment().equals(className)){
				String prefix = c.getIRI().getNamespace();
				NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
				instanceCount = instances.getFlattened().size();
				Object[] test = instances.getFlattened().toArray();
				storeIndiv = new Object[instanceCount];
		    	for (int i = 0; i < instanceCount; i++){
		    		OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + test[i]));
		    		storeIndiv[i] = classIndName;

		    		OWLDataProperty hasIDnProperty = factory.getOWLDataProperty(IRI.create(prefix + "hasIdn"));
		    		OWLDataProperty hasNameProperty = factory.getOWLDataProperty(IRI.create(prefix + "hasName"));
		    		OWLDataProperty hasTypologyNameProperty = factory.getOWLDataProperty(IRI.create(prefix + "hasTypologyName"));
		    		
		    		Set<OWLLiteral> nameV = reasoner.getDataPropertyValues(classIndName, hasNameProperty);
		    		Set<OWLLiteral> idnV = reasoner.getDataPropertyValues(classIndName, hasIDnProperty);
		    		Set<OWLLiteral> typologyNameV = reasoner.getDataPropertyValues(classIndName, hasTypologyNameProperty);

		    		
		    		rows = new Vector<Object>();
		    		rows.add(classIndName);
		    		
		    		for (OWLLiteral idn : reasoner.getDataPropertyValues(classIndName, hasIDnProperty)) {
		    			rows.add(idn.getLiteral());
		    		}
		    		
		    		if (idnV.size() == 0) {
		    			rows.add(null);
		    		}
		    		
		    		for (OWLLiteral name : reasoner.getDataPropertyValues(classIndName, hasNameProperty)){
		    			rows.add(name.getLiteral());
		    		}
		    		
		    		if (nameV.size() == 0) {
		    			rows.add(null);
		    		}
		    		
		    		for (OWLLiteral typologyName : reasoner.getDataPropertyValues(classIndName, hasTypologyNameProperty)) {
		    			rows.add(typologyName.getLiteral());
		    		}
		    		
		    		if (typologyNameV.size() == 0) {
		    			rows.add(null);
		    		}
		    		
		    		rowData.add(rows);
		    	}
			}
		}		
		return new DefaultTableModel(rowData, columnNames);
	}
}
