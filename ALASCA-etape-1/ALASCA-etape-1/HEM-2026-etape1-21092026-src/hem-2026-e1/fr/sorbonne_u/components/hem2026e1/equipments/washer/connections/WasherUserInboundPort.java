package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherImplementationI;

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

    public WasherUserInboundPort( Class<? extends WasherUserCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
        assert owner instanceof WasherImplementationI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    public WasherUserInboundPort(String uri, Class<? extends WasherUserCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
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
    public void startWasher() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI)o).startWasher();
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
}
