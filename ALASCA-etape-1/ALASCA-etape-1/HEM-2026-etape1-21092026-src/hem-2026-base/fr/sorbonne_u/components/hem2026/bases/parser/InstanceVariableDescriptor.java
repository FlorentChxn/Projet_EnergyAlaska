package fr.sorbonne_u.components.hem2026.bases.parser;
import org.w3c.dom.*;
// -----------------------------------------------------------------------------
/**
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * </p>
 * 
 * <p>Created on : 2026-10-29</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class InstanceVariableDescriptor {
    //-----------------------------------------------------------------------------
    // Instance variables
    //-----------------------------------------------------------------------------
    /**the modifiers of the instance variable.*/
    protected final String modifiers;
    /**the type of the instance variable.*/
    protected final String type;
    /**the name of the instance variable.*/
    protected final String name;
    /**the static initialization of the instance variable.*/
    protected final String staticInit;

    /**
     * create an instance variable descriptor.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code element != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param element the XML element describing the instance variable.
     */
    public InstanceVariableDescriptor(Element element) {
        this.modifiers = element.getAttribute("modifiers");
        this.type = element.getAttribute("type");
        this.name = element.getAttribute("name");
        this.staticInit = element.getAttribute("static-init");
    }

    /**
     * get the modifiers of the instance variable.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the modifiers of the instance variable.
     */
    public String getModifiers() {
        return this.modifiers;
    }

    /**
     * get the type of the instance variable.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the type of the instance variable.
     */
    public String getType() {
        return this.type;
    }

    /**
     * get the name of the instance variable.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the name of the instance variable.
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the static initialization of the instance variable.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the static initialization of the instance variable.
     */
    public String getStaticInit() {
        return this.staticInit;
    }
}