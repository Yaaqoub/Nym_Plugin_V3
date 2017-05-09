package edu.amu.nym.protege.plugin.tree.view;

import java.awt.BorderLayout;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeViewComponent extends AbstractOWLViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 836235996021843578L;

	private static final Logger log = LoggerFactory.getLogger(TreeViewComponent.class);

    private FrameTree frameTreeComponent;

    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        frameTreeComponent = new FrameTree(getOWLModelManager());
        add(frameTreeComponent, BorderLayout.CENTER);
        log.info("Tree View Component initialized");
    }

	@Override
	protected void disposeOWLView() {
		frameTreeComponent.dispose();
	}
}
