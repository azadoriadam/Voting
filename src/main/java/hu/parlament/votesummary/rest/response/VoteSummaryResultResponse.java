package hu.parlament.votesummary.rest.response;

import hu.parlament.enums.VoteSummaryResult;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Egy szavazás adatai")
public record VoteSummaryResultResponse(

        @Schema(description = "A szavazás eredményi: F =  Elfogadott, U = Elutasított",
                example = "F", allowableValues = {"F", "U"}, requiredMode = Schema.RequiredMode.REQUIRED)
        VoteSummaryResult eredmeny,

        @Schema(description = "Képviselők száma", example = "200", minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Integer kepviselokSzama,

        @Schema(description = "Igenek száma", example = "101", minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Integer igenekSzama,

        @Schema(description = "Nemek száma", example = "99", minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Integer nemekSzama,

        @Schema(description = "Tartózkodások száma", example = "16", minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Integer tartozkodasokSzama

) { }
