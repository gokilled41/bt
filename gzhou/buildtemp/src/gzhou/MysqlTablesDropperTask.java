package gzhou;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlTablesDropperTask extends TablesDropperTask {

    @Override
    public List<String> getSQLs() {
        List<String> sqls = new ArrayList<String>();
        sqls.addAll(getTableSqls());
        sqls.addAll(getViewSqls());
        return sqls;
    }

    private List<String> getTableSqls() {
        List<String> tables = getTables();
        List<String> sqls = new ArrayList<String>();
        for (String table : tables) {
            sqls.add("drop table " + table);
        }
        return sqls;
    }

    private List<String> getViewSqls() {
        List<String> views = getViews();
        List<String> sqls = new ArrayList<String>();
        for (String view : views) {
            sqls.add("drop view " + view);
        }
        return sqls;
    }

    private List<String> getTables() {
        try {
            Statement stmt = getStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT table_name as name FROM information_schema.tables where table_schema = '"
                            + db_ + "' and table_type like '%table'");
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            return list;
        } catch (Exception e) {
            log(e.getMessage());
        }
        return new ArrayList<String>();
    }

    private List<String> getViews() {
        try {
            Statement stmt = getStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT table_name as name FROM information_schema.tables where table_schema = '"
                            + db_ + "' and table_type like '%view'");
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            return list;
        } catch (Exception e) {
            log(e.getMessage());
        }
        return new ArrayList<String>();
    }

}
