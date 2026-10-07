package hu.parlament.enums;

import lombok.Getter;

@Getter
public enum ProcedureType {

    /**
     * normál
     */
    n,

    /**
     * sürgősségi
     */
    s,

    /**
     *  kivételes
     */
    k,

    /**
     * szabályzattól eltérő
     */
    e

}