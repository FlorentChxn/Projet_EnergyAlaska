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

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationCI;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationImplementationI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;

// -----------------------------------------------------------------------------
/**
 * The class <code>WeatherStationInboundPort</code> implements an inbound port
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
public class			WeatherStationInboundPort
extends		AbstractInboundPort
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
	 * create and initialise an inbound port with an automatically generated URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code implementedInterface != null}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * post	{@code !isDestroyed()}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code owner.isPortExisting(getPortURI())}
	 * </pre>
	 *
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner
		) throws Exception
	{
		super(implementedInterface, owner);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code implementedInterface != null}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * post	{@code !isDestroyed()}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		String uri,
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner
		) throws Exception
	{
		super(uri, implementedInterface, owner);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given plug-in and executor
	 * service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code implementedInterface != null}
	 * pre	{@code implementedInterface.isAssignableFrom(this.getClass())}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code owner.isPortExisting(getPortURI())}
	 * </pre>
	 *
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner,
		String pluginURI,
		String executorServiceURI
		) throws Exception
	{
		super(implementedInterface, owner, pluginURI, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a generated URI and given
	 * plug-in and executor service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code implementedInterface != null}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || !callerRuns}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * </pre>
	 *
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param callerRuns			if true, the call to the owner component must be executed by the caller component thread.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner,
		String pluginURI,
		boolean callerRuns,
		String executorServiceURI
		) throws Exception
	{
		super(implementedInterface, owner, pluginURI, callerRuns, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given URI and given plug-in
	 * and executor service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code implementedInterface != null}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		String uri,
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner,
		String pluginURI,
		String executorServiceURI
		) throws Exception
	{
		super(uri, implementedInterface, owner, pluginURI, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given URI and given plug-in
	 * and executor service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code implementedInterface != null}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * pre	{@code implementedInterface.isAssignableFrom(getClass())}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || !callerRuns}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(implementedInterface)}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param implementedInterface	interface implemented by this port.
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param callerRuns			if true, the call to the owner component must be executed by the caller component thread.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		String uri,
		Class<? extends WeatherStationCI> implementedInterface,
		ComponentI owner,
		String pluginURI,
		boolean callerRuns,
		String executorServiceURI
		) throws Exception
	{
		super(uri, implementedInterface, owner, pluginURI, callerRuns, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with an automatically generated URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * post	{@code !isDestroyed()}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code owner.isPortExisting(getPortURI())}
	 * </pre>
	 *
	 * @param owner					component that owns this port.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		ComponentI owner
		) throws Exception
	{
		super(WeatherStationCI.class, owner);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param owner					component that owns this port.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, WeatherStationCI.class, owner);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given plug-in and executor
	 * service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code owner.isPortExisting(getPortURI())}
	 * </pre>
	 *
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		ComponentI owner,
		String pluginURI,
		String executorServiceURI
		) throws Exception
	{
		super(WeatherStationCI.class, owner, pluginURI, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a generated URI and given
	 * plug-in and executor service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || !callerRuns}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * </pre>
	 *
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param callerRuns			if true, the call to the owner component must be executed by the caller component thread.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		ComponentI owner,
		String pluginURI,
		boolean callerRuns,
		String executorServiceURI
		) throws Exception
	{
		super(WeatherStationCI.class, owner, pluginURI, callerRuns, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given URI and given plug-in
	 * and executor service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		String uri,
		ComponentI owner,
		String pluginURI,
		String executorServiceURI
		) throws Exception
	{
		super(uri, WeatherStationCI.class, owner, pluginURI, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
	}

	/**
	 * create and initialise an inbound port with a given URI and given plug-in
	 * and executor service URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * pre	{@code owner != null}
	 * pre	{@code owner instanceof WeatherStationImplementationI}
	 * pre	{@code !owner.isPortExisting(uri)}
	 * pre	{@code pluginURI == null || owner.isInstalled(pluginURI)}
	 * pre	{@code executorServiceURI == null || !callerRuns}
	 * pre	{@code executorServiceURI == null || owner.validExecutorServiceURI(executorServiceURI)}
	 * post	{@code !isDestroyed()}
	 * post	{@code getPortURI().equals(uri)}
	 * post	{@code getOwner().equals(owner)}
	 * post	{@code getImplementedInterface().equals(WeatherStationCI.class)}
	 * post	{@code owner.isPortExisting(uri)}
	 * </pre>
	 *
	 * @param uri					unique identifier of the port.
	 * @param owner					component that owns this port.
	 * @param pluginURI				URI of the plug-in to be called in the owner or null if none.
	 * @param callerRuns			if true, the call to the owner component must be executed by the caller component thread.
	 * @param executorServiceURI	URI of the executor service to be used to execute the service on the component or null if none.
	 * @throws Exception 			<i>to do</i>.
	 */
	public				WeatherStationInboundPort(
		String uri,
		ComponentI owner,
		String pluginURI,
		boolean callerRuns,
		String executorServiceURI
		) throws Exception
	{
		super(uri, WeatherStationCI.class, owner, pluginURI, callerRuns, executorServiceURI);

		// Preconditions checking
		assert	owner instanceof WeatherStationImplementationI :
				new PreconditionException(
						"owner instanceof WeatherStationImplementationI");
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
		return this.getOwner().handleRequest(
					o -> ((WeatherStationImplementationI)o).
												currentInsideTemperature());
	}

	/**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationCI#currentOutsideTemperature()
	 */
	@Override
	public TimedMeasure<Double> currentOutsideTemperature()  throws Exception
	{
		return this.getOwner().handleRequest(
					o -> ((WeatherStationImplementationI)o).
												currentOutsideTemperature());
	}
}
// -----------------------------------------------------------------------------
