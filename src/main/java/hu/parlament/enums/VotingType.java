package hu.parlament.enums;

import lombok.Getter;

@Getter
public enum VotingType {

    /**
     * jelenlét : csak a jelenlévő képviselők létszámának megállapítására szolgál,
     * eredménye mindig  elfogadott
     */
    j,

    /**
     * egyszerű : eredménye elfogadott, ha a jelenlevő képviselők több mint fele igennel szavazott,
     * egyébként elutasított.
     */
    e,

    /**
     * minősített :  eredménye elfogadott, ha az összes képviselő több mint fele igennel szavazott.
     * Az összes képviselő létszáma adott. Jelenleg 200 fő.
     */
    m
}
