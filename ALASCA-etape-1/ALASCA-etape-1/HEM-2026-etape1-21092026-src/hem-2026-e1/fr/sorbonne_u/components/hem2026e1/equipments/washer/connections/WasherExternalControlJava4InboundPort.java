package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI;

// -----------------------------------------------------------------------------
/**
 * The class <code>WasherExternalControlJava4InboundPort</code> implements an
 * inbound port for the {@code WasherExternalControlJava4CI} component interface.
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
 * <p>Created on : 2025-09-22</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class WasherExternalControlJava4InboundPort extends WasherExternalControlInboundPort implements WasherExternalControlJava4CI{
    
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
    public WasherExternalControlJava4InboundPort(ComponentI owner) throws Exception {
        super(WasherExternalControlJava4CI.class,owner);
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
    public WasherExternalControlJava4InboundPort(String uri,ComponentI owner) throws Exception {
        super(uri,WasherExternalControlJava4CI.class,owner);
    }
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI#getStateJava4()
     */
    @Override
    public WasherState getStateJava4() throws Exception{
        return this.getState();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI#getModeJava4()
     */
    @Override
    public WasherMode getModeJava4() throws Exception{
        return this.getMode();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI#getTemperatureJava4()
     */
    @Override
    public WasherTemperature getTemperatureJava4() throws Exception{
        return this.getTemperature();
    }
    /**
     * @see fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI#getSpinSpeedJava4()
     */
    @Override
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception{
        return this.getSpinSpeed();
    }
}
