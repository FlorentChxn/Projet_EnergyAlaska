package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

// -----------------------------------------------------------------------------
/**
 * The interface <code>WasherUserJava4CI</code> defines the Java-friendly
 * component interface for washer user services.
 */
public interface WasherUserJava4CI extends WasherUserCI {
    /** @return the current state of the washer. */
    public WasherState getStateJava4() throws Exception;
    /** @return the current washer program mode. */
    public WasherMode getModeJava4() throws Exception;
    /** @return the selected washing temperature. */
    public WasherTemperature getTemperatureJava4() throws Exception;
    /** @return the selected spin speed. */
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception;
    /** @param mode the new washer program mode. */
    public void setModeJava4(WasherMode mode) throws Exception;
    /** @param temperature the new washing temperature. */
    public void setTemperatureJava4(WasherTemperature temperature) throws Exception;
    /** @param spinSpeed the new spin speed. */
    public void setSpinSpeedJava4(WasherSpinSpeed spinSpeed) throws Exception;
}
