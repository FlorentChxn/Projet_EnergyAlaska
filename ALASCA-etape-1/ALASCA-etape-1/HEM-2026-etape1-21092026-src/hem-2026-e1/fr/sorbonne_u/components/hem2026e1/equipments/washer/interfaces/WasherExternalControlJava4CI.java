package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

// -----------------------------------------------------------------------------
/**
 * The class <code>WasherExternalControlJava4CI</code> extends the component
 * interface {@code WasherExternalControlCI} with signatures that can be used
 * in Java 4.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariants</strong></p>
 * 
 * <pre>
 * invariant	{@code true}	// no more invariant
 * </pre>
 * 
 * <p>Created on : 2026-10-14</p>
 * 
 * @author	<a href="mailto:Florent.chen94@gmail.com">Florent CHEN</a>
 */
public interface WasherExternalControlJava4CI extends WasherExternalControlCI {
    /** @return the current state of the washer. */
    public WasherState getStateJava4() throws Exception;
    /** @return the current washer program mode. */
    public WasherMode getModeJava4() throws Exception;
    /** @return the selected washing temperature. */
    public WasherTemperature getTemperatureJava4() throws Exception;
    /** @return the selected spin speed. */
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception;
}