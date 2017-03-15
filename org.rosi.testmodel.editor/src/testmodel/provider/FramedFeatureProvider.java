package testmodel.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import testmodel.Attribute;
import testmodel.feature.custom.CollapseFeature;
import testmodel.feature.custom.CreateCustomAttributeFeature;
import testmodel.feature.custom.CustomAttributeEditingFeature;
import testmodel.feature.custom.OpenInNewTabFeature;
import testmodel.pattern.AssociationPattern;
import testmodel.pattern.ClassPattern;
import testmodel.pattern.GroupPattern;

public class FramedFeatureProvider extends DefaultFeatureProviderWithPatterns {

	public FramedFeatureProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
		addPattern(new ClassPattern());
		addPattern(new GroupPattern());
//		addPattern(new AttributePattern());
		addConnectionPattern(new AssociationPattern());
	}
	
	/**
	 * [x] update auf ecore
	 * Kontrollieren, ob getAddFeature wirklich ignoriert wird
	 * [x] gruppen (abgerundete / abgeschliffene ecken) elemente auflisten
	 * stepin verschieben fix
     * -- [] dropdown nur wenn schnell 1-2h geht, sonst h�ndisch eingeben (vorzugsweise checken)
	 *
	 * [x] attribute anzeigen gerade bugged
	 * Step in Command im alten Framed
	 * 	=> wie erzeuge ich neue graphiti instanz h�ndisch
	 * 
	 * openPage �ffnet neuen Editor => erstmal nicht
	 * openEditor �ffnet neue Page => ressource, IEditorInput, EditorID (String), 
	 */
	
	// @Override
	// public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
	// IPeService peService = Graphiti.getPeService();
	//
	// PictogramElement pictogramElement = context.getPictogramElement();
	//
	// String canResize = peService.getPropertyValue(pictogramElement,
	// "canResize");
	//
	// if (canResize != null && canResize.equals("false")) {
	// return new DefaultMoveShapeFeature(this) {
	//
	// @Override
	// public boolean canMoveShape(IMoveShapeContext context) {
	// return false;
	// }
	//
	// };
	// }
	//
	// return super.getMoveShapeFeature(context);
	// }
	//
	// @Override
	// public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext
	// context) {
	// IPeService peService = Graphiti.getPeService();
	//
	// PictogramElement pictogramElement = context.getPictogramElement();
	//
	// String canResize = peService.getPropertyValue(pictogramElement,
	// "canResize");
	//
	// if (canResize != null && canResize.equals("false")) {
	// return new DefaultResizeShapeFeature(this) {
	//
	// @Override
	// public boolean canResizeShape(IResizeShapeContext context) {
	// return false;
	// }
	//
	// };
	// }
	//
	// return super.getResizeShapeFeature(context);
	// }

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		ICustomFeature[] ret = super.getCustomFeatures(context);

		List<ICustomFeature> retList = new ArrayList<ICustomFeature>();

		retList.add(new CreateCustomAttributeFeature(this));
		retList.add(new CollapseFeature(this));
		retList.add(new OpenInNewTabFeature(this));
		
		return retList.toArray(ret);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context) {
	    PictogramElement pe = context.getPictogramElement();
	    Object bo = getBusinessObjectForPictogramElement(pe);
	    if (bo instanceof Attribute) {
	        return new CustomAttributeEditingFeature(this);
	    }
	    return super.getDirectEditingFeature(context);
	}

}
