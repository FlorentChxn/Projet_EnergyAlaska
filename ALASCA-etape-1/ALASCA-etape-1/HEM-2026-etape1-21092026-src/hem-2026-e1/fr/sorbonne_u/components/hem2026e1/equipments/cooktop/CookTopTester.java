package fr.sorbonne_u.components.hem2026e1.equipments.cooktop;

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
import fr.sorbonne_u.alasca.physical_data.Measure;
import fr.sorbonne_u.alasca.physical_data.MeasurementUnit;
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections.CookTopOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections.CookTopConnector;

@RequiredInterfaces(required={CookTopUserCI.class, ClocksServerCI.class})
public class CookTopTester extends AbstractComponent {
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    /**	in clock-driven scenario, the delay from the start instant at which
     *  the CookTop is switched on.											*/
    public static final int SWITCH_ON_DELAY = 2;
    /**	in clock-driven scenario, the delay from the start instant at which
     *  the CookTop is switched off.											*/
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
    protected String cookTopInboundPortURI;
    /** user component interface inbound port.								*/
    protected CookTopOutboundPort			hop;
    /** collector of test statistics.										*/
    protected TestsStatistics statistics;

    protected static boolean implementationInvariants(CookTopTester w) {
        boolean ret = true;
        ret &= AssertionChecking.checkImplementationInvariant(
                w.cookTopInboundPortURI != null && !w.cookTopInboundPortURI.isEmpty(),
                CookTopTester.class,w,
                "w.cookTopInboundPortURI != null && !w.cookTopInboundPortURI.isEmpty()");
        return ret;
    }

    public static boolean staticInvariants() {
        boolean ret = true;
        ret &= AssertionChecking.checkStaticInvariant(
                CookTopTester.SWITCH_ON_DELAY >= 0,
                CookTopTester.class,
                "CookTopTester.SWITCH_ON_DELAY >= 0");
        ret &= AssertionChecking.checkStaticInvariant(
                CookTopTester.SWITCH_OFF_DELAY >= 0,
                CookTopTester.class,
                "CookTopTester.SWITCH_OFF_DELAY >= 0");
        return ret;
    }

    protected static boolean invariants(CookTopTester w) {
        assert w != null : new InvariantException("CookTopTester instance cannot be null");
        boolean ret = true;
        ret &= staticInvariants();
        return ret;
    }
    protected CookTopTester(boolean isUnitTest) throws Exception {
        this(isUnitTest, CookTop.INBOUND_PORT_URI);
    }

    protected CookTopTester(boolean isUnitTest, String cookTopInboundPortURI) throws Exception {
        super(1, 1);
        assert cookTopInboundPortURI != null && !cookTopInboundPortURI.isEmpty() : new PreconditionException("cookTopInboundPortURI != null && !cookTopInboundPortURI.isEmpty()");
        this.isUnitTest = isUnitTest;
        this.initialise(cookTopInboundPortURI);
    }

    protected CookTopTester(boolean isUnitTest, String reflectionInboundPortURI, String cookTopInboundPortURI ) throws Exception {
        super(reflectionInboundPortURI, 1, 1);
        this.isUnitTest = isUnitTest;
        this.initialise(cookTopInboundPortURI);
    }

    protected void initialise( String cookTopInboundPortURI) throws Exception {
        this.cookTopInboundPortURI = cookTopInboundPortURI;
        this.hop = new CookTopOutboundPort(this);
        this.hop.publishPort();
        if (VERBOSE) {
            this.tracer.get().setTitle("CookTop tester component");
            this.tracer.get().setRelativePosition(X_RELATIVE_POSITION, Y_RELATIVE_POSITION);
            this.toggleTracing();
        }
        this.statistics = new TestsStatistics();
                assert	CookTopTester.implementationInvariants(this) :
                new ImplementationInvariantException(
                        "CookTopTester.implementationInvariants(this)");
        assert	CookTopTester.invariants(this) :
                new InvariantException("CookTopTester.invariants(this)");
    }

    public void testState(){
        this.logMessage("Feature : Getting the state of the cooktop.\n");
        this.logMessage("  Scenario : Getting the state when off.\n");
        this.logMessage("    Given the cooktop is off.\n");
        CookTopState result = null;
        try {
            result = this.hop.getState(0);
            this.logMessage("    When the state is requested.\n");
            this.logMessage("    Then the state is " + result + ".\n");
            if (result == CookTopState.OFF) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The state is not OFF when the cooktop is off.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }

        this.statistics.updateStatistics();
        result = null;
        try {
            this.hop.turnOnCookTop();
            result = this.hop.getState(0);
            this.logMessage("  Scenario : Getting the state when on.\n");
            this.logMessage("    Given the cooktop is on.\n");
            this.logMessage("    When the state is requested.\n");
            this.logMessage("    Then the state is " + result + ".\n");
            if (result == CookTopState.OFF) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The state is not ON when the cooktop is on.");
            }
            this.hop.turnOnZone(0);
            result = this.hop.getState(0);
            this.logMessage("    Then the state is " + result + ".\n");
            if (result == CookTopState.ON) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The state is not ON when the cooktop is on.");
            }
            this.hop.turnOffZone(1);
            result = this.hop.getState(1);
            this.logMessage("    Then the state is " + result + ".\n");
            if (result == CookTopState.ON) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The state is not OFF when the cooktop is on and the zone is off.");
            }
            this.hop.turnOffCookTop();
            result = this.hop.getState(0);
            this.logMessage("    Then the state is " + result + ".\n");
            if (result == CookTopState.OFF) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The state is not OFF when the cooktop is off.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }

    }

    public void testPower(){
        this.logMessage("Feature : Getting the power of the cooktop.\n");
        this.logMessage("  Scenario : Getting the power when on.\n");
        this.logMessage("    Given the cooktop is on.\n");
        int result = -1;
        try {
            this.hop.turnOnCookTop();
            this.hop.turnOnZone(0);
            result = this.hop.getPower(0);
            this.logMessage("    When the power is requested.\n");
            this.logMessage("    Then the power is " + result + ".\n");
            if (result == 0) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The power is not 0 when the cooktop is off.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
    }

    public void testTurnOnCookTop() {
        this.logMessage("Feature : Turning on the cooktop.\n");
        this.logMessage("  Scenario : Turning on when off.\n");
        this.logMessage("    Given the cookTop is off");
        try {
            this.hop.getState(0);
            if(this.hop.getState(0) == CookTopState.OFF) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop is not OFF when it should be.");
            }
            this.logMessage("    When the cooktop is turned on.\n");
            this.hop.turnOnCookTop();
            this.logMessage("    Then the cooktop is on.\n");
            if (this.hop.isOn()){
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop is not ON when it should be.");
            }
            this.logMessage("    When the cooktop zone is turned on.\n");
            this.hop.turnOnZone(0);
            this.logMessage("    Then the cooktop zone is on.\n");
            if (this.hop.getState(0) == CookTopState.ON){
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone is not ON when it should be.");
            }
            this.logMessage("     And the power of the cooktop zone is 0.\n");
            if (this.hop.getPower(0) == 0){
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                this.statistics.incorrectResult();
                assert false : new AssertionError("The cooktop zone power is not 0 when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
        this.logMessage("  Scenario : Turning on the cooktop when on.\n");
        this.logMessage("    Given the cooktop is on.\n");
        try {
            this.logMessage("    When the cooktop is turned on.\n");
            this.logMessage("    Then the cooktop is on.\n");
            if (this.hop.isOn()) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop is not ON when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
        this .logMessage("  Scenario : Turning on the cooktop when on and zone is on.\n");
        this.logMessage("    Given the cooktop is on and zone is on.\n");
        try {
            this.hop.turnOnCookTop();
            this.logMessage("    When the cooktop is turned on.\n");
            this.logMessage("    Then the cooktop is on.\n");
            if (this.hop.isOn()) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop is not ON when it should be.");
            }
            this.hop.turnOnZone(0);
            this.logMessage("    And the cooktop zone is on.\n");
            if (this.hop.getState(0) == CookTopState.ON) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone is not ON when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
        this.logMessage("    When the cooktop zone is turned off.\n");
        try {
            this.hop.turnOffZone(0);
            this.logMessage("    Then the cooktop zone is off.\n");
            if (this.hop.getState(0) == CookTopState.OFF) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone is not OFF when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
        this.logMessage("    When the cooktop is turned off.\n");
        try {
            this.hop.turnOffCookTop();
            this.logMessage("    Then the cooktop is off.\n");
            if (!this.hop.isOn()) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop is not OFF when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
        this.logMessage("  Scenario : Turning off the cooktop while a zone is on.\n");
        this.logMessage("    Given the cooktop is off and zone is on.\n");
        try {
            this.hop.turnOnCookTop();
            this.hop.turnOnZone(0);
            this.logMessage("    When the cooktop is turned off.\n");
            this.hop.turnOffCookTop();
            this.logMessage("    Then the cooktop is off.\n");
            if (!this.hop.isOn()) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop is not OFF when it should be.");
            }
            this.logMessage("    And the cooktop zone is off.\n");
            if (this.hop.getState(0) == CookTopState.OFF) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone is not OFF when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
    }

    public void testSetPower() {
        this.logMessage("Feature : Setting the power of the cooktop.\n");
        this.logMessage("  Scenario : Setting the power when on.\n");
        this.logMessage("    Given the cooktop is on.\n");
        try {
            this.hop.turnOnCookTop();
            this.hop.turnOnZone(0);
            this.logMessage("    When the power is on.\n");
            if (this.hop.getPower(0) == 0) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone power is not 0 when it should be.");
            }
            this.hop.increasePowerZone(0);
            this.logMessage("    Then the power is increased 0 -> 1.\n");
            if (this.hop.getPower(0) == 1) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone power is not 1 when it should be.");
            }
            this.hop.increasePowerZone(0);
            this.logMessage("    Then the power is increased 1 -> 2.\n");
            if (this.hop.getPower(0) == 2) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone power is not 2 when it should be.");
            }
            this.hop.decreasePowerZone(0);
            this.logMessage("    Then the power is decreased 2 -> 1.\n");
            if (this.hop.getPower(0) == 1) {
                this.logMessage("    The test has passed.\n");
            } else {
                this.statistics.incorrectResult();
                this.logMessage("    The test has failed.\n");
                assert false : new AssertionError("The cooktop zone power is not 1 when it should be.");
            }
        } catch (Exception e) {
            this.statistics.incorrectResult();
            this.logMessage("    Then an exception occurred: " + e.getMessage() + ".\n");
            assert false : new AssertionError("Exception occurred: " + e.getMessage());
        }
    }
    protected void runAllTests() {
        this.testState();
        this.testPower();
        this.testTurnOnCookTop();
        this.testSetPower();
        return;
    }

    @Override
    public synchronized void start() throws ComponentStartException {
        super.start();
        try {
            this.doPortConnection(
                    this.hop.getPortURI(),
                    this.cookTopInboundPortURI,
                    CookTopConnector.class.getCanonicalName());
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
                                hop.turnOnCookTop();
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
                                o.traceMessage("Cooktop switches off.\n");
                                hop.turnOffCookTop();
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
        super.finalise();
    }

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
     */
    @Override
    public synchronized void	shutdown() throws ComponentShutdownException{
        try {
            this.hop.unpublishPort();
        } catch (Throwable e) {
            throw new ComponentShutdownException(e) ;
        }
        super.shutdown();
    }
}