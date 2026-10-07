package hu.parlament.enums;

import lombok.Getter;

@Getter
public enum VotingType {

    /**
     * jelenlét
     */
    j,

    /**
     * egyszerű többségi szavazás
     */
    e,

    /**
     * minősített többségi szavazás
     */
    m
}
