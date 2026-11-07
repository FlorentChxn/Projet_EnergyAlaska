package fr.sorbonne_u.components.hem2026e1.equipments.cooktop;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

// Le fichier est un type CI , donc elle indique les regles d'accord qui permet de définir qu'un utilisateur a le droit de demander a l'appareil sans jamais dévoiler comment il fonctionne
// OfferedCI : décrit le côté serveur
// Elle décrit les services que l'équipement peut offrir à un autre composant
/*
getState()
getMode()
turnOn()
turnOff()
setHigh()
setLow()
 */
// RequiredCI : décrit le côté client
// Elle décrit les services que le composant peut demander à un autre composant
public interface CookTopUserCI extends OfferedCI, RequiredCI, CookTopConfigurationI {
    // -------------------------------------------------------------------------
    // Invariants
    // -------------------------------------------------------------------------

    /**
     * return true if the static invariants are observed, false otherwise.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return	true if the static invariants are observed, false otherwise.
     */
    public static boolean staticInvariants() throws Exception {
        boolean ret = true;
        ret &= CookTopConfigurationI.staticInvariants();
        return ret;
    }
    public CookTopState getState(int indexZone) throws Exception;
    public int getPower(int indexZone) throws Exception;
    public void turnOnCookTop() throws Exception;
    public void turnOffCookTop() throws Exception;
    public boolean isOn() throws Exception;
    public void turnOnZone(int indexZone) throws Exception;
    public void turnOffZone(int indexZone) throws Exception;
    public void increasePowerZone(int indexZone) throws Exception;
    public void decreasePowerZone(int indexZone) throws Exception;
}