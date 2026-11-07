package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

public interface WasherExternalControlJava4CI extends WasherExternalControlCI {
    public WasherState getStateJava4() throws Exception;
    public WasherMode getModeJava4() throws Exception;
    public WasherTemperature getTemperatureJava4() throws Exception;
    public WasherSpinSpeed getSpinSpeedJava4() throws Exception;
}