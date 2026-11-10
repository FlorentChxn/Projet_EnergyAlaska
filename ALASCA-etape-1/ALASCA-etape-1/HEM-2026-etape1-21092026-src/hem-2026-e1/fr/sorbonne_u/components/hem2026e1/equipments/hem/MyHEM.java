package fr.sorbonne_u.components.hem2026e1.equipments.hem;

import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.BCMRuntimeException;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.utils.aclocks.ClocksServerCI;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import fr.sorbonne_u.components.hem2026.bases.AdjustableCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.hem2026e1.equipments.meter.ElectricMeterCI;
import fr.sorbonne_u.components.hem2026e1.equipments.solar_panel.SolarPanelCI;
import fr.sorbonne_u.components.hem2026e1.equipments.weather_station.WeatherStationCI;
import fr.sorbonne_u.components.utils.tests.TestsStatistics;
import fr.sorbonne_u.components.hem2026e1.equipments.hem.connections.RegistrationInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.batteries.BatteriesCI;
import fr.sorbonne_u.components.hem2026e1.equipments.generator.GeneratorCI;
import fr.sorbonne_u.components.hem2026e1.equipments.heater.Heater;
import fr.sorbonne_u.components.hem2026.bases.parser.ControlAdapterDescriptor;
import fr.sorbonne_u.components.hem2026.bases.parser.ControlAdapterParserXML;
import fr.sorbonne_u.components.hem2026.bases.ControlAdapterGenerator;
import fr.sorbonne_u.components.hem2026.bases.RegistrationCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.Washer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// -----------------------------------------------------------------------------
/**
 * The class <code>MyHEM</code> implements the basis for a household energy
 * management component.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * As is, this component is only a very limited starting point for the actual
 * component. The given code is there only to ease the understanding of the
 * objectives, but most of it must be replaced to get the correct code.
 * Especially, no registration of the components representing the appliances
 * is given.
 * </p>
 * 
 * <p><strong>Implementation Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code X_RELATIVE_POSITION >= 0}
 * invariant	{@code Y_RELATIVE_POSITION >= 0}
 * </pre>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code X_RELATIVE_POSITION >= 0}
 * invariant	{@code Y_RELATIVE_POSITION >= 0}
 * </pre>
 * 
 * <p>Created on : 2026-11-07</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
@OfferedInterfaces(offered = {RegistrationCI.class})
@RequiredInterfaces(required = {ClocksServerCI.class,
                                AdjustableCI.class,
                                ElectricMeterCI.class,
                                BatteriesCI.class,
                                SolarPanelCI.class,
                                GeneratorCI.class,
                                WeatherStationCI.class})
public class MyHEM extends HEM {
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    public final String REGISTRATION_INBOUND_PORT_URI = "HEM-REGISTRATION-INBOUND-PORT-URI";

    protected final RegistrationInboundPort rip;

    // Execution/Simulation

    protected final Map<String, EquipementEnregistered>	EquipmentEnregistered ;

    protected static class EquipementEnregistered {
        protected final String uid;
        protected final String controlPortURI;
        protected final AdjustableOutboundPort outPort;
        protected final Class<?> connector;

        protected EquipementEnregistered(String uid, String controlPortURI, AdjustableOutboundPort outPort, Class<?> connector) {
            this.uid = uid;
            this.controlPortURI = controlPortURI;
            this.outPort = outPort;
            this.connector = connector;
        }	
    }
    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * create a household energy manager component.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     * @throws Exception	<i>to do</i>.
     *
     */
    protected MyHEM() throws Exception {
        // by default, perform the tests planned in the method execute.
        this(true);
    }

    /**
     * create a household energy manager component.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param performTest	if {@code true}, the HEM performs the planned tests, otherwise not.
     * @throws Exception	<i>to do</i>.
     */
    protected MyHEM(boolean performTest) throws Exception {
        // 1 standard thread to execute the method execute and 1 schedulable
        // thread that is used to perform the tests
        super(performTest);

        this.isPreFirstStep = false;
        this.EquipmentEnregistered = new HashMap<>();
        this.rip = new RegistrationInboundPort(REGISTRATION_INBOUND_PORT_URI,this);
        this.rip.publishPort();

        assert HEM.implementationInvariants(this) : new ImplementationInvariantException("HEM.implementationInvariants(this)");
        assert HEM.invariants(this) : new InvariantException("HEM.invariants(this)");
    }

    // -------------------------------------------------------------------------
    // Component life-cycle
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#execute()
     */
    @Override
    public synchronized void execute() throws Exception {
        super.execute();
        if (this.performTest) {
            this.scheduleRegistrationIntegrationTest();
            this.scheduleTestWasher();
        }
    }

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#finalise()
     */
    @Override
    public synchronized void finalise() throws Exception {
        for (EquipementEnregistered er : new ArrayList<EquipementEnregistered>(this.EquipmentEnregistered.values())) {
            if (er.outPort.connected()) {
                this.doPortDisconnection(er.outPort.getPortURI());
            }
        }
        super.finalise();
    }

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
     */
    @Override
    public synchronized void shutdown() throws ComponentShutdownException {
        try {
            for (EquipementEnregistered er : new ArrayList<EquipementEnregistered>(this.EquipmentEnregistered.values())) {
                er.outPort.unpublishPort();
            }
            this.EquipmentEnregistered.clear();
            rip.unpublishPort();
        } catch (Throwable e) {
            throw new ComponentShutdownException(e) ;
        }
        super.shutdown();
    }

    /**
     * Check if an equipment is registered.
     *
     * @param uid The unique identifier of the equipment.
     * @return true if the equipment is registered, false otherwise.
     * @throws Exception If an error occurs while checking the registration.
     */
    public synchronized boolean registered(String uid) throws Exception {
        if (this.EquipmentEnregistered.containsKey(uid)) {
            return true;
        }
        return false;
    }

    /**
     * Register an equipment.
     *
     * @param uid The unique identifier of the equipment.
     * @param controlPortURI The URI of the control port of the equipment.
     * @param xmlControlAdapter The XML representation of the control adapter descriptor.
     * @return true if the equipment was successfully registered, false if it was already registered.
     * @throws Exception If an error occurs while registering the equipment.
     */
    public synchronized boolean register(String uid, String controlPortURI, String xmlControlAdapter) throws Exception {
        if (registered(uid)) {
            return false;
        }
        ControlAdapterParserXML cap = new ControlAdapterParserXML();
        ControlAdapterDescriptor cad = cap.parse(xmlControlAdapter);
        if (!cad.getUid().equals(uid)) {
            throw new BCMRuntimeException("The uid in the control adapter descriptor does not match the given uid.");
        }
        ControlAdapterGenerator cag = new ControlAdapterGenerator(cad);
        Class<?> connectorClass = cag.generatorClass();
        if (connectorClass == null) {
            throw new BCMRuntimeException("The connector class could not be generated.");
        }
        AdjustableOutboundPort outPort = new AdjustableOutboundPort(this);
        outPort.publishPort();
        try{
            this.doPortConnection(outPort.getPortURI(), controlPortURI, connectorClass.getCanonicalName());
        } catch(Exception e) {
            outPort.unpublishPort();
            outPort.destroyPort();
            throw new BCMRuntimeException("The connection to the equipment could not be established.", e);
        }
        this.EquipmentEnregistered.put(uid, new EquipementEnregistered(uid, controlPortURI, outPort, connectorClass));
        if(VERBOSE) {
            this.logMessage("Connector generated for " + uid + " : " + connectorClass.getCanonicalName());
            this.logMessage("Registered equipment with uid " + uid + " and control port URI " + controlPortURI + ".");
        }
        return true;
    }

    /**
     * Unregister an equipment.
     *
     * @param uid The unique identifier of the equipment to unregister.
     * @throws Exception If an error occurs while unregistering the equipment.
     */
    public synchronized void unregister(String uid) throws Exception {
        if (!registered(uid)) {
            throw new BCMRuntimeException("The equipment with uid " + uid + " is not registered.");
        }
        EquipementEnregistered er = this.EquipmentEnregistered.get(uid);
        if (er != null) {
            if (er.outPort.connected()) {
                this.doPortDisconnection(er.outPort.getPortURI());
            }
            er.outPort.unpublishPort();
            er.outPort.destroyPort();
        }
        this.EquipmentEnregistered.remove(uid);
        if(VERBOSE) {
            this.logMessage("Unregistered equipment with uid " + uid + ".");
        }
    }

    // -------------------------------------------------------------------------
    // Internal methods
    // -------------------------------------------------------------------------
    
    protected void scheduleRegistrationIntegrationTest() throws Exception {
        Instant testStart = this.ac.getStartInstant().plusSeconds(1L);
        long delay = this.ac.nanoDelayUntilInstant(testStart);
        this.scheduleTaskOnComponent(new AbstractComponent.AbstractTask(){
            @Override
            public void run() {
                try {
                    testRegistrationIntegration();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BCMRuntimeException("Error during registration integration test", e);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    protected void testRegistrationIntegration() throws Exception {
        String uid = "1A10000";
        Path heaterPath = Paths.get("ALASCA-etape-1","HEM-2026-etape1-21092026-src", "hem-adapter","heaterci-descriptor.xml").toAbsolutePath();
        if (!Files.isRegularFile(heaterPath)) {
            throw new BCMRuntimeException( "The control adapter descriptor was not found: " + heaterPath);
        }

        try {
            boolean registered = this.register( uid, Heater.EXTERNAL_CONTROL_INBOUND_PORT_URI, heaterPath.toString());
            if (!registered || !this.registered(uid)) {
                throw new BCMRuntimeException("The heater registration test failed.");
            }
            this.logMessage("Heater registered successfully.");
            EquipementEnregistered equipment = this.EquipmentEnregistered.get(uid);
            if (equipment == null) {
                throw new BCMRuntimeException("The registered heater was not stored.");
            }
            System.out.println("Connector generated for " + uid + " : " + equipment.connector.getCanonicalName());
            this.unregister(uid);
            if (this.registered(uid)) {
                throw new BCMRuntimeException("The heater was not unregistered.");
            }
            logMessage("Registration integration test passed successfully.");
            System.out.println("Registration integration test passed successfully.");
        } finally {
            if (this.registered(uid)) {
                this.unregister(uid);
            }
        }
    }

    protected void scheduleTestWasher() throws Exception {
        Instant testStart = this.ac.getStartInstant().plusSeconds(2L);
        long delay = this.ac.nanoDelayUntilInstant(testStart);
        this.scheduleTaskOnComponent(new AbstractComponent.AbstractTask(){
            @Override
            public void run() {
                try {
                    testWasher();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BCMRuntimeException("Error during washer test", e);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    protected void testWasher() throws Exception {
        this.logMessage("Washer tests start.");
        Path washerPath = Paths.get("ALASCA-etape-1","HEM-2026-etape1-21092026-src", "hem-adapter","washerci-descriptor.xml").toAbsolutePath();
        if (!Files.isRegularFile(washerPath)) {
            throw new BCMRuntimeException( "The control adapter descriptor was not found: " + washerPath);
        }
        TestsStatistics statistics = new TestsStatistics();
        String washerUid = "1A10001";
        try {
            if (!this.registered(washerUid)) {
                this.register(washerUid, Washer.EXTERNAL_CONTROL_INBOUND_PORT_URI, washerPath.toString());
            }
            AdjustableOutboundPort washerop = this.EquipmentEnregistered.get(washerUid).outPort;

            this.logMessage("Feature: adjustable appliance mode management");
            this.logMessage("  Scenario: Getting the max mode index");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call maxMode()");
            int maxMode = washerop.maxMode();
            if (maxMode != 1) {
                this.logMessage("    But was: " + maxMode + " instead of 1");
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is 1");
            statistics.updateStatistics();

            this.logMessage("  Scenario: Getting the current mode index");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call currentMode()");
            int currentMode = washerop.currentMode();
            if (currentMode != 1) {
                this.logMessage("    But was: " + currentMode + " instead of 1");
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is 1");
            statistics.updateStatistics();

            this.logMessage("  Scenario: going up one mode index");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call upMode()");
            boolean upModeResult = washerop.upMode();
            if (upModeResult) {
                this.logMessage("    But was: " + upModeResult + " instead of false");
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + upModeResult);
            currentMode = washerop.currentMode();
            if (currentMode != 1) {
                this.logMessage("    But was: " + currentMode + " instead of 1");
                statistics.incorrectResult();
            }
            this.logMessage("    And the current mode is still 1");
            statistics.updateStatistics();

            this.logMessage("  Scenario: going down one mode index");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call downMode()");
            boolean downModeResult = washerop.downMode();
            if (downModeResult) {
                this.logMessage("    But was: " + downModeResult + " instead of false");
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + downModeResult);
            currentMode = washerop.currentMode();
            if (currentMode != 1) {
                this.logMessage("    But was: " + currentMode + " instead of 1");
                statistics.incorrectResult();
            }
            this.logMessage("    And the current mode is still 1");
            statistics.updateStatistics();

            this.logMessage("  Scenario: Getting the mode consumption");
                        this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call getModeConsumption(1)");
            double modeConsumption = washerop.getModeConsumption(1);
            if (modeConsumption != 2200.0) {
                this.logMessage("    But was: " + modeConsumption );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + modeConsumption);
            statistics.updateStatistics();

            this.logMessage("  Scenario: Setting the mode");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call setMode(1)");
            boolean setModeResult = washerop.setMode(1);
            if (!setModeResult) {
                this.logMessage("    But was: " + setModeResult );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + setModeResult);
            currentMode = washerop.currentMode();
            if (currentMode != 1) {
                this.logMessage("    But was: " + currentMode + " instead of 1");
                statistics.incorrectResult();
            }
            this.logMessage("    And the current mode is still 1");
            statistics.updateStatistics();

            this.logMessage("Feature: suspending and resuming");
            this.logMessage("  Scenario: checking if the washer is suspended");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call suspend()");
            boolean suspendResult = washerop.suspend();
            if (!suspendResult) {
                this.logMessage("    But was: " + suspendResult );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + suspendResult);
            statistics.updateStatistics();

            this.logMessage("  Scenario: checking if the washer is suspended");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call suspended()");
            boolean suspendedResult = washerop.suspended();
            if (!suspendedResult) {
                this.logMessage("    But was: " + suspendedResult );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + suspendedResult);
            statistics.updateStatistics();

            this.logMessage("  Scenario: resuming the washer");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call resume()");
            suspendedResult = washerop.suspended();
            if (!suspendedResult) {
                this.logMessage("    But was: " + suspendedResult );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + suspendedResult);
            boolean resumeResult = washerop.resume();
            if (!resumeResult) {
                this.logMessage("    But was: " + resumeResult );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + resumeResult);
            statistics.updateStatistics();

            this.logMessage("  Scenario: checking the mergency");
            this.logMessage("   Given a registered washer with uid " + washerUid);
            this.logMessage("   When I call emergency()");
            double emergencyResult = washerop.emergency();
            if (emergencyResult != 0.0) {
                this.logMessage("    But was: " + emergencyResult );
                statistics.incorrectResult();
            }
            this.logMessage("   Then the result is " + emergencyResult);
            statistics.updateStatistics();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.registered(washerUid)) {
            this.unregister(washerUid);
        }
        statistics.updateStatistics();
        statistics.statisticsReport(this);
        this.logMessage("Washer tests end.");
    }
}