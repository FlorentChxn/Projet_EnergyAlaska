package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.PreconditionException;

public interface WasherStateI extends WasherConfigurationI{
    public static boolean staticInvariants(){
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    public static boolean invariants(WasherStateI w){
        assert w != null : new PreconditionException("W != null");
        boolean ret = true;
        ret &= WasherStateI.staticInvariants();
        ret &= AssertionChecking.checkInvariant(true, WasherStateI.class, w ,"");
        return ret;
    }
    public WasherState getState() throws Exception;
    public WasherMode getMode() throws Exception;
    public WasherTemperature getTemperature() throws Exception;
    public WasherSpinSpeed getSpinSpeed() throws Exception;
    public void setMode(WasherMode mode) throws Exception;
    public void setTemperature(WasherTemperature temperature) throws Exception;
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception;
}
