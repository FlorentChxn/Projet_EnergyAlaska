package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;

// -----------------------------------------------------------------------------
/**
 * The class <code>WasherInternalControlOutboundPort</code> implements an
 * outbound port for the {@code WasherInternalControlCI} component interface.
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
 * <p>Created on : 2026-09-17</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherInternalControlOutboundPort extends AbstractOutboundPort implements WasherInternalControlCI {
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
    public WasherInternalControlOutboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherInternalControlCI.class, owner);
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
    public WasherInternalControlOutboundPort(ComponentI owner) throws Exception {
        super(WasherInternalControlCI.class, owner);
    }
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return ((WasherInternalControlCI)this.connector).getState();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception {
        return ((WasherInternalControlCI)this.connector).getMode();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return ((WasherInternalControlCI)this.connector).getTemperature();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return ((WasherInternalControlCI)this.connector).getSpinSpeed();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception {
        return ((WasherInternalControlCI)this.connector).isSuspended();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception {
        return ((WasherInternalControlCI)this.connector).isProgramRunning();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception {
        return ((WasherInternalControlCI)this.connector).isOnWasher();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#startFilling()
     */
    @Override
    public void startFilling() throws Exception {
        ((WasherInternalControlCI)this.connector).startFilling();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#startWashing()
     */
    @Override
    public void startWashing() throws Exception {
        ((WasherInternalControlCI)this.connector).startWashing();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#startDraining()
     */
    @Override
    public void startDraining() throws Exception {
        ((WasherInternalControlCI)this.connector).startDraining();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#startRinsing()
     */
    @Override
    public void startRinsing() throws Exception {
        ((WasherInternalControlCI)this.connector).startRinsing();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#startSpinning()
     */
    @Override
    public void startSpinning() throws Exception {
        ((WasherInternalControlCI)this.connector).startSpinning();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#finishProgram()
     */
    @Override
    public void finishProgram() throws Exception {
        ((WasherInternalControlCI)this.connector).finishProgram();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception {
        return ((WasherInternalControlCI)this.connector).isDelayedStart();
    }
}
