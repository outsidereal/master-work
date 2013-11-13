package com.diploma.classdiagram;

import com.diploma.classdiagram.enumerates.Visibility;
import com.diploma.global.XMLElement;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 18.08.12
 * Time: 17:29
 */
public class Class extends XMLElement {
    private Visibility visibility;
    private boolean isFinal = false;
    private boolean isStatic = false;
    private boolean isAbstract = false;
    private boolean isInterface = false;

    private List<Field> fields;
    private List<Method> methods;

    public Class() {

    }

    public Class(String className) {
        this.setName(className);
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
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
    public String toString() {
        String classInfo =
                "Class name : " + getName() + "\n" +
                        "isInterface : " + isInterface() + "\n" +
                        "Visibility : " + getVisibility() + "\n" +
                        "isStatic : " + isStatic() + "\n" +
                        "isFinal : " + isFinal() + "\n" +
                        "isAbstract : " + isAbstract() + "\n" +
                        "ID : " + getId() + "\n" +
                        "Fields list : " + "\n";
        int count = 0;
        for (Iterator iterator = fields.iterator(); iterator.hasNext(); ) {
            count++;
            classInfo += count + ". " + ((Field) iterator.next()).toString() + "\n";
        }

        count = 0;
        classInfo += "Methods List : \n";
        for (Iterator iterator = methods.iterator(); iterator.hasNext(); ) {
            count++;
            classInfo += count + ". " + ((Method) iterator.next()).toString() + "\n";
        }

        return classInfo;
    }
}
