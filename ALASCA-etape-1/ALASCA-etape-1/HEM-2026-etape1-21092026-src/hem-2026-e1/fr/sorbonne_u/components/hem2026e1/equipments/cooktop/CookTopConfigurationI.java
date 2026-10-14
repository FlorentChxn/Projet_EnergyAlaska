package fr.sorbonne_u.components.hem2026e1.equipments.cooktop;

import fr.sorbonne_u.alasca.physical_data.MeasurementUnit;
import fr.sorbonne_u.exceptions.AssertionChecking;

public interface CookTopConfigurationI {
    // description de la configuration physique de l'appareil de cuisson 
    // Elle ne décrit pas le comportement mais seulement la configuration 
    // elle doit contenir les unités de mesure utilisées pour les grandeurs physiques manipulées par l'appareil de cuisson, constantes , etc.

    public static final MeasurementUnit	POWER_UNIT = MeasurementUnit.WATTS;
    public static final MeasurementUnit	TENSION_UNIT = MeasurementUnit.VOLTS;
    // Elle vérifie donc que les unités ont été bien définies ( la configuration existe)
    public static boolean staticInvariants() {
        boolean ret = true;
        ret &= AssertionChecking.checkStaticInvariant(
                POWER_UNIT != null,
                CookTopConfigurationI.class,
                "POWER_UNIT != null");
        ret &= AssertionChecking.checkStaticInvariant(
                TENSION_UNIT != null,
                CookTopConfigurationI.class,
                "TENSION_UNIT != null");
        return ret;
    }
}

/** Cours
 * BCM est un modèle de programmation par composants.
 * L'idée principale est de ne pas faire communiquer directement les objets entre eux
 * Les composants communiquent avec des interfaces et des ports ( composant -> connector -> composant)
 * Un composant BCM contient généralement :
 * un état interne , des interfaces offertes et requieses , des ports entrants et sortants , un cycle de vie 
 * 
 * Les interfaces CI : elle décrit les services visibles par les autres composants ( elle décrit cee qu'un utilisateur peut demander a l'équipement)
 * L'interret de l'interface de configuration est de pouvoir partager les mêmes constantes entre plusieurs fichier qui compose l'équipement
 * Cela permet d'évite de redéclarer les constantes
 * 
 */