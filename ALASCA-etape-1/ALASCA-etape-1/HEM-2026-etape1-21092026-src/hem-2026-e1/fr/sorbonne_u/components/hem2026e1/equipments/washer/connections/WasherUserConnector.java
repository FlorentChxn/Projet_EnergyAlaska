package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherUserConnector</code> implements a connector for the
 * {@code WasherUserCI} component interface.
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
public class WasherUserConnector extends AbstractConnector implements WasherUserCI {
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return ((WasherUserCI)this.offering).getState();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherUserCI)this.offering).getMode();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherUserCI)this.offering).getTemperature();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherUserCI)this.offering).getSpinSpeed();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception {
        return ((WasherUserCI)this.offering).isSuspended();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception {
        return ((WasherUserCI)this.offering).isProgramRunning();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception {
        return ((WasherUserCI)this.offering).isOnWasher();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception {
        return ((WasherUserCI)this.offering).isDelayedStart();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setDelayedStart(boolean)
     */
    public void setDelayedStart(boolean delayedStart) throws Exception {
        ((WasherUserCI)this.offering).setDelayedStart(delayedStart);
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setMode(WasherMode)
     */
    public void setMode(WasherMode mode) throws Exception {
        ((WasherUserCI)this.offering).setMode(mode);
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setTemperature(WasherTemperature)
     */
    public void setTemperature(WasherTemperature temperature) throws Exception {
        ((WasherUserCI)this.offering).setTemperature(temperature);
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setSpinSpeed(WasherSpinSpeed)
     */
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        ((WasherUserCI)this.offering).setSpinSpeed(spinSpeed);
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#turnOffWasher()
     */
    public void turnOffWasher() throws Exception {
        ((WasherUserCI)this.offering).turnOffWasher();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#turnOnWasher()
     */
    public void turnOnWasher() throws Exception {
        ((WasherUserCI)this.offering).turnOnWasher();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#startWasher()
     */
    public void startWasher() throws Exception {
        ((WasherUserCI)this.offering).startWasher();
    }
}
