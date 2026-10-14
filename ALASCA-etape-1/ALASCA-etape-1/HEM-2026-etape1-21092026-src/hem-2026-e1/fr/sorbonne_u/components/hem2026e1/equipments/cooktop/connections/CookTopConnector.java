package fr.sorbonne_u.components.hem2026e1.equipments.cooktop.connections;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.cooktop.CookTopState;

public class CookTopConnector extends AbstractConnector implements CookTopUserCI {
    @Override
    public CookTopState getState(int indexZone) throws Exception {
        return ((CookTopUserCI)this.offering).getState(indexZone);
    }

    @Override
    public int getPower(int indexZone) throws Exception {
        return ((CookTopUserCI)this.offering).getPower(indexZone);
    }

    @Override
    public void turnOnCookTop() throws Exception {
        ((CookTopUserCI)this.offering).turnOnCookTop();
    }

    @Override
    public void turnOffCookTop() throws Exception {
        ((CookTopUserCI)this.offering).turnOffCookTop();
    }

    @Override
    public void turnOnZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.offering).turnOnZone(indexZone);
    }

    @Override
    public void turnOffZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.offering).turnOffZone(indexZone);
    }

    @Override
    public void increasePowerZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.offering).increasePowerZone(indexZone);
    }

    @Override
    public void decreasePowerZone(int indexZone) throws Exception {
        ((CookTopUserCI)this.offering).decreasePowerZone(indexZone);
    }
    
}
