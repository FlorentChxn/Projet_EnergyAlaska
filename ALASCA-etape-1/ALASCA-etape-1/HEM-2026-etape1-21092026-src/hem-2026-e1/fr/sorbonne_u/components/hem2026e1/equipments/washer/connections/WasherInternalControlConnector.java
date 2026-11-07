package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;

public class WasherInternalControlConnector extends AbstractConnector implements WasherInternalControlCI {
    @Override 
    public WasherState getState() throws Exception {
        return ((WasherInternalControlCI)this.offering).getState();
    }
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherInternalControlCI)this.offering).getMode();
    }
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherInternalControlCI)this.offering).getTemperature();
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherInternalControlCI)this.offering).getSpinSpeed();
    }
    @Override
    public void setMode(WasherMode mode) throws Exception {
        ((WasherInternalControlCI)this.offering).setMode(mode);
    }
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception {
        ((WasherInternalControlCI)this.offering).setTemperature(temperature);
    }
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        ((WasherInternalControlCI)this.offering).setSpinSpeed(spinSpeed);
    }
    @Override
    public void turnOffWasher() throws Exception {
        ((WasherInternalControlCI)this.offering).turnOffWasher();
    }
    @Override
    public void turnOnWasher() throws Exception {
        ((WasherInternalControlCI)this.offering).turnOnWasher();
    }
    @Override
    public void startWasher() throws Exception {
        ((WasherInternalControlCI)this.offering).startWasher();
    }
    @Override
    public void startFilling() throws Exception {
        ((WasherInternalControlCI)this.offering).startFilling();
    }
    @Override
    public void startWashing() throws Exception {
        ((WasherInternalControlCI)this.offering).startWashing();
    }
    @Override
    public void startDraining() throws Exception {
        ((WasherInternalControlCI)this.offering).startDraining();
    }
    @Override
    public void startRinsing() throws Exception {
        ((WasherInternalControlCI)this.offering).startRinsing();
    }
    @Override
    public void startSpinning() throws Exception {
        ((WasherInternalControlCI)this.offering).startSpinning();
    }
    @Override
    public void finishProgram() throws Exception {
        ((WasherInternalControlCI)this.offering).finishProgram();
    }
    @Override 
    public boolean isDelayedStart() throws Exception {
        return ((WasherInternalControlCI)this.offering).isDelayedStart();
    }
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception {
        ((WasherInternalControlCI)this.offering).setDelayedStart(delayedStart);
    }
    
}
