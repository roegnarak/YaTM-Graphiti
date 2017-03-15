package testmodel.pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import testmodel.Attribute;
import testmodel.Class;
import testmodel.TestmodelFactory;

public class AttributePattern extends IdPattern implements IPattern {

	 private static final String ID_NAME_TEXT = "nameText"; 
	 private static final String ID_OUTER_RECTANGLE = "outerRectangle"; 
	 private static final String ID_MAIN_RECTANGLE = "mainRectangle"; 
	
	@Override
	public String getCreateName() {
		return "Attribute";
	}
	
	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Attribute;
	}
	
	@Override
	public boolean canCreate(ICreateContext context) {
		ContainerShape targetContainer = context.getTargetContainer();

		if (targetContainer instanceof ContainerShape) {
			Object businessObject = getBusinessObjectForPictogramElement(targetContainer);
			if (businessObject instanceof testmodel.Class) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Class clazz = (Class) getBusinessObjectForPictogramElement(context.getTargetContainer());
		
		Attribute createdAttribute = TestmodelFactory.eINSTANCE.createAttribute();
		clazz.eResource().getContents().add(createdAttribute);
		clazz.getAttributes().add(createdAttribute);
		
		addGraphicalRepresentation(context, createdAttribute);

		getFeatureProvider().getDirectEditingInfo().setActive(true);
		
		return new Object[] { createdAttribute };
	}

	public boolean canAdd(IAddContext context) {
		if (context.getNewObject() instanceof Attribute) {
			if (context.getTargetContainer() instanceof ContainerShape) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected PictogramElement doAdd(IAddContext context) {
		ContainerShape containerShape = context.getTargetContainer();
		Attribute attribute = (Attribute) context.getNewObject();
		
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();
		
		int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		
		Shape shape = peCreateService.createShape(containerShape, false);
		
		Text text = gaService.createText(shape, attribute.getName() + " : " + attribute.getType().getName());
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setForeground(manageColor(IColorConstant.BLACK));
		gaService.setLocationAndSize(text, 0, context.getY(), 20, 20);

//		Shape separatorShape = peCreateService.createShape(containerShape, false);
//		Text separator = gaService.createText(separatorShape, ":");
//		separator.setForeground(manageColor(IColorConstant.BLACK));
//		gaService.setLocationAndSize(separator, text.getX() + text.getWidth(), context.getY(), 5, 20);
//		
		peCreateService.createChopboxAnchor(containerShape);
		
		link(shape, attribute);
//		link(separatorShape, attribute);
		
		return containerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		return null;
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		return false;
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Classname must not be empty.";
		}

		Attribute attribute = (Attribute) getBusinessObjectForPictogramElement(context.getPictogramElement());
		EList<Shape> children = getDiagram().getChildren();
		for (Shape shape : children) {
			Object domainObject = getBusinessObjectForPictogramElement(shape);
			if (domainObject instanceof Attribute) {
				if (!domainObject.equals(attribute) && value.equals(((Attribute) domainObject).getName())) {
					return "An attribute with that name already exists.";
				}
			}
		}

		return null;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		
		if (domainObject instanceof Attribute && ga instanceof Text) {
			return true;
		}
		
		return false;
	}
	
}
