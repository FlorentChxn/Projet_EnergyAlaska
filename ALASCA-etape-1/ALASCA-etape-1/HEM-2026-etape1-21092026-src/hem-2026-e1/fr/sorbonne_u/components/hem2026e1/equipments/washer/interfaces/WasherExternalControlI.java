package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.exceptions.AssertionChecking;
import fr.sorbonne_u.exceptions.PreconditionException;

// -----------------------------------------------------------------------------
/**
 * The interface <code>WasherExternalControlI</code> declares the external
 * control services of the washer component.
 *
 * <p><strong>Invariants</strong></p>
 *
 * <pre>
 * invariant	{@code true}
 * </pre>
 *
 * <p>Created on : 2026-10-14</p>
 *
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public interface WasherExternalControlI extends WasherStateI {
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
    public static boolean staticInvariants() {
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
     * pre	{@code instance != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param instance		instance to be tested.
     * @return				true if the invariants are observed, false otherwise.
     */
    public static boolean invariants(WasherExternalControlI w){
        assert w != null : new PreconditionException("W != null");
        boolean ret = true;
        ret &= WasherExternalControlI.staticInvariants();
        ret &= AssertionChecking.checkInvariant(true, WasherExternalControlI.class, w ,"");
        return ret;
    }
    /** resume the suspended washer. */
    public void resume() throws Exception;
    /** suspend the washer. */
    public void suspend() throws Exception;
}