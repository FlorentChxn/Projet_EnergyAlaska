package fr.sorbonne_u.components.hem2026.bases;
import javassist.*;
import fr.sorbonne_u.components.hem2026.bases.parser.ControlAdapterParserXML;
import fr.sorbonne_u.components.hem2026.bases.parser.ControlAdapterDescriptor;

public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.test1();
    }

    public void tutoadd() throws Exception {
        // 1. On récupère le ClassPool par défaut
        ClassPool pool = ClassPool.getDefault(); // renvoie un ClassPool préconfiguré pour chercher les fichiers .class

        // 2. On récupère la classe que l'on veut modifier
        // Remplacez par le vrai package de votre classe
        CtClass cc = pool.get("fr.sorbonne_u.components.hem2026.bases.ControlAdapterGenerator");

        // 3. On cible une méthode spécifique
        CtMethod methode = cc.getDeclaredMethod("retirerArgent");

        // 4. On injecte du vrai code Java au tout début de la méthode
        methode.insertBefore("{ System.out.println(\"Tentative de retrait détectée !\"); }");

        // 5. On sauvegarde le résultat. 
        // writeFile() va générer le nouveau fichier .class sur le disque.
        cc.writeFile("test"); 
        
        // (Alternative) Pour l'utiliser immédiatement en mémoire :
        // Class maClasseModifiee = cc.toClass(); 
        // C'est une règle de sécurité de Javassist : une fois que tu as appelé writeFile(), toClass(), ou toBytecode(), la CtClass est gelée. Javassist t'empêche de la modifier à nouveau pour éviter les conflits, car la machine virtuelle Java (JVM) n'aime pas qu'on change une classe qui a déjà été chargée en mémoire
    }

    public void tutoCreate() throws Exception {
        // 1. On récupère le ClassPool par défaut
        ClassPool pool = ClassPool.getDefault();
        // Imports (pour te simplifier l'écriture du code texte plus tard)
        pool.importPackage("java.util");

        // Package + Nom de la classe
        CtClass nouvelleClasse = pool.makeClass("mon.super.package.MaClasse");

        // Extends
        nouvelleClasse.setSuperclass(pool.get("fr.sorbonne_u.components.hem2026e1.equipments.heater.Heater"));

        // Implements
        nouvelleClasse.addInterface(pool.get("java.lang.Runnable"));

        // 3. On ajoute un champ à la classe
        CtField champ = new CtField(CtClass.intType, "monChamp", nouvelleClasse);
        champ.setModifiers(Modifier.PRIVATE);
        nouvelleClasse.addField(champ);
        //cc.addField(CtField.make(src, cc))

        // 4. On ajoute une méthode à la classe
        CtMethod methode = new CtMethod(CtClass.voidType, "maMethode", new CtClass[]{}, nouvelleClasse);
        methode.setModifiers(Modifier.PUBLIC);
        methode.setBody("{ System.out.println(\"Bonjour depuis maMethode !\"); }");
        nouvelleClasse.addMethod(methode);
        System.out.println(Modifier.toString(champ.getModifiers()));
        // 5. On sauvegarde la nouvelle classe sur le disque
        nouvelleClasse.writeFile("test");
    }

    public void test1() throws Exception {
        ControlAdapterParserXML parser = new ControlAdapterParserXML();
        ControlAdapterDescriptor descriptor = parser.parse("projet/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-adapter/heaterci-descriptor.xml");
        ControlAdapterGenerator generator = new ControlAdapterGenerator(descriptor);
        Class<?> generatedClass = generator.generatorClass();
        System.out.println("Classe générée : " + generatedClass.getName());
    }
}