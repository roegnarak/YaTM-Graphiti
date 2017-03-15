package testmodel.feature.custom;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

import testmodel.Group;

public class CollapseFeature extends AbstractCustomFeature {

	public CollapseFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Collapse.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			// Add more of the objects that collapse here
			if (bo instanceof Group) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public boolean isAvailable(IContext context) {

		return true;
	}

	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);

			if (bo instanceof Group) {
				collapseShape(pes[0]);
			}
		}

	}

	/**
	 * Collapse the shape for the BusinessObject
	 * 
	 * @param pe
	 *            PictogamElement for the shape of the object
	 */
	public void collapseShape(PictogramElement pe) {

		ContainerShape cs = (ContainerShape) pe;
		int width = pe.getGraphicsAlgorithm().getWidth();
		int height = pe.getGraphicsAlgorithm().getHeight();

		int changeWidth = 200;
		int changeHeight = 200;

		boolean showChildren = false;
		IPeService peService = Graphiti.getPeService();

		String steppedIn = peService.getPropertyValue(pe, "steppedIn");

		ResizeShapeContext resizeContext = new ResizeShapeContext(cs);
		if (steppedIn == null || steppedIn.equals("false")) {
			peService.setPropertyValue(pe, "initial_width", String.valueOf(width));
			peService.setPropertyValue(pe, "initial_height", String.valueOf(height));

			peService.setPropertyValue(pe, "initial_x", String.valueOf(cs.getGraphicsAlgorithm().getX()));
			peService.setPropertyValue(pe, "initial_y", String.valueOf(cs.getGraphicsAlgorithm().getY()));

			showChildren = false;
			resizeContext.setSize(getDiagram().getGraphicsAlgorithm().getWidth(),
					getDiagram().getGraphicsAlgorithm().getHeight());
			resizeContext.setLocation(0, 0);

		} else if (steppedIn != null && steppedIn.equals("true")) {
			changeWidth = Integer.parseInt(peService.getPropertyValue(pe, "initial_width"));
			changeHeight = Integer.parseInt(peService.getPropertyValue(pe, "initial_height"));
			peService.setPropertyValue(pe, "steppedIn", "false");
			showChildren = true;
			resizeContext.setSize(changeWidth, changeHeight);
			resizeContext.setLocation(Integer.parseInt(peService.getPropertyValue(pe, "initial_x")),
					Integer.parseInt(peService.getPropertyValue(pe, "initial_y")));
			peService.setPropertyValue(pe, "canResize", "true");
		}

		for (Shape shape : getDiagram().getChildren()) {
			if (!shape.equals(cs)) {
				shape.setVisible(showChildren);
			}
		}

		IResizeShapeFeature rsf = getFeatureProvider().getResizeShapeFeature(resizeContext);
		if (rsf.canExecute(resizeContext)) {
			rsf.execute(resizeContext);
			peService.setPropertyValue(pe, "canResize", Boolean.toString(showChildren));
		}

		if (!showChildren) {
			peService.setPropertyValue(pe, "steppedIn", "true");
		}

		// visible/invisible all the children
		makeChildrenInvisible(cs, showChildren);
	}

	/**
	 * Recursive function that makes all the children inside a shape
	 * visible/invisible
	 * 
	 * @param cs
	 *            ContainerShape
	 * @param visible
	 *            true/false
	 */
	public void makeChildrenInvisible(ContainerShape cs, boolean visible) {
		if (!cs.getChildren().isEmpty()) {
			for (Shape shape : cs.getChildren()) {
				if (shape instanceof ContainerShape) { // It is another shape
					makeChildrenInvisible((ContainerShape) shape, visible);

					shape.setVisible(!visible);
					if (!shape.getAnchors().isEmpty()) {
						Anchor anchr = shape.getAnchors().get(0);
						boolean initVisible = false;

						// Check whether the initial shape is visible or not
						for (Shape childrenShape : ((ContainerShape) shape).getChildren()) {
							if (childrenShape.getGraphicsAlgorithm() instanceof Ellipse) {
								initVisible = childrenShape.isVisible();
							}
						}

						for (int i = 0; i < anchr.getIncomingConnections().size(); i++) {
							Connection conn = anchr.getIncomingConnections().get(i);
							if (initVisible) { // Change visibility only to
												// visible
												// connections
								conn.setVisible(!visible);
								for (int j = 0; j < conn.getConnectionDecorators().size(); j++) {
									conn.getConnectionDecorators().get(j).setVisible(!visible);
								}
							}
						}

						for (int i = 0; i < anchr.getOutgoingConnections().size(); i++) {

							Connection conn = anchr.getOutgoingConnections().get(i);
							conn.setVisible(!visible);
							for (int j = 0; j < conn.getConnectionDecorators().size(); j++) {
								conn.getConnectionDecorators().get(j).setVisible(!visible);
							}
						}
					}
				}
			}
		}
	}
}