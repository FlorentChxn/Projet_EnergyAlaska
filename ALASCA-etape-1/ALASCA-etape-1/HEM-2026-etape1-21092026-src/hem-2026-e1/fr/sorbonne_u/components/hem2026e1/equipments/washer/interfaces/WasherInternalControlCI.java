package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface WasherInternalControlCI extends OfferedCI , RequiredCI, WasherConfigurationI {

    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    
    public WasherState getState() throws Exception;
    public WasherMode getMode() throws Exception;
    public WasherTemperature getTemperature() throws Exception;
    public WasherSpinSpeed getSpinSpeed() throws Exception;
    public void setMode(WasherMode mode) throws Exception;
    public void setTemperature(WasherTemperature temperature) throws Exception;
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception;
    public void turnOffWasher() throws Exception;
    public void turnOnWasher() throws Exception;
    public void startWasher() throws Exception;
    public void startFilling() throws Exception;
    public void startWashing() throws Exception;
    public void startDraining() throws Exception;
    public void startRinsing() throws Exception;
    public void startSpinning() throws Exception;
    public void finishProgram() throws Exception;
    public boolean isDelayedStart() throws Exception;
    public void setDelayedStart(boolean delayedStart) throws Exception;
}
