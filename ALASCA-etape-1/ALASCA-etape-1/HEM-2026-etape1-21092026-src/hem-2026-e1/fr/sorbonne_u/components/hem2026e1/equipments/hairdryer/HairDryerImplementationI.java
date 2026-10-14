package fr.sorbonne_u.components.hem2026e1.equipments.hairdryer;

// Copyright Jacques Malenfant, Sorbonne Universite.
// Jacques.Malenfant@lip6.fr
//
// This software is a computer program whose purpose is to implement a mock-up
// of household energy management system.
//
// This software is governed by the CeCILL-C license under French law and
// abiding by the rules of distribution of free software.  You can use,
// modify and/ or redistribute the software under the terms of the
// CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
// URL "http://www.cecill.info".
//
// As a counterpart to the access to the source code and  rights to copy,
// modify and redistribute granted by the license, users are provided only
// with a limited warranty  and the software's author,  the holder of the
// economic rights,  and the successive licensors  have only  limited
// liability. 
//
// In this respect, the user's attention is drawn to the risks associated
// with loading,  using,  modifying and/or developing or reproducing the
// software by the user in light of its specific status of free software,
// that may mean  that it is complicated to manipulate,  and  that  also
// therefore means  that it is reserved for developers  and  experienced
// professionals having in-depth computer knowledge. Users are therefore
// encouraged to load and test the software's suitability as regards their
// requirements in conditions enabling the security of their systems and/or 
// data to be ensured and,  more generally, to use and operate it in the 
// same conditions as regards security. 
//
// The fact that you are presently reading this means that you have had
// knowledge of the CeCILL-C license and that you accept its terms.

// -----------------------------------------------------------------------------
/**
 * The interface <code>HairDryerImplementationI</code> defines the signatures
 * of methods implemented by the hair dryer component.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This interface proposes as methods the same signatures as in the component
 * interface {@code HairDryerUserCI}.
 * </p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p>Created on : 2023-09-19</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 * @see	fr.sorbonne_u.components.hem2026e1.equipments.hairdryer.HairDryerUserCI
 */
public interface		HairDryerImplementationI
extends		HairDryerConfigurationI
{
	// -------------------------------------------------------------------------
	// Invariants
	// -------------------------------------------------------------------------

	/**
	 * return true if the static invariants are observed, false otherwise.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @return	true if the static invariants are observed, false otherwise.
	 */
	public static boolean	staticInvariants()
	{
		boolean ret = true;
		ret &= HairDryerConfigurationI.staticInvariants();
		return ret;
	}

	// -------------------------------------------------------------------------
	// Signatures
	// -------------------------------------------------------------------------

	/**
	 * return the current state of the hair dryer.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @return				the current state of the hair dryer.
	 */
	public HairDryerState	getState();

	/**
	 * return the current operation mode of the hair dryer.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @return				the current state of the hair dryer.
	 */
	public HairDryerMode	getMode();

	/**
	 * turn on the hair dryer, put in the low temperature and slow fan mode.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code getState() == HairDryerState.OFF}
	 * post	{@code getMode() == HairDryerMode.LOW}
	 * post	{@code getState() == HairDryerState.ON}
	 * </pre>
	 *
	 */
	public void			turnOn();

	/**
	 * turn off the hair dryer.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code getState() == HairDryerState.OFF}
	 * </pre>
	 *
	 */
	public void			turnOff();

	/**
	 * set the hair dryer in high mode.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code getState() == HairDryerState.ON}
	 * pre	{@code getMode() == HairDryerMode.LOW}
	 * post	{@code getMode() == HairDryerMode.HIGH}
	 * </pre>
	 *
	 */
	public void			setHigh();

	/**
	 * set the hair dryer in low mode.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code getState() == HairDryerState.ON}
	 * pre	{@code getMode() == HairDryerMode.HIGH}
	 * post	{@code getMode() == HairDryerMode.LOW}
	 * </pre>
	 *
	 */
	public void			setLow();
}
// -----------------------------------------------------------------------------
