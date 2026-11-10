package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherUserInboundPort</code> implements an inbound port for
 * the {@code WasherUserCI} component interface.
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
 * invariant	{@code getOwner() instanceof WasherUserI}
 * </pre>
 *
 * <p>Created on : 2026-10-17</p>
 *
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherUserInboundPort extends AbstractInboundPort implements WasherUserCI{
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;
    
    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * create an inbound port.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code uri != null && !uri.isEmpty()}
     * pre	{@code owner != null}
     * pre	{@code owner instanceof WasherUserI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uri unique identifier of the port.
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherUserCI.class, owner);
        assert owner instanceof WasherUserI : new PreconditionException("owner instanceof WasherUserI");
    }
    
    /**
     * create an inbound port with an automatically generated URI.
     *
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserInboundPort(ComponentI owner) throws Exception {
        super(WasherUserCI.class, owner);
        assert owner instanceof WasherUserI : new PreconditionException("owner instanceof WasherUserI");
    }

    /**
     * create an inbound port for a specific component interface.
     *
     * @param implementedInterface interface implemented by this port.
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserInboundPort( Class<? extends WasherUserCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
        assert owner instanceof WasherUserI : new PreconditionException("owner instanceof WasherUserI");
    }
    /**
     * create an inbound port with a URI and a specific interface.
     *
     * @param uri unique identifier of the port.
     * @param implementedInterface interface implemented by this port.
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserInboundPort(String uri, Class<? extends WasherUserCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
        assert owner instanceof WasherUserI : new PreconditionException("owner instanceof WasherUserI");
    }
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#getState()
     */
    @Override
    public WasherState getState() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).getState());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).getMode());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).getTemperature());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).getSpinSpeed());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#isDelayedStart()
     */
    @Override
    public boolean isSuspended() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).isSuspended());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).isProgramRunning());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).isOnWasher());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception{
        return this.getOwner().handleRequest(o->((WasherUserI)o).isDelayedStart());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setDelayedStart(boolean)
     */
    @Override
    public void turnOffWasher() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).turnOffWasher();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#turnOnWasher()
     */
    @Override
    public void turnOnWasher() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).turnOnWasher();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#startWasher()
     */
    @Override
    public void startWasher() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).startWasher();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setDelayedStart(boolean)
     */
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).setDelayedStart(delayedStart);
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setMode(WasherMode)
     */
    @Override
    public void setMode(WasherMode mode) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).setMode(mode);
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setTemperature(WasherTemperature)
     */
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).setTemperature(temperature);
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserI#setSpinSpeed(WasherSpinSpeed)
     */
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherUserI)o).setSpinSpeed(spinSpeed);
            return null;
        });
    }
}
