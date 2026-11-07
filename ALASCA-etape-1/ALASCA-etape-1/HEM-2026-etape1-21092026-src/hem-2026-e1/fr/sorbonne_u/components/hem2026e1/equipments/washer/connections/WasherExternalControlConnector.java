package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
public class WasherExternalControlConnector extends AbstractConnector implements WasherExternalControlCI {
    @Override
    public WasherState getState() throws Exception {
        return ((WasherExternalControlCI)this.offering).getState();
    }
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherExternalControlCI)this.offering).getMode();
    }
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherExternalControlCI)this.offering).getTemperature();
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherExternalControlCI)this.offering).getSpinSpeed();
    }
    @Override
    public boolean isSuspended() throws Exception {
        return ((WasherExternalControlCI)this.offering).isSuspended();
    }
    @Override
    public boolean isProgramRunning() throws Exception {
        return ((WasherExternalControlCI)this.offering).isProgramRunning();
    }
    @Override
    public void resume() throws Exception {
        ((WasherExternalControlCI)this.offering).resume();
    }
    @Override
    public void suspend() throws Exception {
        ((WasherExternalControlCI)this.offering).suspend();
    }
}
