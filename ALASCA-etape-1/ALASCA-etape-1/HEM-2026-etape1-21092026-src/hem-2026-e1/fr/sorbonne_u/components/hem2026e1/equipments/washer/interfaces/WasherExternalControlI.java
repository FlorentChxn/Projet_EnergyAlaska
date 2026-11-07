package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.PreconditionException;

public interface WasherExternalControlI extends WasherConfigurationI {
    public static boolean staticInvariants() {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }

    public static boolean invariants(WasherExternalControlI w){
        assert w != null : new PreconditionException("W != null");
        boolean ret = true;
        ret &= WasherExternalControlI.staticInvariants();
        ret &= AssertionChecking.checkInvariant(true, WasherExternalControlI.class, w ,"");
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