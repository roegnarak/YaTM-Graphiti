package testmodel.feature.custom;

import java.util.Collections;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import testmodel.presentation.TestmodelEditor;

public class OpenInNewTabFeature extends AbstractCustomFeature {

	public OpenInNewTabFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Open in new Tab.";
	}
	
	@Override
	public String getDescription() {
		return "Open in new Tab.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pe = context.getPictogramElements();
		if (pe != null && pe.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pe[0]);
			
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {												
				//TODO: businessObject als root übergeben
				TestmodelEditor testmodelEditor = (TestmodelEditor) activePage.openEditor(activePage.getActiveEditor().getEditorInput(), "org.eclipse.graphiti.ui.editor.DiagramEditor", false, IWorkbenchPage.MATCH_NONE);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

}
