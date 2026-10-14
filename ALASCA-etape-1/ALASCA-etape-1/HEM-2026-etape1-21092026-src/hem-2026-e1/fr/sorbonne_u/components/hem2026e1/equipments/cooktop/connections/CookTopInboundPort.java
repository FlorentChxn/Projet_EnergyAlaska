package fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopImplementationI;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopState;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopUserCI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;

public class CookTopInboundPort extends AbstractInboundPort implements CookTopUserCI {
    private static final long serialVersionUID = 1L;

    public CookTopInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, CookTopUserCI.class, owner);
        assert owner instanceof CookTopImplementationI : new PreconditionException("owner instanceof CookTopImplementationI");
    }

    public CookTopInboundPort(ComponentI owner) throws Exception {
        super(CookTopUserCI.class, owner);
        assert owner instanceof CookTopImplementationI : new PreconditionException("owner instanceof CookTopImplementationI");
    }
    
    @Override
    public CookTopState getState(int indexZone) throws Exception {
        return this.getOwner().handleRequest(o -> ((CookTopImplementationI)o).getState(indexZone));
    }

    @Override
    public int getPower(int indexZone) throws Exception {
        return this.getOwner().handleRequest(o -> ((CookTopImplementationI)o).getPower(indexZone));
    }

    @Override
    public void turnOnCookTop() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((CookTopImplementationI)o).turnOnCookTop();
            return null;
        });
    }

    @Override
    public void turnOffCookTop() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((CookTopImplementationI)o).turnOffCookTop();
            return null;
        });
    }

    @Override
    public void turnOnZone(int indexZone) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((CookTopImplementationI)o).turnOnZone(indexZone);
            return null;
        });
    }

    @Override
    public void turnOffZone(int indexZone) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((CookTopImplementationI)o).turnOffZone(indexZone);
            return null;
        });
    }

    @Override
    public void increasePowerZone(int indexZone) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((CookTopImplementationI)o).increasePowerZone(indexZone);
            return null;
        });
    }

    @Override
    public void decreasePowerZone(int indexZone) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((CookTopImplementationI)o).decreasePowerZone(indexZone);
            return null;
        });
    }
}
