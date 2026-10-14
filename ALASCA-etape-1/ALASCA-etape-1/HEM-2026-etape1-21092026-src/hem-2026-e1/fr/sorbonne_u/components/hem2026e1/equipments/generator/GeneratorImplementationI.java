package fr.sorbonne_u.components.hem2026e1.equipments.generator;

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
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;

// -----------------------------------------------------------------------------
/**
 * The interface <code>GeneratorImplementationI</code> defines the signatures
 * of methods to be implemented by an electric generator component.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This interface proposes as methods the same signatures as in the component
 * interface {@code GeneratorCI}.
 * </p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p>Created on : 2025-09-29</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 * @see	fr.sorbonne_u.components.hem2026e1.equipments.generator.GeneratorCI
 */
public interface		GeneratorImplementationI
extends		GeneratorConfigurationI
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
	 * @return	true if the invariants are observed, false otherwise.
	 */
	public static boolean	staticInvariants()
	{
		boolean ret = true;
		ret &= GeneratorConfigurationI.staticInvariants();
		return ret;
	}

	// -------------------------------------------------------------------------
	// Signatures
	// -------------------------------------------------------------------------

	/**
	 * return the state of the generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @return	the state of the generator.
	 */
	public GeneratorState		getState();

	/**
	 * return the output AC tension in the tension unit used by the generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getData() > 0.0 && return.getMeasurementUnit().equals(TENSION_UNIT)}
	 * </pre>
	 *
	 * @return	the output AC tension in the tension unit used by the generator.
	 */
	public Measure<Double>	nominalOutputTension();

	/**
	 * return the tank capacity in the capacity unit used by the generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getData() > 0.0 && return.getMeasurementUnit().equals(CAPACITY_UNIT)}
	 * </pre>
	 *
	 * @return	the current tank level in the tension unit used by the generator.
	 */
	public Measure<Double>	tankCapacity();

	/**
	 * refill the tank up to the quantity provided or to its capacity if the
	 * quantity os too large.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code quantity != null}
	 * pre	{@code quantity.getData() > 0.0}
	 * pre	{@code quantity.getMeasurementUnit().equals(CAPACITY_UNIT)}
	 * post	{@code true}	// TO DO
	 * </pre>
	 *
	 * @param quantity	quantity of fuel to be added to the generator tank.
	 */
	public void			refillTank(Measure<Double> quantity);

	/**
	 * return the current tank level in the capacity unit used by the generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getMeasure().getData() >= 0.0 && return.getMeasure().getMeasurementUnit().equals(CAPACITY_UNIT)}
	 * </pre>
	 *
	 * @return	the current tank level in the capacity unit used by the generator.
	 */
	public TimedMeasure<Double>	currentTankLevel();

	/**
	 * return the maximum power production level in the power unit used by the
	 * generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code ret != null && ret.getData() >= 0.0 && ret.getMeasurementUnit().equals(POWER_UNIT)}
	 * </pre>
	 *
	 * @return	the maximum power production level in the power unit used by the generator.
	 */
	public Measure<Double>	maxPowerProductionCapacity();

	/**
	 * return the current power production level in the power unit used by the
	 * generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code ret != null && ret.getMeasure().getData() >= 0.0 && ret.getMeasure().getMeasurementUnit().equals(POWER_UNIT)}
	 * post {@code ret.getMeasure().getData() <= maxPowerProductionCapacity().getData()}
	 * </pre>
	 *
	 * @return	the current power production level in the power unit used by the generator.
	 */
	public TimedMeasure<Double>	currentPowerProduction();

	/**
	 * return the minimal fuel consumption of the generator in the generator
	 * consumption unit.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getData() > 0.0 && return.getMeasurementUnit().equals(CONSUMPTION_UNIT)}
	 * post	{@code return.getData() <= maxFuelConsumption().getData()}
	 * </pre>
	 *
	 * @return	the minimal fuel consumption of the generator in the generator consumption unit.
	 */
	public Measure<Double>	minFuelConsumption();

	/**
	 * return the maximal fuel consumption of the generator in the generator
	 * consumption unit.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getData() > 0.0 && return.getMeasurementUnit().equals(CONSUMPTION_UNIT)}
	 * </pre>
	 *
	 * @return	the maximal fuel consumption of the generator in the generator consumption unit.
	 */
	public Measure<Double>	maxFuelConsumption();

	/**
	 * return the current fuel consumption of the generator in the generator
	 * consumption unit.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getMeasurementUnit().equals(CONSUMPTION_UNIT)}
	 * post	{@code return.getMeasure().getData() >= 0.0 && return.getMeasure().getData() <= maxFuelConsumption().getData()}
	 * </pre>
	 *
	 * @return	the current fuel consumption of the generator in the generator consumption unit.
	 */
	public TimedMeasure<Double>	currentFuelConsumption();

	/**
	 * start the generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code State.OFF.equals(getState())}
	 * post	{@code State.IDLE.equals(getState())}
	 * </pre>
	 *
	 */
	public void			startGenerator();

	/**
	 * stop the generator.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code !State.OFF.equals(getState())}
	 * post	{@code State.OFF.equals(getState())}
	 * </pre>
	 *
	 */
	public void			stopGenerator();
}
// -----------------------------------------------------------------------------
