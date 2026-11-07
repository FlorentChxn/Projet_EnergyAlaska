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
    protected final String uid;
    protected final String offered;
    protected final Double minimum;
    protected final double nominal;
    protected final Double maximum;
    protected final List<String> required;
    protected final List<InstanceVariableDescriptor> instanceVariables;
    protected final List<MethodeDescriptor> operations;

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

    public String getUid() {
        return this.uid;
    }

    public String getOffered() {
        return this.offered;
    }

    public Double getMinimum() {
        return this.minimum;
    }

    public double getNominal() {
        return this.nominal;
    }

    public Double getMaximum() {
        return this.maximum;
    }

    public List<String> getRequired() {
        return this.required;
    }

    public List<InstanceVariableDescriptor> getInstanceVariables() {
        return this.instanceVariables;
    }

    public List<MethodeDescriptor> getOperations() {
        return this.operations;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ControlAdapterDescriptor [uid=").append(uid).append(", offered=").append(offered)
                .append(", minimum=").append(minimum).append(", nominal=").append(nominal)
                .append(", maximum=").append(maximum).append(", required=").append(required)
                .append(", instanceVariables=").append(instanceVariables).append(", operations=")
                .append(operations).append("]");
        return sb.toString();
    }
}
