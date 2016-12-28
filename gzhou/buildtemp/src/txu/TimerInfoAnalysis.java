package txu;

import gzhou.SQLQuery;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimerInfoAnalysis {

    private static final Log log_ = LogFactory.getLog(TimerInfoAnalysis.class);

    public static void analyze(String triggerName) {
        //        log_.info("triggerName: " + triggerName);
        String activityId = triggerName.substring(0, triggerName.indexOf("$"));
        //        log_.info("activityId: " + activityId);
        String sql = "SELECT * FROM VT_BPMN_ACTIVITY WHERE activity_id = '" + activityId + "'";
        Object[] results = SQLQuery.getQuery().queryWithResultFirstRow(sql);
        //        log_.info("activity: " + Arrays.asList(results));
        String instanceId = (String) results[1];
        sql = "SELECT * FROM VT_BPMN_PROCESS WHERE instance_id = '" + instanceId + "'";
        results = SQLQuery.getQuery().queryWithResultFirstRow(sql);
        log_.info("process: " + Arrays.asList(results));
    }

}
