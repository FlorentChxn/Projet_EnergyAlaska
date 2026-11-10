package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.exceptions.PreconditionException;

// -----------------------------------------------------------------------------
/**
 * The interface <code>WasherStateI</code> declares services giving access to
 * the current state of the washer component.
 *
 * <p><strong>Invariants</strong></p>
 *
 * <pre>
 * invariant	{@code true}
 * </pre>
 *
 * <p>Created on : 2026-10-18</p>
 *
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public interface WasherStateI extends WasherConfigurationI {
    // -------------------------------------------------------------------------
    // Invariants
    // -------------------------------------------------------------------------

    /**
     * return true if the static invariants are observed, false otherwise.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}
     * post	{@code true}
     * </pre>
     *
     * @return true if the static invariants are observed, false otherwise.
     */
    public static boolean staticInvariants(){
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    /**
     * return true if the invariants are observed, false otherwise.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code w != null}
     * post	{@code true}
     * </pre>
     *
     * @param w instance to be tested.
     * @return true if the invariants are observed, false otherwise.
     */
    public static boolean invariants(WasherStateI w){
        assert w != null : new PreconditionException("W != null");
        boolean ret = true;
        ret &= WasherStateI.staticInvariants();
        return ret;
    }
    /** @return the current state of the washer. */
    public WasherState getState() throws Exception;
    /** @return the current washer program mode. */
    public WasherMode getMode() throws Exception;
    /** @return the selected washing temperature. */
    public WasherTemperature getTemperature() throws Exception;
    /** @return the selected spin speed. */
    public WasherSpinSpeed getSpinSpeed() throws Exception;

    /** @return true if the washer is suspended. */
    public boolean isSuspended() throws Exception;
    /** @return true if a program is running. */
    public boolean isProgramRunning() throws Exception;
    /** @return true if the washer is switched on. */
    public boolean isOnWasher() throws Exception;
    /** @return true if delayed start is enabled. */
    public boolean isDelayedStart() throws Exception;
}
