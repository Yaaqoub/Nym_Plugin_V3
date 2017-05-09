package edu.amu.nym.protege.plugin.get.view;

import java.awt.BorderLayout;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetViewComponent extends AbstractOWLViewComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8542170863085349759L;

	private static final Logger log = LoggerFactory.getLogger(GetViewComponent.class);

    private FrameGet frameGetComponent;

    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        frameGetComponent = new FrameGet(getOWLModelManager());
        add(frameGetComponent, BorderLayout.CENTER);
        log.info("Get View Component initialized");
    }

	@Override
	protected void disposeOWLView() {
		frameGetComponent.dispose();
	}
}
