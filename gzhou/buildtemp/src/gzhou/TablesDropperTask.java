package gzhou;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public abstract class TablesDropperTask extends Task {

    protected String driver_, url_, userId_, password_, db_;
    protected Connection conn_;
    protected Statement stmt_;

    public void setDriver(String driver) {
        driver_ = driver;
    }

    public void setPassword(String password) {
        password_ = password;
    }

    public void setUrl(String url) {
        url_ = url;
    }

    public void setUserid(String userId) {
        userId_ = userId;
    }

    public void setDb(String db) {
        db_ = db;
    }

    @Override
    public void execute() throws BuildException {

        try {
            Statement stmt = getStatement();
            List<String> sqls = getSQLs();

            for (String sql : sqls) {
                try {
                    stmt.execute(sql);
                    log("Executec: " + sql);
                } catch (Exception e) {
                    log("Failed to execute: " + sql);
                }
            }
            conn_.commit();

        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            close();
        }

    }

    protected Connection getConnection() throws Exception {
        if (conn_ == null) {
            Class.forName(driver_);
            conn_ = DriverManager.getConnection(url_, userId_, password_);
            conn_.setAutoCommit(false);
        }
        return conn_;
    }

    protected Statement getStatement() throws Exception {
        if (stmt_ == null) {
            Connection c = getConnection();
            stmt_ = c.createStatement();
        }
        return stmt_;
    }

    protected void close() {
        try {
            Statement s = getStatement();
            if (s != null)
                s.close();
            Connection c = getConnection();
            if (c != null)
                c.close();
        } catch (Exception e) {
        }
    }

    public abstract List<String> getSQLs();
}
