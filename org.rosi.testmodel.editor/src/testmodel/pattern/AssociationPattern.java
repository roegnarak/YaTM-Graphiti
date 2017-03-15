package testmodel.pattern;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.pattern.IConnectionPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import testmodel.Association;
import testmodel.Attribute;
import testmodel.Class;
import testmodel.TestmodelFactory;

public class AssociationPattern extends AbstractConnectionPattern implements IConnectionPattern {
	
	@Override
	public String getCreateName() {
		return "Association";
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext && context.getNewObject() instanceof Association) {
			return true;
		}

		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IAddConnectionContext addContext = (IAddConnectionContext) context;
		Association addedAssociation = (Association) context.getNewObject();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();

		Connection connection = peCreateService.createFreeFormConnection(getDiagram());
		connection.setStart(addContext.getSourceAnchor());
		connection.setEnd(addContext.getTargetAnchor());

		IGaService gaService = Graphiti.getGaService();
		Polyline solidMidLine = gaService.createPolyline(connection);
		solidMidLine.setLineWidth(1);
		solidMidLine.setForeground(manageColor(IColorConstant.BLACK));

		// create link and wire it
		link(connection, addedAssociation);

		ConnectionDecorator firstLabelDecorator = peCreateService.createConnectionDecorator(connection, true, 0, true);
		Text firstLabelText = gaService.createText(firstLabelDecorator);
		gaService.setLocation(firstLabelText, 10, 0);
		firstLabelText.setValue(addedAssociation.getFirstLabel());

		ConnectionDecorator secondLabelDecorator = peCreateService.createConnectionDecorator(connection, true, 1, true);
		Text secondLabelText = gaService.createText(secondLabelDecorator);
		gaService.setLocation(secondLabelText, -10, 0);
		secondLabelText.setValue(addedAssociation.getSecondLabel());

		return connection;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		// return true if both anchors belong to an Class
		// and those EClasses are not identical
		Class source = getClass(context.getSourceAnchor());
		Class target = getClass(context.getTargetAnchor());
		if (source != null && target != null && source != target) {
			return true;
		}
		return false;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		// get EClasses which should be connected
		Class source = getClass(context.getSourceAnchor());
		Class target = getClass(context.getTargetAnchor());

		// create new business object
		Association association = createAssociation(source, target);

		// add connection for business object
		AddConnectionContext addContext = new AddConnectionContext(context.getSourceAnchor(),
				context.getTargetAnchor());
		addContext.setNewObject(association);
	
		getDiagram().eResource().getContents().add(association);
		
		return (Connection) getFeatureProvider().addIfPossible(addContext);
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		if (getClass(context.getSourceAnchor()) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the EClass belonging to the anchor, or null if not available.
	 */
	private Class getClass(Anchor anchor) {
		if (anchor != null) {
			Object object = getBusinessObjectForPictogramElement(anchor.getParent());
			if (object instanceof Class) {
				return (Class) object;
			}
		}
		return null;
	}

	/**
	 * Creates a EReference between two EClasses.
	 */
	private Association createAssociation(Class source, Class target) {
		Association association = TestmodelFactory.eINSTANCE.createAssociation();
		association.setName("new Association");

		association.setFirstLabel("first");
		association.setSecondLabel("second");

		association.setFirst(source);
		association.setSecond(target);
		
		return association;
	}

}
