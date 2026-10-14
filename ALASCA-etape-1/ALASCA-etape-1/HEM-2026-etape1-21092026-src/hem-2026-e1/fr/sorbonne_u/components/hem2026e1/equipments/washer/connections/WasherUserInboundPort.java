package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherImplementationI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;

public class WasherUserInboundPort extends AbstractInboundPort implements WasherUserCI{
	private static final long serialVersionUID = 1L;
	
    public WasherUserInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherUserCI.class, owner);
        assert owner instanceof WasherImplementationI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    
    public WasherUserInboundPort(ComponentI owner) throws Exception {
    	super(WasherUserCI.class, owner);
        assert owner instanceof WasherImplementationI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    @Override
    public WasherState getState() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherImplementationI)o).getState());
    }
    @Override
    public WasherMode getMode() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherImplementationI)o).getMode());
    }
    @Override
    public WasherTemperature getTemperature() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherImplementationI)o).getTemperature());
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
    	return this.getOwner().handleRequest(o->((WasherImplementationI)o).getSpinSpeed());
    }
    @Override
    public void turnOffWasher() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI)o).turnOffWasher();
            return null;
        });
    }
    @Override
    public void turnOnWasher() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI)o).turnOnWasher();
            return null;
        });
    }
    @Override
    public void setMode(WasherMode mode) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI)o).setMode(mode);
            return null;
        });
    }
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI)o).setTemperature(temperature);
            return null;
        });
    }
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI)o).setSpinSpeed(spinSpeed);
            return null;
        });
    }
    @Override
    public void isOnWasher() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).isOnWasher();
            return null;
        });
    }
    @Override
    public void resume() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).resume();
            return null;
        });
    }
    @Override
    public void suspend() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).suspend();
            return null;
        });
    }
}
