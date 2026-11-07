package fr.sorbonne_u.components.hem2026e1.equipments.cooktop;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections.CookTopInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
// -----------------------------------------------------------------------------
/**
 * La classe <code>CookTop</code> implémente le composant plaque de cuisson.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * Le composant plaque de cuisson est un composant qui représente une plaque de cuisson avec plusieurs zones de chauffe. 
 * Il offre des services pour allumer et éteindre la plaque, allumer et éteindre chaque zone de chauffe, 
 * augmenter et diminuer la puissance de chaque zone de chauffe ( de 1 à 9, de façon incrémentale ),
 * et obtenir l'état et la puissance actuelle de chaque zone de chauffe.
 * </p>
 * 
 * <p>Created on : 2026-10-14</p>
 * 
 * @author	<a href="florent.chen94@gmail.com">Florent CHEN</a>
 */
@OfferedInterfaces(offered={CookTopUserCI.class})
public class CookTop extends AbstractComponent implements CookTopImplementationI {

    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------
    
    // URI of the cooktop inbound port used in tests.
    public static final String REFLECTION_INBOUND_PORT_URI = "COOK-TOP-RIP-URI";
    // URI of the cooktop inbound port used in tests.
    public static final String INBOUND_PORT_URI = "COOK-TOP-INBOUND-PORT-URI";

    // when true, methods trace their actions.
    public static boolean VERBOSE = false;
    // when tracing, x coordinate of the window relative position.
    public static int X_RELATIVE_POSITION = 0;
    // when tracing, y coordinate of the window relative position.
    public static int Y_RELATIVE_POSITION = 0;

    public static final int NUMBER_ZONE = 4; //nombre de plaque chauffante
    public static final int MAX_HEATING_POWER = 9; // la puissance maximale de chauffe du cooktop est de 9

    protected boolean isOn;
    protected CookTopState[] currentState;
    protected int[] currentPower;

    protected CookTopInboundPort ctip;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
	 * create a cookTop component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no precondition.
	 * post	{@code getState() == CookTopState.OFF}
	 * post	{@code getPower() == 0}
	 * </pre>
	 * 
	 * @throws Exception	<i>to do</i>.
	 */
    protected CookTop() throws Exception {
        this(INBOUND_PORT_URI);
    }

	/**
	 * create a cooktop component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * post	{@code getState() == CookTopState.OFF}
	 * post	{@code getPower() == 0}
	 * </pre>
	 * 
	 * @param uri	URI of the cooktop inbound port.
	 * @throws Exception				<i>to do</i>.
	 */    
    protected CookTop(String uri) throws Exception {
        this(REFLECTION_INBOUND_PORT_URI,uri);
    }

    /**
	 * create a cooktop component with the given reflection innbound port
	 * URI.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code reflectionInboundPortURI != null && !reflectionInboundPortURI.isEmpty()}
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * post	{@code getState() == CookTopState.OFF}
	 * post	{@code getPower() == 0}
	 * </pre>
	 *
	 * @param reflectionInboundPortURI	URI of the reflection innbound port of the component.
	 * @param uri	URI of the cooktop inbound port.
	 * @throws Exception				<i>to do</i>.
	 */
    protected CookTop(String reflectionInboundPortURI, String uri) throws Exception {
        super(reflectionInboundPortURI, 0, 1);
        this.initialise(uri);
    }

    /**
	 * initialise the cooktop component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code uri != null && !uri.isEmpty()}
	 * post	{@code getState() == CookTopState.OFF}
	 * post	{@code getPower() == 0}
	 * </pre>
	 * 
	 * @param uri	URI of the cooktop inbound port.
	 * @throws Exception				<i>to do</i>.
	 */
    protected void initialise(String uri) throws Exception {
        assert uri != null : new PreconditionException("uri cannot be null");
        assert !uri.isEmpty() : new PreconditionException("uri cannot be empty");
        this.isOn = false;
        this.currentState = new CookTopState[NUMBER_ZONE];
        this.currentPower = new int[NUMBER_ZONE];
        for(int i=0; i<NUMBER_ZONE; i++) {
            this.currentState[i] = CookTopState.OFF;
            this.currentPower[i] = 0;
        }
        this.ctip = new CookTopInboundPort(uri, this);
        this.ctip.publishPort();
        if (CookTop.VERBOSE) {
            this.tracer.get().setTitle("Cooktop component");
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
        try{
            this.ctip.unpublishPort();
        } catch (Exception e) {
            throw new ComponentShutdownException("Cooktop shutdown error: " + e.getMessage());
        }
        super.shutdown();
    }
	// -------------------------------------------------------------------------
	// Component services implementation
	// -------------------------------------------------------------------------
    
    /**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.cookTop.CookTopImplementationI#getState()
	 */
    @Override
    public CookTopState getState(int indexZone) throws Exception {
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        return this.currentState[indexZone];
    }
    public boolean isOn() {
        return this.isOn;
    }
    
    /**
	 * @see fr.sorbonne_u.components.hem2026e1.equipments.cookTop.CookTopImplementationI#getPower()
	 */
    public int getPower(int indexZone) throws Exception {
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        return this.currentPower[indexZone];
    }

    public void turnOnCookTop() throws Exception {
        if (CookTop.VERBOSE) {
            this.traceMessage("Cooktop is turned on.\n");
        }
        assert !this.isOn : new PreconditionException("Cooktop is already on");
        this.isOn = true;
    }

    public void turnOffCookTop() throws Exception {
        if (CookTop.VERBOSE) {
            this.traceMessage("Cooktop is turned off.\n");
        }
        assert this.isOn : new PreconditionException("Cooktop is already off");
        for(int i=0; i<NUMBER_ZONE; i++) {
            if (this.currentState[i] == CookTopState.ON) {
                this.currentState[i] = CookTopState.OFF;
                this.currentPower[i] = 0;
            }
        }
        this.isOn = false;
    }

    public void turnOnZone(int indexZone) throws Exception {
        if (CookTop.VERBOSE) {
            this.traceMessage("Zone " + indexZone + " is turned on.\n");
        }
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        if(this.currentState[indexZone] == CookTopState.OFF) {
            this.currentState[indexZone] = CookTopState.ON;
            this.currentPower[indexZone] = 1;
        }
    }

    public void turnOffZone(int indexZone) throws Exception {
        if (CookTop.VERBOSE) {
            this.traceMessage("Zone " + indexZone + " is turned off.\n");
        }
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        if(this.currentState[indexZone] == CookTopState.ON) {
            this.currentState[indexZone] = CookTopState.OFF;
            this.currentPower[indexZone] = 0;
        }
    }
    
    public void increasePowerZone(int indexZone) throws Exception {
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        if(this.currentState[indexZone] == CookTopState.ON && this.currentPower[indexZone] < MAX_HEATING_POWER) {
            this.currentPower[indexZone]++;
        }
    }
    
    public void decreasePowerZone(int indexZone) throws Exception {
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        if(this.currentState[indexZone] == CookTopState.ON && this.currentPower[indexZone] > 1) {
            this.currentPower[indexZone]--;
        }
    }
}
