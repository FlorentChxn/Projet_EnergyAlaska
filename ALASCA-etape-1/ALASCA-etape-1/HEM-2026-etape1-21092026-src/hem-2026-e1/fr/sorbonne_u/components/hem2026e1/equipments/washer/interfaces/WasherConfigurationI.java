package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.alasca.physical_data.MeasurementUnit;
import fr.sorbonne_u.exceptions.AssertionChecking;

public interface WasherConfigurationI {
    public static final MeasurementUnit POWER_UNIT = MeasurementUnit.WATTS;
    public static final MeasurementUnit TENSION_UNIT = MeasurementUnit.VOLTS;

    public enum WasherState { OFF, ON, FILLING, WASHING, DRAINING, RINSING, SPINNING, FINISHED, SUSPENDED }

    public enum WasherMode { 
        COTON (WasherTemperature.TEMP_60, WasherSpinSpeed.SPEED_1200), 
        ECO_40_60 (WasherTemperature.TEMP_40, WasherSpinSpeed.SPEED_600), 
        SYNTHETIQUE (WasherTemperature.TEMP_40, WasherSpinSpeed.SPEED_800), 
        EXPRESS (WasherTemperature.TEMP_30, WasherSpinSpeed.SPEED_800), 
        MIX40 (WasherTemperature.TEMP_40, WasherSpinSpeed.SPEED_1200), 
        LAINE (WasherTemperature.TEMP_30, WasherSpinSpeed.SPEED_600), 
        DELICAT (WasherTemperature.TEMP_30, WasherSpinSpeed.SPEED_600);
        private final WasherTemperature temperature;
        private final WasherSpinSpeed spinSpeed;
        private WasherMode(WasherTemperature temperature, WasherSpinSpeed spinSpeed) {
            this.temperature = temperature;
            this.spinSpeed = spinSpeed;
        }
        public WasherTemperature getTemperature() {
            return this.temperature;
        }
        public WasherSpinSpeed getSpinSpeed() {
            return this.spinSpeed;
        }
    }
    public enum WasherTemperature { TEMP_30, TEMP_40, TEMP_60, TEMP_90 }
    public enum WasherSpinSpeed { SPEED_600, SPEED_800, SPEED_1200 }
    
    public static boolean staticInvariants() {// ajouter les autres checking pour les autres variables
        boolean ret = true;
        ret &= AssertionChecking.checkStaticInvariant(
                POWER_UNIT != null,
                WasherConfigurationI.class,
                "POWER_UNIT != null");
        ret &= AssertionChecking.checkStaticInvariant(
                TENSION_UNIT != null,
                WasherConfigurationI.class,
                "TENSION_UNIT != null");
        return ret;
    }
}
