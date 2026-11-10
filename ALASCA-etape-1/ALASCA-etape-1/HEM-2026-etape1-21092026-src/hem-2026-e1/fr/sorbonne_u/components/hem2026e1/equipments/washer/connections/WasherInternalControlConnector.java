package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherInternalControlConnector</code> implements a
 * connector for the {@code WasherInternalControlCI} component interface.
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
public class WasherInternalControlConnector extends AbstractConnector implements WasherInternalControlCI {
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return ((WasherInternalControlCI)this.offering).getState();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherInternalControlCI)this.offering).getMode();
    }
 
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherInternalControlCI)this.offering).getTemperature();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherInternalControlCI)this.offering).getSpinSpeed();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception {
        return ((WasherInternalControlCI)this.offering).isSuspended();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception {
        return ((WasherInternalControlCI)this.offering).isProgramRunning();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception {
        return ((WasherInternalControlCI)this.offering).isOnWasher();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startFilling()
     */
    @Override
    public void startFilling() throws Exception {
        ((WasherInternalControlCI)this.offering).startFilling();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startWashing()
     */
    @Override
    public void startWashing() throws Exception {
        ((WasherInternalControlCI)this.offering).startWashing();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startDraining()
     */
    @Override
    public void startDraining() throws Exception {
        ((WasherInternalControlCI)this.offering).startDraining();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startRinsing()
     */
    @Override
    public void startRinsing() throws Exception {
        ((WasherInternalControlCI)this.offering).startRinsing();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startSpinning()
     */
    @Override
    public void startSpinning() throws Exception {
        ((WasherInternalControlCI)this.offering).startSpinning();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#finishProgram()
     */
    @Override
    public void finishProgram() throws Exception {
        ((WasherInternalControlCI)this.offering).finishProgram();
    }
    
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception {
        return ((WasherInternalControlCI)this.offering).isDelayedStart();
    }
}
