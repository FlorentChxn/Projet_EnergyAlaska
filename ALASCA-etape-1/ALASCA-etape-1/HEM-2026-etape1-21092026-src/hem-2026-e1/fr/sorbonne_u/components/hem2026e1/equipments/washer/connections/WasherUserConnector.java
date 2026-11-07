package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
public class WasherUserConnector extends AbstractConnector implements WasherUserCI {
    @Override
    public WasherState getState() throws Exception {
        return ((WasherUserCI)this.offering).getState();
    }
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherUserCI)this.offering).getMode();
    }
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherUserCI)this.offering).getTemperature();
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherUserCI)this.offering).getSpinSpeed();
    }
    @Override
    public void setMode(WasherMode mode) throws Exception {
        ((WasherUserCI)this.offering).setMode(mode);
    }
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception {
        ((WasherUserCI)this.offering).setTemperature(temperature);
    }
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        ((WasherUserCI)this.offering).setSpinSpeed(spinSpeed);
    }
    @Override
    public void turnOffWasher() throws Exception {
        ((WasherUserCI)this.offering).turnOffWasher();
    }
    @Override
    public void turnOnWasher() throws Exception {
        ((WasherUserCI)this.offering).turnOnWasher();
    }
    @Override
    public void startWasher() throws Exception {
        ((WasherUserCI)this.offering).startWasher();
    }
}
