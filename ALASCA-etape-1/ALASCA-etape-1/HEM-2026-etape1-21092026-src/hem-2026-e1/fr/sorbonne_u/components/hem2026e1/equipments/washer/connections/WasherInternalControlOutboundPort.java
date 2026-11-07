package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
public class WasherInternalControlOutboundPort extends AbstractOutboundPort implements WasherInternalControlCI {
    private static final long serialVersionUID = 1L;
    public WasherInternalControlOutboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherInternalControlCI.class, owner);
    }
    public WasherInternalControlOutboundPort(ComponentI owner) throws Exception {
        super(WasherInternalControlCI.class, owner);
    }
    @Override
    public void turnOnWasher() throws Exception {
        ((WasherInternalControlCI)this.connector).turnOnWasher();
    }
    @Override
    public void turnOffWasher() throws Exception {
        ((WasherInternalControlCI)this.connector).turnOffWasher();
    }
    @Override
    public WasherState getState() throws Exception {
        return ((WasherInternalControlCI)this.connector).getState();
    }
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherInternalControlCI)this.connector).getMode();
    }
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherInternalControlCI)this.connector).getTemperature();
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherInternalControlCI)this.connector).getSpinSpeed();
    }
    @Override
    public void setMode(WasherMode mode) throws Exception {
        ((WasherInternalControlCI)this.connector).setMode(mode);
    }
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception {
        ((WasherInternalControlCI)this.connector).setTemperature(temperature);
    }
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        ((WasherInternalControlCI)this.connector).setSpinSpeed(spinSpeed);
    }
    @Override 
    public void startWasher() throws Exception {
        ((WasherInternalControlCI)this.connector).startWasher();
    }
    @Override
    public void startFilling() throws Exception {
        ((WasherInternalControlCI)this.connector).startFilling();
    }
    @Override
    public void startWashing() throws Exception {
        ((WasherInternalControlCI)this.connector).startWashing();
    }
    @Override
    public void startDraining() throws Exception {
        ((WasherInternalControlCI)this.connector).startDraining();
    }
    @Override
    public void startRinsing() throws Exception {
        ((WasherInternalControlCI)this.connector).startRinsing();
    }
    @Override
    public void startSpinning() throws Exception {
        ((WasherInternalControlCI)this.connector).startSpinning();
    }
    @Override
    public void finishProgram() throws Exception {
        ((WasherInternalControlCI)this.connector).finishProgram();
    }
    @Override
    public boolean isDelayedStart() throws Exception {
        return ((WasherInternalControlCI)this.connector).isDelayedStart();
    }
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception {
        ((WasherInternalControlCI)this.connector).setDelayedStart(delayedStart);
    }
}
