package fr.sorbonne_u.components.hem2026e1.equipments.heater;

// Copyright Jacques Malenfant, Sorbonne Universite.
// Jacques.Malenfant@lip6.fr
//
// This software is a computer program whose purpose is to provide a
// basic component programming model to program with components
// distributed applications in the Java programming language.
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

import fr.sorbonne_u.alasca.physical_data.Measure;
import fr.sorbonne_u.alasca.physical_data.MeasurementUnit;
import fr.sorbonne_u.exceptions.AssertionChecking;

// -----------------------------------------------------------------------------
/**
 * The interface <code>HeaterConfigurationI</code> defines configuration
 * information for the heater component.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code TEMPERATURE_UNIT != null}
 * invariant	{@code MIN_TARGET_TEMPERATURE != null && MIN_TARGET_TEMPERATURE.getMeasurementUnit().equals(TEMPERATURE_UNIT)}
 * invariant	{@code MAX_TARGET_TEMPERATURE != null && MAX_TARGET_TEMPERATURE.getMeasurementUnit().equals(TEMPERATURE_UNIT)}
 * invariant	{@code POWER_UNIT != null}
 * invariant	{@code TENSION_UNIT != null}
 * invariant	{@code MAX_POWER_LEVEL != null && MAX_POWER_LEVEL.getMeasurementUnit().equals(POWER_UNIT) && MAX_POWER_LEVEL.getData() > 0.0}
 * invariant	{@code TENSION != null && TENSION.getMeasurementUnit().equals(TENSION_UNIT) && TENSION.getData() == 220.0}
 * </pre>
 * 
 * <p>Created on : 2026-08-21</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public interface		HeaterConfigurationI
{
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------

	public static final MeasurementUnit	TEMPERATURE_UNIT =
													MeasurementUnit.CELSIUS;

	/** minimal target temperature for the heater in celsius.				*/
	public static final Measure<Double>	MIN_TARGET_TEMPERATURE =
												new Measure<>(
														-50.0,
														TEMPERATURE_UNIT);
	/** maximal target temperature for the heater in celsius.				*/
	public static final Measure<Double>	MAX_TARGET_TEMPERATURE =
												new Measure<>(
														50.0,
														TEMPERATURE_UNIT);

	/** measurement unit for power used in this appliance.					*/
	public static final MeasurementUnit	POWER_UNIT = MeasurementUnit.WATTS;
	/** measurement unit for tension used in this appliance.				*/
	public static final MeasurementUnit	TENSION_UNIT = MeasurementUnit.VOLTS;

	/** power level of the heater when on but not heating, in the power
	 *  measurement unit used by the heater.								*/
	public static final Measure<Double>	NOT_HEATING_POWER =
											new Measure<>(2.2, POWER_UNIT);
	/** max power level of the heater, in the power measurement unit used
	 *  by the heater.														*/
	public static final Measure<Double>	MAX_POWER_LEVEL =
											new Measure<>(2200.0, POWER_UNIT);
	/** operating voltage of the heater, in the tension measurement unit
	 *  used by the heater.													*/
	public static final Measure<Double>	TENSION =
											new Measure<>(220.0, TENSION_UNIT);

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
	public static boolean	staticInvariants()
	{
		boolean ret = true;
		ret &= AssertionChecking.checkStaticInvariant(
				TEMPERATURE_UNIT != null,
				HeaterStateI.class,
				"TEMPERATURE_UNIT != null");
		ret &= AssertionChecking.checkStaticInvariant(
				MIN_TARGET_TEMPERATURE != null &&
					MIN_TARGET_TEMPERATURE.getMeasurementUnit().equals(
															TEMPERATURE_UNIT),
				HeaterConfigurationI.class,
				"MIN_TARGET_TEMPERATURE != null && MIN_TARGET_TEMPERATURE."
				+ "getMeasurementUnit().equals(TEMPERATURE_UNIT)");
		ret &= AssertionChecking.checkStaticInvariant(
				MAX_TARGET_TEMPERATURE != null &&
					MAX_TARGET_TEMPERATURE.getMeasurementUnit().equals(
															TEMPERATURE_UNIT),
				HeaterConfigurationI.class,
				"MAX_TARGET_TEMPERATURE != null && MAX_TARGET_TEMPERATURE."
				+ "getMeasurementUnit().equals(TEMPERATURE_UNIT)");
		ret &= AssertionChecking.checkStaticInvariant(
				POWER_UNIT != null,
				HeaterExternalControlI.class,
				"POWER_UNIT != null");
		ret &= AssertionChecking.checkStaticInvariant(
				TENSION_UNIT != null,
				HeaterConfigurationI.class,
				"TENSION_UNIT != null");
		ret &= AssertionChecking.checkStaticInvariant(
				MAX_POWER_LEVEL != null &&
					MAX_POWER_LEVEL.getMeasurementUnit().equals(POWER_UNIT) &&
					MAX_POWER_LEVEL.getData() > 0.0,
				HeaterConfigurationI.class,
				"MAX_POWER_LEVEL != null && MAX_POWER_LEVEL.getMeasurementUnit()."
				+ "equals(POWER_UNIT) && MAX_POWER_LEVEL.getData() > 0.0");
		ret &= AssertionChecking.checkStaticInvariant(
				TENSION != null && TENSION.getMeasurementUnit().equals(TENSION_UNIT) && TENSION.getData() == 220.0,
				HeaterConfigurationI.class,
				"TENSION != null && TENSION.getMeasurementUnit().equals(TENSION_UNIT) && TENSION.getData() == 220.0");
		return ret;
	}
}
// -----------------------------------------------------------------------------
