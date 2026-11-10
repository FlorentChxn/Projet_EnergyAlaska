package fr.sorbonne_u.components.hem2026e1.equipments.hem;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026.bases.AdjustableCI;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI;
/**
 * The class <code>WasherConnector</code> connects the energy manager, which
 * requires the <code>AdjustableCI</code> interface, to the washer, which offers the <code>WasherExternalControlJava4CI</code> interface.
 * 
 * <p><strong>Description</strong></p>
 * The washer is controlled only in time, it has a single mode 
 * (its nominal one ) annd the eneergy ùanager can suspend and resume its program. </p>
 * 
 * <p>Created on : 2026-10-20</p>
 *
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherConnector extends AbstractConnector implements AdjustableCI{

    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------
    
    /**The maximum mode of the washer, which is 1 (nominal mode).*/
    public static final int MAX_MOD = 1;
    /**The nominal power of the washer, which is 2200.0 watts.*/    
    public static final double NOMINAL_POWER = 2200.0;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /** create a new washer connector.*/
    public WasherConnector() {
        super();
    }

    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#maxMode()
     */
    @Override
    public int maxMode() throws Exception {
        return MAX_MOD;
    }

    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#upMode()
     */
    @Override
    public boolean upMode() throws Exception {
        assert !this.suspended() : new PreconditionException("Washer is suspended");
        return false;
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#downMode()
     */
    @Override
    public boolean downMode() throws Exception {
        assert !this.suspended() : new PreconditionException("Washer is suspended");
        return false;
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#currentMode()
     */
    @Override
    public int currentMode() throws Exception {
        assert !this.suspended() : new PreconditionException("Washer is suspended");
        return 1;
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#getModeConsumption(int)
     */
    @Override
    public double getModeConsumption(int mode) throws Exception {
        assert mode >= 1 && mode <= MAX_MOD : new PreconditionException("Invalid mode: " + mode);
        return NOMINAL_POWER;
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#setMode(int)
     */
    @Override
    public boolean setMode(int mode) throws Exception {
        assert !this.suspended() : new PreconditionException("Washer is suspended");
        assert mode >= 1 && mode <= MAX_MOD : new PreconditionException("Invalid mode: " + mode);
        return true;
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#suspended()
     */
    @Override
    public boolean suspended() throws Exception {
        return ((WasherExternalControlJava4CI)this.offering).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#emergency()
     */
    @Override
    public boolean suspend() throws Exception {
        assert !this.suspended() : new PreconditionException("Washer is already suspended");
        try {
            ((WasherExternalControlJava4CI)this.offering).suspend();
        } catch (Exception e) {
            throw new Exception("Error while suspending the washer", e);
        }
        return ((WasherExternalControlJava4CI)this.offering).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#resume()
     */
    @Override
    public boolean resume() throws Exception {
        assert this.suspended() : new PreconditionException("Washer is not suspended");
        try {
            ((WasherExternalControlJava4CI)this.offering).resume();
        } catch (Exception e) {
            throw new Exception("Error while resuming the washer", e);
        }
        return !((WasherExternalControlJava4CI)this.offering).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026.bases.AdjustableCI#emergency()
     */
    @Override
    public double emergency() throws Exception {
        return 0.0;
    }
}
