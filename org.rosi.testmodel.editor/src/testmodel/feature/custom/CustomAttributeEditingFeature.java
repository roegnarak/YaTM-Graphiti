package testmodel.feature.custom;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import testmodel.Attribute;
import testmodel.Class;

public class CustomAttributeEditingFeature extends AbstractDirectEditingFeature {

	public CustomAttributeEditingFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Edit attribute.";
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
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
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
		return ((Attribute) getBusinessObjectForPictogramElement(context.getPictogramElement())).getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Attribute must not be empty.";
		}

		Attribute attribute = (Attribute) getBusinessObjectForPictogramElement(context.getPictogramElement());
		EList<Shape> children = getDiagram().getChildren();
		for (Shape shape : children) {
			Object domainObject = getBusinessObjectForPictogramElement(shape);
			if (domainObject instanceof Attribute) {
				if (!domainObject.equals(attribute) && value.equals(((Attribute) domainObject).getName())) {
					return "A attribute with that name already exists.";
				}
			}
		}

		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Attribute attribute = (Attribute) getBusinessObjectForPictogramElement(pe);
		
		if (value.contains(":")) {
			String[] values = value.split(":");
			attribute.setName(values[0]);
	
			EList<EObject> contents = getDiagram().eResource().getContents();
			
			for (EObject content : contents) {
				if (content instanceof Class) {
					Class clazz = (Class) content;
					if (clazz.getName().equals(values[1])) {
						attribute.setType(clazz);
						updatePictogramElement(((Shape) pe).getContainer());
						return;
					}
				}
			}
		} else {
			attribute.setName(value);
		}
		
		updatePictogramElement(((Shape) pe).getContainer());
	}

}
