package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

public interface WasherUserI extends WasherStateI {
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    public void turnOffWasher() throws Exception;
    public void turnOnWasher() throws Exception;
    public void startWasher() throws Exception;
}
