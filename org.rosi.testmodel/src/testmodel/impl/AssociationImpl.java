/**
 */
package testmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import testmodel.Association;
import testmodel.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link testmodel.impl.AssociationImpl#getName <em>Name</em>}</li>
 *   <li>{@link testmodel.impl.AssociationImpl#getFirstLabel <em>First Label</em>}</li>
 *   <li>{@link testmodel.impl.AssociationImpl#getSecondLabel <em>Second Label</em>}</li>
 *   <li>{@link testmodel.impl.AssociationImpl#getFirst <em>First</em>}</li>
 *   <li>{@link testmodel.impl.AssociationImpl#getSecond <em>Second</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociationImpl extends MinimalEObjectImpl.Container implements Association {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFirstLabel() <em>First Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRST_LABEL_EDEFAULT = "*";

	/**
	 * The cached value of the '{@link #getFirstLabel() <em>First Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstLabel()
	 * @generated
	 * @ordered
	 */
	protected String firstLabel = FIRST_LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getSecondLabel() <em>Second Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String SECOND_LABEL_EDEFAULT = "*";

	/**
	 * The cached value of the '{@link #getSecondLabel() <em>Second Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondLabel()
	 * @generated
	 * @ordered
	 */
	protected String secondLabel = SECOND_LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFirst() <em>First</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirst()
	 * @generated
	 * @ordered
	 */
	protected testmodel.Class first;

	/**
	 * The cached value of the '{@link #getSecond() <em>Second</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecond()
	 * @generated
	 * @ordered
	 */
	protected testmodel.Class second;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssociationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestmodelPackage.Literals.ASSOCIATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.ASSOCIATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFirstLabel() {
		return firstLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstLabel(String newFirstLabel) {
		String oldFirstLabel = firstLabel;
		firstLabel = newFirstLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.ASSOCIATION__FIRST_LABEL, oldFirstLabel, firstLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSecondLabel() {
		return secondLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondLabel(String newSecondLabel) {
		String oldSecondLabel = secondLabel;
		secondLabel = newSecondLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.ASSOCIATION__SECOND_LABEL, oldSecondLabel, secondLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public testmodel.Class getFirst() {
		if (first != null && first.eIsProxy()) {
			InternalEObject oldFirst = (InternalEObject)first;
			first = (testmodel.Class)eResolveProxy(oldFirst);
			if (first != oldFirst) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.ASSOCIATION__FIRST, oldFirst, first));
			}
		}
		return first;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public testmodel.Class basicGetFirst() {
		return first;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirst(testmodel.Class newFirst) {
		testmodel.Class oldFirst = first;
		first = newFirst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.ASSOCIATION__FIRST, oldFirst, first));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public testmodel.Class getSecond() {
		if (second != null && second.eIsProxy()) {
			InternalEObject oldSecond = (InternalEObject)second;
			second = (testmodel.Class)eResolveProxy(oldSecond);
			if (second != oldSecond) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestmodelPackage.ASSOCIATION__SECOND, oldSecond, second));
			}
		}
		return second;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public testmodel.Class basicGetSecond() {
		return second;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecond(testmodel.Class newSecond) {
		testmodel.Class oldSecond = second;
		second = newSecond;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestmodelPackage.ASSOCIATION__SECOND, oldSecond, second));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestmodelPackage.ASSOCIATION__NAME:
				return getName();
			case TestmodelPackage.ASSOCIATION__FIRST_LABEL:
				return getFirstLabel();
			case TestmodelPackage.ASSOCIATION__SECOND_LABEL:
				return getSecondLabel();
			case TestmodelPackage.ASSOCIATION__FIRST:
				if (resolve) return getFirst();
				return basicGetFirst();
			case TestmodelPackage.ASSOCIATION__SECOND:
				if (resolve) return getSecond();
				return basicGetSecond();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestmodelPackage.ASSOCIATION__NAME:
				setName((String)newValue);
				return;
			case TestmodelPackage.ASSOCIATION__FIRST_LABEL:
				setFirstLabel((String)newValue);
				return;
			case TestmodelPackage.ASSOCIATION__SECOND_LABEL:
				setSecondLabel((String)newValue);
				return;
			case TestmodelPackage.ASSOCIATION__FIRST:
				setFirst((testmodel.Class)newValue);
				return;
			case TestmodelPackage.ASSOCIATION__SECOND:
				setSecond((testmodel.Class)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TestmodelPackage.ASSOCIATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestmodelPackage.ASSOCIATION__FIRST_LABEL:
				setFirstLabel(FIRST_LABEL_EDEFAULT);
				return;
			case TestmodelPackage.ASSOCIATION__SECOND_LABEL:
				setSecondLabel(SECOND_LABEL_EDEFAULT);
				return;
			case TestmodelPackage.ASSOCIATION__FIRST:
				setFirst((testmodel.Class)null);
				return;
			case TestmodelPackage.ASSOCIATION__SECOND:
				setSecond((testmodel.Class)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TestmodelPackage.ASSOCIATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TestmodelPackage.ASSOCIATION__FIRST_LABEL:
				return FIRST_LABEL_EDEFAULT == null ? firstLabel != null : !FIRST_LABEL_EDEFAULT.equals(firstLabel);
			case TestmodelPackage.ASSOCIATION__SECOND_LABEL:
				return SECOND_LABEL_EDEFAULT == null ? secondLabel != null : !SECOND_LABEL_EDEFAULT.equals(secondLabel);
			case TestmodelPackage.ASSOCIATION__FIRST:
				return first != null;
			case TestmodelPackage.ASSOCIATION__SECOND:
				return second != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", firstLabel: ");
		result.append(firstLabel);
		result.append(", secondLabel: ");
		result.append(secondLabel);
		result.append(')');
		return result.toString();
	}

} //AssociationImpl
