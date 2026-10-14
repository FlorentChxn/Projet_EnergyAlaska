package fr.sorbonne_u.components.hem2026e1.equipments.cooktop;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections.CookTopInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered={CookTopUserCI.class})
public class CookTop extends AbstractComponent implements CookTopImplementationI {

    public static final String REFLECTION_INBOUND_PORT_URI = "COOK-TOP-RIP-URI";
    public static final String INBOUND_PORT_URI = "COOK-TOP-INBOUND-PORT-URI";

    public static boolean VERBOSE = false;
    public static int X_RELATIVE_POSITION = 0;
    public static int Y_RELATIVE_POSITION = 0;

    public static final int NUMBER_ZONE = 4; //nombre de plaque chauffante
    public static final int MAX_HEATING_POWER = 9; // la puissance maximale de chauffe du cooktop est de 9

    protected boolean isOn;
    protected CookTopState[] currentState;
    protected int[] currentPower;

    protected CookTopInboundPort ctip;

    protected CookTop() throws Exception {
        this(INBOUND_PORT_URI);
    }

    protected CookTop(String uri) throws Exception {
        this(REFLECTION_INBOUND_PORT_URI,uri);
    }

    protected CookTop(String reflectionInboundPortURI, String uri) throws Exception {
        super(reflectionInboundPortURI, 0, 1);
        this.initialise(uri);
    }

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

    @Override 
    public synchronized void shutdown() throws ComponentShutdownException {
        try{
            this.ctip.unpublishPort();
        } catch (Exception e) {
            throw new ComponentShutdownException("Cooktop shutdown error: " + e.getMessage());
        }
        super.shutdown();
    }

    public CookTopState getState(int indexZone) throws Exception {
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        return this.currentState[indexZone];
    }

    public int getPower(int indexZone) throws Exception {
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        return this.currentPower[indexZone];
    }

    public void turnOnCookTop() throws Exception {
        assert !this.isOn : new PreconditionException("Cooktop is already on");
        this.isOn = true;
    }

    public void turnOffCookTop() throws Exception {
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
        assert indexZone >= 0 && indexZone < NUMBER_ZONE : new PreconditionException("indexZone must be between 0 and " + (NUMBER_ZONE-1));
        if(this.currentState[indexZone] == CookTopState.OFF) {
            this.currentState[indexZone] = CookTopState.ON;
            this.currentPower[indexZone] = 1;
        }
    }

    public void turnOffZone(int indexZone) throws Exception {
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
