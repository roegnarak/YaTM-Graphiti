/**
 */
package testmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link testmodel.Association#getFirstLabel <em>First Label</em>}</li>
 *   <li>{@link testmodel.Association#getSecondLabel <em>Second Label</em>}</li>
 *   <li>{@link testmodel.Association#getFirst <em>First</em>}</li>
 *   <li>{@link testmodel.Association#getSecond <em>Second</em>}</li>
 * </ul>
 *
 * @see testmodel.TestmodelPackage#getAssociation()
 * @model
 * @generated
 */
public interface Association extends ModelElement {
	/**
	 * Returns the value of the '<em><b>First Label</b></em>' attribute.
	 * The default value is <code>"*"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Label</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Label</em>' attribute.
	 * @see #setFirstLabel(String)
	 * @see testmodel.TestmodelPackage#getAssociation_FirstLabel()
	 * @model default="*" required="true"
	 * @generated
	 */
	String getFirstLabel();

	/**
	 * Sets the value of the '{@link testmodel.Association#getFirstLabel <em>First Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Label</em>' attribute.
	 * @see #getFirstLabel()
	 * @generated
	 */
	void setFirstLabel(String value);

	/**
	 * Returns the value of the '<em><b>Second Label</b></em>' attribute.
	 * The default value is <code>"*"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second Label</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second Label</em>' attribute.
	 * @see #setSecondLabel(String)
	 * @see testmodel.TestmodelPackage#getAssociation_SecondLabel()
	 * @model default="*" required="true"
	 * @generated
	 */
	String getSecondLabel();

	/**
	 * Sets the value of the '{@link testmodel.Association#getSecondLabel <em>Second Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second Label</em>' attribute.
	 * @see #getSecondLabel()
	 * @generated
	 */
	void setSecondLabel(String value);

	/**
	 * Returns the value of the '<em><b>First</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First</em>' reference.
	 * @see #setFirst(testmodel.Class)
	 * @see testmodel.TestmodelPackage#getAssociation_First()
	 * @model required="true"
	 * @generated
	 */
	testmodel.Class getFirst();

	/**
	 * Sets the value of the '{@link testmodel.Association#getFirst <em>First</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First</em>' reference.
	 * @see #getFirst()
	 * @generated
	 */
	void setFirst(testmodel.Class value);

	/**
	 * Returns the value of the '<em><b>Second</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second</em>' reference.
	 * @see #setSecond(testmodel.Class)
	 * @see testmodel.TestmodelPackage#getAssociation_Second()
	 * @model required="true"
	 * @generated
	 */
	testmodel.Class getSecond();

	/**
	 * Sets the value of the '{@link testmodel.Association#getSecond <em>Second</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second</em>' reference.
	 * @see #getSecond()
	 * @generated
	 */
	void setSecond(testmodel.Class value);

} // Association
