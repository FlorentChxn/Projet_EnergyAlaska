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
    protected final String modifiers;
    protected final String type;
    protected final String name;
    protected final String staticInit;

    public InstanceVariableDescriptor(Element element) {
        this.modifiers = element.getAttribute("modifiers");
        this.type = element.getAttribute("type");
        this.name = element.getAttribute("name");
        this.staticInit = element.getAttribute("static-init");
    }

    public String getModifiers() {
        return this.modifiers;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getStaticInit() {
        return this.staticInit;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InstanceVariableDescriptor [modifiers=").append(modifiers).append(", type=").append(type).append(", name=").append(name).append(", staticInit=").append(staticInit).append("]");
        return sb.toString();
    }
}