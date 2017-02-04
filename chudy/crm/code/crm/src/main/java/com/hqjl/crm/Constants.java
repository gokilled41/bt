package com.hqjl.crm;

import java.io.File;
import java.text.SimpleDateFormat;

public interface Constants {

    // common constants
    public static final String EMPTY_STRING = "";
    public static final String DEFAULT = "default";
    public static final String DATAFLOW_DEFAULT_NAME = "default";
    public static final String NAME_SEPARATOR = "::";
    public static final String FILE_SEPARATOR = File.separator;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String PATH_SEPARATOR = "/";
    public static final String MODULE_NAME_SEPARATOR = ",";
    public static final String SDK_INSTANCE_SEPARATOR = "-";
    public static final String SEPARATOR_PLACE_HOLDER = "##sep##";
    public static final int BUFFER_SIZE = 8192; // 8Kb
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public final static Integer dataSuccess = 1;
    public final static Integer dataFailure = 0;

    public static final String ApprovalStatusAll = "全部";
    public static final String ApprovalStatusReady = "待审批";
    public static final String ApprovalStatusPass = "已结案-通过";
    public static final String ApprovalStatusReject = "已结案-不通过";
    public static final String ApprovalStatusApproving = "未结案";

    public static final String UserStatusAll = "全部";
    public static final String UserStatusNormal = "正常";
    public static final String UserStatusFreezed = "冻结";

    public static final long B = 1;
    public static final long KB = 1024 * B;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;

}
