package hu.parlament.enums.votingtype;

import lombok.Getter;

@Getter
public enum VotingType {

    /**
     * jelenlét
     */
    ATTENDANCE("j"),

    /**
     * egyszerű többségi szavazás
     */
    SIMPLE_MAJORITY("e"),

    /**
     * minősített többségi szavazás
     */
    QUALIFIED_MAJORITY("m");


    private final String code;

    VotingType(String code) {
        this.code = code;
    }

    public static VotingType fromCode(String code) {
        for (VotingType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown voting type code: " + code);
    }
}
