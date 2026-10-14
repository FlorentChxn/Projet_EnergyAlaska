package fr.sorbonne_u.components.hem2026e1.equipments.heater;

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
import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.alasca.physical_data.Measure;
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;

// -----------------------------------------------------------------------------
/**
 * The component interface <code>HeaterExternalControlCI</code> declares the
 * signatures of services used by the household energy manager to adjust
 * the power consumption of the heater.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This component interface declares the same signatures as the ones declared
 * in the interface {@code HeaterExternalControlI} and the ones in its inherited
 * interface {@code HeaterTemperatureI}.
 * </p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code getCurrentPowerLevel().getData() <= getMaxPowerLevel().getData()}
 * </pre>
 * 
 * <p>Created on : 2023-09-18</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public interface		HeaterExternalControlCI
extends		RequiredCI,
			OfferedCI,
			HeaterConfigurationI
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
	 * pre	{@code true}	// no precondition.
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @return	true if the invariants are observed, false otherwise.
	 */
	public static boolean	staticInvariants() throws Exception
	{
		boolean ret = true;
		ret &= HeaterConfigurationI.staticInvariants();
		return ret;
	}

	/**
	 * return true if the invariants are observed, false otherwise.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code instance != null}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param instance		instance to be tested.
	 * @return				true if the invariants are observed, false otherwise.
	 * @throws Exception	<i>to do</i>.
	 */
	public static boolean	invariants(HeaterExternalControlCI instance) throws Exception
	{
		assert instance != null : new PreconditionException("instance != null");

		boolean ret = true;
		ret &= HeaterExternalControlCI.staticInvariants();
		ret &= AssertionChecking.checkInvariant(
				instance.getCurrentPowerLevel().getData() <=
									instance.getMaxPowerLevel().getData(),
				HeaterExternalControlCI.class, instance,
				"getCurrentPowerLevel().getData() <= "
				+ "getMaxPowerLevel().getData()");
		return ret;
	}

	// -------------------------------------------------------------------------
	// Signatures
	// -------------------------------------------------------------------------

	/**
	 * return true if the heater is currently running.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @return				true if the heater is currently running.
	 * @throws Exception	<i>to do</i>.
	 */
	public boolean		on() throws Exception;

	/**
	 * get the current target temperature.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code on()}
	 * post	{@code return != null && Heater.TEMPERATURE_UNIT.equals(return.getMeasure().getMeasurementUnit())}
	 * post	{@code return.getMeasure().getData() >= Heater.MIN_TARGET_TEMPERATURE.getData() && return.getMeasure().getData() <= Heater.MAX_TARGET_TEMPERATURE.getData()}
	 * </pre>
	 *
	 * @return				the current target temperature.
	 * @throws Exception	<i>to do</i>.
	 */
	public TimedMeasure<Double>	getTargetTemperature()
	throws Exception;

	/**
	 * return the current temperature measured by the thermostat.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code on()}
	 * post	{@code return != null && Heater.TEMPERATURE_UNIT.equals(return.getMeasure().getMeasurementUnit())}
	 * </pre>
	 *
	 * @return				the current temperature measured by the thermostat.
	 * @throws Exception	<i>to do</i>.
	 */
	public TimedMeasure<Double>	getCurrentTemperature()
	throws Exception;

	/**
	 * return the maximum power of the heater in the power unit used by the
	 * heater.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code on()}
	 * post	{@code return != null && return.getData() > 0.0 && return.getMeasurementUnit().equals(POWER_UNIT)}
	 * </pre>
	 *
	 * @return				the maximum power of the heater in the power unit used by the heater.
	 * @throws Exception	<i>to do</i>.
	 */
	public Measure<Double>	getMaxPowerLevel() throws Exception;

	/**
	 * return the current power level of the heater in the power unit used by
	 * the heater.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code on()}
	 * post	{@code return != null && return.getMeasure().getMeasurementUnit().equals(POWER_UNIT)}
	 * post	{@code return.getMeasure().getData() >= 0.0 && return.getMeasure().getData() <= getMaxPowerLevel().getData()}
	 * </pre>
	 *
	 * @return				the current power level of the heater.
	 * @throws Exception	<i>to do</i>.
	 */
	public TimedMeasure<Double>	getCurrentPowerLevel() throws Exception;

	/**
	 * set the power level of the heater; if
	 * {@code powerLevel.getData() > getMaxPowerLevel().getData()} then set the
	 * power level to the maximum.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code on()}
	 * pre	{@code powerLevel != null && powerLevel.getData() >= 0.0 && powerLevel.getMeasurementUnit().equals(POWER_UNIT)}
	 * post	{@code powerLevel.getData() > getMaxPowerLevel().getData() || getCurrentPowerLevel().getData() == powerLevel.getData()}
	 * post	{@code powerLevel.getData() <= getMaxPowerLevel().getData() || getCurrentPowerLevel().getData() == Heater.MAX_POWER_LEVEL.getData()}
	 * </pre>
	 *
	 * @param powerLevel	the powerLevel to be set.
	 * @throws Exception	<i>to do</i>.
	 */
	public void			setCurrentPowerLevel(Measure<Double> powerLevel)
	throws Exception;
}
// -----------------------------------------------------------------------------
