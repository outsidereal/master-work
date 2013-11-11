package com.diploma.classdiagram;

import com.diploma.classdiagram.enumerates.Visibility;
import com.diploma.global.XMLElement;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 18.08.12
 * Time: 21:57
 */
public class Method extends XMLElement {

    private List<MethodParameter> parameters;
    private String returnType;
    private Visibility visibility = Visibility.PUBLIC;
    private boolean isStatic = false;
    private boolean isFinal = false;
    private boolean isAbstract = false;


    public List<MethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<MethodParameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return - Return type of method.
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * @param returnType - Set the return type of method.
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Method method = (Method) o;

        if (isFinal != method.isFinal) return false;
        if (isStatic != method.isStatic) return false;
        if (!parameters.equals(method.parameters)) return false;
        if (!returnType.equals(method.returnType)) return false;
        //if (visibility != method.visibility) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parameters.hashCode();
        result = 31 * result + returnType.hashCode();
        result = 31 * result + visibility.hashCode();
        result = 31 * result + (isStatic ? 1 : 0);
        result = 31 * result + (isFinal ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String methodInfo =
                "Method name : " + getName() + "\n" +
                        "Visibility : " + getVisibility() + "\n" +
                        "isStatic : " + isStatic() + "\n" +
                        "isFinal : " + isFinal() + "\n" +
                        "isAbstract : " + isAbstract() + "\n" +
                        "return type : " + getReturnType() + "\n" +
                        "ID : " + getId() + "\n";

        methodInfo += "Parameters list : \n";

        int count = 0;

        if (parameters != null) {
            for (Iterator iterator = parameters.iterator(); iterator.hasNext(); ) {
                count++;
                methodInfo += count + ". " + ((MethodParameter) iterator.next()).toString() + "\n";
            }
        }
        return methodInfo;
    }
}
