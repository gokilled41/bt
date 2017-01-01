package gzhou;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OracleTablesDropperTask extends TablesDropperTask {

    @Override
    public List<String> getSQLs() {
        List<String> sqls = new ArrayList<String>();
        sqls.addAll(getTableSqls());
        sqls.addAll(getViewSqls());
        sqls.addAll(getSequenceSqls());
        sqls.addAll(getFunctionSqls());
        sqls.addAll(getProcedureSqls());
        sqls.addAll(getPackageSqls());
        return sqls;
    }

    private List<String> getTableSqls() {
        List<String> tables = getTables();
        List<String> sqls = new ArrayList<String>();
        for (String table : tables) {
            sqls.add("drop table " + table + " cascade constraints");
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

    private List<String> getSequenceSqls() {
        List<String> sequences = getSequences();
        List<String> sqls = new ArrayList<String>();
        for (String sequence : sequences) {
            sqls.add("drop sequence " + sequence);
        }
        return sqls;
    }

    private List<String> getFunctionSqls() {
        List<String> functions = getObjects("FUNCTION");
        List<String> sqls = new ArrayList<String>();
        for (String function : functions) {
            sqls.add("drop function " + function);
        }
        return sqls;
    }

    private List<String> getProcedureSqls() {
        List<String> procedures = getObjects("PROCEDURE");
        List<String> sqls = new ArrayList<String>();
        for (String procedure : procedures) {
            sqls.add("drop procedure " + procedure);
        }
        return sqls;
    }

    private List<String> getPackageSqls() {
        List<String> packages = getObjects("PACKAGE");
        List<String> sqls = new ArrayList<String>();
        for (String p : packages) {
            sqls.add("drop package " + p);
        }
        return sqls;
    }

    private List<String> getTables() {
        try {
            Statement stmt = getStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString("table_name"));
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
            ResultSet rs = stmt.executeQuery("select view_name from user_views");
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString("view_name"));
            }
            return list;
        } catch (Exception e) {
            log(e.getMessage());
        }
        return new ArrayList<String>();
    }

    private List<String> getSequences() {
        try {
            Statement stmt = getStatement();
            ResultSet rs = stmt.executeQuery("select sequence_name from user_sequences");
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString("sequence_name"));
            }
            return list;
        } catch (Exception e) {
            log(e.getMessage());
        }
        return new ArrayList<String>();
    }

    private List<String> getObjects(String name) {
        try {
            Statement stmt = getStatement();
            ResultSet rs = stmt.executeQuery("select object_name from user_objects where object_type='" + name + "'");
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString("object_name"));
            }
            return list;
        } catch (Exception e) {
            log(e.getMessage());
        }
        return new ArrayList<String>();
    }

}
