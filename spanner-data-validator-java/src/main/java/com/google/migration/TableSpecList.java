package com.google.migration;

import com.google.migration.dto.TableSpec;
import java.util.ArrayList;
import java.util.List;

public class TableSpecList {
  public static List<TableSpec> getTableSpecs() {
    ArrayList<TableSpec> tableSpecs = new ArrayList<>();

    // Must use p1 and p2 here because that's what the query expression
    // binder expects downstream
    TableSpec spec = new TableSpec(
        "customers",
        "select * from customers where customerNumber > ? and customerNumber <= ?",
        "select * from customers where customerNumber > @p1 "
            + " and customerNumber <= @p2",
        0,
        2,
        TableSpec.INT_FIELD_TYPE,
        "0",
        String.valueOf(Integer.MAX_VALUE)
    );
    tableSpecs.add(spec);

    return tableSpecs;
  }

  public static List<TableSpec> getPostgresTableSpecs() {
    ArrayList<TableSpec> tableSpecs = new ArrayList<>();

    TableSpec spec = new TableSpec(
        "DataProductMetadata",
        "select * from \"data-products\".data_product_metadata where data_product_id > uuid(?) and data_product_id <= uuid(?)",
        "SELECT key, value, data_product_id FROM data_product_metadata "
            + "WHERE data_product_id > $1 AND data_product_id <= $2", // Spangres
        2,
        2,
        TableSpec.UUID_FIELD_TYPE,
        "00000000-0000-0000-0000-000000000000",
        "ffffffff-ffff-ffff-ffff-ffffffffffff"
    );
    tableSpecs.add(spec);

    spec = new TableSpec(
        "DataProductRecords",
        "select * from \"data-products\".data_product_records "
            + "where id > uuid(?) and id <= uuid(?)",
        "SELECT * FROM data_product_records "
            + "WHERE id > $1 AND id <= $2",
        0, // zero based index of column that is key (in this case, it's id)
        2, // integer percentage of rows per partition range - top 2 percent *within range*
        TableSpec.UUID_FIELD_TYPE,
        "00000000-0000-0000-0000-000000000000",
        //"02010000-0000-0000-ffff-ffffffffffff"
        "ffffffff-ffff-ffff-ffff-ffffffffffff"
    );
    tableSpecs.add(spec);

    return tableSpecs;
  }
}