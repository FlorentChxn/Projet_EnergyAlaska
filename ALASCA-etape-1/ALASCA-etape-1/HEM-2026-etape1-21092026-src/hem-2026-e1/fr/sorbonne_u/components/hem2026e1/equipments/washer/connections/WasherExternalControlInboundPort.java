package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlI;

public class WasherExternalControlInboundPort extends AbstractInboundPort implements WasherExternalControlCI{
    private static final long serialVersionUID = 1L;
    
    public WasherExternalControlInboundPort(ComponentI owner) throws Exception {
        this(WasherExternalControlCI.class, owner);
    }
    public WasherExternalControlInboundPort(String uri, ComponentI owner)throws Exception{
        this(uri,WasherExternalControlCI.class,owner);
    }
    public WasherExternalControlInboundPort(Class<? extends WasherExternalControlCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
        assert implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface) : new PreconditionException("implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface)");
        assert owner instanceof WasherExternalControlI : new PreconditionException("owner instanceof WasherExternalControlI");

    }
    public WasherExternalControlInboundPort(String uri,Class<? extends WasherExternalControlCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri,implementedInterface,owner);
        assert implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface) : new PreconditionException("implementedInterface != null && WasherExternalControlCI.class.isAssignableFrom(implementedInterface)");
        assert owner instanceof WasherExternalControlI : new PreconditionException("owner instanceof WasherExternalControlI");
    }
    @Override
    public WasherState getState() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getState());
    }
    @Override
    public WasherMode getMode() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getMode());
    }
    @Override
    public WasherTemperature getTemperature() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getTemperature());
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception{
        return this.getOwner().handleRequest(o->((WasherExternalControlI)o).getSpinSpeed());
    }
    @Override
    public boolean isSuspended() throws Exception{
        return this.getOwner().handleRequest(o -> {
            return ((WasherExternalControlI)o).isSuspended();
        });
    }
    @Override
    public boolean isProgramRunning() throws Exception{
        return this.getOwner().handleRequest(o -> {
            return ((WasherExternalControlI)o).isProgramRunning();
        });
    }
    @Override
    public void resume() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).resume();
            return null;
        });
    }
    @Override
    public void suspend() throws Exception{
        this.getOwner().handleRequest(o -> {
            ((WasherExternalControlI)o).suspend();
            return null;
        });
    }
}
