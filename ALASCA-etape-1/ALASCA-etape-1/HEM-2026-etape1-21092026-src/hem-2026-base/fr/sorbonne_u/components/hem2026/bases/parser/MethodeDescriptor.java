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
public class MethodeDescriptor {
    //-----------------------------------------------------------------------------
    // Instance variables
    //-----------------------------------------------------------------------------

    /**the name of the method.*/
    protected final String name;
    /**the modifiers of the method.*/
    protected final String modifiers;
    /**the return type of the method.*/
    protected final String returnType;
    /**the parameters of the method.*/
    protected final List<ParameterDescriptor> parameters;
    /**the exceptions of the method.*/
    protected final List<String> exceptions;
    /**the equipment reference of the method.*/
    protected final String equipmentReference;
    /**the body of the method.*/
    protected final String body;

    /**
     * create a method descriptor.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code name != null && modifiers != null && returnType != null && parameters != null && exceptions != null && equipmentReference != null && body != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param name the name of the method.
     * @param modifiers the modifiers of the method.
     * @param returnType the return type of the method.
     * @param parameters the parameters of the method.
     * @param exceptions the exceptions of the method.
     * @param equipmentReference the equipment reference of the method.
     * @param body the body of the method.
     */
    public MethodeDescriptor(String name, String modifiers, String returnType, List<ParameterDescriptor> parameters, List<String> exceptions, String equipmentReference, String body) {
        this.name = name;
        this.modifiers = modifiers;
        this.returnType = returnType;
        this.exceptions = new ArrayList<String>(exceptions);
        this.parameters = new ArrayList<ParameterDescriptor>(parameters);
        this.equipmentReference = equipmentReference;
        this.body = body;
    }

    //-----------------------------------------------------------------------------
    // Getters
    //-----------------------------------------------------------------------------
    /**
     * get the name of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the name of the method.
     */
    public String getName() {
        return this.name;
    }
    /**
     * get the modifiers of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the modifiers of the method.
     */
    public String getModifiers() {
        return this.modifiers;
    }
    /**
     * get the return type of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the return type of the method.
     */
    public String getReturnType() {
        return this.returnType;
    }
    /**
     * get the parameters of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the parameters of the method.
     */
    public List<ParameterDescriptor> getParameters() {
        return this.parameters;
    }
    /**
     * get the exceptions of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the exceptions of the method.
     */
    public List<String> getExceptions() {
        return this.exceptions;
    }
    /**
     * get the equipment reference of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the equipment reference of the method.
     */
    public String getEquipmentReference() {
        return this.equipmentReference;
    }
    /**
     * get the body of the method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the body of the method.
     */
    public String getBody() {
        return this.body;
    }

    //-----------------------------------------------------------------------------
    // Inner classes
    //-----------------------------------------------------------------------------
    /**a descriptor for a method parameter.*/
    public static class ParameterDescriptor {
        /** the type of the parameter */
        protected final String type;
        /** the name of the parameter */
        protected final String name;
        /**
         * create a parameter descriptor.
         *
         * <p><strong>Contract</strong></p>
         *
         * <pre>
         * pre	{@code type != null && name != null}
         * post	{@code true}	// no postcondition.
         * </pre>
         *
         * @param type the type of the parameter.
         * @param name the name of the parameter.
         */
        public ParameterDescriptor(String type, String name){
            this.type = type;
            this.name = name;
        }
        /**
         * get the type of the parameter.
         *
         * <p><strong>Contract</strong></p>
         *
         * <pre>
         * pre	{@code true}	// no precondition.
         * post	{@code true}	// no postcondition.
         * </pre>
         *
         * @return the type of the parameter.
         */
        public String getType() {
            return this.type;
        }
        /**
         * get the name of the parameter.
         *
         * <p><strong>Contract</strong></p>
         *
         * <pre>
         * pre	{@code true}	// no precondition.
         * post	{@code true}	// no postcondition.
         * </pre>
         *
         * @return the name of the parameter.
         */
        public String getName() {
            return this.name;
        }
    }
}
