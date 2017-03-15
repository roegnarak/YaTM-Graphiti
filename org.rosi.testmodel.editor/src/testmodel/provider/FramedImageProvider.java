package testmodel.provider;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class FramedImageProvider extends AbstractImageProvider {

	private final static String PREFIX = "testmodel.";
	
	static final String IMG_CONNECTION = PREFIX + "connection";
	
	static final String IMG_ATTRIBUTE = PREFIX + "attribute";
	
	@Override
	protected void addAvailableImages() {
		addImageFilePath(IMG_CONNECTION, "icons/expandArrow.png");
		addImageFilePath(IMG_ATTRIBUTE, "icons/EAttribute.gif");
	}

}
