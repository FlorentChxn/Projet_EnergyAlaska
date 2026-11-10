package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;
// -----------------------------------------------------------------------------
/**
 * The interface <code>WasherUserI</code> declares the signature of the washer
 * component services corresponding to the actions a user can perform on the
 * washer.
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * <p>Created on : 2026-10-18</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public interface WasherUserI extends WasherStateI {
    // -------------------------------------------------------------------------
    // Invariants
    // -------------------------------------------------------------------------

    /**
     * return true if the invariants are observed, false otherwise.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code instance != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param instance instance to be tested.
     * @return true if the invariants are observed, false otherwise.
     */
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    // -------------------------------------------------------------------------
    // Signatures
    // -------------------------------------------------------------------------

    /**
     * turn off the washer.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code true}
     * post {@code !isOnWasher()}
     * </pre>
     *
     * @throws Exception if the operation cannot be performed.
     */
    public void turnOffWasher() throws Exception;

    /**
     * turn on the washer.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code !isOnWasher()}
     * post {@code isOnWasher()}
     * </pre>
     *
     * @throws Exception if the operation cannot be performed.
     */
    public void turnOnWasher() throws Exception;

    /**
     * start the washer.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code isOnWasher()}
     * post {@code isProgramRunning()}
     * </pre>
     *
     * @throws Exception if the operation cannot be performed.
     */
    public void startWasher() throws Exception;

    /**
     * set the washer operating mode.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code mode != null}
     * post {@code getMode() == mode}
     * </pre>
     *
     * @param mode the new operating mode.
     * @throws Exception if the operation cannot be performed.
     */
    public void setMode(WasherMode mode) throws Exception;

    /**
     * set the washer temperature.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code temperature != null}
     * post {@code getTemperature() == temperature}
     * </pre>
     *
     * @param temperature the new washing temperature.
     * @throws Exception if the operation cannot be performed.
     */
    public void setTemperature(WasherTemperature temperature) throws Exception;

    /**
     * set the washer spin speed.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code spinSpeed != null}
     * post {@code getSpinSpeed() == spinSpeed}
     * </pre>
     *
     * @param spinSpeed the new spin speed.
     * @throws Exception if the operation cannot be performed.
     */
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception;

    /**
     * enable or disable the delayed start.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre  {@code true}
     * post {@code isDelayedStart() == delayedStart}
     * </pre>
     *
     * @param delayedStart true to enable delayed start, false otherwise.
     * @throws Exception if the operation cannot be performed.
     */
    public void setDelayedStart(boolean delayedStart) throws Exception;
}
