package fr.sorbonne_u.components.hem2026e1.equipments.cooktop;

public interface CookTopImplementationI extends CookTopConfigurationI {
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= CookTopConfigurationI.staticInvariants();
        return ret;
    }
    public CookTopState getState(int indexZone) throws Exception;
    public int getPower(int indexZone) throws Exception;
    public void turnOnCookTop() throws Exception;
    public void turnOffCookTop() throws Exception;
    public void turnOnZone(int indexZone) throws Exception;
    public void turnOffZone(int indexZone) throws Exception;
    public void increasePowerZone(int indexZone) throws Exception;
    public void decreasePowerZone(int indexZone) throws Exception;
}