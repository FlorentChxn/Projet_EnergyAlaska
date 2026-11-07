package fr.sorbonne_u.components.hem2026e1.equipments.washer.connections;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserCI;
import fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces.WasherUserJava4CI;
import fr.sorbonne_u.exceptions.PreconditionException;

public class WasherUserJava4InboundPort extends WasherUserInboundPort implements WasherUserJava4CI {
    private static final long serialVersionUID = 1L;

    public WasherUserJava4InboundPort(ComponentI owner) throws Exception {
        super(WasherUserCI.class, owner);
        assert owner instanceof WasherUserJava4CI : new PreconditionException("owner instanceof WasherUserJava4CI");
    }

    public WasherUserJava4InboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, WasherUserJava4CI.class, owner);
        assert owner instanceof WasherUserJava4CI : new PreconditionException("owner instanceof WasherUserJava4CI");
    }

    @Override
    public WasherState getStateJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getStateJava4());
    }
    @Override
    public WasherMode getModeJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getModeJava4());
    }
    @Override
    public WasherTemperature getTemperatureJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getTemperatureJava4());
    }
    @Override
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception {
        return this.getOwner().handleRequest(o -> ((WasherUserJava4CI) o).getSpinSpeedJava4());
    }
    @Override
    public void setModeJava4(WasherMode mode) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserJava4CI) o).setModeJava4(mode);
            return null;
        });
    }
    @Override
    public void setTemperatureJava4(WasherTemperature temperature) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserJava4CI) o).setTemperatureJava4(temperature);
            return null;
        });
    }
    @Override
    public void setSpinSpeedJava4(WasherSpinSpeed spinSpeed) throws Exception {
        this.getOwner().handleRequest(o -> {
            ((WasherUserJava4CI) o).setSpinSpeedJava4(spinSpeed);
            return null;
        });
    }
}
