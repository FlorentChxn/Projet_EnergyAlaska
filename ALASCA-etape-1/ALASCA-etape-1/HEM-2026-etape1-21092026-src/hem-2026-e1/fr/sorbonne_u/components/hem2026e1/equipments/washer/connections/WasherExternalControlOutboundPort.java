package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;

public class WasherExternalControlOutboundPort extends AbstractOutboundPort implements WasherExternalControlCI{
    private static final long serialVersionUID = 1L;
    public WasherExternalControlOutboundPort(ComponentI owner) throws Exception {
        super(WasherExternalControlCI.class, owner);
    }
    public WasherExternalControlOutboundPort(String uri,ComponentI owner) throws Exception {
        super(uri,WasherExternalControlCI.class,owner);
    }
    @Override
    public WasherState getState() throws Exception {
        return ((WasherExternalControlCI)this.getConnector()).getState();
    }
    @Override
    public WasherMode getMode() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).getMode();
    }
    @Override
    public WasherTemperature getTemperature() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).getTemperature();
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).getSpinSpeed();
    }
    @Override
    public boolean isSuspended() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).isSuspended();
    }
    @Override
    public boolean isProgramRunning() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).isProgramRunning();
    }
    @Override
    public void resume() throws Exception{
        ((WasherExternalControlCI)this.getConnector()).resume();
    }
    @Override
    public void suspend() throws Exception{
        ((WasherExternalControlCI)this.getConnector()).suspend();
    }
}
