package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class WasherUserOutboundPort extends AbstractOutboundPort implements WasherUserCI{
	private static final long serialVersionUID = 1L;
	
	public WasherUserOutboundPort(String uri, ComponentI owner)throws Exception {
		super(uri, WasherUserCI.class, owner);
	}
	
	public WasherUserOutboundPort(ComponentI owner) throws Exception{
		super(WasherUserCI.class, owner);
	}
	
	@Override
    public WasherState getState() throws Exception{
        return ((WasherUserCI)this.getConnector()).getState();
    }
	@Override
    public WasherMode getMode() throws Exception{
        return ((WasherUserCI)this.getConnector()).getMode();
    }
	@Override
    public WasherTemperature getTemperature() throws Exception{
        return ((WasherUserCI)this.getConnector()).getTemperature();
    }
	@Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return ((WasherUserCI)this.getConnector()).getSpinSpeed();
    }
	@Override
    public void turnOffWasher() throws Exception{
        ((WasherUserCI)this.getConnector()).turnOffWasher();
    }
	@Override
    public void turnOnWasher() throws Exception{
        ((WasherUserCI)this.getConnector()).turnOnWasher();
    }
    @Override
    public void startWasher() throws Exception{
        ((WasherUserCI)this.getConnector()).startWasher();
    }
	@Override
    public void setMode(WasherMode mode) throws Exception{
        ((WasherUserCI)this.getConnector()).setMode(mode);
    }
	@Override
    public void setTemperature(WasherTemperature temperature) throws Exception{
        ((WasherUserCI)this.getConnector()).setTemperature(temperature);
    }
	@Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception{
        ((WasherUserCI)this.getConnector()).setSpinSpeed(spinSpeed);
    }
}
