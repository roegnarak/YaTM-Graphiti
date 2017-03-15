package testmodel.pattern;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.StyledString.Style;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.algorithms.styles.RenderingStyle;
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
import org.eclipse.ui.internal.keys.model.ModelElement;

import testmodel.Attribute;
import testmodel.Class;
import testmodel.Group;
import testmodel.TestmodelFactory;

public class GroupPattern extends IdPattern implements IPattern {

	private static final IColorConstant TEXT_FOREGROUND = IColorConstant.BLACK;

	private static final IColorConstant CLASS_FOREGROUND = IColorConstant.BLACK;

	private static final IColorConstant CLASS_BACKGROUND = IColorConstant.WHITE;

	private static final String ID_OUTER_RECTANGLE = "outerGroupRectangle";
	private static final String ID_MAIN_RECTANGLE = "mainGroupRectangle";

	private static final String ID_GROUP_NAME_TEXT = "groupName";

	private static final String ID_NAME_SEPARATOR = "nameSeparator";

	private static final String ID_CLASSES_NAME_RECTANGLE = "classesNameRectangle";
	private static final String ID_CLASSES_NAME_TEXT = "classesNameText";

	@Override
	public String getCreateName() {
		return "Group";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Group;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		if (context.getTargetContainer() instanceof Diagram) {
			return true;
		} else if (context.getTargetContainer() instanceof ContainerShape) {
			if (context.getTargetContainer() instanceof ContainerShape) {
				Object bo = getBusinessObjectForPictogramElement(context.getTargetContainer());
				
				if (bo instanceof Group) {
					IPeService peService = Graphiti.getPeService();
					String steppedIn = peService.getPropertyValue(context.getTargetContainer(), "steppedIn");
					
					if (steppedIn != null && steppedIn.equals("true")) {
						return true;
					}
				}
			}
		}
		
		return  false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Group createdGroup = TestmodelFactory.eINSTANCE.createGroup();
		getDiagram().eResource().getContents().add(createdGroup);

		getFeatureProvider().getDirectEditingInfo().setActive(true);

		addGraphicalRepresentation(context, createdGroup);
		return new Object[] { createdGroup };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		boolean retValue = false;

		if (context.getNewObject() instanceof Group) {
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
			ContainerShape targetContainer = Graphiti.getPeCreateService().createContainerShape((Diagram) context.getTargetContainer(), true);
			return addGroup(context, targetContainer);
		} else if (context.getTargetContainer() instanceof ContainerShape) {
			return addGroup(context, context.getTargetContainer());
		}
		
		return null;
	}
	
	private PictogramElement addGroup(IAddContext context, final ContainerShape target) {
		Group addedDomainObject = (Group) context.getNewObject();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		int height = context.getHeight() <= 0 ? 50 : context.getHeight();

		ContainerShape outerContainerShape = peCreateService.createContainerShape(target, true);

		// invisible outer rectangle
		Rectangle outerRectangle = gaService.createInvisibleRectangle(outerContainerShape);
		setId(outerRectangle, ID_OUTER_RECTANGLE);
		gaService.setLocationAndSize(outerRectangle, context.getX(), context.getY(), width, height);

		// Main contents area
		int edgeDiff = 15;
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerRectangle, edgeDiff, edgeDiff);
		mainRectangle.setParentGraphicsAlgorithm(outerRectangle);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		mainRectangle.setBackground(manageColor(IColorConstant.WHITE));

		// Class name
		Shape textShape = peCreateService.createShape(outerContainerShape, false);
		Text domainObjectNameText = gaService.createText(textShape, "");
		setId(domainObjectNameText, ID_GROUP_NAME_TEXT);
		domainObjectNameText.setForeground(manageColor(TEXT_FOREGROUND));
		domainObjectNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		domainObjectNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// Separating line
		Shape lineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline polyline = gaService.createPolyline(lineShape);
		setId(polyline, ID_NAME_SEPARATOR);
		polyline.setForeground(manageColor(IColorConstant.BLACK));

		// List of files in folder
		ContainerShape attributesContainerShape = peCreateService.createContainerShape(outerContainerShape, false);
		Rectangle attributesRectangle = gaService.createInvisibleRectangle(attributesContainerShape);
		setId(attributesRectangle, ID_CLASSES_NAME_RECTANGLE);

		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedDomainObject);
		link(textShape, addedDomainObject);
		link(attributesContainerShape, addedDomainObject);

		IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		directEditingInfo.setMainPictogramElement(outerContainerShape);
		directEditingInfo.setPictogramElement(textShape);
		directEditingInfo.setGraphicsAlgorithm(domainObjectNameText);

		return outerContainerShape;

	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		
		return super.canLayout(context);
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		Rectangle outerRectangle = (Rectangle) context.getRootPictogramElement().getGraphicsAlgorithm();

		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (id.equals(ID_MAIN_RECTANGLE)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 10, outerRectangle.getWidth(), outerRectangle.getHeight() - 10);
			changesDone = true;
		} else if (id.equals(ID_GROUP_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 10, outerRectangle.getWidth(), 20);
			changesDone = true;
		} else if (id.equals(ID_NAME_SEPARATOR)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, 30, outerRectangle.getWidth(), 30 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_GROUP_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Group domainObject = (Group) context.getDomainObject();
			if (domainObject.getName() == null || !domainObject.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs.");
			}
		} else if (id.equals(ID_CLASSES_NAME_RECTANGLE)) {
			ContainerShape classesContainerShape = (ContainerShape) context.getPictogramElement();
			Group group = (Group) context.getDomainObject();
			if (classesContainerShape.getChildren().size() != group.getElements().size()) {
				return Reason.createTrueReason("Number of classes differ.");
			}
		}

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_GROUP_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Group domainObject = (Group) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		} else if (id.equals(ID_CLASSES_NAME_RECTANGLE)) {
			EList<Shape> children = ((ContainerShape) context.getPictogramElement()).getChildren();
			Shape[] toDelete = children.toArray(new Shape[children.size()]);
			for (Shape shape : toDelete) {
				EcoreUtil.delete(shape, true);
			}
			EList<testmodel.ModelElement> classes = ((Group) context.getDomainObject()).getElements();
			int index = 0;
			for (testmodel.ModelElement clazz : classes) {
				Shape shape = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(),
						true);
				Text classNameText = Graphiti.getGaService().createText(shape, clazz.getName());
				setId(classNameText, ID_CLASSES_NAME_TEXT);
				setIndex(classNameText, index);
				index++;
				classNameText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				classNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				classNameText.setForeground(manageColor(TEXT_FOREGROUND));
				link(shape, clazz);
			}
			return true;
		}
		
		return false;
	}

	@Override
	protected void setValue(String value, IDirectEditingContext context, String id) {
		Group object = (Group) getBusinessObjectForPictogramElement(context.getPictogramElement());
		object.setName(value);
		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		return ((Group) getBusinessObjectForPictogramElement(context.getPictogramElement())).getName();
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Groupname must not be empty.";
		}

		Group group = (Group) getBusinessObjectForPictogramElement(context.getPictogramElement());
		EList<Shape> children = getDiagram().getChildren();
		for (Shape shape : children) {
			Object domainObject = getBusinessObjectForPictogramElement(shape);
			if (domainObject instanceof Group) {
				if (!domainObject.equals(group) && value.equals(((Group) domainObject).getName())) {
					return "A group with that name already exists.";
				}
			}
		}

		return null;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();

		if (domainObject instanceof Group && ga instanceof Text) {
			return true;
		}

		return false;
	}
}
