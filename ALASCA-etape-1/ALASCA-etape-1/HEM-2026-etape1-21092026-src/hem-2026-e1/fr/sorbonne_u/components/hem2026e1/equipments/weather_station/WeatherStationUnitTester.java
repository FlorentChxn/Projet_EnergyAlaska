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

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.connections.WeatherStationConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.connections.WeatherStationOutboundPort;
import fr.sorbonne_u.components.utils.tests.TestsStatistics;
import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;

// -----------------------------------------------------------------------------
/**
 * The class <code>WeatherStationUnitTester</code> performs unit tests for
 * the weather station component.
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
 * </pre>
 * 
 * <p>Created on : 2026-08-21</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
@RequiredInterfaces(required = { WeatherStationCI.class })
public class			WeatherStationUnitTester
extends		AbstractComponent
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

	protected WeatherStationOutboundPort wsop;

	/** collector of tests statistics.										*/
	protected TestsStatistics			statistics;

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
	protected static boolean	implementationInvariants(
		WeatherStationUnitTester instance
		)
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
		ret &= AssertionChecking.checkStaticInvariant(
				X_RELATIVE_POSITION >= 0,
				WeatherStationUnitTester.class,
				"X_RELATIVE_POSITION >= 0");
		ret &= AssertionChecking.checkStaticInvariant(
				Y_RELATIVE_POSITION >= 0,
						WeatherStationUnitTester.class,
				"Y_RELATIVE_POSITION >= 0");
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
	protected static boolean	invariants(WeatherStationUnitTester instance)
	{
		assert	instance != null : new PreconditionException("instance != null");

		boolean ret = true;
		ret &= staticInvariants();
		return ret;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * create a weather station unit tester component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @throws Exception	<i>to do</i>.
	 */
	protected			WeatherStationUnitTester() throws Exception
	{
		super(1, 0);

		this.wsop = new WeatherStationOutboundPort(this);
		this.wsop.publishPort();

		if(VERBOSE) {
			this.tracer.get().setTitle("Weather station tester component");
			this.tracer.get().setRelativePosition(X_RELATIVE_POSITION,
												  Y_RELATIVE_POSITION);
			this.toggleTracing();
		}

		this.statistics = new TestsStatistics();

		// Invariant checking
		assert	WeatherStationUnitTester.implementationInvariants(this) :
				new ImplementationInvariantException(
						"WeatherStationUnitTester."
						+ "implementationInvariants(this)");
		assert	WeatherStationUnitTester.invariants(this) :
				new InvariantException("WeatherStationUnitTester.invariants(this)");
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * test getting the current inside temperature from the weather station.
	 * 
	 * <p><strong>Gherkin specification</strong></p>
	 * 
	 * <pre>
	 * Feature: Getting the current inside temperature
	 *   Scenario: getting the current inside temperature
	 *     Given the weather station has just been initialised
	 *     When I get the current inside temperature from the weather station
	 *     Then the current inside temperature is the initial one as defined by the weather station component
	 * </pre>
	 *
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code tester != null}
	 * pre	{@code wsop != null}
	 * pre	{@code statistics != null}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param tester		tester component.
	 * @param wsop			outbound port to be used to call the tested methods.
	 * @param statistics	collector of tests statistics.
	 */
	public static void		testCurrentInsideTemperature(
		AbstractComponent tester,
		WeatherStationOutboundPort wsop,
		TestsStatistics statistics
		)
	{
		assert	tester != null : new PreconditionException("tester != null");
		assert	wsop != null : new PreconditionException("wsop != null");
		assert	statistics != null :
				new PreconditionException("statistics != null");

		tester.logMessage("Feature: Getting the current inside temperature");
		tester.logMessage("  Scenario: getting the current inside temperature");
		tester.logMessage("    Given the weather station has just been initialised");
		TimedMeasure<Double> result = null;
		try {
			tester.logMessage("    When I get the current inside temperature"
							  + " from the weather station");
			result = wsop.currentInsideTemperature();
			if (WeatherStation.INITIAL_INSIDE_TEMPERATURE.equals(result.getMeasure())) {
				tester.logMessage("    Then the current inside temperature  is "
								  + "the initial one as defined by the weather"
								  + " station component");
			} else {
				tester.logMessage("     but was: " + result + " instead of "
								  + WeatherStation.INITIAL_INSIDE_TEMPERATURE);
				statistics.incorrectResult();
			}
		} catch (Throwable e) {
			statistics.incorrectResult();
			tester.logMessage("     but the exception " + e + " has been raised");
		}

		statistics.updateStatistics();
	}

	/**
	 * test getting the current outside temperature from the weather station.
	 * 
	 * <p><strong>Gherkin specification</strong></p>
	 * 
	 * <pre>
	 * Feature: Getting the current outside temperature
	 *   Scenario: getting the current outside temperature
	 *     Given the weather station has just been initialised
	 *     When I get the current outside temperature from the weather station
	 *     Then the current outside temperature is the initial one as defined by the weather station component
	 * </pre>
	 *
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code tester != null}
	 * pre	{@code wsop != null}
	 * pre	{@code statistics != null}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param tester		tester component.
	 * @param wsop			outbound port to be used to call the tested methods.
	 * @param statistics	collector of tests statistics.
	 */
	public static void		testCurrentOutsideTemperature(
		AbstractComponent tester,
		WeatherStationOutboundPort wsop,
		TestsStatistics statistics
		)
	{
		assert	tester != null : new PreconditionException("tester != null");
		assert	wsop != null : new PreconditionException("wsop != null");
		assert	statistics != null :
				new PreconditionException("statistics != null");

		tester.logMessage("Feature: Getting the current outside temperature");
		tester.logMessage("  Scenario: getting the current outside temperature");
		tester.logMessage("    Given the weather station has just been initialised");
		TimedMeasure<Double> result = null;
		try {
			tester.logMessage("    When I get the current outside temperature"
							  + " from the weather station");
			result = wsop.currentOutsideTemperature();
			if (WeatherStation.INITIAL_OUTSIDE_TEMPERATURE.equals(result.getMeasure())) {
				tester.logMessage("    Then the current outside temperature  is "
								  + "the initial one as defined by the weather"
								  + " station component");
			} else {
				tester.logMessage("     but was: " + result + " instead of "
								  + WeatherStation.INITIAL_OUTSIDE_TEMPERATURE);
				statistics.incorrectResult();
			}
		} catch (Throwable e) {
			statistics.incorrectResult();
			tester.logMessage("     but the exception " + e + " has been raised");
		}

		statistics.updateStatistics();
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#start()
	 */
	@Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();

		try {
			this.doPortConnection(
					this.wsop.getPortURI(),
					WeatherStation.STANDARD_INBOUND_PORT_URI,
					WeatherStationConnector.class.getCanonicalName());
		} catch (Throwable e) {
			throw new ComponentStartException(e) ;
		}
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#execute()
	 */
	@Override
	public synchronized void	execute() throws Exception
	{
		testCurrentInsideTemperature(this, wsop, statistics);
		testCurrentOutsideTemperature(this, wsop, statistics);
		this.statistics.statisticsReport(this);
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public synchronized void	finalise() throws Exception
	{
		this.doPortDisconnection(this.wsop.getPortURI());
		super.finalise();
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public synchronized void	shutdown() throws ComponentShutdownException
	{
		try {
			this.wsop.unpublishPort();
		} catch (Throwable e) {
			throw new ComponentShutdownException(e) ;
		}
		super.shutdown();
	}
}
// -----------------------------------------------------------------------------
