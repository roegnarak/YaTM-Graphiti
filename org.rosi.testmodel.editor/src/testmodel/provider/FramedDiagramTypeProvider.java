package testmodel.provider;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class FramedDiagramTypeProvider extends AbstractDiagramTypeProvider implements IDiagramTypeProvider {

	public IToolBehaviorProvider[] toolBehaviorProviders;
	
	public FramedDiagramTypeProvider() {
		super();

		setFeatureProvider(new FramedFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new ToolBehaviorProvider(this) };
		}

		return toolBehaviorProviders;
	}
	
	

}
