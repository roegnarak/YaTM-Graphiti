package util;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class SimpleDialogUtil {

	public static String askString(String title, String message, String value) {
		Shell shell = getShell();
		InputDialog inputDialog = new InputDialog(shell, title, message, value, null);
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			return inputDialog.getValue();
		}
		
		return null;
	}

	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}
