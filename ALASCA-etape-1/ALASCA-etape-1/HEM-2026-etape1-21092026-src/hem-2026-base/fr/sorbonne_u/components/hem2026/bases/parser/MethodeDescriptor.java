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
    protected final String name;
    protected final String modifiers;
    protected final String returnType;
    protected final List<ParameterDescriptor> parameters;
    protected final List<String> exceptions;
    protected final String equipmentReference;
    protected final String body;

    public MethodeDescriptor(String name, String modifiers, String returnType, List<ParameterDescriptor> parameters, List<String> exceptions, String equipmentReference, String body) {
        this.name = name;
        this.modifiers = modifiers;
        this.returnType = returnType;
        this.exceptions = new ArrayList<String>(exceptions);
        this.parameters = new ArrayList<ParameterDescriptor>(parameters);
        this.equipmentReference = equipmentReference;
        this.body = body;
    }

    public String getName() {
        return this.name;
    }
    public String getModifiers() {
        return this.modifiers;
    }
    public String getReturnType() {
        return this.returnType;
    }
    public List<ParameterDescriptor> getParameters() {
        return this.parameters;
    }
    public List<String> getExceptions() {
        return this.exceptions;
    }
    public String getEquipmentReference() {
        return this.equipmentReference;
    }
    public String getBody() {
        return this.body;
    }

    public static class ParameterDescriptor {
        protected final String type;
        protected final String name;

        public ParameterDescriptor(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getType() {
            return this.type;
        }
        public String getName() {
            return this.name;
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MethodeDescriptor [name=").append(name).append(", parameters=").append(parameters)
                .append(", equipmentReference=").append(equipmentReference).append(", body=").append(body).append("]");
        return sb.toString();
    }
}
