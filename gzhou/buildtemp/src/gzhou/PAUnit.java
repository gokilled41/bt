package gzhou;

import java.io.File;

import gzhou.FileUtil.FileTimestampResult;
import gzhou.FileUtil.Filters;
import gzhou.FileUtil.ListConditionResult;
import gzhou.FileUtil.PAOperations;
import gzhou.FileUtil.Params;
import junit.framework.TestCase;

public class PAUnit extends TestCase implements Constants {

    public void testFilters_01() throws Exception {
        doTest(rndir, "abc", "abc", true);
        doTest(rndir, "abc", "a", true);
        doTest(rndir, "abc", "b", true);
        doTest(rndir, "abc", "c", true);
        doTest(rndir, "abc", "d", false);
        doTest(rndir, "abc", "a*", true);
        doTest(rndir, "abc", "/a", true);
        doTest(rndir, "abc", "/a/b/c", true);
        doTest(rndir, "abc", "/a\\d", true);
        doTest(rndir, "abc", "\\d", true);
        doTest(rndir, "abc", "\\d\\e", true);
        doTest(rndir, "abc", "/a/d", false);
        doTest(rndir, "abc", "/a\\d/b\\e/c\\f", true);
        doTest(rndir, "abc", "/a\\d\\e/b/c\\f", true);
        doTest(rndir, "abc", "/a\\d\\e/b\\f/c", true);
        doTest(rndir, "abc", "/a\\d\\e/b\\f/c/123", false);
        doTest(rndir, "abc", "/a\\d\\e/123/b\\f/c", false);
    }

    public void testFilters_02() throws Exception {
        doTest(rndir, "abc", "'abc'", true);
        doTest(rndir, "abc", "/'abc'", true);
        doTest(rndir, "abc*+", "'abc*+'", true);
        doTest(rndir, "abc*a+", "abc*+", true);
        doTest(rndir, "abc*a+", "'abc*+'", false);
    }

    public void testFilters_03() throws Exception {
        doTest(rndir, "abc", "@abc@", true);
        doTest(rndir, "13693680644", "@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@", true);
        doTest(rndir, "23693680644", "@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@", false);
        doTest(rndir, "12693680644", "@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@", false);
    }

    public void testFilters_04() throws Exception {
        doTest(rndir, "abc", "/a/(b\\d)", true);
        doTest(rndir, "abc", "(b\\d)/a", true);
        doTest(rndir, "abc", "/(b\\d)/a", true);
        doTest(rndir, "abc", "/a/(/b\\d)", true);
        doTest(rndir, "abc", "/a/(\\e\\d)", true);
        doTest(rndir, "abc", "/a/(c\\b\\d)", false);
        doTest(rndir, "abc", "/a\\(c\\b\\d)", true);
        doTest(rndir, "abc", "/a\\(c\\(b\\d))", true);
        doTest(rndir, "abc", "/a\\(c\\(b\\d/e))", false);
        doTest(rndir, "abc", "(c\\(b\\d/e))\\(c\\(b\\d/e))", false);
    }

    public void testFilters_05() throws Exception {
        doTest(rndir, "abc", "a\\(@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@/'a')", true);
    }

    public void testFilters_06() throws Exception {
        doTest(rndir, "abc", "a##b", true);
        doTest(rndir, "abc", "a##c", true);
        doTest(rndir, "abc", "a##d##e", true);
        doTest(rndir, "abc", "a##(\\b)", true);
        doTest(rndir, "abc", "a/(b##d)", true);
        doTest(rndir, "abc", "a/(d##e)", false);
        doTest(rndir, "abc", "a\\(d##e)", true);
        doTest(rndir, "abc", "a\\(b##e)", false);
        doTest(rndir, "abc", "/a\\(c\\((b\\d/e)##(\\c)))", false);
        doTest(rndir, "abc", "/a\\(c\\((b\\d/e)##(c)))", true);
    }

    public void testFilters_11() throws Exception {
        doTest1("abc", "abc", true, 1);
        doTest1("abc", "a", true, 1);
        doTest1("abc", "b", true, 1);
        doTest1("abc", "c", true, 1);
        doTest1("abc", "d", false, 1);
        doTest1("abc", "a*", true, 1);
        doTest1("abc", "/a", true, 1);
        doTest1("abc", "/a/b/c", true, 1);
        doTest1("abc", "/a\\d", true, 1);
        doTest1("abc", "\\d", true, 1);
        doTest1("abc", "\\d\\e", true, 1);
        doTest1("abc", "/a/d", false, 1);
        doTest1("abc", "/a\\d/b\\e/c\\f", true, 1);
        doTest1("abc", "/a\\d\\e/b/c\\f", true, 1);
        doTest1("abc", "/a\\d\\e/b\\f/c", true, 1);
        doTest1("abc", "/a\\d\\e/b\\f/c/123", false, 1);
        doTest1("abc", "/a\\d\\e/123/b\\f/c", false, 1);
        doTest1("abc", "a/l1", true, 100);
        doTest1("abc", "a/l1-200", true, 100);
        doTest1("abc", "a/l1-", true, 100);
        doTest1("abc", "a/l-200", true, 100);
        doTest1("abc", "a/l200-", false, 100);
        doTest1("abc", "a/l-99", false, 100);
        doTest1("abc", "a/l100-", true, 100);
        doTest1("abc", "a/l-100", true, 100);
        doTest1("abc", "l1", true, 100);
        doTest1("abc", "l1-200", true, 100);
        doTest1("abc", "l1-", true, 100);
        doTest1("abc", "l-200", true, 100);
        doTest1("abc", "l200-", false, 100);
        doTest1("abc", "l-99", false, 100);
        doTest1("abc", "l100-", true, 100);
        doTest1("abc", "l-100", true, 100);
    }

    public void testFilters_12() throws Exception {
        doTest1("abc", "'abc'", true, 1);
        doTest1("abc", "/'abc'", true, 1);
        doTest1("abc*+", "'abc*+'", true, 1);
        doTest1("abc*a+", "abc*+", true, 1);
        doTest1("abc*a+", "'abc*+'", false, 1);
    }

    public void testFilters_13() throws Exception {
        doTest1("abc", "@abc@", true, 1);
        doTest1("13693680644", "@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@", true, 1);
        doTest1("23693680644", "@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@", false, 1);
        doTest1("12693680644", "@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@", false, 1);
    }

    public void testFilters_14() throws Exception {
        doTest1("abc", "/a/(b\\d)", true, 1);
        doTest1("abc", "(b\\d)/a", true, 1);
        doTest1("abc", "/(b\\d)/a", true, 1);
        doTest1("abc", "/a/(/b\\d)", true, 1);
        doTest1("abc", "/a/(\\e\\d)", true, 1);
        doTest1("abc", "/a/(c\\b\\d)", false, 1);
        doTest1("abc", "/a\\(c\\b\\d)", true, 1);
        doTest1("abc", "/a\\(c\\(b\\d))", true, 1);
        doTest1("abc", "/a\\(c\\(b\\d/e))", false, 1);
        doTest1("abc", "(c\\(b\\d/e))\\(c\\(b\\d/e))", false, 1);
    }

    public void testFilters_15() throws Exception {
        doTest1("abc", "a\\(@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@/'a')", true, 1);
    }

    public void testFilters_21() throws Exception {
        doTest2("abc", "a\\(@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@/'a')", "a");
        doTest2("abc", "(@(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}@/'a')", "(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}");
        doTest2("abc", "(c\\(b\\d/e))\\(c\\(b\\d/e))", "c");
        doTest2("abc", "'a'/b\\d", "a");
        doTest2("abc", "'a*+'/b\\d", "a*+");
        doTest2("abc", "a*+/b\\d", "a*+");
    }

    public void testTARPath_01() throws Exception {
        if (Util.exists("C:\\Users\\gzhou")) {
            String p = "dd\\ol";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, desktopDir + "old");
            p = "dd\\ol\\buildt";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, desktopDir + "old\\buildtemp");
            p = "dd\\ol\\buildt\\build.xml";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, desktopDir + "old\\buildtemp\\build.xml");
            p = "dd\\old\\buildtemp\\build.xml";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, desktopDir + "old\\buildtemp\\build.xml");
            p = "dd\\old1\\buildtemp1\\build1.xml";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, desktopDir + "old1\\buildtemp1\\build1.xml");
        }
    }

    public void testUtil_01() throws Exception {
        String p = rn;
        assertEquals(Util.getFileSimpleName(p), "rename");
        assertEquals(Util.getFileExtName(p), "txt");
        p = rndir + "\\a.b.c.zip";
        assertEquals(Util.getFileSimpleName(p), "a.b.c");
        assertEquals(Util.getFileExtName(p), "zip");
        p = "rename.txt";
        assertEquals(Util.getFileSimpleName(p), "rename");
        assertEquals(Util.getFileExtName(p), "txt");
        p = "a.b.c.zip";
        assertEquals(Util.getFileSimpleName(p), "a.b.c");
        assertEquals(Util.getFileExtName(p), "zip");
    }

    public void testUtil_02() throws Exception {
        assertEquals(Util.toTimestampFormat("1"), "20170101000000");
        assertEquals(Util.toTimestampFormat("01"), "20170101000000");
        assertEquals(Util.toTimestampFormat("101"), "20170101000000");
        assertEquals(Util.toTimestampFormat("0101"), "20170101000000");
        assertEquals(Util.toTimestampFormat("010100"), "20170101000000");
        assertEquals(Util.toTimestampFormat("01010000"), "20170101000000");
        assertEquals(Util.toTimestampFormat("20170101"), "20170101000000");
        assertEquals(Util.toTimestampFormat("0101000000"), "20170101000000");
        assertEquals(Util.toTimestampFormat("2017010100"), "20170101000000");
        assertEquals(Util.toTimestampFormat("201701010000"), "20170101000000");
        assertEquals(Util.toTimestampFormat("20170101000000"), "20170101000000");
        assertEquals(Util.subLast("201701", 2), "01");
    }

    public void testFileTimestamp_01() throws Exception {
        assertEquals(FileTimestampResult.isParam("t1d"), true);
        assertEquals(FileTimestampResult.isParam("t1w"), true);
        assertEquals(FileTimestampResult.isParam("t1m"), true);
        assertEquals(FileTimestampResult.isParam("t1y"), true);
        assertEquals(FileTimestampResult.isParam("t1ds"), false);
        assertEquals(FileTimestampResult.isParam("t1m1w1d"), true);
        assertEquals(FileTimestampResult.isParam("t1d1m1y1w1d"), true);
        assertEquals(FileTimestampResult.isParam("t1d1m1y1s1d"), false);
        assertEquals(FileTimestampResult.isParam("t1m1w-1d"), false);
    }

    public void testListCondition_01() throws Exception {
        assertEquals(ListConditionResult.isParam("in(lib)"), true);
    }

    public void testNewFileNameInCopy_01() throws Exception {
        Params p = new Params();
        p.newFileName = "{n}";
        assertEquals(PAOperations.newFileNameInCopy("a.txt", p, true), "a.txt");
        p.newFileName = "{n}.{e}";
        assertEquals(PAOperations.newFileNameInCopy("a.txt", p, true), "a.txt");
        p.newFileName = "{n}-2";
        assertEquals(PAOperations.newFileNameInCopy("a.txt", p, true), "a-2.txt");
        p.newFileName = "1-{n}";
        assertEquals(PAOperations.newFileNameInCopy("a.txt", p, true), "1-a.txt");
        p.newFileName = "{n1-2}";
        assertEquals(PAOperations.newFileNameInCopy("abc.txt", p, true), "ab.txt");
        p.newFileName = "{n2}";
        assertEquals(PAOperations.newFileNameInCopy("abc.txt", p, true), "ab.txt");
        p.newFileName = "{n-2}";
        assertEquals(PAOperations.newFileNameInCopy("abc.txt", p, true), "bc.txt");
        p.newFileName = "{n-2}";
        assertEquals(PAOperations.newFileNameInCopy("1234567890.txt", p, true), "90.txt");
        p.newFileName = "a{n7-8}b{n7-8}c{n-2}";
        assertEquals(PAOperations.newFileNameInCopy("1234567890.txt", p, true), "a78b78c90.txt");
        p.newFileName = "{n4}";
        assertEquals(PAOperations.newFileNameInCopy("yoda_sjb", p, false), "yoda");
        p.newFileName = "{n}_main";
        assertEquals(PAOperations.newFileNameInCopy("yoda", p, false), "yoda_main");
    }

    private void doTest(String dir, String name, String filefrom, boolean expect) {
        doTest(dir, name, filefrom, expect, 0);
    }

    private void doTest(String dir, String name, String filefrom, boolean expect, int i) {
        if (i > 0)
            setDebug();
        boolean b = doTest0(dir, name, filefrom);
        if (i > 0)
            setNoDebug();
        if (b != expect) {
            setDebug();
            doTest0(dir, name, filefrom);
            setNoDebug();
            fail(Util.format("dir={0}, name={1}, filefrom={2}, expect={3}", dir, name, filefrom, expect));
        }
    }

    private boolean doTest0(String dir, String name, String filefrom) {
        Filters filters = Filters.getFilters(filefrom, null);
        return filters.accept(new File(dir), name);
    }

    private void doTest1(String line, String from, boolean expect, int pos) {
        doTest1(line, from, expect, pos, 0);
    }

    private void doTest1(String line, String from, boolean expect, int pos, int i) {
        if (i > 0)
            setDebug();
        boolean b = doTest10(line, from, pos);
        if (i > 0)
            setNoDebug();
        if (b != expect) {
            setDebug();
            doTest10(line, from, pos);
            setNoDebug();
            fail(Util.format("line={0}, from={1}, expect={2}", line, from, expect));
        }
    }

    private boolean doTest10(String line, String from, int pos) {
        Filters filters = Filters.getFilters(from, null);
        return filters.accept(line, pos);
    }

    private void doTest2(String line, String from, String expect) {
        Filters filters = Filters.getFilters(from, null);
        assertEquals(filters.getFirst(), expect);
    }

    private void setDebug() {
        FileUtil.debug2_ = true;
    }

    private void setNoDebug() {
        FileUtil.debug2_ = false;
    }

}
