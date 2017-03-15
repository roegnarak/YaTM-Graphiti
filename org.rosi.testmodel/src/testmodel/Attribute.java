/**
 */
package testmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link testmodel.Attribute#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see testmodel.TestmodelPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends TypedElement {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link testmodel.Class#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(testmodel.Class)
	 * @see testmodel.TestmodelPackage#getAttribute_Owner()
	 * @see testmodel.Class#getAttributes
	 * @model opposite="attributes" required="true" transient="false"
	 * @generated
	 */
	testmodel.Class getOwner();

	/**
	 * Sets the value of the '{@link testmodel.Attribute#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(testmodel.Class value);

} // Attribute
