package edu.amu.nym.protege.plugin.set.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("deprecation")
public class SaveOntology {
	public SaveOntology () {
		
	}
	
	public void save(OWLOntologyManager manager, OWLOntology ontology) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File("myFile.owl"));
		int rVal = fileChooser.showSaveDialog(null);
		
		if (rVal == JFileChooser.APPROVE_OPTION) {
			String savePath = fileChooser.getSelectedFile().getAbsolutePath();
			try {
				File f = new File(savePath);
				manager.saveOntology(ontology, new OWLFunctionalSyntaxOntologyFormat(), IRI.create(f));
		        JOptionPane.showMessageDialog(null, "Your Ontology has been Saved Successfully");
				
			} catch (OWLOntologyStorageException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
	}
}
