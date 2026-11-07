package fr.sorbonne_u.components.hem2026.bases;

import javassist.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import fr.sorbonne_u.components.hem2026.bases.parser.ControlAdapterDescriptor;
import fr.sorbonne_u.components.hem2026.bases.parser.InstanceVariableDescriptor;
import fr.sorbonne_u.components.hem2026.bases.parser.MethodeDescriptor;
// -----------------------------------------------------------------------------
/**
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * </p>
 * 
 * <p>Created on : 2026-11-01</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */
public class ControlAdapterGenerator {
    
    private final ControlAdapterDescriptor descriptor;
    private final static String packageName = "fr.sorbonne_u.components.hem2026";
    public static boolean VERBOSE = false;
    public final static Map<String, Class<?>> tabGeneratedClass = new HashMap<String, Class<?>>();

    public ControlAdapterGenerator(ControlAdapterDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public Class<?> generatorClass() throws Exception {
        // création de la classe + héritage + implementation + constructeur par défaut
        String className = packageName + ".generated.Connector_" + this.descriptor.getUid() ;
        Class<?> generatedClass = tabGeneratedClass.get(className);
        if (generatedClass != null) {
            if (VERBOSE) {
                System.out.println("Class " + className + " already generated, returning the existing class.");
            }
            return generatedClass;
        }
        ClassPool pool = ClassPool.getDefault();
        addRequiredImports(pool, this.descriptor.getRequired());
        //System.out.println("Code de la méthode :\n" + className);
        CtClass cc = pool.makeClass(className);
        try{
            cc.setSuperclass(pool.get(AbstractConnector.class.getName()));
            cc.addInterface(pool.get(AdjustableCI.class.getName()));
            cc.addConstructor(CtNewConstructor.defaultConstructor(cc));
            addInstance(cc, this.descriptor.getInstanceVariables());
            addMethods(pool, cc, this.descriptor.getOperations());
            Class<?> generated = cc.toClass();
            tabGeneratedClass.put(className, generated);
            if (VERBOSE) {
                System.out.println("Class " + className + " generated successfully.");
            }
            return generated;
        } catch (Exception e) {
            throw new Exception("Error while generating class: " + e.getMessage(), e);
        } finally {
            System.out.println("Detaching class " + cc.getName() + " from the ClassPool.");
            cc.detach();
        }
    }

    public void addRequiredImports (ClassPool pool, List<String> imports) throws Exception {
        // Ajout d'import à la classe générée
        for (String importStr : imports) {
            // Ajouter l'import à la classe générée
            if (importStr != null && !importStr.isEmpty()) {
                try {
                    pool.get(importStr);
                    pool.importPackage(importStr);
                    if (VERBOSE) {
                        System.out.println("Import added: " + importStr);
                    }
                } catch (NotFoundException e) {
                    throw new Exception("Import not found: " + importStr, e);
                }
            }
        }
    }

    public void addInstance (CtClass cc,List<InstanceVariableDescriptor> instances) throws Exception {
        // Ajout d'une variable d'instance à la classe générée
        try {
            for (InstanceVariableDescriptor instance : instances) {
                if (instance != null) {
                    StringBuilder attributeCode = new StringBuilder();
                    attributeCode.append(instance.getModifiers()).append(" ");
                    attributeCode.append(instance.getType()).append(" ").append(instance.getName());
                    if (instance.getStaticInit() != null && !instance.getStaticInit().isEmpty()) {
                        attributeCode.append(" = ").append(instance.getStaticInit());
                    }
                    if (VERBOSE) {
                        System.out.println("Code de la variable d'instance :\n" + attributeCode);
                    }
                    cc.addField(CtField.make(attributeCode.toString() + ";",cc));
                }
            }
        } catch (CannotCompileException e) {
            throw new Exception("cannot compile instance variable: " + e.getMessage(), e);
        }
    }

    public void addMethods (ClassPool pool, CtClass cc,List<MethodeDescriptor> methods) throws Exception {
        // Ajout d'une méthode à la classe générée
        try {
            for ( MethodeDescriptor methode : methods) {
                if (methode.getName() != null || !methode.getName().isEmpty()) {
                    String modifiers = methode.getModifiers() != null ? methode.getModifiers() : "public";
                    String returnType = methode.getReturnType() != null ? methode.getReturnType() : getTypeMethodAdjustableCI(pool, methode.getName());
                    String parameters = getParameterList(pool, methode);

                    String ref1 = getExceptionList(methode);
                    String exceptions = (ref1 == null || ref1.isEmpty()) ? "" : "throws " + ref1;

                    String ref2 = methode.getEquipmentReference();
                    StringBuilder equipmentReference = new StringBuilder();
                    if (ref2 != null && !ref2.isEmpty()) {
                        equipmentReference.append(this.descriptor.getOffered()).append(" ").append(ref2).append(" = (");
                        equipmentReference.append(this.descriptor.getOffered()).append(") this.offering;");
                    }

                    StringBuilder src = new StringBuilder();
                    src.append(modifiers).append(" ").append(returnType).append(" ").append(methode.getName());
                    src.append("(").append(parameters).append(")").append(exceptions).append(" { ");
                    src.append(equipmentReference).append(methode.getBody()).append(" }");
                    if (VERBOSE) {
                        System.out.println("Code de la méthode :\n" + src.toString());
                    }
                    cc.addMethod(CtNewMethod.make(src.toString(), cc));
                }
            }
        } catch (CannotCompileException e) {
            throw new Exception("cannot compile method: " + e.getMessage(), e);
        }
    }

    public String getTypeMethodAdjustableCI (ClassPool pool,String name)throws Exception {
        CtClass cc = pool.get(packageName + ".bases.AdjustableCI");
        String typeMethod = cc.getDeclaredMethod(name).getReturnType().getName();
        if (typeMethod == null || typeMethod.isEmpty()) {
            return "void";
        }
        return typeMethod;
    }

    public String getParameterList(ClassPool pool, MethodeDescriptor methode) {
        List<MethodeDescriptor.ParameterDescriptor> parameters = methode.getParameters();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            MethodeDescriptor.ParameterDescriptor parameter = parameters.get(i);
            String type = parameter.getType();
            if (type == null || type.isEmpty()) {
                try {
                    type = getTypeParametreAdjustableCI(pool, methode.getName(), i);
                } catch (Exception e) {
                    throw new RuntimeException("Error while getting parameter type for method: " + parameter.getName(), e);
                }
            }
            result.append(type).append(" ").append(parameter.getName());
            if (i < parameters.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    public String getTypeParametreAdjustableCI (ClassPool pool,String name, int index)throws Exception {
        CtClass cc = pool.get(packageName + ".bases.AdjustableCI");
        CtMethod method = cc.getDeclaredMethod(name);
        CtClass[] paraType = method.getParameterTypes();
        return paraType[index].getName();
    }

    public String getExceptionList(MethodeDescriptor methode) {
        List<String> exceptions = methode.getExceptions();
        if (exceptions == null || exceptions.isEmpty()) {
            return getAdjustableException(methode.getName());
        }
        String result ="";
        for (int i = 0; i < exceptions.size(); i++) {
            result += exceptions.get(i);
            if (i < exceptions.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }
    
    public String getAdjustableException(String name) {
        if ("maxMode".equals(name) || "upMode".equals(name) || "downMode".equals(name) || "currentMode".equals(name) || "setMode".equals(name) || "currentMode".equals(name) || "getModeConsumption".equals(name) || "suspended".equals(name) || "suspend".equals(name) || "resume".equals(name) || "emergency".equals(name)) {
            return "java.lang.Exception";
        }
        return "";
    }
}