package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherExternalControlInboundPort</code> implements the
 * inbound port for the {@code WasherExternalControlCI} component interface.
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
 * invariant	{@code getOwner() instanceof HeaterExternalControlI}
 * </pre>
 * 
 * <p>Created on : 2026-10-17</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherExternalControlInboundPort extends AbstractInboundPort implements WasherExternalControlCI{
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
     * pre	{@code owner != null}
     * pre	{@code owner instanceof WasherExternalControlI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param owner			component that owns this port.
     * @throws Exception	<i>to do</i>.
     */
    public WasherExternalControlInboundPort(ComponentI owner) throws Exception {
        this(WasherExternalControlCI.class, owner);
    }
    /**
     * create an inbound port.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code uri != null && !uri.isEmpty()}
     * pre	{@code owner != null}
     * pre	{@code owner instanceof WasherExternalControlI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uri			unique identifier of the port.
     * @param owner			component that owns this port.
     * @throws Exception	<i>to do</i>.
     */
    public WasherExternalControlInboundPort(String uri, ComponentI owner)throws Exception{
        this(uri,WasherExternalControlCI.class,owner);
    }
    /**
     * create an inbound port.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code owner != null}
     * pre	{@code implementedInterface != null}
     * pre	{@code owner instanceof WasherExternalControlI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param implementedInterface	interface implemented by this port.
     * @param owner					component that owns this port.
     * @throws Exception			<i>to do</i>.
     */
    public WasherExternalControlInboundPort(Class<? extends WasherExternalControlCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
        assert implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface) : new PreconditionException("implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface)");
        assert owner instanceof WasherExternalControlI : new PreconditionException("owner instanceof WasherExternalControlI");
    }
    /**
     * create an inbound port.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code uri != null && !uri.isEmpty()}
     * pre	{@code implementedInterface != null}
     * pre	{@code owner != null}
     * pre	{@code owner instanceof WasherExternalControlI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uri					unique identifier of the port.
     * @param implementedInterface	interface implemented by this port.
     * @param owner					component that owns this port.
     * @throws Exception			<i>to do</i>.
     */
    public WasherExternalControlInboundPort(String uri,Class<? extends WasherExternalControlCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri,implementedInterface,owner);
        assert implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface) : new PreconditionException("implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface)");
        assert owner instanceof WasherExternalControlI : new PreconditionException("owner instanceof WasherExternalControlI");
    }
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getState()
     */
    @Override
    public WasherState getState() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getState());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getMode());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getTemperature());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getSpinSpeed());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception{
        return this.getOwner().handleRequest(o -> {
            return ((WasherExternalControlI)o).isSuspended();
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception{
        return this.getOwner().handleRequest(o -> {
            return ((WasherExternalControlI)o).isProgramRunning();
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception{
        return this.getOwner().handleRequest(o -> {
            return ((WasherExternalControlI)o).isOnWasher();
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception{
        return this.getOwner().handleRequest(o -> {
            return ((WasherExternalControlI)o).isDelayedStart();
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#resume()
     */
    @Override
    public void resume() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).resume();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI#suspend()
     */
    @Override
    public void suspend() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).suspend();
            return null;
        });
    }
}
