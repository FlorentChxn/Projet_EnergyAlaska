package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherExternalControlConnector</code> implements a
 * connector for the {@code WasherExternalControlCI} component interface.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Implementation Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p>Created on : 2026-10-17</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherExternalControlConnector extends AbstractConnector implements WasherExternalControlCI {

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return ((WasherExternalControlCI)this.offering).getState();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherExternalControlCI)this.offering).getMode();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherExternalControlCI)this.offering).getTemperature();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherExternalControlCI)this.offering).getSpinSpeed();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception {
        return ((WasherExternalControlCI)this.offering).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception {
        return ((WasherExternalControlCI)this.offering).isProgramRunning();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception {
        return ((WasherExternalControlCI)this.offering).isOnWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception {
        return ((WasherExternalControlCI)this.offering).isDelayedStart();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#resume()
     */
    @Override
    public void resume() throws Exception {
        ((WasherExternalControlCI)this.offering).resume();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#suspend()
     */
    @Override
    public void suspend() throws Exception {
        ((WasherExternalControlCI)this.offering).suspend();
    }
}
