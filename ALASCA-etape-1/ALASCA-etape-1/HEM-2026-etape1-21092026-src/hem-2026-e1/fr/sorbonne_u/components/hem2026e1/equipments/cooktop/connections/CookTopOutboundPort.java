package fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopState;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopUserCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class CookTopOutboundPort extends AbstractOutboundPort implements CookTopUserCI {
    private static final long serialVersionUID = 1L;

    public CookTopOutboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, CookTopUserCI.class, owner);
    }

    public CookTopOutboundPort(ComponentI owner) throws Exception {
        super(CookTopUserCI.class, owner);
    }

    @Override
    public CookTopState getState(int indexZone) throws Exception {
        return ((CookTopUserCI)this.getConnector()).getState(indexZone);
    }

    @Override
    public int getPower(int indexZone) throws Exception {
        return ((CookTopUserCI)this.getConnector()).getPower(indexZone);
    }

    @Override
    public void turnOnCookTop() throws Exception {
        ((CookTopUserCI)this.getConnector()).turnOnCookTop();
    }

    @Override
    public void turnOffCookTop() throws Exception {
        ((CookTopUserCI)this.getConnector()).turnOffCookTop();
    }

    @Override
    public void turnOnZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.getConnector()).turnOnZone(indexZone);
    }

    @Override
    public void turnOffZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.getConnector()).turnOffZone(indexZone);
    }

    @Override
    public void increasePowerZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.getConnector()).increasePowerZone(indexZone);
    }

    @Override
    public void decreasePowerZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.getConnector()).decreasePowerZone(indexZone);
    }
}
