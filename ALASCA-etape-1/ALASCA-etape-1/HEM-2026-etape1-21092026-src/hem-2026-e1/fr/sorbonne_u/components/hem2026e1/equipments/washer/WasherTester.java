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
import fr.sorbonne_u.alasca.physical_data.Measure;
import fr.sorbonne_u.alasca.physical_data.MeasurementUnit;
import fr.sorbonne_u.alasca.physical_data.TimedMeasure;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherInternalControlOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherInternalControlConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;

@RequiredInterfaces(required={WasherUserCI.class, WasherInternalControlCI.class, ClocksServerCI.class})
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

    protected static boolean invariants(WasherTester w) {
        assert w != null : new InvariantException("WasherTester instance cannot be null");
        boolean ret = true;
        ret &= staticInvariants();
        return ret;
    }
    protected WasherTester(boolean isUnitTest) throws Exception {
        this(isUnitTest, Washer.USER_INBOUND_PORT_URI,Washer.INTERNAL_CONTROL_INBOUND_PORT_URI, Washer.EXTERNAL_CONTROL_INBOUND_PORT_URI);
    }

    protected WasherTester(boolean isUnitTest, String washerUserInboundPortURI,String washerInternalControlInboundPortURI ,String washerExternalControlInboundPortURI) throws Exception {
        super(1, 1);
        this.isUnitTest = isUnitTest;
        this.initialise(washerUserInboundPortURI,washerInternalControlInboundPortURI,washerExternalControlInboundPortURI);
    }

    protected WasherTester(boolean isUnitTest, String reflectionInboundPortURI, String washerUserInboundPortURI, String washerInternalControlInboundPortURI, String washerExternalControlInboundPortURI ) throws Exception {
        super(reflectionInboundPortURI, 1, 1);
        this.isUnitTest = isUnitTest;
        this.initialise(washerUserInboundPortURI,washerInternalControlInboundPortURI,washerExternalControlInboundPortURI);
    }

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
                assert	WasherTester.implementationInvariants(this) :
                new ImplementationInvariantException(
                        "WasherTester.implementationInvariants(this)");
        assert	WasherTester.invariants(this) :
                new InvariantException("WasherTester.invariants(this)");
    }

    protected void testOff(){
        this.logMessage( "Feature: getting the state of the washer");
        this.logMessage("  Scenario: getting the state of the washer when off");
        this.logMessage("    Given the washer is initialised");
        this.logMessage("    And the washer has not been used yet");
        try {
            this.logMessage("    When I test the state of the washer");
            if (this.hop.getState()==Washer.WasherState.OFF) {
                this.logMessage("    Then the state of the washer is off");
            } else {
                this.logMessage("     but was: on");
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
        this.statistics.updateStatistics();
    }

    protected void testOn(){
        this.logMessage("Feature: getting the state of the washer");
        this.logMessage("  Scenario: getting the state of the washer when ON");
        this.logMessage("    Given the washer is initialised");
        this.logMessage("    And the washer has not been used yet");
        try {
            this.logMessage("    When I test the state of the washer");
            this.hop.turnOnWasher();
            this.hop.turnOffWasher();
            this.hop.turnOnWasher();
            if (this.hop.getState()==Washer.WasherState.ON) {
                this.logMessage("    Then the state of the washer is on");
            } else {
                this.logMessage("     but was: off");
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
        this.statistics.updateStatistics();
    }

    protected void testMode(){
        this.logMessage("Feature: choosing a washing mode");
        this.logMessage("  Scenario: a mode sets its temperature and spin speed");
        this.logMessage("    Given the washer is initialised");
        try {
            this.logMessage("    When I test the mode of the washer ECO_40_60");
            this.hop.turnOnWasher();
            if (this.hop.getMode()==Washer.WasherMode.ECO_40_60 && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_40 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_600) {
                this.logMessage("    Then the mode of the washer is ECO_40_60");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I test the mode of the washer COTON");
            if (this.hop.getMode()==Washer.WasherMode.COTON && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_60 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_1200) {
                this.logMessage("    Then the mode of the washer is COTON");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I test the mode of the washer SYNTHETIQUE");
            if (this.hop.getMode()==Washer.WasherMode.SYNTHETIQUE && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_40 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_800) {
                this.logMessage("    Then the mode of the washer is SYNTHETIQUE");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I test the mode of the washer EXPRESS");
            if (this.hop.getMode()==Washer.WasherMode.EXPRESS && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_30 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_800) {
                this.logMessage("    Then the mode of the washer is EXPRESS");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I test the mode of the washer MIX40");
            if (this.hop.getMode()==Washer.WasherMode.MIX40 && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_40 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_1200) {
                this.logMessage("    Then the mode of the washer is MIX40");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I test the mode of the washer LAINE");
            if (this.hop.getMode()==Washer.WasherMode.LAINE && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_30 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_600) {
                this.logMessage("    Then the mode of the washer is LAINE");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
            this.logMessage("    When I test the mode of the washer DELICAT");
            if (this.hop.getMode()==Washer.WasherMode.DELICAT && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_30 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_600) {
                this.logMessage("    Then the mode of the washer is DELICAT");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
        this.statistics.updateStatistics();
    }

    protected void testAdjustAfterMode(){
        this.logMessage("Feature: adjusting the temperature and spin speed of the washer");
        this.logMessage("  Scenario: adjusting the temperature and spin speed of the washer after choosing a mode");
        this.logMessage("    Given the washer is initialised");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            this.hop.setTemperature(Washer.WasherTemperature.TEMP_40);
            this.hop.setSpinSpeed(Washer.WasherSpinSpeed.SPEED_800);
            if (this.hop.getMode()==Washer.WasherMode.COTON && this.hop.getTemperature()==Washer.WasherTemperature.TEMP_40 && this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_800) {
                this.logMessage("    Then the mode of the washer is COTON");
            } else {
                this.logMessage("     but was: " + this.hop.getMode());
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
    }

    protected void testTemperature(){
        this.logMessage("Feature: adjusting the temperature of the washer");
        this.logMessage("  Scenario: adjusting the temperature of the washer");
        this.logMessage("    Given the washer is initialised");
        try {
            this.hop.turnOnWasher();
            this.hop.setTemperature(Washer.WasherTemperature.TEMP_40);
            if (this.hop.getTemperature()==Washer.WasherTemperature.TEMP_40) {
                this.logMessage("    Then the temperature of the washer is TEMP_40");
            } else {
                this.logMessage("     but was: " + this.hop.getTemperature());
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
    }

    protected void testSpinSpeed(){
        this.logMessage("Feature: adjusting the spin speed of the washer");
        this.logMessage("  Scenario: adjusting the spin speed of the washer");
        this.logMessage("    Given the washer is initialised");
        try {
            this.hop.turnOnWasher();
            this.hop.setSpinSpeed(Washer.WasherSpinSpeed.SPEED_800);
            if (this.hop.getSpinSpeed()==Washer.WasherSpinSpeed.SPEED_800) {
                this.logMessage("    Then the spin speed of the washer is SPEED_800");
            } else {
                this.logMessage("     but was: " + this.hop.getSpinSpeed());
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
    }

    protected void testStartProgram(){
        this.logMessage("Feature: starting a washing program");
        this.logMessage("  Scenario: starting a washing program");
        this.logMessage("    Given the washer is initialised");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            this.hop.startWasher();
            if (this.hop.getState()==Washer.WasherState.FILLING) {
                this.logMessage("    Then the state of the washer is FILLING");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hicop.startWashing();
            if (this.hop.getState()==Washer.WasherState.WASHING) {
                this.logMessage("    Then the state of the washer is FILLING");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hicop.startDraining();
            if (this.hop.getState()==Washer.WasherState.DRAINING) {
                this.logMessage("    Then the state of the washer is DRAINING");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hicop.startRinsing();
            if (this.hop.getState()==Washer.WasherState.RINSING) {
                this.logMessage("    Then the state of the washer is RINSING");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hicop.startSpinning();
            if (this.hop.getState()==Washer.WasherState.SPINNING) {
                this.logMessage("    Then the state of the washer is SPINNING");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hicop.finishProgram();
            if (this.hop.getState()==Washer.WasherState.ON) {
                this.logMessage("    Then the state of the washer is ON");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hop.turnOffWasher();
            if (this.hop.getState()==Washer.WasherState.OFF) {
                this.logMessage("    Then the state of the washer is OFF");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
    }

    protected void testDelayedStart(){
        this.logMessage("Feature: starting a washing program with a delayed start");
        this.logMessage("  Scenario: starting a washing program with a delayed start");
        this.logMessage("    Given the washer is initialised");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            this.hicop.setDelayedStart(true);
            if (this.hicop.isDelayedStart()) {
                this.logMessage("    Then the delayed start of the washer is true");
            } else {
                this.logMessage("     but was: false");
                this.statistics.incorrectResult();
            }
            this.hicop.setDelayedStart(false);
            if (!this.hicop.isDelayedStart()) {
                this.logMessage("    Then the delayed start of the washer is false");
            } else {
                this.logMessage("     but was: true");
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
    }
    protected void testSuspendResume(){
        this.logMessage("Feature: suspending and resuming a washing program");
        this.logMessage("  Scenario: suspending and resuming a washing program");
        this.logMessage("    Given the washer is initialised");
        try {
            this.hop.turnOnWasher();
            this.hop.setMode(Washer.WasherMode.COTON);
            this.hop.startWasher();
            this.hecop.suspend();
            if (this.hecop.isSuspended() && this.hop.getState()==Washer.WasherState.SUSPENDED) {
                this.logMessage("    Then the state of the washer is SUSPENDED");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
            this.hecop.resume();
            if (this.hop.getState()==Washer.WasherState.FILLING && !this.hecop.isSuspended()) {
                this.logMessage("    Then the state of the washer is FILLING");
            } else {
                this.logMessage("     but was: " + this.hop.getState());
                this.statistics.incorrectResult();
            }
        } catch (Throwable e) {
            this.statistics.incorrectResult();
            this.logMessage("     but the exception " + e + " has been raised");
        }
    }

    protected void runAllTests() {
        this.testOff();
        this.testOn();
        this.testMode();
        this.testAdjustAfterMode();
        this.testTemperature();
        this.testSpinSpeed();
        this.testStartProgram();
        this.testDelayedStart();
        this.testSuspendResume();
    }

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
            this.hecop.unpublishPort();
        } catch (Throwable e) {
            throw new ComponentShutdownException(e) ;
        }
        super.shutdown();
    }
}