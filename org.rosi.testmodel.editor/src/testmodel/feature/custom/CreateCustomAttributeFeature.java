package testmodel.feature.custom;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import testmodel.Attribute;
import testmodel.Class;
import testmodel.TestmodelFactory;

public class CreateCustomAttributeFeature extends AbstractCustomFeature {

	public CreateCustomAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Create Attribute";
	}

	@Override
	public String getDescription() {
		return "Creates attribute inside of this class";
	}

	@Override
	public boolean isAvailable(IContext context) {
		return getClassDomainObject((ICustomContext) context) != null;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return getClassDomainObject(context) != null;
	}

	public void execute(ICustomContext context) {
		Class clazz = getClassDomainObject(context);
		Attribute attribute = TestmodelFactory.eINSTANCE.createAttribute();
		attribute.setName("New Attribute");
		clazz.eResource().getContents().add(attribute);
		clazz.getAttributes().add(attribute);
	}

	private Class getClassDomainObject(ICustomContext context) {
		PictogramElement[] pictogramElements = context.getPictogramElements();
		if (pictogramElements != null && pictogramElements.length == 1) {
			Object domainObject = getBusinessObjectForPictogramElement(pictogramElements[0]);
			if (domainObject instanceof Class) {
				return (Class) domainObject;
			}
		}
		return null;
	}

}
