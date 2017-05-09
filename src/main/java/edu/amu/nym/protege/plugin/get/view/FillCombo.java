package edu.amu.nym.protege.plugin.get.view;

import javax.swing.DefaultComboBoxModel;

public class FillCombo {
		
	private DefaultComboBoxModel<Object> classesComboBoxModel = new DefaultComboBoxModel<Object>();

	public FillCombo() {
		
	}
	
	public DefaultComboBoxModel<Object> fillComboBox() {
		int ClassesCount = FrameGet.modelManager.getActiveOntology().getClassesInSignature().size();
		Object[] test = FrameGet.modelManager.getActiveOntology().getClassesInSignature().toArray();
		
		for (int i = 0; i < ClassesCount; i++){
			classesComboBoxModel.addElement(test[i]);
    	}
		
		return classesComboBoxModel;
	}
}
