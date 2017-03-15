package testmodel.provider;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.eclipse.graphiti.util.ILocationInfo;
import org.eclipse.graphiti.util.LocationInfo;

import testmodel.feature.custom.CreateCustomAttributeFeature;

public class ToolBehaviorProvider extends DefaultToolBehaviorProvider implements IToolBehaviorProvider {

	public ToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	@Override
	public ILocationInfo getLocationInfo(PictogramElement pe, ILocationInfo locationInfo) {
		Object domainObject = getFeatureProvider().getBusinessObjectForPictogramElement(pe);

		if (domainObject instanceof Class && locationInfo != null) {
			return new LocationInfo((Shape) pe, locationInfo.getGraphicsAlgorithm()) ;
		}
		
		return super.getLocationInfo(pe, locationInfo);
	}

	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {

		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pe = context.getPictogramElement();

		setGenericContextButtons(data, pe, CONTEXT_BUTTON_DELETE | CONTEXT_BUTTON_UPDATE);

		CreateConnectionContext createConnectionContext = new CreateConnectionContext();
		createConnectionContext.setSourcePictogramElement(pe);
		Anchor anchor = null;
		if (pe instanceof Anchor) {
			anchor = (Anchor) pe;
		} else if (pe instanceof AnchorContainer) {
			anchor = Graphiti.getPeService().getChopboxAnchor((AnchorContainer) pe);
		}
		createConnectionContext.setSourceAnchor(anchor);

		ContextButtonEntry connectionContextButton = new ContextButtonEntry(null, context);
		connectionContextButton.setText("Create connection");
		connectionContextButton.setIconId(FramedImageProvider.IMG_CONNECTION);
		ICreateConnectionFeature[] features = getFeatureProvider().getCreateConnectionFeatures();

		for (ICreateConnectionFeature feature : features) {
			if (feature.isAvailable(createConnectionContext) && feature.canStartConnection(createConnectionContext)) {
				connectionContextButton.addDragAndDropFeature(feature);
			}
		}

		if (connectionContextButton.getDragAndDropFeatures().size() > 0) {
			data.getDomainSpecificContextButtons().add(connectionContextButton);
		}

		CustomContext customContext = new CustomContext(new PictogramElement[] { pe });

		ICustomFeature[] customFeatures = getFeatureProvider().getCustomFeatures(customContext);
		for (ICustomFeature feature : customFeatures) {
			if (feature instanceof CreateCustomAttributeFeature) {
				ContextButtonEntry attributeContextButton = new ContextButtonEntry(feature, customContext);
				attributeContextButton.setText("Create attribute");
				attributeContextButton.setIconId(FramedImageProvider.IMG_ATTRIBUTE);
				data.getDomainSpecificContextButtons().add(attributeContextButton);
			}
		}

		return data;
	}

}
