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

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

// -----------------------------------------------------------------------------
/**
 * The component interface <code>HairDryerUserCI</code> defines the signatures
 * of services a hair dryer component offers and that can be required from it.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This component interface proposes as services the same signatures as in
 * the implementation interface {@code HairDryerImplementationI}.
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
 * @see	fr.sorbonne_u.components.hem2026e1.equipments.hairdryer.HairDryerImplementationI
 */
public interface		HairDryerUserCI
extends		OfferedCI,
			RequiredCI,
			HairDryerConfigurationI

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
	public static boolean	staticInvariants() throws Exception
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
	 * @throws Exception 	<i>to do</i>.
	 */
	public HairDryerState	getState() throws Exception;

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
	 * @throws Exception 	<i>to do</i>.
	 */
	public HairDryerMode	getMode() throws Exception;

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
	 * @throws Exception	<i>to do</i>.
	 */
	public void			turnOn() throws Exception;

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
	 * @throws Exception	<i>to do</i>.
	 */
	public void			turnOff() throws Exception;

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
	 * @throws Exception	<i>to do</i>.
	 */
	public void			setHigh() throws Exception;

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
	 * @throws Exception	<i>to do</i>.
	 */
	public void			setLow() throws Exception;
}
// -----------------------------------------------------------------------------
