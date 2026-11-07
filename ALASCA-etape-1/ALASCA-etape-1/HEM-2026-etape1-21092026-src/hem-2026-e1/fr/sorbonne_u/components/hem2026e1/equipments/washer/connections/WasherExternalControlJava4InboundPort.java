package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI;


public class WasherExternalControlJava4InboundPort extends WasherExternalControlInboundPort implements WasherExternalControlJava4CI{
    private static final long serialVersionUID = 1L;
    public WasherExternalControlJava4InboundPort(ComponentI owner) throws Exception {
    	super(WasherExternalControlJava4CI.class,owner);
    }
    public WasherExternalControlJava4InboundPort(String uri,ComponentI owner) throws Exception {
    	super(uri,WasherExternalControlJava4CI.class,owner);
    }
    @Override
	public WasherState getStateJava4() throws Exception{
		return this.getState();
	}
    @Override
    public WasherMode getModeJava4() throws Exception{
		return this.getMode();
	}
    @Override
    public WasherTemperature getTemperatureJava4() throws Exception{
		return this.getTemperature();
	}
    @Override
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception{
		return this.getSpinSpeed();
	}
}
