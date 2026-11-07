package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface WasherExternalControlCI extends RequiredCI, OfferedCI, WasherConfigurationI {
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    public WasherState getState() throws Exception;
    public WasherMode getMode() throws Exception;
    public WasherTemperature getTemperature() throws Exception;
    public WasherSpinSpeed getSpinSpeed() throws Exception;
    public boolean isSuspended() throws Exception;
    public boolean isProgramRunning() throws Exception;
    public void resume() throws Exception;
    public void suspend() throws Exception;
    
}
