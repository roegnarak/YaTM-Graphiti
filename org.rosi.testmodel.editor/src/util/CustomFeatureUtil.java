package util;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class CustomFeatureUtil {

	public static boolean isSinglePictogramElement(PictogramElement[] pictogramElements) {
		return (pictogramElements != null && pictogramElements.length == 1);
	}

}
