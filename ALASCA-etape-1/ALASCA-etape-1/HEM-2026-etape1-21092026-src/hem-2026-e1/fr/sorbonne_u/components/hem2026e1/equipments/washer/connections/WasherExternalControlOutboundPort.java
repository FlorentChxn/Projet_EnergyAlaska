package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherExternalControlOutboundPort</code> implements an
 * outbound port for the {@code WasherExternalControlCI} component interface.
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
public class WasherExternalControlOutboundPort extends AbstractOutboundPort implements WasherExternalControlCI{
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
     * pre	{@code owner != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param owner			component that owns this port.
     * @throws Exception 	<i>to do</i>.
     */
    public WasherExternalControlOutboundPort(ComponentI owner) throws Exception {
        super(WasherExternalControlCI.class, owner);
    }
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
     * @param uri			unique identifier of the port.
     * @param owner			component that owns this port.
     * @throws Exception 	<i>to do</i>.
     */
    public WasherExternalControlOutboundPort(String uri,ComponentI owner) throws Exception {
        super(uri,WasherExternalControlCI.class,owner);
    }
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return ((WasherExternalControlCI)this.getConnector()).getState();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).getMode();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).getTemperature();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).getSpinSpeed();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).isProgramRunning();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).isOnWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception{
        return ((WasherExternalControlCI)this.getConnector()).isDelayedStart();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#resume()
     */
    @Override
    public void resume() throws Exception{
        ((WasherExternalControlCI)this.getConnector()).resume();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#suspend()
     */
    @Override
    public void suspend() throws Exception{
        ((WasherExternalControlCI)this.getConnector()).suspend();
    }
}
