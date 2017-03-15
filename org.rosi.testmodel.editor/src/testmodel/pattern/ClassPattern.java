package testmodel.pattern;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.util.IColorConstant;

import testmodel.Attribute;
import testmodel.Class;
import testmodel.Group;
import testmodel.TestmodelFactory;

public class ClassPattern extends IdPattern implements IPattern {

	private static final IColorConstant TEXT_FOREGROUND = IColorConstant.BLACK;

	private static final IColorConstant CLASS_FOREGROUND = IColorConstant.BLACK;

	private static final IColorConstant CLASS_BACKGROUND = IColorConstant.WHITE;

	private static final String ID_OUTER_RECTANGLE = "outerClassRectangle";
	private static final String ID_MAIN_RECTANGLE = "mainClassRectangle";

	private static final String ID_CLASS_NAME_TEXT = "className";

	private static final String ID_NAME_SEPARATOR = "nameSeparator";

	private static final String ID_ATTRIBUTE_NAME_RECTANGLE = "attributeNameRectangle";
	private static final String ID_ATTRIBUTE_NAME_TEXT = "attributeNameText";
	private static final String ID_ATTRIBUTE_DOT_TEXT = "attributeNameText";
	
	public ClassPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "Class";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Class;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		ContainerShape targetContainer = context.getTargetContainer();
		
		if (targetContainer instanceof Diagram) {
			return true;
		}
		
		if (targetContainer instanceof ContainerShape) {
			Object bo = getBusinessObjectForPictogramElement(targetContainer);
			
			if (bo instanceof Group) {
				IPeService peService = Graphiti.getPeService();
				String steppedIn = peService.getPropertyValue(targetContainer, "steppedIn");
				
				if (steppedIn != null && steppedIn.equals("true")) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Class createdClass = TestmodelFactory.eINSTANCE.createClass();
		context.getTargetContainer().eResource().getContents().add(createdClass);

		getFeatureProvider().getDirectEditingInfo().setActive(true);
		
		addGraphicalRepresentation(context, createdClass);
		return new Object[] { createdClass };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		boolean retValue = false;

		if (context.getNewObject() instanceof Class) {
			if (context.getTargetContainer() instanceof Diagram) {
				retValue = true;
			}

			if (context.getTargetContainer() instanceof ContainerShape) {
				retValue = true;
			}
		}

		return retValue;
	}

	@Override
	protected PictogramElement doAdd(IAddContext context) {
		if (context.getTargetContainer() instanceof Diagram) {
			return addIfTargetContainerIsDiagram(context);
		} else if (context.getTargetContainer() instanceof ContainerShape) {
			return addIfTargetContainerIsGroup(context);
		}
		
		return null;
	}

	private PictogramElement addIfTargetContainerIsGroup(IAddContext context) {
		ContainerShape target = context.getTargetContainer();
		Class addedClass = (Class) context.getNewObject();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		ContainerShape outerContainerShape = peCreateService.createContainerShape(target, true);
		
		int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		int height = context.getHeight() <= 0 ? 50 : context.getHeight();

		// invisible outer rectangle
		Rectangle outerRectangle = gaService.createInvisibleRectangle(outerContainerShape);
		setId(outerRectangle, ID_OUTER_RECTANGLE);
		gaService.setLocationAndSize(outerRectangle, context.getX(), context.getY(), width, height);

		// Main contents area
		Rectangle mainRectangle = gaService.createRectangle(outerRectangle);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		mainRectangle.setBackground(manageColor(IColorConstant.WHITE));

		// Class name
		Shape textShape = peCreateService.createShape(outerContainerShape, false);
		Text classNameText = gaService.createText(textShape, "");
		setId(classNameText, ID_CLASS_NAME_TEXT);
		classNameText.setForeground(manageColor(TEXT_FOREGROUND));
		classNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		classNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// Separating line
		Shape lineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline polyline = gaService.createPolyline(lineShape);
		setId(polyline, ID_NAME_SEPARATOR);
		polyline.setForeground(manageColor(IColorConstant.BLACK));

		// List of attributes in class
		ContainerShape attributesContainerShape = peCreateService.createContainerShape(outerContainerShape, false);
		Rectangle attributesRectangle = gaService.createInvisibleRectangle(attributesContainerShape);
		setId(attributesRectangle, ID_ATTRIBUTE_NAME_RECTANGLE);

		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedClass);
		link(textShape, addedClass);
		link(attributesContainerShape, addedClass);

		IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		directEditingInfo.setMainPictogramElement(outerContainerShape);
		directEditingInfo.setPictogramElement(textShape);
		directEditingInfo.setGraphicsAlgorithm(classNameText);
		
		return outerContainerShape;
	}
	
	private ContainerShape addIfTargetContainerIsDiagram(IAddContext context) {
		ContainerShape outerContainerShape = Graphiti.getPeCreateService().createContainerShape((Diagram) context.getTargetContainer(), true);		
		Class addedClass = (Class) context.getNewObject();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		int height = context.getHeight() <= 0 ? 50 : context.getHeight();

		// invisible outer rectangle
		Rectangle outerRectangle = gaService.createInvisibleRectangle(outerContainerShape);
		setId(outerRectangle, ID_OUTER_RECTANGLE);
		gaService.setLocationAndSize(outerRectangle, context.getX(), context.getY(), width, height);

		// Main contents area
		Rectangle mainRectangle = gaService.createRectangle(outerRectangle);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		mainRectangle.setBackground(manageColor(IColorConstant.WHITE));

		// Class name
		Shape textShape = peCreateService.createShape(outerContainerShape, false);
		Text classNameText = gaService.createText(textShape, "");
		setId(classNameText, ID_CLASS_NAME_TEXT);
		classNameText.setForeground(manageColor(TEXT_FOREGROUND));
		classNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		classNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// Separating line
		Shape lineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline polyline = gaService.createPolyline(lineShape);
		setId(polyline, ID_NAME_SEPARATOR);
		polyline.setForeground(manageColor(IColorConstant.BLACK));

		// List of attributes in class
		ContainerShape attributesContainerShape = peCreateService.createContainerShape(outerContainerShape, false);
		Rectangle attributesRectangle = gaService.createInvisibleRectangle(attributesContainerShape);
		setId(attributesRectangle, ID_ATTRIBUTE_NAME_RECTANGLE);

		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedClass);
		link(textShape, addedClass);
		link(attributesContainerShape, addedClass);

		IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		directEditingInfo.setMainPictogramElement(outerContainerShape);
		directEditingInfo.setPictogramElement(textShape);
		directEditingInfo.setGraphicsAlgorithm(classNameText);

		return outerContainerShape;
	}
	
	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		Rectangle outerRectangle = (Rectangle) context.getRootPictogramElement().getGraphicsAlgorithm();

		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (id.equals(ID_MAIN_RECTANGLE)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 10, outerRectangle.getWidth(),
					outerRectangle.getHeight() - 10);
			changesDone = true;
		} else if (id.equals(ID_CLASS_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 10, outerRectangle.getWidth(), 20);
			changesDone = true;
		} else if (id.equals(ID_NAME_SEPARATOR)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, 30, outerRectangle.getWidth(), 30 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_ATTRIBUTE_NAME_RECTANGLE)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 30, outerRectangle.getWidth(),
					outerRectangle.getHeight() - 30);
			changesDone = true;
		} else if (id.equals(ID_ATTRIBUTE_NAME_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 5, 30 + 20 * index, outerRectangle.getWidth() - 10, 20);
			changesDone = true;
		}

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_CLASS_NAME_TEXT)) {
			Text classNameText = (Text) context.getGraphicsAlgorithm();
			Class clazz = (Class) context.getDomainObject();
			if (clazz.getName() == null || !clazz.getName().equals(classNameText.getValue())) {
				return Reason.createTrueReason("Name differs.");
			}
		} else if (id.equals(ID_ATTRIBUTE_NAME_RECTANGLE)) {
			ContainerShape attributeContainerShape = (ContainerShape) context.getPictogramElement();
			Class clazz = (Class) context.getDomainObject();
			if (attributeContainerShape.getChildren().size() != clazz.getAttributes().size()) {
				return Reason.createTrueReason("Number of attributes differ.");
			}
		} else if (id.equals(ID_ATTRIBUTE_NAME_TEXT)) {
			Text attributeNameText = (Text) context.getGraphicsAlgorithm();
			Attribute attribute = (Attribute) context.getDomainObject();
			if (attribute.getName() == null || !attribute.getName().equals(attributeNameText.getValue())) {
				return Reason.createTrueReason("Attribute name differs.");
			}
		}

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_CLASS_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Class domainObject = (Class) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		} else if (id.equals(ID_ATTRIBUTE_NAME_RECTANGLE)) {
			EList<Shape> children = ((ContainerShape) context.getPictogramElement()).getChildren();
			Shape[] toDelete = children.toArray(new Shape[children.size()]);
			for (Shape shape : toDelete) {
				EcoreUtil.delete(shape, true);
			}
			EList<Attribute> attributes = ((Class) context.getDomainObject()).getAttributes();
			int index = 0;
			for (Attribute attribute : attributes) {
				IPeCreateService peCreateService = Graphiti.getPeCreateService();
				IGaService gaService = Graphiti.getGaService();
				
				Shape textShape = peCreateService.createShape((ContainerShape) context.getPictogramElement(),
						true);
				Text attributeNameText;
				if (attribute.getType() != null) {
					attributeNameText = gaService.createText(textShape, attribute.getName() + " : " + attribute.getType().getName());
				} else {
					attributeNameText = gaService.createText(textShape, attribute.getName());
				}
				setId(attributeNameText, ID_ATTRIBUTE_NAME_TEXT);
				setIndex(attributeNameText, index);
				index++;
				attributeNameText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				attributeNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				attributeNameText.setForeground(manageColor(TEXT_FOREGROUND));
				link(textShape, attribute);
			}
			return true;
		} else if (id.equals(ID_ATTRIBUTE_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Attribute attribute = (Attribute) context.getDomainObject();
			nameText.setValue(attribute.getName());
			nameText.setForeground(manageColor(TEXT_FOREGROUND));
			return true;
		}
		return false;
	}

	@Override
	protected void setValue(String value, IDirectEditingContext context, String id) {
		Class object = (Class) getBusinessObjectForPictogramElement(context.getPictogramElement());
		object.setName(value);
		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		return ((Class) getBusinessObjectForPictogramElement(context.getPictogramElement())).getName();
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

		Class clazz = (Class) getBusinessObjectForPictogramElement(context.getPictogramElement());
		EList<Shape> children = getDiagram().getChildren();
		for (Shape shape : children) {
			Object domainObject = getBusinessObjectForPictogramElement(shape);
			if (domainObject instanceof Class) {
				if (!domainObject.equals(clazz) && value.equals(((Class) domainObject).getName())) {
					return "A class with that name already exists.";
				}
			}
		}

		return null;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		
		if (domainObject instanceof Class && ga instanceof Text) {
			return true;
		}
		
		return false;
	}

	private RoundedRectangle createRoundedRectangle(IAddContext context, ContainerShape containerShape, int cornerWidht,
			int cornerHeight, int width, int height) {
		IGaService gaService = Graphiti.getGaService();

		RoundedRectangle rectangle = gaService.createRoundedRectangle(containerShape, 5, 5);
		rectangle.setForeground(manageColor(CLASS_FOREGROUND));
		rectangle.setBackground(manageColor(CLASS_BACKGROUND));
		rectangle.setLineWidth(1);

		gaService.setLocationAndSize(rectangle, context.getX(), context.getY(), width, height);

		return rectangle;
	}

}
