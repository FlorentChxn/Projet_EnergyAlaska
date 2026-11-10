package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherUserOutboundPort</code> implements an outbound port
 * for the {@code WasherUserCI} component interface.
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
public class WasherUserOutboundPort extends AbstractOutboundPort implements WasherUserCI{
    
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * create an outbound port.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code uri != null && !uri.isEmpty()}
     * pre	{@code owner != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uri unique identifier of the port.
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserOutboundPort(String uri, ComponentI owner)throws Exception {
        super(uri, WasherUserCI.class, owner);
    }
    
    /**
     * create an outbound port with an automatically generated URI.
     *
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserOutboundPort(ComponentI owner) throws Exception{
        super(WasherUserCI.class, owner);
    }

    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#getState()
     */
    @Override
    public WasherState getState() throws Exception{
        return ((WasherUserCI)this.getConnector()).getState();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception{
        return ((WasherUserCI)this.getConnector()).getMode();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception{
        return ((WasherUserCI)this.getConnector()).getTemperature();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return ((WasherUserCI)this.getConnector()).getSpinSpeed();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception{
        return ((WasherUserCI)this.getConnector()).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception{
        return ((WasherUserCI)this.getConnector()).isProgramRunning();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception{
        return ((WasherUserCI)this.getConnector()).isOnWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception{
        return ((WasherUserCI)this.getConnector()).isDelayedStart();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#turnOffWasher()
     */
    @Override
    public void turnOffWasher() throws Exception{
        ((WasherUserCI)this.getConnector()).turnOffWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#turnOnWasher()
     */
    @Override
    public void turnOnWasher() throws Exception{
        ((WasherUserCI)this.getConnector()).turnOnWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#startWasher()
     */
    @Override
    public void startWasher() throws Exception{
        ((WasherUserCI)this.getConnector()).startWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#setDelayedStart()
     */
    public void setDelayedStart(boolean delayedStart) throws Exception{
        ((WasherUserCI)this.getConnector()).setDelayedStart(delayedStart);
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#setMode()
     */
    @Override
    public void setMode(WasherMode mode) throws Exception{
        ((WasherUserCI)this.getConnector()).setMode(mode);
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#setTemperature()
     */
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception{
        ((WasherUserCI)this.getConnector()).setTemperature(temperature);
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI#setSpinSpeed()
     */
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception{
        ((WasherUserCI)this.getConnector()).setSpinSpeed(spinSpeed);
    }
}
