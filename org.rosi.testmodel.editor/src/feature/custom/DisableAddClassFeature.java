package feature.custom;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

import testmodel.Class;
import testmodel.TestmodelFactory;
import util.CustomFeatureUtil;

public class DisableAddClassFeature extends AbstractCustomFeature {

	public DisableAddClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Disable add class feature.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		IPeService peCreateService = Graphiti.getPeService();
		
		PictogramElement[] pictogramElements = context.getPictogramElements();
		if (CustomFeatureUtil.isSinglePictogramElement(pictogramElements)) {
			//ContainerShape containerShape = (ContainerShape) pictogramElements[0];
			
			//peCreateService.setPropertyValue(pictogramElements[0], "disabledAddClassFeature", "true");
			
			Class createdClass = TestmodelFactory.eINSTANCE.createClass();
			createdClass.setName("DisableAddClassFeature");
			
			getDiagram().eResource().getContents().add(createdClass);
		}
	}
}
