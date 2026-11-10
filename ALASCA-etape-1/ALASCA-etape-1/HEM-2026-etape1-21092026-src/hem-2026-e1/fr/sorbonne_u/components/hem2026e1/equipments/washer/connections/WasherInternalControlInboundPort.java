package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;

// -----------------------------------------------------------------------------
/**
 * The class <code>WasherInternalControlInboundPort</code> implements an
 * inbound port for the {@code WasherInternalControlCI} component interface.
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
 * invariant	{@code getOwner() instanceof HeaterInternalControlI}
 * </pre>
 * 
 * <p>Created on : 2026-10-17</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherInternalControlInboundPort extends AbstractInboundPort implements WasherInternalControlCI {
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
     * pre	{@code owner instanceof WasherInternalControlI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param owner			component that owns this port.
     * @throws Exception	<i>to do</i>.
     */
    public WasherInternalControlInboundPort(ComponentI owner) throws Exception {
        super(WasherInternalControlCI.class, owner);
        assert owner instanceof WasherInternalControlI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    /**
     * create an inbound port.
     * 
     * <p><strong>Contract</strong></p>
     * 
     * <pre>
     * pre	{@code uri != null && !uri.isEmpty()}
     * pre	{@code owner != null}
     * pre	{@code owner instanceof WasherInternalControlI}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uri			unique identifier of the port.
     * @param owner			component that owns this port.
     * @throws Exception	<i>to do</i>.
     */
    public WasherInternalControlInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherInternalControlCI.class, owner);
        assert owner instanceof WasherInternalControlI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getState()
     */
    @Override
    public WasherState getState() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).getState());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getMode()
     */
    @Override
    public WasherMode getMode() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).getMode());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#getTemperature()
     */
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).getTemperature());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherSpinSpeedI#getSpinSpeed()
     */
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).getSpinSpeed());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#isSuspended()
     */
    @Override
    public boolean isSuspended() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).isSuspended());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#isProgramRunning()
     */
    @Override
    public boolean isProgramRunning() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).isProgramRunning());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#isOnWasher()
     */
    @Override
    public boolean isOnWasher() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).isOnWasher());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startFilling()
     */
    @Override
    public void startFilling() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherInternalControlI) o).startFilling();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startWashing()
     */
    @Override
    public void startWashing() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherInternalControlI) o).startWashing();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startDraining()
     */
    @Override
    public void startDraining() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherInternalControlI) o).startDraining();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startRinsing()
     */
    @Override
    public void startRinsing() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherInternalControlI) o).startRinsing();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#startSpinning()
     */
    @Override
    public void startSpinning() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherInternalControlI) o).startSpinning();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlI#finishProgram()
     */
    @Override
    public void finishProgram() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherInternalControlI) o).finishProgram();
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherStateI#isDelayedStart()
     */
    @Override
    public boolean isDelayedStart() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherInternalControlI) o).isDelayedStart());
    }
}
