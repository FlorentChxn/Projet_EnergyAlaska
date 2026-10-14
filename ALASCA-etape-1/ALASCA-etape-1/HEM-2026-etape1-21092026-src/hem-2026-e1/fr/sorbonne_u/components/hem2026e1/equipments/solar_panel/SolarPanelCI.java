package fr.sorbonne_u.components.hem2026e1.equipments.solar_panel;

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

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.alasca.physical_data.Measure;
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;

// -----------------------------------------------------------------------------
/**
 * The component interface <code>SolarPanelCI</code> defines the signatures of
 * the services that a solar panel component offers.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This component interface proposes as services the same signatures as in
 * the implementation interface {@code SolarPanelImplementationI}.
 * </p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p>Created on : 2025-09-26</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 * @see	fr.sorbonne_u.components.hem2026e1.equipments.solar_panel.SolarPanelImplementationI
 */
public interface		SolarPanelCI
extends		OfferedCI,
			RequiredCI,
			SolarPanelConfigurationI
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
	 * @return			true if the invariants are observed, false otherwise.
	 */
	public static boolean	staticInvariants() throws Exception
	{
		boolean ret = true;
		ret &= SolarPanelConfigurationI.staticInvariants();
		return ret;
	}

	// -------------------------------------------------------------------------
	// Signature and default methods
	// -------------------------------------------------------------------------

	/**
	 * return the number of square meters in the solar panel.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return > 0}
	 * </pre>
	 *
	 * @return				the number of square meters in the solar panel.
	 * @throws Exception	<i>to do</i>.
	 */
	public int				getNumberOfSquareMeters() throws Exception;

	/**
	 * return the nominal power production capacity of the solar panels in the
	 * power unit used by the solar panel.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getPower() > 0.0 && return.getMeasurementUnit().equals(POWER_UNIT)}
	 * </pre>
	 *
	 * @return				the nominal power production capacity of the solar panels in the power unit used by the solar panel.
	 * @throws Exception	<i>to do</i>.
	 */
	public Measure<Double>		getNominalPowerProductionCapacity()
	throws Exception;

	/**
	 * return the current power production capacity of the solar panels in the
	 * power unit used by the solar panel.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code return != null && return.getMeasurementUnit().equals(POWER_UNIT)}
	 * post	{@code return.getPower() >= 0.0 && return.getPower() <= getNominalPowerProductionCapacity().getPower()}
	 * </pre>
	 *
	 * @return				the current power production capacity of the solar panels in the power unit used by the solar panel.
	 * @throws Exception	<i>to do</i>.
	 */
	public TimedMeasure<Double>	getCurrentPowerProductionLevel()
	throws Exception;
}
// -----------------------------------------------------------------------------
