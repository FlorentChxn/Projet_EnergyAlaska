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
    
    //-----------------------------------------------------------------------------
    // Instance variables
    //-----------------------------------------------------------------------------

    /**the descriptor of the control adapter.*/
    private final ControlAdapterDescriptor descriptor;
    /**the package name of the generated classes.*/
    private final static String packageName = "fr.sorbonne_u.components.hem2026";
    /**the verbose mode of the generator.*/
    public static boolean VERBOSE = false;
    /**a map to store the generated classes.*/
    public final static Map<String, Class<?>> tabGeneratedClass = new HashMap<String, Class<?>>();

    //-----------------------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------------------
    /**
     * create a control adapter generator.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code descriptor != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param descriptor the descriptor of the control adapter.
     */
    public ControlAdapterGenerator(ControlAdapterDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    //-----------------------------------------------------------------------------
    // Methods
    //-----------------------------------------------------------------------------

    /**
     * generate the class of the control adapter.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @return the class of the control adapter.
     * @throws Exception if the class cannot be generated.
     */
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

    /**
     * add the required imports to the class pool.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code pool != null && imports != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param pool the class pool.
     * @param imports the list of required imports.
     * @throws Exception if the imports cannot be added.
     */
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

    /**
     * add the instance variables to the class.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code cc != null && instances != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param cc the class to which the instance variables will be added.
     * @param instances the list of instance variables.
     * @throws Exception if the instance variables cannot be added.
     */
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

    /**
     * add the methods to the class.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code pool != null && cc != null && methods != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param pool the class pool.
     * @param cc the class to which the methods will be added.
     * @param methods the list of methods.
     * @throws Exception if the methods cannot be added.
     */
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

    /**
     * get the return type of a method in the AdjustableCI class.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code pool != null && name != null && !name.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param pool the class pool.
     * @param name the name of the method.
     * @return the return type of the method.
     * @throws Exception if the return type cannot be retrieved.
     */
    public String getTypeMethodAdjustableCI (ClassPool pool,String name)throws Exception {
        CtClass cc = pool.get(packageName + ".bases.AdjustableCI");
        String typeMethod = cc.getDeclaredMethod(name).getReturnType().getName();
        if (typeMethod == null || typeMethod.isEmpty()) {
            return "void";
        }
        return typeMethod;
    }

    /**
     * get the parameter list of a method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code pool != null && methode != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param pool the class pool.
     * @param methode the method descriptor.
     * @return the parameter list of the method.
     * @throws Exception if the parameter list cannot be retrieved.
     */
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

    /**
     * get the type of a parameter in the AdjustableCI class.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code pool != null && name != null && !name.isEmpty() && index >= 0}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param pool the class pool.
     * @param name the name of the method.
     * @param index the index of the parameter.
     * @return the type of the parameter.
     * @throws Exception if the type cannot be retrieved.
     */
    public String getTypeParametreAdjustableCI (ClassPool pool,String name, int index)throws Exception {
        CtClass cc = pool.get(packageName + ".bases.AdjustableCI");
        CtMethod method = cc.getDeclaredMethod(name);
        CtClass[] paraType = method.getParameterTypes();
        return paraType[index].getName();
    }

    /**
     * get the exception list of a method.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code methode != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param methode the method descriptor.
     * @return the exception list of the method.
     */
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

    /**
     * get the exception of a method in the AdjustableCI class.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code name != null && !name.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param name the name of the method.
     * @return the exception of the method.
     */
    public String getAdjustableException(String name) {
        if ("maxMode".equals(name) || "upMode".equals(name) || "downMode".equals(name) || "currentMode".equals(name) || "setMode".equals(name) || "currentMode".equals(name) || "getModeConsumption".equals(name) || "suspended".equals(name) || "suspend".equals(name) || "resume".equals(name) || "emergency".equals(name)) {
            return "java.lang.Exception";
        }
        return "";
    }
}