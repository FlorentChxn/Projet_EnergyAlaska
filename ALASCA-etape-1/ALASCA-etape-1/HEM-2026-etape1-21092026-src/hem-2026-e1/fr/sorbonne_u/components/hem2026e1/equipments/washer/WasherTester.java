package fr.sorbonne_u.components.hem2026e1.equipments.washer;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.hem2026e1.CVMIntegrationTest;
import fr.sorbonne_u.components.utils.tests.TestsStatistics;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.utils.aclocks.AcceleratedClock;
import fr.sorbonne_u.utils.aclocks.ClocksServer;
import fr.sorbonne_u.utils.aclocks.ClocksServerCI;
import fr.sorbonne_u.utils.aclocks.ClocksServerConnector;
import fr.sorbonne_u.utils.aclocks.ClocksServerOutboundPort;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherInternalControlOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherInternalControlConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;

// -----------------------------------------------------------------------------
/**
 * The class <code>WasherTester</code> implements a component performing
 * unit tests for the class <code>Washer</code> as a BCM component.
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Implementation Invariant</strong></p>
 * 
 * <pre>
 * invariant {@code washerUserInboundPortURI != null && !washerUserInboundPortURI.isEmpty()}
 * invariant {@code washerInternalControlInboundPortURI != null && !washerInternalControlInboundPortURI.isEmpty()}
 * invariant {@code washerExternalControlInboundPortURI != null && !washerExternalControlInboundPortURI.isEmpty()}
 * </pre>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant {@code X_RELATIVE_POSITION >= 0}
 * invariant {@code Y_RELATIVE_POSITION >= 0}
 * </pre>
 * 
 * <p>Created on : 2026-10-14</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
@RequiredInterfaces(required={WasherUserCI.class, WasherInternalControlCI.class, WasherExternalControlCI.class ,ClocksServerCI.class})
public class WasherTester extends AbstractComponent {
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    /**	in clock-driven scenario, the delay from the start instant at which
     *  the Washer is switched on.											*/
    public static final int SWITCH_ON_DELAY = 2;
    /**	in clock-driven scenario, the delay from the start instant at which
     *  the Washer is switched off.											*/
    public static final int SWITCH_OFF_DELAY = 9;

    /** when true, methods trace their actions.								*/
    public static boolean VERBOSE = false;
    /** when tracing, x coordinate of the window relative position.			*/
    public static int X_RELATIVE_POSITION = 0;
    /** when tracing, y coordinate of the window relative position.			*/
    public static int Y_RELATIVE_POSITION = 0;

    /** true if the component must perform unit tests, otherwise it
     *  executes integration tests actions.									*/
    protected final boolean isUnitTest;
    /** URI of the user component interface inbound port.					*/
    protected String washerUserInboundPortURI;
    /** URI of the internal control component interface inbound port.		*/
    protected String washerInternalControlInboundPortURI;
    /** URI of the external control component interface inbound port.		*/
    protected String washerExternalControlInboundPortURI;
    /** user component interface inbound port.								*/
    protected WasherUserOutboundPort			hop;
    /** internal control component interface inbound port.					*/
    protected WasherInternalControlOutboundPort	hicop;
    /** external control component interface inbound port.					*/
    protected WasherExternalControlOutboundPort	hecop;
    /** collector of test statistics.										*/
    protected TestsStatistics statistics;

    // -------------------------------------------------------------------------
    // Invariants
    // -------------------------------------------------------------------------

    /**
     * return true if the implementation invariants are observed, false
     * otherwise.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code ht != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param ht	instance to be tested.
     * @return		true if the implementation invariants are observed, false otherwise.
     */
    protected static boolean implementationInvariants(WasherTester w) {
        boolean ret = true;
        ret &= AssertionChecking.checkImplementationInvariant(
                w.washerUserInboundPortURI != null && !w.washerUserInboundPortURI.isEmpty(),
                WasherTester.class,w,
                "w.washerUserInboundPortURI != null && !w.washerUserInboundPortURI.isEmpty()");
        ret &= AssertionChecking.checkImplementationInvariant(
                w.washerInternalControlInboundPortURI != null && !w.washerInternalControlInboundPortURI.isEmpty(),
                WasherTester.class, w,
                "w.washerInternalControlInboundPortURI != null && !w.washerInternalControlInboundPortURI.isEmpty()");
        ret &= AssertionChecking.checkImplementationInvariant(
                w.washerExternalControlInboundPortURI != null && !w.washerExternalControlInboundPortURI.isEmpty(),
                WasherTester.class, w,
                "w.washerExternalControlInboundPortURI != null && !w.washerExternalControlInboundPortURI.isEmpty()");
        return ret;
    }

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
    public static boolean staticInvariants() {
        boolean ret = true;
        ret &= AssertionChecking.checkStaticInvariant(
                WasherTester.SWITCH_ON_DELAY >= 0,
                WasherTester.class,
                "WasherTester.SWITCH_ON_DELAY >= 0");
        ret &= AssertionChecking.checkStaticInvariant(
                WasherTester.SWITCH_OFF_DELAY >= 0,
                WasherTester.class,
                "WasherTester.SWITCH_OFF_DELAY >= 0");
        return ret;
    }

    /**
     * return true if the invariants are observed, false otherwise.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code w != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param w	instance to be tested.
     * @return		true if the invariants are observed, false otherwise.
     */
    protected static boolean invariants(WasherTester w) {
        assert w != null : new InvariantException("WasherTester instance cannot be null");
        boolean ret = true;
        ret &= staticInvariants();
        return ret;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * create a Washer test component.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param isUnitTest	true if the component must perform unit tests, otherwise it executes integration tests actions.
     * @throws Exception	<i>to do</i>.
     */
    protected WasherTester(boolean isUnitTest) throws Exception {
        this(isUnitTest, Washer.USER_INBOUND_PORT_URI,Washer.INTERNAL_CONTROL_INBOUND_PORT_URI, Washer.EXTERNAL_CONTROL_INBOUND_PORT_URI);
    }

    /**
     * create a Washer test component.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code washerUserInboundPortURI != null && !washerUserInboundPortURI.isEmpty()}
     * pre	{@code washerInternalControlInboundPortURI != null && !washerInternalControlInboundPortURI.isEmpty()}
     * pre	{@code washerExternalControlInboundPortURI != null && !washerExternalControlInboundPortURI.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param isUnitTest							true if the component must perform unit tests, otherwise it executes integration tests actions.
     * @param washerUserInboundPortURI				URI of the user component interface inbound port.
     * @param washerInternalControlInboundPortURI	URI of the internal control component interface inbound port.
     * @param washerExternalControlInboundPortURI	URI of the external control component interface inbound port.
     * @throws Exception							<i>to do</i>.
     */
    protected WasherTester(boolean isUnitTest, String washerUserInboundPortURI,String washerInternalControlInboundPortURI ,String washerExternalControlInboundPortURI) throws Exception {
        super(1, 1);
        this.isUnitTest = isUnitTest;
        this.initialise(washerUserInboundPortURI,washerInternalControlInboundPortURI,washerExternalControlInboundPortURI);
    }
    /**
     * create a washer test component.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code washerUserInboundPortURI != null && !washerUserInboundPortURI.isEmpty()}
     * pre	{@code washerInternalControlInboundPortURI != null && !washerInternalControlInboundPortURI.isEmpty()}
     * pre	{@code washerExternalControlInboundPortURI != null && !washerExternalControlInboundPortURI.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param isUnitTest							true if the component must perform unit tests, otherwise it executes integration tests actions.
     * @param reflectionInboundPortURI				URI of the reflection inbound port of the component.
     * @param washerUserInboundPortURI				URI of the user component interface inbound port.
     * @param washerInternalControlInboundPortURI	URI of the internal control component interface inbound port.
     * @param washerExternalControlInboundPortURI	URI of the external control component interface inbound port.
     * @throws Exception							<i>to do</i>.
     */
    protected WasherTester(boolean isUnitTest, String reflectionInboundPortURI, String washerUserInboundPortURI, String washerInternalControlInboundPortURI, String washerExternalControlInboundPortURI ) throws Exception {
        super(reflectionInboundPortURI, 1, 1);
        this.isUnitTest = isUnitTest;
        this.initialise(washerUserInboundPortURI,washerInternalControlInboundPortURI,washerExternalControlInboundPortURI);
    }

    /**
     * initialise a washer test component.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code washerUserInboundPortURI != null && !washerUserInboundPortURI.isEmpty()}
     * pre	{@code washerInternalControlInboundPortURI != null && !washerInternalControlInboundPortURI.isEmpty()}
     * pre	{@code washerExternalControlInboundPortURI != null && !washerExternalControlInboundPortURI.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param washerUserInboundPortURI				URI of the user component interface inbound port.
     * @param washerInternalControlInboundPortURI	URI of the internal control component interface inbound port.
     * @param washerExternalControlInboundPortURI	URI of the external control component interface inbound port.
     * @throws Exception							<i>to do</i>.
     */
    protected void initialise( String washerUserInboundPortURI,String washerInternalControlInboundPortURI, String washerExternalControlInboundPortURI ) throws Exception {
        this.washerUserInboundPortURI = washerUserInboundPortURI;
        this.hop = new WasherUserOutboundPort(this);
        this.hop.publishPort();
        this.washerInternalControlInboundPortURI = washerInternalControlInboundPortURI;
        this.hicop = new WasherInternalControlOutboundPort(this);
        this.hicop.publishPort();
        this.washerExternalControlInboundPortURI = washerExternalControlInboundPortURI;
        this.hecop = new WasherExternalControlOutboundPort(this);
        this.hecop.publishPort();
        if (VERBOSE) {
            this.tracer.get().setTitle("Washer tester component");
            this.tracer.get().setRelativePosition(X_RELATIVE_POSITION, Y_RELATIVE_POSITION);
            this.toggleTracing();
        }
        this.statistics = new TestsStatistics();
        assert WasherTester.implementationInvariants(this) :
                new ImplementationInvariantException("WasherTester.implementationInvariants(this)");
        assert WasherTester.invariants(this) :
                new InvariantException("WasherTester.invariants(this)");
    }

    // -------------------------------------------------------------------------
    // Component services implementation
    // -------------------------------------------------------------------------

    /** run the washer through a complete program.*/
    protected void runWasher() throws Exception {
        this.hop.startWasher();
        this.hicop.startWashing();
        this.hicop.startDraining();
        this.hicop.startRinsing();
        this.hicop.startSpinning();
        this.hicop.finishProgram();
    }

    /**
     * test the initial state of the washer.
     * <p><strong>Description</strong></p>
     * 
     * <p>Gherkin specification</p>
     * <p></p>
     * <pre>
     * Feature: initial state of the washer
     *   Scenario: a new washer is off with the default settings
     *    Given the washer is initialised
     *    And the washer has not been used yet
     *   When I read its state and its settings
     *   Then the washer state is OFF
     *   And the washer mode is ECO_40_60
     *   And the washer temperature is 40°C
     *   And the washer spin speed is 600 rpm
     *   And the washer is not delayed start
     *   And the washer is not suspended
     *   And the washer is not running a program
     * </pre>
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     * 
     */
    protected void testInitialState(){
        this.logMessage("Feature: initial state of the washer");
        this.logMessage("  Scenario: a new washer is off with the default settings");
        this.logMessage("    Given the washer is initialised");
        this.logMessage("    And the washer has not been used yet");
        try { 
            this.logMessage("    When I read its state and its settings");
            if (this.hop.getState() == Washer.WasherState.OFF) {
                this.logMessage("    Then the washer state is OFF");
            } else {
                this.logMessage("    But the washer state is " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            if (!this.hecop.isOnWasher()) {
                this.logMessage("    Then the washer is off");
            } else {
                this.logMessage("    But the washer is on");
                this.statistics.incorrectResult();
            }
            if (this.hop.getMode() == Washer.WasherMode.ECO_40_60) {
                this.logMessage("    Then the washer mode is ECO_40_60");
            } else {
                this.logMessage("    But the washer mode is " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            if (this.hop.getTemperature() == Washer.WasherTemperature.TEMP_40) {
                this.logMessage("    Then the washer temperature is 40°C");
            } else {
                this.logMessage("    But the washer temperature is " + this.hop.getTemperature());
                this.statistics.incorrectResult();
            }
            if (this.hop.getSpinSpeed() == Washer.WasherSpinSpeed.SPEED_600) {
                this.logMessage("    Then the washer spin speed is 600 rpm");
            } else {
                this.logMessage("    But the washer spin speed is " + this.hop.getSpinSpeed());
                this.statistics.incorrectResult();
            }
            if (!this.hop.isDelayedStart()) {
                this.logMessage("    Then the washer is not dalyaded start");
            } else {
                this.logMessage("    But the washer is delayed start");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is running a program");
                this.statistics.incorrectResult();
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /** test switching the washer on and off.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <p></p>
     * <pre>
     * Feature: switching the washer on and off
     *  Scenario: switching on then off
     *    Given the washer is off
     *    When I turn the washer on
     *    Then the washer state is ON
     *    And the washer is on
     *    When I switch it off
     *    Then the washer state is OFF
     *    And the washer is off
     * </pre>
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     * 
     */
    protected void testTurnOnOff() {
        this.logMessage("Feature: switching the washer on and off");
        this.logMessage("  Scenario: switching on then off");
        this.logMessage("    Given the washer is off");
        try {
            this.logMessage("    When I turn the washer on");
            this.hop.turnOnWasher();
            if (this.hop.getState() == Washer.WasherState.ON) {
                this.logMessage("    Then the washer state is ON");
            } else {
                this.logMessage("    Then the washer state is " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            if (this.hecop.isOnWasher()) {
                this.logMessage("    Then the washer is on");
                this.statistics.incorrectResult();
            } else {
                this.logMessage("    Then the washer is off");
            }
            this.logMessage("    When I switch it off");
            this.hop.turnOffWasher();
            if (this.hop.getState() == Washer.WasherState.OFF) {
                this.logMessage("    Then the washer state is OFF");
            } else {
                this.logMessage("    Then the washer state is " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            if (!this.hecop.isOnWasher()) {
                this.logMessage("    Then the washer is off");
            } else {
                this.logMessage("    But the washer is on");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I turn the washer on, off and on again");
            this.hop.turnOnWasher();
            this.hop.turnOffWasher();
            this.hop.turnOnWasher();
            if (this.hop.getState() == Washer.WasherState.ON) {
                this.logMessage("    Then the washer state is ON");
            } else {
                this.logMessage("    Then the washer state is " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * test setting all the modes of the washer.
     * <p><strong>Description</strong></p>
     * <p>Gherkin specification</p>
     * <p></p>
     * <pre>
     * Feature: setting the washer mode
     *   Scenario: setting all modes
     *     Given the washer is on
     *     When I set the mode to COTON
     *     Then the washer mode is COTON
     *     And the washer temperature is 90°C
     *     And the washer spin speed is 1200 rpm
     *     When I set the mode to EXPRESS
     *     Then the washer mode is EXPRESS
     *     And the washer temperature is 30°C
     *     And the washer spin speed is 800 rpm
     *     When I set the mode to ECO_40_60
     *     Then the washer mode is ECO_40_60
     *     And the washer temperature is 40°C
     *     And the washer spin speed is 600 rpm
     *     When I set the mode to DELICATE
     *     Then the washer mode is DELICATE
     *     And the washer temperature is 30°C
     *     And the washer spin speed is 400 rpm
     *     When I set the mode to WOOL
     *     Then the washer mode is WOOL
     *     And the washer temperature is 30°C
     *     And the washer spin speed is 400 rpm
     *     When I set the mode to RINSE
     *     Then the washer mode is RINSE
     *     And the washer temperature is 30°C
     *     And the washer spin speed is 400 rpm
     *     When I set the mode to SPIN
     *     Then the washer mode is SPIN
     *     And the washer temperature is 30°C
     *     And the washer spin speed is 1200 rpm
     *     When I set the mode to DRAIN
     *     Then the washer mode is DRAIN
     *     And the washer temperature is 30°C
     *     And the washer spin speed is 400 rpm
     * </pre>
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     */
    protected void testAllModes() {
        this.logMessage("Feature: setting the washer mode");
        this.logMessage("  Scenario: setting all modes");
        this.logMessage("    Given the washer is on");
        try {
            this.hop.turnOnWasher();
            for (Washer.WasherMode m : Washer.WasherMode.values()) {
                this.logMessage("    When I set the mode to " + m);
                this.hop.setMode(m);
                if (this.hop.getMode() == m) {
                    this.logMessage("    Then the washer mode is " + m);
                } else {
                    this.logMessage("    But the washer mode is " + this.hop.getMode());
                    this.statistics.incorrectResult();
                }
                if (this.hop.getTemperature() == m.getTemperature()) {
                    this.logMessage("    Then the washer temperature is " + m.getTemperature());
                } else {
                    this.logMessage("    But the washer temperature is " + this.hop.getTemperature());
                    this.statistics.incorrectResult();
                }
                if (this.hop.getSpinSpeed() == m.getSpinSpeed()) {
                    this.logMessage("    Then the washer spin speed is " + m.getSpinSpeed());
                } else {
                    this.logMessage("    But the washer spin speed is " + this.hop.getSpinSpeed());
                    this.statistics.incorrectResult();
                }
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test setting every available washer temperature.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: setting the washer temperature
     *   Scenario: each temperature can be set without changing the mode
     *     Given the washer is on with the mode COTON
     *     When I set the temperature to each available temperature
     *     Then the washer temperature is the selected temperature
     *     And the washer mode is still COTON
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testAllTemperatures() {
        this.logMessage("Feature: setting the washer temperature");
        this.logMessage("  Scenario: each temperature can be set and leaves the mode unchanged");
        this.logMessage("    Given the washer is on with the mode COTON");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            for (Washer.WasherTemperature t : Washer.WasherTemperature.values()) {
                this.logMessage("    When I set the temperature to " + t);
                this.hop.setTemperature(t);
                if (this.hop.getTemperature() == t) {
                    this.logMessage("    Then the washer temperature is " + t);
                } else {
                    this.logMessage("    But the washer temperature is " + this.hop.getTemperature());
                    this.statistics.incorrectResult();
                }
                if (this.hop.getMode() == Washer.WasherMode.COTON) {
                    this.logMessage("    And the washer mode is still COTON");
                } else {
                    this.logMessage("    But the washer mode is " + this.hop.getMode());
                    this.statistics.incorrectResult();
                }
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test setting every available washer spin speed.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: setting the washer spin speed
     *   Scenario: each spin speed can be set without changing the mode
     *     Given the washer is on with the mode COTON
     *     When I set the spin speed to each available spin speed
     *     Then the washer spin speed is the selected spin speed
     *     And the washer mode is still COTON
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testAllSpinSpeeds() {
        this.logMessage("Feature: setting the washer spin speed");
        this.logMessage("  Scenario: each spin speed can be set and leaves the mode unchanged");
        this.logMessage("    Given the washer is on with the mode COTON");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            for (Washer.WasherSpinSpeed s : Washer.WasherSpinSpeed.values()) {
                this.logMessage("    When I set the spin speed to " + s);
                this.hop.setSpinSpeed(s);
                if (this.hop.getSpinSpeed() == s) {
                    this.logMessage("    Then the washer spin speed is " + s);
                } else {
                    this.logMessage("    But the washer spin speed is " + this.hop.getSpinSpeed());
                    this.statistics.incorrectResult();
                }
                if (this.hop.getMode() == Washer.WasherMode.COTON) {
                    this.logMessage("    And the washer mode is still COTON");
                } else {
                    this.logMessage("    But the washer mode is " + this.hop.getMode());
                    this.statistics.incorrectResult();
                }
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that selecting a mode replaces the previous adjustments.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: choosing a washing mode
     *   Scenario: choosing a mode erases the previous adjustments
     *     Given the washer is on
     *     And the temperature TEMP_90 and the spin speed SPEED_1200 are set
     *     When I set the mode to EXPRESS
     *     Then the washer mode is EXPRESS
     *     And the washer temperature is TEMP_30
     *     And the washer spin speed is SPEED_800
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testModeOverridesAdjustments() {
        this.logMessage("Feature: choosing a washing mode");
        this.logMessage("  Scenario: choosing a mode erases the previous adjustments");
        this.logMessage("    Given the washer is on");
        this.logMessage("    And the temperature TEMP_90 and the spin speed SPEED_1200 have been set");
        try {
            this.hop.turnOnWasher();
            this.hop.setTemperature(Washer.WasherTemperature.TEMP_90);
            this.hop.setSpinSpeed(Washer.WasherSpinSpeed.SPEED_1200);
            this.logMessage("    When I set the mode to EXPRESS");
            this.hop.setMode(Washer.WasherMode.EXPRESS);
            if (this.hop.getTemperature() == Washer.WasherTemperature.TEMP_30) {
                this.logMessage("    Then the washer temperature is " + this.hop.getTemperature());
            } else {
                this.logMessage("    But the washer temperature is " + this.hop.getTemperature());
                this.statistics.incorrectResult();
            }
            if (this.hop.getSpinSpeed() == Washer.WasherSpinSpeed.SPEED_800) {
                this.logMessage("    Then the washer spin speed is " + this.hop.getSpinSpeed());
            } else {
                this.logMessage("    But the washer spin speed is " + this.hop.getSpinSpeed());
                this.statistics.incorrectResult();
            }
            if (this.hop.getMode() == Washer.WasherMode.EXPRESS) {
                this.logMessage("    Then the washer mode is EXPRESS");
            } else {
                this.logMessage("    But the washer mode is " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test enabling and disabling delayed start.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: delayed start
     *   Scenario: setting and clearing a delayed start
     *     Given the washer is on
     *     When I set the delayed start to true
     *     Then the washer is in delayed start mode
     *     When I set the delayed start to false
     *     Then the washer is not in delayed start mode
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testDelayedStart() {
        this.logMessage("Feature: delayed start");
        this.logMessage("  Scenario: setting a delayed start");
        this.logMessage("    Given the washer is on");
        try {
            this.hop.turnOnWasher();
            this.logMessage("    When I set the delayed start to true");
            this.hop.setDelayedStart(true);
            if (this.hop.isDelayedStart()) {
                this.logMessage("    Then the washer is in delayed start mode");
            } else {
                this.logMessage("    But the washer is not in delayed start mode");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I set the delayed start to false");
            this.hop.setDelayedStart(false);
            if (!this.hop.isDelayedStart()) {
                this.logMessage("    Then the washer is not in delayed start mode");
            } else {
                this.logMessage("    But the washer is in delayed start mode");
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that settings cannot be changed while the washer is off.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: refusing commands when the washer is off
     *   Scenario: all setting commands are refused when the washer is off
     *     Given the washer is off
     *     When I try to set a mode, a temperature and a spin speed
     *     Then a PreconditionException is raised for each command
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testRefusedWhenOff() {
        this.logMessage("Feature: refusing commands when the washer is off");
        this.logMessage("  Scenario: all commands are refused when the washer is off");
        this.logMessage("    Given the washer is off");
        try {
            this.hop.turnOffWasher();
            this.logMessage("    When I try to set amode , a tempereture and a spin speed");
            try {
                this.hop.setMode(Washer.WasherMode.EXPRESS);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            try {
                this.hop.setTemperature(Washer.WasherTemperature.TEMP_90);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            try {
                this.hop.setSpinSpeed(Washer.WasherSpinSpeed.SPEED_1200);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            this.hop.turnOnWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that settings cannot be changed while the washer is running.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: refusing commands when the washer is running
     *   Scenario: all setting commands are refused while a program is running
     *     Given the washer is on and running a program
     *     When I try to set a mode, a temperature and a spin speed
     *     Then a PreconditionException is raised for each command
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testRefusedWhenRunning() {
        this.logMessage("Feature: refusing commands when the washer is running");
        this.logMessage("  Scenario: all commands are refused when the washer is running");
        this.logMessage("    Given the washer is on and running a program");
        try {
            this.hop.turnOnWasher();
            this.hop.startWasher();
            this.logMessage("    When I try to set amode , a tempereture and a spin speed");
            try {
                this.hop.setMode(Washer.WasherMode.EXPRESS);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            try {
                this.hop.setTemperature(Washer.WasherTemperature.TEMP_90);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            try {
                this.hop.setSpinSpeed(Washer.WasherSpinSpeed.SPEED_1200);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that starting the washer is refused while it is off.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: refusing to start the washer when it is off
     *   Scenario: starting the washer is refused when it is off
     *     Given the washer is off
     *     When I try to start the washer
     *     Then a PreconditionException is raised
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testSartRefusedWhenOff() {
        this.logMessage("Feature: refusing to start when the washer is off");
        this.logMessage("  Scenario: starting the washer is refused when it is off");
        this.logMessage("    Given the washer is off");
        try {
            this.hop.turnOffWasher();
            this.logMessage("    When I try to start the washer");
            try {
                this.hop.startWasher();
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            this.hop.turnOnWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test starting a complete washer program.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: starting the washer
     *   Scenario: starting the washer when it is on
     *     Given the washer is on
     *     When I start the washer
     *     Then the washer is running a program
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testStartWasher() {
        this.logMessage("Feature: starting the washer");
        this.logMessage("  Scenario: starting the washer when it is on");
        this.logMessage("    Given the washer is on");
        try {
            this.hop.turnOnWasher();
            this.logMessage("    When I start the washer");
            this.runWasher();
            if (this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test a complete program through all its phases.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: setting all settings and starting the washer
     *   Scenario: the program goes through all its phases
     *     Given the washer is on with the mode COTON
     *     When I start the washer
     *     Then the washer is in the filling phase
     *     When the controller starts the washing phase
     *     Then the washer is in the washing phase
     *     When the controller starts the draining phase
     *     Then the washer is in the draining phase
     *     When the controller starts the rinsing phase
     *     Then the washer is in the rinsing phase
     *     When the controller starts the spinning phase
     *     Then the washer is in the spinning phase
     *     When the controller finishes the program
     *     Then the washer is not running a program
     *     And the washer is off
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testAllSettingsAndRun() {
        this.logMessage("Feature: setting all settings and start the washer");
        this.logMessage("  Scenario: the program goes throught all its phases");
        this.logMessage("    Given the washer is on");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            this.logMessage("    When I start the washer");
            this.hop.startWasher();
            if (this.hop.getState() == Washer.WasherState.FILLING) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            
            this.logMessage("    When the controller starts the washing phase");
            this.hicop.startWashing();
            if (this.hop.getState() == Washer.WasherState.WASHING) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the controller starts the draining phase");
            this.hicop.startDraining();
            if (this.hop.getState() == Washer.WasherState.DRAINING) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the controller starts the rinsing phase");
            this.hicop.startRinsing();
            if (this.hop.getState() == Washer.WasherState.RINSING) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the controller starts the spinning phase");
            this.hicop.startSpinning();
            if (this.hop.getState() == Washer.WasherState.SPINNING) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the controller finishes the program");
            this.hicop.finishProgram();
            if (this.hop.getState() == Washer.WasherState.FINISHED) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is running a program");
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
            if (this.hop.getState() == Washer.WasherState.OFF) {
                this.logMessage("    Then the washer is off");
            } else {
                this.logMessage("    But the washer is not off");
                this.statistics.incorrectResult();
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test suspending and resuming the washer during every program phase.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: suspending and resuming the washer
     *   Scenario: suspending and resuming the washer during a program
     *     Given the washer is on and running a program
     *     When the energy manager suspends the washer during each program phase
     *     Then the washer is suspended
     *     And the washer is not running a program
     *     When the energy manager resumes the washer
     *     Then the washer is not suspended
     *     And the washer is running a program
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testSuspendResume() {
        this.logMessage("Feature: suspending and resuming the washer");
        this.logMessage("  Scenario: suspending and resuming the washer");
        this.logMessage("    Given the washer is on");
        try {
            this.hop.turnOnWasher();
            this.hop.startWasher();
            this.logMessage("    When the energy manager suspends the washer during Filling");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the energy manager resumes the washer");
            this.hecop.resume();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hicop.startWashing();
            this.logMessage("    When the energy manager suspends the washer during Washing");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the energy manager resumes the washer");
            this.hecop.resume();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hicop.startDraining();
            this.logMessage("    When the energy manager suspends the washer during Draining");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the energy manager resumes the washer");
            this.hecop.resume();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hicop.startRinsing();
            this.logMessage("    When the energy manager suspends the washer during Rinsing");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the energy manager resumes the washer");
            this.hecop.resume();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hicop.startSpinning();
            this.logMessage("    When the energy manager suspends the washer during Spinning");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the energy manager resumes the washer");
            this.hecop.resume();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hicop.finishProgram();
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that suspending an off washer has no effect.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: suspending and resuming a washing program
     *   Scenario: suspending the washer when it is off has no effect
     *     Given the washer is off
     *     When the energy manager suspends the washer
     *     Then the washer is not suspended
     *     And the washer is not running a program
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    public void testSuspendIgnored() {
        this.logMessage("Feature: suspending and resuming a washing program");
        this.logMessage("  Scenario: suspending the washer when it is off has no effect");
        this.logMessage("    Given the washer is off");
        try {
            this.hop.turnOffWasher();
            this.logMessage("    When the energy manager suspends the washer");
            this.hecop.suspend();
            if (!this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is running a program");
                this.statistics.incorrectResult();
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that suspending an already suspended washer has no additional effect.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: suspending and resuming a washing program
     *   Scenario: suspending the washer twice has no effect
     *     Given the washer is on and running a program
     *     When the energy manager suspends the washer
     *     Then the washer is suspended
     *     And the washer is not running a program
     *     When the energy manager suspends the washer again
     *     Then the washer is suspended
     *     And the washer is not running a program
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    public void testDoubleSuspend() {
        this.logMessage("Feature: suspending and resumin a washing program");
        this.logMessage("  Scenario: suspending the washer twice has no effect");
        this.logMessage("    Given the washer is on and running a program");
        try {
            this.hop.turnOnWasher();
            this.hop.startWasher();
            this.logMessage("    When the energy manager suspends the washer");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is running a program");
                this.statistics.incorrectResult();
            }
            this.logMessage("    When the energy manager suspends the washer again");
            this.hecop.suspend();
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            if (!this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is not running a program");
            } else {
                this.logMessage("    But the washer is running a program");
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that resuming a washer that is not suspended has no effect.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: suspending and resuming a washing program
     *   Scenario: resuming the washer when it is not suspended has no effect
     *     Given the washer is on and running a program
     *     When the energy manager resumes the washer while it is on
     *     Then the washer is ON
     *     When the energy manager resumes the washer while it is running
     *     Then the washer is not suspended
     *     And the washer is running a program
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testResumeIgnored() {
        this.logMessage("Feature: suspending and resuming a washing program");
        this.logMessage("  Scenario: resuming the washer when it is not suspended has no effect");
        this.logMessage("    Given the washer is on and running a program");
        try {
            this.hop.turnOnWasher();
            this.logMessage("    When the energy manager resumes the washer while it's on");
            if (this.hop.getState() == Washer.WasherState.ON) {
                this.logMessage("    Then the washer is ON");
            } else {
                this.logMessage("    But the washer is" + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hop.startWasher();
            this.logMessage("    When the energy manager resumes the washer while it's running");
            this.hecop.resume();
            if (!this.hop.isSuspended()) {
                this.logMessage("    Then the washer is not suspended");
            } else {
                this.logMessage("    But the washer is suspended");
                this.statistics.incorrectResult();
            }
            if (this.hop.isProgramRunning()) {
                this.logMessage("    Then the washer is running a program");
            } else {
                this.logMessage("    But the washer is not running a program");
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test that commands are refused while the washer is suspended.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: refusing commands when the washer is suspended
     *   Scenario: all commands are refused when the washer is suspended
     *     Given the washer is on and running a program
     *     When the energy manager suspends the washer
     *     Then the washer is suspended
     *     When I try to set a mode, a temperature and a spin speed
     *     Then a PreconditionException is raised for each setting
     *     When the controller tries to start the washing phase
     *     Then a PreconditionException is raised
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    protected void testRefusedWhenSuspended() {
        this.logMessage("Feature: refusing commands when the washer is suspended");
        this.logMessage("  Scenario: all commands are refused when the washer is suspended");
        this.logMessage("    Given the washer is on and running a program");
        try {
            this.hop.turnOnWasher();
            this.hop.startWasher();
            this.hecop.suspend();
            this.logMessage("    When I try to set a mode , a tempereture and a spin speed");
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            try {
                this.hop.setMode(Washer.WasherMode.EXPRESS);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            try {
                this.hop.setTemperature(Washer.WasherTemperature.TEMP_90);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            try {
                this.hop.setSpinSpeed(Washer.WasherSpinSpeed.SPEED_1200);
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            this.logMessage("    When the controller tries to start the washing phase");
            try {
                this.hicop.startWashing();
                this.logMessage("    But no exception has been raised");
                this.statistics.incorrectResult();
            } catch (PreconditionException e) {
                this.logMessage("    Then a PreconditionException has been raised");
            }
            this.hecop.resume();
            this.hop.turnOffWasher();
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * Test turning off a suspended washer.
     * <p><strong>Description</strong></p>
     *
     * <p>Gherkin specification</p>
     * <pre>
     * Feature: turning off the washer when it is suspended
     *   Scenario: turning off the washer when it is suspended
     *     Given the washer is on and running a program
     *     When the energy manager suspends the washer
     *     Then the washer is suspended
     *     When the energy manager turns off the washer
     *     Then the washer is off
     * </pre>
     * <p><strong>Contract</strong></p>
     * <pre>
     * pre {@code true}
     * post {@code true}
     * </pre>
     */
    public void testTurnOffWhenSuspended() {
        this.logMessage("Feature: turning off the washer when it is suspended");
        this.logMessage("  Scenario: turning off the washer when it is suspended");
        this.logMessage("    Given the washer is on and running a program");
        try {
            this.hop.turnOnWasher();
            this.hop.startWasher();
            this.hecop.suspend();
            this.logMessage("    When the energy manager turns off the washer");
            if (this.hop.isSuspended()) {
                this.logMessage("    Then the washer is suspended");
            } else {
                this.logMessage("    But the washer is not suspended");
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
            if (this.hop.getState() == Washer.WasherState.OFF) {
                this.logMessage("    Then the washer is off");
            } else {
                this.logMessage("    But the washer is not off");
                this.statistics.incorrectResult();
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    but the exception "+ e + "has been raised");
        }
        this.statistics.updateStatistics();
    }

    /**
     * run all unit tests.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     */
    protected void runAllTests() {
        this.testInitialState();
        this.testTurnOnOff();
        this.testAllModes();
        this.testAllTemperatures();
        this.testAllSpinSpeeds();
        this.testModeOverridesAdjustments();
        this.testDelayedStart();
        this.testRefusedWhenOff();
        this.testRefusedWhenRunning();
        this.testSuspendResume();
        this.testSartRefusedWhenOff();
        this.testStartWasher();
        this.testAllSettingsAndRun();
        this.testSuspendResume();
        this.testSuspendIgnored();
        this.testDoubleSuspend();
        this.testResumeIgnored();
        this.testRefusedWhenSuspended();
        this.testTurnOffWhenSuspended();
        this.logMessage("End of the washer tests.");
        this.logMessage(this.statistics.toString());
    }

    // -------------------------------------------------------------------------
    // Component life-cycle
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#start()
     */
    @Override
    public synchronized void start() throws ComponentStartException {
        super.start();
        try {
            this.doPortConnection(
                    this.hop.getPortURI(),
                    this.washerUserInboundPortURI,
                    WasherUserConnector.class.getCanonicalName());
            this.doPortConnection(
                    this.hicop.getPortURI(),
                    this.washerInternalControlInboundPortURI,
                    WasherInternalControlConnector.class.getCanonicalName());
            this.doPortConnection(
                    this.hecop.getPortURI(),
                    this.washerExternalControlInboundPortURI,
                    WasherExternalControlConnector.class.getCanonicalName());
        } catch (Exception e) {
            throw new ComponentStartException(e);   
        }
    }

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#execute()
     */
    @Override
    public synchronized void	execute() throws Exception
    {
        if (this.isUnitTest) {
            this.runAllTests();
        } else {
            ClocksServerOutboundPort clocksServerOutboundPort = new ClocksServerOutboundPort(this);
            clocksServerOutboundPort.publishPort();
            this.doPortConnection(
                    clocksServerOutboundPort.getPortURI(),
                    ClocksServer.STANDARD_INBOUNDPORT_URI,
                    ClocksServerConnector.class.getCanonicalName());
            this.traceMessage("Washer tester gets the clock.\n");
            AcceleratedClock ac =
                    clocksServerOutboundPort.getClock(
                                        CVMIntegrationTest.CLOCK_URI);
            this.doPortDisconnection(
                        clocksServerOutboundPort.getPortURI());
            clocksServerOutboundPort.unpublishPort();
            clocksServerOutboundPort = null;

            Instant startInstant = ac.getStartInstant();
            Instant washerSwitchOn = startInstant.plusSeconds(SWITCH_ON_DELAY);
            Instant washerSwitchOff = startInstant.plusSeconds(SWITCH_OFF_DELAY);
            this.traceMessage("Washer tester waits until start.\n");
            ac.waitUntilStart();
            this.traceMessage("Washer tester schedules switch on and off.\n");
            long delayToSwitchOn = ac.nanoDelayUntilInstant(washerSwitchOn);
            long delayToSwitchOff = ac.nanoDelayUntilInstant(washerSwitchOff);

            // This is to avoid mixing the 'this' of the task object with the 'this'
            // representing the component object in the code of the next methods run
            AbstractComponent o = this;

            // schedule the switch on heater
            this.scheduleTaskOnComponent(
                    new AbstractComponent.AbstractTask() {
                        @Override
                        public void run() {
                            try {
                                o.traceMessage("Washer switches on.\n");
                                hop.turnOnWasher();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }, delayToSwitchOn, TimeUnit.NANOSECONDS);

            // to be completed with a more covering scenario

            // schedule the switch off heater
            this.scheduleTaskOnComponent(
                    new AbstractComponent.AbstractTask() {
                        @Override
                        public void run() {
                            try {
                                o.traceMessage("Washer switches off.\n");
                                hop.turnOffWasher();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }, delayToSwitchOff, TimeUnit.NANOSECONDS);
        }
    }
    
    /**
     * @see fr.sorbonne_u.components.AbstractComponent#finalise()
     */
    @Override
    public synchronized void finalise() throws Exception {
        this.doPortDisconnection(this.hop.getPortURI());
        this.doPortDisconnection(this.hicop.getPortURI());
        this.doPortDisconnection(this.hecop.getPortURI());
        super.finalise();
    }

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
     */
    @Override
    public synchronized void	shutdown() throws ComponentShutdownException{
        try {
            this.hop.unpublishPort();
            this.hicop.unpublishPort();
            this.hecop.unpublishPort();
        } catch (Throwable e) {
            throw new ComponentShutdownException(e) ;
        }
        super.shutdown();
    }
}