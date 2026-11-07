package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherImplementationI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherInternalControlCI;

public class WasherInternalControlInboundPort extends AbstractInboundPort implements WasherInternalControlCI {
    private static final long serialVersionUID = 1L;

    public WasherInternalControlInboundPort(ComponentI owner) throws Exception {
        super(WasherInternalControlCI.class, owner);
        assert owner instanceof WasherImplementationI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    public WasherInternalControlInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherInternalControlCI.class, owner);
        assert owner instanceof WasherImplementationI : new PreconditionException("owner instanceof WasherImplementationI");
    }
    @Override
    public WasherState getState() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherImplementationI) o).getState());
    }
    @Override
    public WasherMode getMode() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherImplementationI) o).getMode());
    }
    @Override
    public WasherTemperature getTemperature() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherImplementationI) o).getTemperature());
    }
    @Override
    public WasherSpinSpeed getSpinSpeed() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherImplementationI) o).getSpinSpeed());
    }
    @Override
    public void turnOffWasher() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).turnOffWasher();
            return null;
        });
    }
    @Override
    public void turnOnWasher() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).turnOnWasher();
            return null;
        });
    }
    @Override
    public void startWasher() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).startWasher();
            return null;
        });
    }
    @Override
    public void setMode(WasherMode mode) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).setMode(mode);
            return null;
        });
    }
    @Override
    public void setTemperature(WasherTemperature temperature) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).setTemperature(temperature);
            return null;
        });
    }
    @Override
    public void setSpinSpeed(WasherSpinSpeed spinSpeed) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).setSpinSpeed(spinSpeed);
            return null;
        });
    }
    @Override
    public void startFilling() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).startFilling();
            return null;
        });
    }
    @Override
    public void startWashing() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).startWashing();
            return null;
        });
    }
    @Override
    public void startDraining() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).startDraining();
            return null;
        });
    }
    @Override
    public void startRinsing() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).startRinsing();
            return null;
        });
    }
    @Override
    public void startSpinning() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).startSpinning();
            return null;
        });
    }
    @Override
    public void finishProgram() throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).finishProgram();
            return null;
        });
    }
    @Override
    public boolean isDelayedStart() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherImplementationI) o).isDelayedStart());
    }
    @Override
    public void setDelayedStart(boolean delayedStart) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherImplementationI) o).setDelayedStart(delayedStart);
            return null;
        });
    }
}
