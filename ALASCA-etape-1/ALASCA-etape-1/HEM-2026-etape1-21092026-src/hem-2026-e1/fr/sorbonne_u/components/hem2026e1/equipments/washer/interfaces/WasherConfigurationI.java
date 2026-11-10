package fr.sorbonne_u.components.hem2026e1.equipments.washer.interfaces;

import fr.sorbonne_u.alasca.physical_data.MeasurementUnit;
import fr.sorbonne_u.alasca.physical_data.Measure;
import fr.sorbonne_u.exceptions.AssertionChecking;

// -----------------------------------------------------------------------------
/**
 * The interface <code>WasherConfigurationI</code> defines configuration
 * information for the washer component.
 *
 * <p><strong>Description</strong></p>
 *
 * <p><strong>Invariants</strong></p>
 *
 * <pre>
 * invariant	{@code POWER_UNIT != null}
 * invariant	{@code TENSION_UNIT != null}
 * invariant	{@code TEMPERATURE_UNIT != null}
 * invariant	{@code MAX_POWER != null && MAX_POWER.getMeasurementUnit().equals(POWER_UNIT) && MAX_POWER.getData() > 0.0}
 * </pre>
 *
 * <p>Created on : 2026-10-14</p>
 *
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public interface WasherConfigurationI {
    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** measurement unit for power used by the washer. */
    public static final MeasurementUnit POWER_UNIT = MeasurementUnit.WATTS;
    /** measurement unit for tension used by the washer. */
    public static final MeasurementUnit TENSION_UNIT = MeasurementUnit.VOLTS;
    /** measurement unit for temperature used by the washer. */
    public static final MeasurementUnit TEMPERATURE_UNIT = MeasurementUnit.CELSIUS;

    /** maximal power level of the washer. */
    public static final Measure<Double> MAX_POWER = new Measure<Double>(2200.0, POWER_UNIT);

    /** states in which the washer can operate. */
    public enum WasherState { OFF, ON, FILLING, WASHING, DRAINING, RINSING, SPINNING, FINISHED, SUSPENDED }

    /** predefined washer programs. */
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
        /** return the default temperature for this program. */
        public WasherTemperature getTemperature() {
            return this.temperature;
        }
        /** return the default spin speed for this program. */
        public WasherSpinSpeed getSpinSpeed() {
            return this.spinSpeed;
        }
    }
    /** temperatures supported by the washer. */
    public enum WasherTemperature {
        TEMP_30(30), TEMP_40(40), TEMP_60(60), TEMP_90(90);
        private final Measure<Double> value;

        private WasherTemperature(double temperature) {
            this.value = new Measure<Double>(temperature, WasherConfigurationI.TEMPERATURE_UNIT);
        }
        /** return the temperature as a physical measure. */
        public Measure<Double> getValue() {
            return this.value;
        }
    }
    /** spin speeds supported by the washer. */
    public enum WasherSpinSpeed { 
        SPEED_600(600), SPEED_800(800), SPEED_1200(1200);
        private final int value;
        private WasherSpinSpeed(int spinSpeed) {
            this.value = spinSpeed;
        }
        /** return the numeric spin speed. */
        public int getValue() {
            return this.value;
        }
    }
    
    // -------------------------------------------------------------------------
    // Invariants
    // -------------------------------------------------------------------------

    /**
     * return true if the static invariants are observed, false otherwise.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}
     * post	{@code true}
     * </pre>
     *
     * @return	true if the static invariants are observed, false otherwise.
     */
    public static boolean staticInvariants() {
        boolean ret = true;
        ret &= AssertionChecking.checkStaticInvariant(
                POWER_UNIT != null,
                WasherConfigurationI.class,
                "POWER_UNIT != null");
        ret &= AssertionChecking.checkStaticInvariant(
                TENSION_UNIT != null,
                WasherConfigurationI.class,
                "TENSION_UNIT != null");
        ret &= AssertionChecking.checkStaticInvariant(
                TEMPERATURE_UNIT != null && MAX_POWER.getData() > 0.0 && MAX_POWER.getMeasurementUnit().equals(POWER_UNIT),
                WasherConfigurationI.class,
                "TEMPERATURE_UNIT != null");
        for ( WasherTemperature wt : WasherTemperature.values()) {
            ret &= AssertionChecking.checkStaticInvariant(
                    wt.getValue().getMeasurementUnit().equals(TEMPERATURE_UNIT) && wt.getValue().getData() > 0.0,
                    WasherConfigurationI.class,
                    "WasherTemperature " + wt.name() + " is a positive temperature in TEMPERATURE_UNIT");
        }
        for (WasherSpinSpeed ws : WasherSpinSpeed.values()) {
            ret &= AssertionChecking.checkStaticInvariant(
                    ws.getValue() > 0,
                    WasherConfigurationI.class,
                    "WasherSpinSpeed " + ws.name() + " is a positive spin speed");
        }
        for (WasherMode wm : WasherMode.values()) {
            ret &= AssertionChecking.checkStaticInvariant(
                    wm.getTemperature() != null && wm.getSpinSpeed() != null,
                    WasherConfigurationI.class,
                    "WasherMode " + wm.name() + " has a null temperature or spin speed");
        }
        return ret;
    }
}
