package fr.sorbonne_u.components.hem2026e1.equipments.weather_station.connections;

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

import fr.sorbonne_u.alasca.physical_data.TimedMeasure;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

// -----------------------------------------------------------------------------
/**
 * The class <code>WeatherStationOutboundPort</code> implements an outbound port
 * for the {@code WeatherStationCI} component interface.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Implementation Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p>Created on : 2026-08-14</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public class			WeatherStationOutboundPort
extends		AbstractOutboundPort
implements	WeatherStationCI
{
	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * create and initialise outbound ports, with a given URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null && uri != null && implementedInterface != null}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * post	{@code !isDestroyed()}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code !connected()}
	 * post {@code !isRemotelyConnected()}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationOutboundPort(
		String uri,
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner
		) throws Exception
	{
		super(uri, implementedInterface, owner) ;
	}

	/**
	 * create and initialize outbound ports.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null && implementedInterface != null}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * post	{@code !isDestroyed()}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code !connected()}
	 * post {@code !isRemotelyConnected()}
	 * post	{@code owner.isPortExisting(getPortURI())}
	 * </pre>
	 *
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @throws Exception			<i>to do</i>.
	 */
	public				WeatherStationOutboundPort(
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner
		) throws Exception
	{
		super(implementedInterface, owner);
 	}

	/**
	 * create and initialise outbound ports, with a given URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null && uri != null}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code !connected()}
	 * post {@code !isRemotelyConnected()}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param owner					component that owns this port.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationOutboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, WeatherStationCI.class, owner) ;
	}

	/**
	 * create and initialise outbound ports.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * post	{@code !isDestroyed()}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code !connected()}
	 * post {@code !isRemotelyConnected()}
	 * post	{@code owner.isPortExisting(getPortURI())}
	 * </pre>
	 *
	 * @param owner					component that owns this port.
	 * @throws Exception			<i>to do</i>.
	 */
	public				WeatherStationOutboundPort(
		ComponentI owner
		) throws Exception
	{
		super(WeatherStationCI.class, owner);
 	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationCI#currentInsideTemperature()
	 */
	@Override
	public TimedMeasure<Double>	currentInsideTemperature() throws Exception
	{
		return ((WeatherStationCI)this.getConnector()).
												currentInsideTemperature();
	}

	/**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationCI#currentOutsideTemperature()
	 */
	@Override
	public TimedMeasure<Double>	currentOutsideTemperature() throws Exception
	{
		return ((WeatherStationCI)this.getConnector()).
												currentOutsideTemperature();
	}
}
// -----------------------------------------------------------------------------
