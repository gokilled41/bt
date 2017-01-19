package gzhou;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public interface Constants {

    public static final String NAME_SEPARATOR = "::";
    public static final String FILE_SEPARATOR = File.separator;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String PATH_SEPARATOR = "/";
    public static final String SEPARATOR_PLACE_HOLDER = "##sep##";

    public static final String YODA_DIR = "D:\\jedi\\yoda";
    public static final String TMP_FILE = "C:\\workspace\\buildtemp\\files\\xml\\tmp.xml";
    public static final String TMP_TXT = "C:\\workspace\\buildtemp\\files\\txt\\tmp.txt";
    public static final String TMP2_TXT = "C:\\workspace\\buildtemp\\files\\txt\\tmp2.txt";
    public static final String DIFF_TXT = "C:\\workspace\\buildtemp\\files\\txt\\diff.txt";
    public static final String BAT_TEMPLATE_FILE = "C:\\workspace\\buildtemp\\files\\txt\\bat_vtbahome_template.txt";
    public static final String desktopDir = "C:\\Users\\gzhou\\Desktop\\";
    public static final String rndir = desktopDir + "rename";
    public static final String rn = desktopDir + "rename\\rename.txt";
    public static final String btDir = "C:\\workspace\\buildtemp\\";
    public static final String filesDir = btDir + "files\\";
    public static final String sqlDir = filesDir + "sql\\";
    public static final String txtDir = filesDir + "txt\\";
    public static final String batDir = "C:\\workspace\\buildtemp\\files\\bat\\";
    public static final String TMP_XML = "C:\\workspace\\buildtemp\\files\\xml\\tmp.xml";
    public static final String serverLog = "D:\\jedi\\yoda\\export\\home\\jboss\\server\\vtba\\log\\server.log";
    public static final String serverLogDir = "D:\\jedi\\yoda\\export\\home\\jboss\\server\\vtba\\log\\";

    public static final String privateBranchesDir = "D:\\jedi\\branches\\";
    public static final String featureName = "oi60_parquet";
    public static final String featureShortCut = "pq";
    public static final String privateBranchDir = privateBranchesDir + featureName + "\\";
    public static final String mainBranchUrl = "http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/";
    public static final String privateBranchUrl = "http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/" + featureName
            + "/";

    public static final String TYPEANDRUN_CONFIG = "D:\\Workflow-G\\software\\typeandrun\\Config.ini";
    public static final String TYPEANDRUN_CONFIG2 = "D:\\share\\typeandrun\\Config.ini";
    public static final String TYPEANDRUN_CONFIG3 = "D:\\ftp\\Config.ini";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    public static final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat sdfDay2 = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

    public static final DecimalFormat df = new DecimalFormat("#,###");

    public static final String[] msBatArray = new String[] { "mscl", "msc", "mse", "msvs", "msds", "msl", "mja" };
    public static final String[] msBatLocArray = new String[] { "m3o\\server\\src\\client", "m3o\\server\\src\\core",
            "m3o\\server\\src\\engine", "m3o\\server\\src\\virtualserver", "m3o\\server\\src\\domainservice",
            "m3o\\server\\locale\\en_US", "m3o\\j2ee\\src\\application" };

    public static final String GBK = "gbk";
    public static final String UTF8 = "utf-8";

    public static final String UltraEdit = "C:\\Program Files (x86)\\IDM Computer Solutions\\UltraEdit\\Uedit32.exe";

    public static final long MILLISECOND = 1;
    public static final long SECOND = 1000 * MILLISECOND;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;

    public static final String HANDLE_LINE = "HANDLE LINE";

}
