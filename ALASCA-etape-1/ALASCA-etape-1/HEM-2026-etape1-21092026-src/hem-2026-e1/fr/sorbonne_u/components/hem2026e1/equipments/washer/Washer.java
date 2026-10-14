package fr.sorbonne_u.components.hem2026e1.equipments.washer;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PostconditionException;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherImplementationI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherUserInboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.connections.WasherExternalControlInboundPort;

@OfferedInterfaces(offered= {WasherUserCI.class,WasherExternalControlJava4CI.class, WasherInternalControlCI.class})
public class Washer extends AbstractComponent implements WasherImplementationI, WasherExternalControlI{
	
    public static final String REFLECTION_INBOUND_PORT_URI = "WASHER-RIP-URI";
    public static final String USER_INBOUND_PORT_URI = "WASHER-USER-INBOUND-PORT-URI";
    public static final String INTERNAL_CONTROL_INBOUND_PORT_URI = "WASHER-INTERNAL-CONTROL-INBOUND-PORT-URI";
    public static final String  EXTERNAL_CONTROL_INBOUND_PORT_URI = "WASHER-EXTERNAL-CONTROL-INBOUND-PORT-URI";
    public static boolean VERBOSE = false;
    public static int X_RELATIVE_POSITION = 0;
    public static int Y_RELATIVE_POSITION = 0;
    
    protected WasherState currentState;
    protected WasherMode currentMode;
    protected WasherTemperature currentTemperature;
    protected WasherSpinSpeed currentSpinSpeed;
    protected boolean delayedStart;
    
    protected WasherUserInboundPort wip;
    protected WasherExternalControlInboundPort wecip;
    
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
    	this.currentMode = WasherMode.values()[0];
    	this.currentTemperature = WasherTemperature.values()[0];
    	this.currentSpinSpeed = WasherSpinSpeed.values()[0];
    	this.delayedStart = false;
    	this.wip = new WasherUserInboundPort(uri, this);
    	this.wip.publishPort();
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
    public boolean isDelayedSart() throws Exception {
    	return this.delayedStart;
    }
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception{
    	this.delayedStart = delayedStart;
    }    
    public void isOnWasher() throws Exception{
    	return;
    }
    public void resume() throws Exception{
    	return;
    }
    public void suspend() throws Exception{
    	return;
    }
}