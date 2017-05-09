package edu.amu.nym.protege.plugin.set.view;

import java.awt.BorderLayout;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetViewComponent extends AbstractOWLViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4745650719222639842L;

	private static final Logger log = LoggerFactory.getLogger(SetViewComponent.class);

    private FrameSet frameSetComponent;

    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        frameSetComponent = new FrameSet(getOWLModelManager());
        add(frameSetComponent, BorderLayout.CENTER);
        log.info("Set View Component initialized");
    }

	@Override
	protected void disposeOWLView() {
		frameSetComponent.dispose();
	}
}
