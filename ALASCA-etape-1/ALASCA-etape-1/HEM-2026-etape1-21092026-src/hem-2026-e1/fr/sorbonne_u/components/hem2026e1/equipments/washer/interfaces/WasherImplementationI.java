package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

public interface WasherImplementationI extends WasherStateI {
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
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
