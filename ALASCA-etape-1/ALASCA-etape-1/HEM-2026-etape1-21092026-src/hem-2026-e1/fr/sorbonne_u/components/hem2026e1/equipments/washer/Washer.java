package fr.sorbonne_u.components.hem2026e1.equipments.washer;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherInternalControlInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI;

// -----------------------------------------------------------------------------
/**
 * The class <code>Washer</code> implements a washer component.
 * 
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Implementation Invariants</strong></p>
 * 
 * <p>Created on : 2026-10-14</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
@OfferedInterfaces(offered= {WasherUserCI.class,WasherExternalControlCI.class, WasherInternalControlCI.class})
public class Washer extends AbstractComponent implements WasherUserI, WasherInternalControlI, WasherExternalControlI{
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    // BCM4Java information
    /** URI of the reflection inbound port.									*/
    public static final String REFLECTION_INBOUND_PORT_URI = "WASHER-RIP-URI";
    /** URI of the user inbound port.										*/
    public static final String USER_INBOUND_PORT_URI = "WASHER-USER-INBOUND-PORT-URI";
    /** URI of the internal control inbound port.								*/
    public static final String INTERNAL_CONTROL_INBOUND_PORT_URI = "WASHER-INTERNAL-CONTROL-INBOUND-PORT-URI";
    /** URI of the external control inbound port.								*/
    public static final String  EXTERNAL_CONTROL_INBOUND_PORT_URI = "WASHER-EXTERNAL-CONTROL-INBOUND-PORT-URI";
    /** when true, methods trace their actions.								*/
    public static boolean			VERBOSE = false;
    /** when tracing, x coordinate of the window relative position.			*/
    public static int				X_RELATIVE_POSITION = 0;
    /** when tracing, y coordinate of the window relative position.			*/
    public static int				Y_RELATIVE_POSITION = 0;

    // -------------------------------------------------------------------------
    // Component state
    // -------------------------------------------------------------------------
    
    /** The current state of the washer.*/
    protected WasherState currentState;
    /** The stock state of the washer, if suspended.*/
    protected WasherState stockState;
    /** The current mode of the washer.*/
    protected WasherMode currentMode;
    /** The current temperature of the washer.*/
    protected WasherTemperature currentTemperature;
    /** The current spin speed of the washer.*/
    protected WasherSpinSpeed currentSpinSpeed;
    /** Indicates if the washer has a delayed start. */
    protected boolean delayedStart;
    /** inbound port offering the <code>WasherUserCI</code> interface.		*/
    protected WasherUserInboundPort wip;
    /** inbound port offering the <code>WasherInternalControlCI</code>
     *  interface.															*/
    protected WasherInternalControlInboundPort wicip;
    /** inbound port offering the <code>WasherExternalControlCI</code>
     *  interface.															*/
    protected WasherExternalControlInboundPort wecip;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * create a new washer.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     * 
     * @throws Exception <i>to do</i>.
     */
    protected Washer() throws Exception {
        this(USER_INBOUND_PORT_URI,INTERNAL_CONTROL_INBOUND_PORT_URI,EXTERNAL_CONTROL_INBOUND_PORT_URI);
    }

    /**
     * create a new washer.
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
     * @param washerUserInboundPortURI				URI of the inbound port to call the washer component for user interactions.
     * @param washerInternalControlInboundPortURI	URI of the inbound port to call the washer component for internal control.
     * @param washerExternalControlInboundPortURI	URI of the inbound port to call the washer component for external control.
     * @throws Exception							<i>to do</i>.
     */
    protected Washer(String userInboundPortUri, String internalControlInboudPortUri, String externalControlInboundPortUri) throws Exception {
        super(1,0);
        this.initialise(userInboundPortUri,internalControlInboudPortUri,externalControlInboundPortUri);
    }

    /**
     * create a new washer.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code reflectionInboundPortURI != null && !reflectionInboundPortURI.isEmpty()}
     * pre	{@code washerUserInboundPortURI != null && !washerUserInboundPortURI.isEmpty()}
     * pre	{@code washerInternalControlInboundPortURI != null && !washerInternalControlInboundPortURI.isEmpty()}
     * pre	{@code washerExternalControlInboundPortURI != null && !washerExternalControlInboundPortURI.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     * 
     * @param reflectionInboundPortURI				URI of the reflection inbound port of the component.
     * @param washerUserInboundPortURI				URI of the inbound port to call the washer component for user interactions.
     * @param washerInternalControlInboundPortURI	URI of the inbound port to call the washer component for internal control.
     * @param washerExternalControlInboundPortURI	URI of the inbound port to call the washer component for external control.
     * @throws Exception							<i>to do</i>.
     */
    protected Washer(String reflectionInboundPortURI, String userInboundPortUri, String internalControlInboudPortUri, String externalControlInboundPortUri) throws Exception {
        super(reflectionInboundPortURI, 1, 0);
        this.initialise(userInboundPortUri,internalControlInboudPortUri,externalControlInboundPortUri);
    }
    
    /**
     * initialise a new thermostated washer.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code uri != null && !uri.isEmpty()}
     * pre	{@code internalControlInboundPortURI != null && !internalControlInboundPortURI.isEmpty()}
     * pre	{@code externalControlInboundPortURI != null && !externalControlInboundPortURI.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uri				URI of the inbound port to call the washer component for user interactions.
     * @param internalControlInboundPortURI	URI of the inbound port to call the washer component for internal control.
     * @param externalControlInboundPortURI	URI of the inbound port to call the washer component for external control.
     * @throws Exception							<i>to do</i>.
     */
    protected void initialise(String uri, String internalControlInboudPortUri, String externalControlInboundPortUri) throws Exception {
        assert uri != null && !uri.isEmpty() : new PreconditionException("uri cannot be null or empty");
        assert internalControlInboudPortUri != null && !internalControlInboudPortUri.isEmpty() : new PreconditionException("internalControlInboundPortURI cannot be null or empty");
        assert externalControlInboundPortUri != null && !externalControlInboundPortUri.isEmpty() : new PreconditionException("externalControlInboundPortURI cannot be null or empty");
        this.currentState = WasherState.OFF;
        this.currentMode = WasherMode.ECO_40_60;
        this.currentTemperature = this.currentMode.getTemperature();
        this.currentSpinSpeed = this.currentMode.getSpinSpeed();
        this.delayedStart = false;
        this.wip = new WasherUserInboundPort(uri, this);
        this.wicip = new WasherInternalControlInboundPort(internalControlInboudPortUri, this);
        this.wecip = new WasherExternalControlInboundPort(externalControlInboundPortUri, this);
        this.wecip.publishPort();
        this.wip.publishPort();
        this.wicip.publishPort();
        if (Washer.VERBOSE) {
            this.tracer.get().setTitle("Washer component");
            this.tracer.get().setRelativePosition(X_RELATIVE_POSITION, Y_RELATIVE_POSITION);
            this.toggleTracing();
        }
    }
    // -------------------------------------------------------------------------
    // Component life-cycle
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
     */
    @Override
    public synchronized void shutdown() throws ComponentShutdownException {
        try {
            this.wip.unpublishPort();
            this.wicip.unpublishPort();
            this.wecip.unpublishPort();
        } catch (Exception e) {
            throw new ComponentShutdownException(e);
        }
        super.shutdown();
    }
    // -------------------------------------------------------------------------
    // Component services implementation
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return this.currentState;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception {
        return this.currentMode;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return this.currentTemperature;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return this.currentSpinSpeed;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setMode(fr.sorbonne_u.components.hem2026e1.equipments.washer.Washer.WasherMode)
     */
    @Override
    public void setMode(WasherMode mode) throws Exception {
        assert this.currentState == WasherState.ON : new PreconditionException(" CurrentState is ON");
        this.currentMode = mode;
        this.currentTemperature = mode.getTemperature();
        this.currentSpinSpeed = mode.getSpinSpeed();
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setTemperature(fr.sorbonne_u.components.hem2026e1.equipments.washer.Washer.WasherTemperature)
     */
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception {
        assert this.currentState == WasherState.ON : new PreconditionException(" CurrentState is ON");
        this.currentTemperature = temperature;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setSpinSpeed(fr.sorbonne_u.components.hem2026e1.equipments.washer.Washer.WasherSpinSpeed)
     */
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        assert this.currentState == WasherState.ON : new PreconditionException(" CurrentState is ON");
        this.currentSpinSpeed = spinSpeed;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#turnOffWasher()
     */
    @Override
    public void turnOffWasher() throws Exception{
        this.currentState = WasherState.OFF;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#turnOnWasher()
     */
    @Override
    public void turnOnWasher() throws Exception{
        this.currentState = WasherState.ON;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#startWasher()
     */
    @Override
    public void startWasher() throws Exception{
        assert this.currentState == WasherState.ON :
                new PreconditionException("Washer is not ON");
        this.startFilling();
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startFilling()
     */
    @Override
    public void startFilling() throws Exception{
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
        this.currentState = WasherState.FILLING;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startWashing()
     */
    @Override
    public void startWashing() throws Exception{
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
        this.currentState = WasherState.WASHING;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startDraining()
     */
    @Override
    public void startDraining() throws Exception{
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
        this.currentState = WasherState.DRAINING;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startRinsing()
     */
    @Override
    public void startRinsing() throws Exception{
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
        this.currentState = WasherState.RINSING;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startSpinning()
     */
    @Override
    public void startSpinning() throws Exception{
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
        this.currentState = WasherState.SPINNING;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#finishProgram()
     */
    @Override
    public void finishProgram() throws Exception{
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
        this.currentState = WasherState.FINISHED;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception {
        return this.delayedStart;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setDelayedStart(boolean)
     */
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception{
        this.delayedStart = delayedStart;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception{
        return this.currentState == WasherState.FILLING || this.currentState == WasherState.WASHING || this.currentState == WasherState.DRAINING || this.currentState == WasherState.RINSING || this.currentState == WasherState.SPINNING;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception{
        return this.currentState != WasherState.OFF;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception{
        return this.currentState == WasherState.SUSPENDED;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI#suspend()
     */
    @Override 
    public void suspend() throws Exception{
        if(Washer.VERBOSE) {
            this.traceMessage("Suspending the washer component.\n");
        }
        if(this.isSuspended() || !this.isProgramRunning()) return;
        this.stockState = this.currentState;
        this.currentState = WasherState.SUSPENDED;
        assert this.isSuspended() : new PreconditionException("Washer is suspended");
    }

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI#resume()
     */
    @Override 
    public void resume() throws Exception{
        if(Washer.VERBOSE) {
            this.traceMessage("Resuming the washer component.\n");
        }
        if(!this.isSuspended()) return;
        this.currentState = this.stockState;
        assert !this.isSuspended() : new PreconditionException("Washer is not suspended");
    }
}