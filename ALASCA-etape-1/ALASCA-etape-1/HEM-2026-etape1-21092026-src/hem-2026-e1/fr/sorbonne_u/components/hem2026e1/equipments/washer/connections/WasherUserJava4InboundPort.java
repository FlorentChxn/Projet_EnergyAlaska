package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI;
import fr.sorbonne_u.exceptions.PreconditionException;
// -----------------------------------------------------------------------------
/**
 * The class <code>WasherUserJava4InboundPort</code> implements an inbound
 * port for the {@code WasherUserJava4CI} component interface.
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
 * invariant	{@code getOwner() instanceof WasherUserJava4CI}
 * </pre>
 *
 * <p>Created on : 2026-10-17</p>
 *
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherUserJava4InboundPort extends WasherUserInboundPort implements WasherUserJava4CI {

    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;
    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * create a Java 4 inbound port.
     *
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserJava4InboundPort(ComponentI owner) throws Exception {
        super(WasherUserCI.class, owner);
        assert owner instanceof WasherUserJava4CI : new PreconditionException("owner instanceof WasherUserJava4CI");
    }

    /**
     * create a Java 4 inbound port with the given URI.
     *
     * @param uri unique identifier of the port.
     * @param owner component that owns this port.
     * @throws Exception if the port cannot be created.
     */
    public WasherUserJava4InboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherUserJava4CI.class, owner);
        assert owner instanceof WasherUserJava4CI : new PreconditionException("owner instanceof WasherUserJava4CI");
    }

    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#getStateJava4()
     */
    @Override
    public WasherState getStateJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getStateJava4());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#getModeJava4()
     */
    @Override
    public WasherMode getModeJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getModeJava4());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#getTemperatureJava4()
     */
    @Override
    public WasherTemperature getTemperatureJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getTemperatureJava4());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#getSpinSpeedJava4()
     */
    @Override
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getSpinSpeedJava4());
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#setModeJava4()
     */
    @Override
    public void setModeJava4(WasherMode mode) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserJava4CI) o).setModeJava4(mode);
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#setTemperatureJava4()
     */
    @Override
    public void setTemperatureJava4(WasherTemperature temperature) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserJava4CI) o).setTemperatureJava4(temperature);
            return null;
        });
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI#setSpinSpeedJava4()
     */
    @Override
    public void setSpinSpeedJava4(WasherSpinSpeed spinSpeed) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserJava4CI) o).setSpinSpeedJava4(spinSpeed);
            return null;
        });
    }
}
