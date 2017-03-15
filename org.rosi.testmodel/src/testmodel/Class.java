/**
 */
package testmodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link testmodel.Class#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see testmodel.TestmodelPackage#getClass_()
 * @model
 * @generated
 */
public interface Class extends ModelElement {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link testmodel.Attribute}.
	 * It is bidirectional and its opposite is '{@link testmodel.Attribute#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see testmodel.TestmodelPackage#getClass_Attributes()
	 * @see testmodel.Attribute#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

} // Class
