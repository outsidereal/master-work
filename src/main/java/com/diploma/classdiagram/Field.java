package com.diploma.classdiagram;

import com.diploma.classdiagram.enumerates.Visibility;

/**
 * User: ZIM
 * Date: 18.08.12
 * Time: 17:33
 */
public class Field extends MethodParameter {
    private Visibility visibility;
    private boolean isFinal = false;
    private boolean isStatic = false;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Field field = (Field) o;

        if (isFinal != field.isFinal) return false;
        if (isStatic != field.isStatic) return false;
        if (visibility != field.visibility) return false;
        if (getValue() != field.getValue()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + visibility.hashCode();
        result = 31 * result + (isFinal ? 1 : 0);
        result = 31 * result + (isStatic ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String fieldInfo =
                "Attribute name : " + getName() + "\n" +
                        "Visibility : " + getVisibility().toString() + "\n" +
                        "isStatic : " + isStatic() + "\n" +
                        "isFinal : " + isFinal() + "\n" +
                        "Type : " + getType() + "\n" +
                        "ID : " + getId() + "\n" +
                        "Value : " + getValue() + "\n";

        return fieldInfo;
    }
}
