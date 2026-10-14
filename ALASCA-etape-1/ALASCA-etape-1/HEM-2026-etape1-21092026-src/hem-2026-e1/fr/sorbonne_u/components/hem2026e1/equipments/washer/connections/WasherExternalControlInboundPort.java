package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;

public class WasherExternalControlInboundPort extends AbstractInboundPort implements WasherExternalControlCI{
	private static final long serialVersionUID = 1L;
	
	public WasherExternalControlInboundPort(ComponentI owner) throws Exception {
		this(WasherExternalControlCI.class, owner);
	}
	public WasherExternalControlInboundPort(String uri, ComponentI owner)throws Exception{
		this(uri,WasherExternalControlCI.class,owner);
	}
	public WasherExternalControlInboundPort(Class<? extends WasherExternalControlCI> implementedInterface, ComponentI owner) throws Exception {
		super(implementedInterface, owner);
	}
	public WasherExternalControlInboundPort(String uri,Class<? extends WasherExternalControlCI> implementedInterface, ComponentI owner) throws Exception {
		super(uri,implementedInterface,owner);
	}
    public WasherState getState() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getState());
    }
    public WasherMode getMode() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getMode());
    }
    public WasherTemperature getTemperature() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getTemperature());
    }
    public WasherSpinSpeed getSpinSpeed() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getSpinSpeed());
    }
    public void setMode(WasherMode mode) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).setMode(mode);
            return null;
        });
    }
    public void setTemperature(WasherTemperature temperature) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).setTemperature(temperature);
            return null;
        });
    }
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).setSpinSpeed(spinSpeed);
            return null;
        });
    }
    
    public void isOnWasher() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).isOnWasher();
            return null;
        });
    }
    public void resume() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).resume();
            return null;
        });
    }
    public void suspend() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).suspend();
            return null;
        });
    }
}
