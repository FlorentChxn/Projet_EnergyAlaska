package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

// -----------------------------------------------------------------------------
/**
 * The interface <code>WasherInternalControlI</code> declares the internal
 * services used to drive the washer program.
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
public interface WasherInternalControlI extends WasherStateI {
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
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= WasherConfigurationI.staticInvariants();
        return ret;
    }
    /** start filling the washer. */
    public void startFilling() throws Exception;
    /** start washing. */
    public void startWashing() throws Exception;
    /** start draining the washer. */
    public void startDraining() throws Exception;
    /** start rinsing the washer. */
    public void startRinsing() throws Exception;
    /** start spinning the washer. */
    public void startSpinning() throws Exception;
    /** finish the current washer program. */
    public void finishProgram() throws Exception;
}
