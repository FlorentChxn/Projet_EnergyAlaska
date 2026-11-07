package fr.sorbonne_u.components.hem2026e1.equipments.hem;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026.bases.AdjustableCI;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherExternalControlJava4CI;

public class WasherConnector extends AbstractConnector implements AdjustableCI{
    public static final int MAX_MOD = 7;

    protected int currentMode;

    public WasherConnector() {
        super();
        this.currentMode = 1;
    }

    @Override
    public int maxMode() throws Exception {
        return MAX_MOD;
    }

    @Override
    public boolean upMode() throws Exception {
        return false;
    }
    @Override
    public boolean downMode() throws Exception {
        return false;
    }
    @Override
    public int currentMode() throws Exception {
        return this.currentMode;
    }
    @Override
    public double getModeConsumption(int mode) throws Exception {
        return this.currentMode;
    }
    @Override
    public boolean setMode(int mode) throws Exception {
        return false;
    }
    @Override
    public boolean suspended() throws Exception {
        return ((WasherExternalControlJava4CI)this.offering).isSuspended();
    }
    @Override
    public boolean suspend() throws Exception {
        assert !this.suspended() : new PreconditionException("Washer is already suspended");
        try {
            ((WasherExternalControlJava4CI)this.offering).suspend();
        } catch (Exception e) {
            throw new Exception("Error while suspending the washer", e);
        }
        return true;
    }
    @Override
    public boolean resume() throws Exception {
        assert this.suspended() : new PreconditionException("Washer is not suspended");
        try {
            ((WasherExternalControlJava4CI)this.offering).resume();
        } catch (Exception e) {
            throw new Exception("Error while resuming the washer", e);
        }
        return true;
    }
    @Override
    public double emergency() throws Exception {
        return 0;
    }
}
