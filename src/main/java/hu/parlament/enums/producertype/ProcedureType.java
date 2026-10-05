package hu.parlament.enums.producertype;

import lombok.Getter;

@Getter
public enum ProcedureType {

    /**
     * normál
     */
    NORMAL("n"),

    /**
     * sürgősségi
     */
    URGENT("s"),

    /**
     *  kivételes
     */
    EXCEPTIONAL("k"),

    /**
     * szabályzattól eltérő
     */
    DEVIATING_FROM_RULES("e");

    /**
     * -- GETTER --
     *  Returns the single-character database code of this procedure type.
     *
     * @return the database code
     */
    private final String code;

    ProcedureType(String code) {
        this.code = code;
    }

    /**
     * Resolves a procedure type from its database code.
     *
     * @param code the database code ({@code n}, {@code s}, {@code k} or {@code e})
     * @return the matching procedure type
     * @throws IllegalArgumentException if the code is unknown
     */
    public static ProcedureType fromCode(String code) {
        for (ProcedureType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown procedure type code: " + code);
    }
}