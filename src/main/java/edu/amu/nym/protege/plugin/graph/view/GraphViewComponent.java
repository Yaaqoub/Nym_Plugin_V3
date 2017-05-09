package edu.amu.nym.protege.plugin.graph.view;

import java.awt.BorderLayout;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GraphViewComponent extends AbstractOWLViewComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3713088175935483553L;

	private static final Logger log = LoggerFactory.getLogger(GraphViewComponent.class);

    private FrameGraph frameGraphComponent;

    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        frameGraphComponent = new FrameGraph(getOWLModelManager());
        add(frameGraphComponent, BorderLayout.CENTER);
        log.info("Get View Component initialized");
    }

	@Override
	protected void disposeOWLView() {
		frameGraphComponent.dispose();
	}
}
