package fr.sorbonne_u.components.hem2026e1.equipments.weather_station;

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
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.connections.WeatherStationInboundPort;
import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PostconditionException;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.utils.URIGenerator;
import fr.sorbonne_u.alasca.physical_data.Measure;

// -----------------------------------------------------------------------------
/**
 * The class <code>WeatherStation</code> implements a home weather station
 * component.
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
 * invariant	{@code X_RELATIVE_POSITION >= 0}
 * invariant	{@code Y_RELATIVE_POSITION >= 0}
 * invariant	{@code NUMBER_OF_STANDARD_THREADS >= 0}
 * invariant	{@code NUMBER_OF_SCHEDULABLE_THREADS >= 0}
 * invariant	{@code STANDARD_INBOUND_PORT_URI != null && !STANDARD_INBOUND_PORT_URI.isEmpty()}
 * invariant	{@code INITIAL_INSIDE_TEMPERATURE != null}
 * invariant	{@code INITIAL_INSIDE_TEMPERATURE.getMeasurementUnit().equals(TEMPERATURE_UNIT)}
 * invariant	{@code INITIAL_OUTSIDE_TEMPERATURE != null}
 * invariant	{@code INITIAL_OUTSIDE_TEMPERATURE.getMeasurementUnit().equals(TEMPERATURE_UNIT)}
 * </pre>
 * 
 * <p>Created on : 2026-08-14</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
@OfferedInterfaces(offered = { WeatherStationCI.class })
public class			WeatherStation
extends		AbstractComponent
implements	WeatherStationImplementationI
{
	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	/** when true, methods trace their actions.								*/
	public static boolean		VERBOSE = false;
	/** when tracing, x coordinate of the window relative position.			*/
	public static int			X_RELATIVE_POSITION = 0;
	/** when tracing, y coordinate of the window relative position.			*/
	public static int			Y_RELATIVE_POSITION = 0;

	/** one thread for the method execute, which starts the local SIL
	 *  simulator and wait until the end of the simulation to get the
	 *  simulation report, and one to answer the calls to the component
	 *  services.															*/
	protected static int		NUMBER_OF_STANDARD_THREADS = 2;
	/** no need for statically defined schedulable threads.					*/
	protected static int		NUMBER_OF_SCHEDULABLE_THREADS = 0;

	/** the standard inbound port URI when just one port is used.			*/
	public static final String	STANDARD_INBOUND_PORT_URI = "weather-ibp-uri";
	/** initial inside temperature when the weather station as just been
	 *  initialised and before its first measure.							*/
	public static final Measure<Double>	INITIAL_INSIDE_TEMPERATURE =
						new Measure<Double>(
								0.0,
								WeatherStationConfigurationI.TEMPERATURE_UNIT);
	/** initial outside temperature when the weather station as just been
	 *  initialised and before its first measure.							*/
	public static final Measure<Double>	INITIAL_OUTSIDE_TEMPERATURE =
						new Measure<Double>(
								0.0,
								WeatherStationConfigurationI.TEMPERATURE_UNIT);

	/**	inbound port offering the {@code WeatherStationCI} component
	 *  interface.															*/
	protected WeatherStationInboundPort	inboundPort;

	// -------------------------------------------------------------------------
	// Invariants
	// -------------------------------------------------------------------------

	/**
	 * return true if the implementation invariants are observed, false otherwise.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code instance != null}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param instance	instance to be tested.
	 * @return			true if the implementation invariants are observed, false otherwise.
	 */
	protected static boolean	implementationInvariants(WeatherStation instance)
	{
		assert instance != null : new PreconditionException("instance != null");

		boolean ret = true;
		return ret;
	}

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
	public static boolean		staticInvariants()
	{
		boolean ret = true;
		ret &= WeatherStationImplementationI.staticInvariants();
		ret &= AssertionChecking.checkStaticInvariant(
				X_RELATIVE_POSITION >= 0,
				WeatherStation.class,
				"X_RELATIVE_POSITION >= 0");
		ret &= AssertionChecking.checkStaticInvariant(
				Y_RELATIVE_POSITION >= 0,
				WeatherStation.class,
				"Y_RELATIVE_POSITION >= 0");
		ret &= AssertionChecking.checkStaticInvariant(
				NUMBER_OF_STANDARD_THREADS >= 0,
				WeatherStation.class,
				"NUMBER_OF_STANDARD_THREADS >= 0");
		ret &= AssertionChecking.checkStaticInvariant(
				NUMBER_OF_SCHEDULABLE_THREADS >= 0,
				WeatherStation.class,
				"NUMBER_OF_SCHEDULABLE_THREADS >= 0");
		ret &= AssertionChecking.checkStaticInvariant(
				STANDARD_INBOUND_PORT_URI != null &&
								!STANDARD_INBOUND_PORT_URI.isEmpty(),
				WeatherStation.class,
				"STANDARD_INBOUND_PORT_URI != null &&"
							+ "!STANDARD_INBOUND_PORT_URI.isEmpty()");
		ret &= AssertionChecking.checkStaticInvariant(
				INITIAL_INSIDE_TEMPERATURE != null,
				WeatherStation.class,
				"INITIAL_INSIDE_TEMPERATURE != null");
		ret &= AssertionChecking.checkStaticInvariant(
				INITIAL_INSIDE_TEMPERATURE.getMeasurementUnit().
														equals(TEMPERATURE_UNIT),
				WeatherStation.class,
				"INITIAL_INSIDE_TEMPERATURE.getMeasurementUnit()."
				+ "equals(TEMPERATURE_UNIT)");
		ret &= AssertionChecking.checkStaticInvariant(
				INITIAL_OUTSIDE_TEMPERATURE != null,
				WeatherStation.class,
				"INITIAL_OUTSIDE_TEMPERATURE != null");
		ret &= AssertionChecking.checkStaticInvariant(
				INITIAL_OUTSIDE_TEMPERATURE.getMeasurementUnit().
														equals(TEMPERATURE_UNIT),
				WeatherStation.class,
				"INITIAL_OUTSIDE_TEMPERATURE.getMeasurementUnit()."
				+ "equals(TEMPERATURE_UNIT)");
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
	 * @param instance	instance to be tested.
	 * @return			true if the invariants are observed, false otherwise.
	 */
	protected static boolean	invariants(WeatherStation instance)
	{
		assert instance != null : new PreconditionException("instance != null");

		boolean ret = true;
		ret &= WeatherStation.staticInvariants();
		return ret;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * create a weather station component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code !(this instanceof ComponentInterface)}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @throws Exception	<i>to do</i>.
	 */
	protected			WeatherStation() throws Exception
	{
		this(URIGenerator.generateURIwithPrefix(
								WeatherStationCI.class.getCanonicalName()));
	}

	/**
	 * create a weather station component with the given reflection inbound
	 * port URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code !(this instanceof ComponentInterface)}
	 * pre	{@code reflectionInboundPortURI != null}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param reflectionInboundPortURI	URI of the inbound port offering the <code>ReflectionI</code> interface.
	 * @throws Exception				<i>to do</i>.
	 */
	protected			WeatherStation(
		String reflectionInboundPortURI
		) throws Exception
	{
		super(reflectionInboundPortURI,
			  NUMBER_OF_STANDARD_THREADS,
			  NUMBER_OF_SCHEDULABLE_THREADS);

		this.inboundPort = new WeatherStationInboundPort(
										STANDARD_INBOUND_PORT_URI, this);
		this.inboundPort.publishPort();

		if (VERBOSE) {
			this.tracer.get().setTitle("Weather station component");
			this.tracer.get().setRelativePosition(X_RELATIVE_POSITION,
												  Y_RELATIVE_POSITION);
			this.toggleTracing();
		}

		// Invariant checking
		assert	WeatherStation.implementationInvariants(this) :
				new ImplementationInvariantException(
						"WeatherStation.implementationInvariants(this)");
		assert	WeatherStation.invariants(this) :
				new InvariantException("WeatherStation.invariants(this)");
	}

	// -------------------------------------------------------------------------
	// Life-cycle methods
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public synchronized void	shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		
		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Service methods
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationImplementationI#currentInsideTemperature()
	 */
	@Override
	public TimedMeasure<Double>	currentInsideTemperature()
	{
		// temporary implementation, would need a physical sensor.
		TimedMeasure<Double> ret =
				new TimedMeasure<Double>(INITIAL_INSIDE_TEMPERATURE);			

		if (VERBOSE) {
			this.logMessage(
					"Weather station returns the current inside temperature: "
					+ ret);
		}

		// Postconditions checking
		assert	ret != null && ret.getMeasure().getMeasurementUnit().
						equals(WeatherStationConfigurationI.TEMPERATURE_UNIT) :
				new PostconditionException(
						"return != null && return.getMeasurementUnit()."
						+ "equals(WeatherStationConfigurationI.TEMPERATURE_UNIT)");
		
		return ret;
	}

	/**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationImplementationI#currentOutsideTemperature()
	 */
	@Override
	public TimedMeasure<Double>	currentOutsideTemperature()
	{
		// temporary implementation, would need a physical sensor.
		TimedMeasure<Double> ret =
				new TimedMeasure<Double>(INITIAL_OUTSIDE_TEMPERATURE);			

		if (VERBOSE) {
			this.logMessage(
					"Weather station returns the current outside temperature: "
					+ ret);
		}

		// Postconditions checking
		assert	ret != null && ret.getMeasure().getMeasurementUnit().
						equals(WeatherStationConfigurationI.TEMPERATURE_UNIT) :
				new PostconditionException(
						"return != null && return.getMeasurementUnit()."
						+ "equals(WeatherStationConfigurationI.TEMPERATURE_UNIT)");
		
		return ret;
	}
}
// -----------------------------------------------------------------------------
