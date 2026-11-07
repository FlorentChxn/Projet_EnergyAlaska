package fr.sorbonne_u.components.hem2026e1.equipments.washer;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherImplementationI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherInternalControlInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI;

@OfferedInterfaces(offered= {WasherUserCI.class,WasherExternalControlJava4CI.class, WasherInternalControlCI.class})
public class Washer extends AbstractComponent implements WasherUserI, WasherImplementationI, WasherExternalControlI{
    
    public static final String REFLECTION_INBOUND_PORT_URI = "WASHER-RIP-URI";
    public static final String USER_INBOUND_PORT_URI = "WASHER-USER-INBOUND-PORT-URI";
    public static final String INTERNAL_CONTROL_INBOUND_PORT_URI = "WASHER-INTERNAL-CONTROL-INBOUND-PORT-URI";
    public static final String  EXTERNAL_CONTROL_INBOUND_PORT_URI = "WASHER-EXTERNAL-CONTROL-INBOUND-PORT-URI";
    public static boolean VERBOSE = false;
    public static int X_RELATIVE_POSITION = 0;
    public static int Y_RELATIVE_POSITION = 0;
    
    protected WasherState currentState;
    protected WasherState stockState;
    protected WasherMode currentMode;
    protected WasherTemperature currentTemperature;
    protected WasherSpinSpeed currentSpinSpeed;
    protected boolean delayedStart;
    
    protected WasherUserInboundPort wip;
    protected WasherInternalControlInboundPort wicip;
    protected WasherExternalControlInboundPort wecip;
    protected boolean suspended = false;
    protected Washer() throws Exception {
        this(USER_INBOUND_PORT_URI,INTERNAL_CONTROL_INBOUND_PORT_URI,EXTERNAL_CONTROL_INBOUND_PORT_URI);
    }
    protected Washer(String userInboundPortUri, String internalControlInboudPortUri, String externalControlInboundPortUri) throws Exception {
        super(1,0);
        this.initialise(userInboundPortUri,internalControlInboudPortUri,externalControlInboundPortUri);
    }

    protected Washer(String reflectionInboundPortURI, String userInboundPortUri, String internalControlInboudPortUri, String externalControlInboundPortUri) throws Exception {
        super(reflectionInboundPortURI, 1, 0);
        this.initialise(userInboundPortUri,internalControlInboudPortUri,externalControlInboundPortUri);
    }
    
    protected void initialise(String uri, String internalControlInboudPortUri, String externalControlInboundPortUri) throws Exception {
        assert uri != null : new PreconditionException("uri cannot be null");
        assert !uri.isEmpty() : new PreconditionException("uri cannot be empty");
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
    @Override
    public WasherState getState() throws Exception {
        return this.currentState;
    }
    @Override
    public WasherMode getMode() throws Exception {
        return this.currentMode;
    }
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return this.currentTemperature;
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return this.currentSpinSpeed;
    }
    @Override
    public void setMode(WasherMode mode) throws Exception {
        assert this.currentState != WasherState.OFF : new PreconditionException(" CurrentState is not OFF");
        this.currentMode = mode;
        this.currentTemperature = mode.getTemperature();
        this.currentSpinSpeed = mode.getSpinSpeed();
    }
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception {
        assert this.currentState != WasherState.OFF : new PreconditionException(" CurrentState is not OFF");
        this.currentTemperature = temperature;
    }
    
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        assert this.currentState != WasherState.OFF : new PreconditionException(" CurrentState is not OFF");
        this.currentSpinSpeed = spinSpeed;
    }
    @Override
    public void turnOffWasher() throws Exception{
        this.currentState = WasherState.OFF;
    }
    @Override
    public void turnOnWasher() throws Exception{
        this.currentState = WasherState.ON;
    }
    @Override
    public void startWasher() throws Exception{
        this.startFilling();
    }
    @Override
    public void startFilling() throws Exception{
        this.currentState = WasherState.FILLING;
    }
    @Override
    public void startWashing() throws Exception{
        this.currentState = WasherState.WASHING;
    }
    @Override
    public void startDraining() throws Exception{
        this.currentState = WasherState.DRAINING;
    }
    @Override
    public void startRinsing() throws Exception{
        this.currentState = WasherState.RINSING;
    }
    @Override
    public void startSpinning() throws Exception{
        this.currentState = WasherState.SPINNING;
    }
    @Override
    public void finishProgram() throws Exception{
        this.currentState = WasherState.FINISHED;
    }
    @Override
    public boolean isDelayedStart() throws Exception {
        return this.delayedStart;
    }
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception{
        this.delayedStart = delayedStart;
    }
    @Override
    public boolean isProgramRunning() throws Exception{
        return this.currentState == WasherState.FILLING || this.currentState == WasherState.WASHING || this.currentState == WasherState.DRAINING || this.currentState == WasherState.RINSING || this.currentState == WasherState.SPINNING;
    }
    @Override
    public boolean isSuspended() throws Exception{
        return this.suspended;
    }
    @Override 
    public void suspend() throws Exception{
        if(Washer.VERBOSE) {
            this.traceMessage("Suspending the washer component.\n");
        }
        if(this.suspended) return;
        this.suspended = true;
        this.stockState = this.currentState;
        this.currentState = WasherState.SUSPENDED;
        assert this.suspended : new PreconditionException("Washer is suspended");
        
    }
    @Override 
    public void resume() throws Exception{
        if(Washer.VERBOSE) {
            this.traceMessage("Resuming the washer component.\n");
        }
        if(!this.suspended) return;
        this.suspended = false;
        this.currentState = this.stockState;
        assert !this.suspended : new PreconditionException("Washer is not suspended");
    }
}