package fr.sorbonne_u.components.hem2026.bases.parser;

import java.util.ArrayList;
import java.util.List;
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
public class ControlAdapterDescriptor {
    //-----------------------------------------------------------------------------
    // Instance variables
    //-----------------------------------------------------------------------------

    /**the unique identifier of the control adapter.*/
    protected final String uid;
    /**the offered interface of the control adapter.*/
    protected final String offered;
    /**the minimum value of the control adapter.*/
    protected final Double minimum;
    /**the nominal value of the control adapter.*/
    protected final double nominal;
    /**the maximum value of the control adapter.*/
    protected final Double maximum;
    /**the required interfaces of the control adapter.*/
    protected final List<String> required;
    /**the instance variables of the control adapter.*/
    protected final List<InstanceVariableDescriptor> instanceVariables;
    /**the operations of the control adapter.*/
    protected final List<MethodeDescriptor> operations;

    //-----------------------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------------------

    /**
     * create a control adapter descriptor.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code uid != null && offered != null && minimum != null && nominal >= 0 && maximum != null && required != null && instanceVariables != null && operations != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param uid the unique identifier of the control adapter.
     * @param offered the offered interface of the control adapter.
     * @param minimum the minimum value of the control adapter.
     * @param nominal the nominal value of the control adapter.
     * @param maximum the maximum value of the control adapter.
     * @param required the required interfaces of the control adapter.
     * @param instanceVariables the instance variables of the control adapter.
     * @param operations the operations of the control adapter.
     */
    public ControlAdapterDescriptor(String uid, String offered, Double minimum, double nominal, Double maximum, List<String> required, List<InstanceVariableDescriptor> instanceVariables, List<MethodeDescriptor> operations) {
        this.uid = uid;
        this.offered = offered;
        this.minimum = minimum;
        this.nominal = nominal;
        this.maximum = maximum;
        this.required = new ArrayList<String>(required);
        this.instanceVariables = new ArrayList<InstanceVariableDescriptor>(instanceVariables);
        this.operations = new ArrayList<MethodeDescriptor>(operations);
    }

    //-----------------------------------------------------------------------------
    // Getters
    //-----------------------------------------------------------------------------

    /**
     * get the unique identifier of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the unique identifier of the control adapter.
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * get the offered interface of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the offered interface of the control adapter.
     */
    public String getOffered() {
        return this.offered;
    }

    /**
     * get the minimum value of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the minimum value of the control adapter.
     */
    public Double getMinimum() {
        return this.minimum;
    }

    /**
     * get the nominal value of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the nominal value of the control adapter.
     */
    public double getNominal() {
        return this.nominal;
    }

    /**
     * get the maximum value of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the maximum value of the control adapter.
     */
    public Double getMaximum() {
        return this.maximum;
    }

    /**
     * get the required interfaces of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the required interfaces of the control adapter.
     */
    public List<String> getRequired() {
        return this.required;
    }

    /**
     * get the instance variables of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the instance variables of the control adapter.
     */
    public List<InstanceVariableDescriptor> getInstanceVariables() {
        return this.instanceVariables;
    }

    /**
     * get the operations of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the operations of the control adapter.
     */
    public List<MethodeDescriptor> getOperations() {
        return this.operations;
    }
}
