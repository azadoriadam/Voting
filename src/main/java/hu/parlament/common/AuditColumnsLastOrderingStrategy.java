package hu.parlament.common;


import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.ColumnOrderingStrategyStandard;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings("ALL")
public class AuditColumnsLastOrderingStrategy extends ColumnOrderingStrategyStandard {

    private static final Set<String> AUDIT_COLUMNS =
            Set.of("created_at", "created_by", "modified_at", "modified_by");

    @Override
    public List<Column> orderTableColumns(Table table, Metadata metadata) {
        List<Column> ordered = new ArrayList<>(table.getColumns());
        List<Column> standard = super.orderTableColumns(table, metadata);
        if (standard != null) {
            ordered = new ArrayList<>(standard);
        }

        List<Column> audit = ordered.stream()
                .filter(c -> AUDIT_COLUMNS.contains(c.getName().toLowerCase(Locale.ROOT)))
                .toList();
        ordered.removeAll(audit);
        ordered.addAll(audit);
        return ordered;
    }
}
