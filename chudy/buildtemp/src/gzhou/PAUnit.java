package gzhou;

import gzhou.FileUtil.FileTimestampResult;
import gzhou.FileUtil.Filters;
import gzhou.FileUtil.FiltersPattern;
import gzhou.FileUtil.ListConditionResult;
import gzhou.FileUtil.PAOperations;
import gzhou.FileUtil.Params;

import java.io.File;

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

    public void testFilters_31() throws Exception {
        doTestFP("abc", "/abc");
        doTestFP("a/b", "/a/b");
        doTestFP("a/(b/c)", "/a/(b/c)");
        doTestFP("abc;", "/abc;");
        doTestFP("abc;;", "/abc;;");
    }

    public void testTARPath_01() throws Exception {
        if (Util.exists("C:\\Users\\Chudy")) {
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
            p="y/m3o/*/(src\\dev)/domainser";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, "D:\\jedi\\yoda\\m3o\\server\\src\\domainservice");
            p="y/m3o/*/s/domainser";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, "D:\\jedi\\yoda\\m3o\\server\\src\\domainservice");
            p="y/m3o/*/src/domainser";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, "D:\\jedi\\yoda\\m3o\\server\\src\\domainservice");
            p="y/m3o/*/src/domainser/**/util";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, "D:\\jedi\\yoda\\m3o\\server\\src\\domainservice\\com\\vitria\\domainservice\\util");
            p="y/m3o/*/src/domainser/**/com";
            p = FileUtil.toTARAlias(p);
            assertEquals(p, "D:\\jedi\\yoda\\m3o\\server\\src\\domainservice\\com");
        }
    }
    
    public void testToRegex_01() throws Exception {
        assertEquals(Util.toRegex("*"), ".*");
        assertEquals(Util.toRegex("a"), ".*a.*");
        assertEquals(Util.toRegex("abc;s"), ".*abc .*");
        assertEquals(Util.toRegex("abc;s;"), "abc ");
        assertEquals(Util.toRegex("a.b"), ".*a\\.b.*");
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

    public void testUtil_03() throws Exception {
        assertEquals(Util.isAbsolutePath("D:\\jedi\\yoda\\m3o\\server\\src\\domainservice"), true);
        assertEquals(Util.isAbsolutePath("D:"), true);
        assertEquals(Util.isAbsolutePath("dd\\rename.txt"), false);
        assertEquals(Util.isAbsolutePath("dd\\rename:abc.txt"), false);
        assertEquals(Util.isAbsolutePath("yoda\\m3o\\server\\src\\domainservice"), false);
        assertEquals(Util.isAbsolutePath("dd/abc.txt"), false);
        assertEquals(Util.isAbsolutePath("dd/abc/a/b/c"), false);
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

    public void testNewFileName_01() throws Exception {
        assertEquals(PAOperations.newFileName("a.txt", "{n}", true), "a.txt"); // {n} is given file simple name
        assertEquals(PAOperations.newFileName("a.txt", "{n}.{e}", true), "a.txt"); // {e} is given file ext
        assertEquals(PAOperations.newFileName("a.txt", "{n}-2", true), "a-2.txt");
        assertEquals(PAOperations.newFileName("a.txt", "1-{n}", true), "1-a.txt");
        assertEquals(PAOperations.newFileName("abc.txt", "{n1-2}", true), "ab.txt"); // sub from given file simple name, from 1 to 2 chars, both including
        assertEquals(PAOperations.newFileName("abc.txt", "1-2", true), "ab.txt"); // {n} can be omitted
        assertEquals(PAOperations.newFileName("abc.txt", "'1-2'", true), "1-2.txt"); // wrap with '' as exactly use it
        assertEquals(PAOperations.newFileName("abc.txt", "{n2}", true), "ab.txt"); // sub from
        assertEquals(PAOperations.newFileName("abc.txt", "2", true), "ab.txt");
        assertEquals(PAOperations.newFileName("abc.txt", "'2'", true), "2.txt");
        assertEquals(PAOperations.newFileName("abc.txt", "{n-2}", true), "bc.txt"); // sub to
        assertEquals(PAOperations.newFileName("abc.txt", "-2", true), "bc.txt");
        assertEquals(PAOperations.newFileName("1234567890.txt", "{n-2}", true), "90.txt");
        assertEquals(PAOperations.newFileName("1234567890.txt", "a{n7-8}b{n7-8}c{n-2}", true), "a78b78c90.txt"); // sub multiple
        assertEquals(PAOperations.newFileName("yoda_sjb", "{n4}", false), "yoda");
        assertEquals(PAOperations.newFileName("yoda", "{n}_main", false), "yoda_main");
        assertEquals(PAOperations.newFileName("1234567890", "l2", false), "90"); // last 2
        assertEquals(PAOperations.newFileName("abc01.txt", "l2", true), "01.txt");
        assertEquals(PAOperations.newFileName("1234567890", "last2", false), "90");
        assertEquals(PAOperations.newFileName("abc01.txt", "last2", true), "01.txt");
        assertEquals(PAOperations.newFileName("1234567890", "f2", false), "12"); // first 2
        assertEquals(PAOperations.newFileName("abc01.txt", "f2", true), "ab.txt");
        assertEquals(PAOperations.newFileName("1234567890", "first2", false), "12");
        assertEquals(PAOperations.newFileName("abc01.txt", "first2", true), "ab.txt");
        assertEquals(PAOperations.newFileName("1234567890", "app-2", false), "1234567890-2"); // append str
        assertEquals(PAOperations.newFileName("1234567890", "'app-2'", false), "app-2");
        assertEquals(PAOperations.newFileName("abc01.txt", "pre2-", true), "2-abc01.txt"); // pre str
        assertEquals(PAOperations.newFileName("abc01.txt", "'pre2-'", true), "pre2-.txt");
        assertEquals(PAOperations.newFileName("1234567890", "c2", false), "34567890"); // cut first 2
        assertEquals(PAOperations.newFileName("abc01.txt", "c-2", true), "abc.txt"); // cut last 2
        assertEquals(PAOperations.newFileName("1234567890.txt", "1{2-3}4", true), "1234.txt"); // {n} can be omitted
        assertEquals(PAOperations.newFileName("1234567890.txt", "1{2}4", true), "1124.txt");
        assertEquals(PAOperations.newFileName("1234567890.txt", "1{-3}4", true), "18904.txt");
        assertEquals(PAOperations.newFileName("1234567890", "1{2-3}4", false), "1234");
        assertEquals(PAOperations.newFileName("1234567890", "1{2}4", false), "1124");
        assertEquals(PAOperations.newFileName("1234567890", "1{-3}4", false), "18904");
        assertEquals(PAOperations.newFileName("abcdddefg", "cc-e", false, true), "ddd"); // cut from to
        assertEquals(PAOperations.newFileName("abcdefg", "cabc", false, true), "defg"); // cut from
        assertEquals(PAOperations.newFileName("abcdefg01.txt", "c-fg", true, true), "abcde.txt"); // cut to
        assertEquals(
                PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c...;s", false, true),
                "282.44 -- 1,689.33 loan 23:46:09 return "); // /s is space ' '
        assertEquals(
                PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c...;s", false, true),
                "282.44 -- 1,689.33 loan 23:46:09 return ");
        assertEquals(PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c..", false, true),
                ". 282.44 -- 1,689.33 loan 23:46:09 return ");
        assertEquals(
                PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c-...;s", false, true),
                "zhou ");
        assertEquals(PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c-23:46:09", false,
                true), "zhou ... 282.44 -- 1,689.33 loan ");
        assertEquals(PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c...;s-;s--",
                false, true), "282.44");
        assertEquals(PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ", "c...;s-;s--",
                false, true), "282.44");
        assertEquals(PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ",
                "123{n[...;s,;s--]}123{n[...;s,;s--]}123", false, true), "123282.44123282.44123");
        assertEquals(PAOperations.newFileName("zhou ... 282.44 -- 1,689.33 loan 23:46:09 return ",
                "123{n[...;s,;s--]}123{n[--;s,;sloan]}123", false, true), "123282.441231,689.33123");
        assertEquals(PAOperations.newFileName("a.txt", "dd\\", true, false), desktopDir + "a.txt"); // tar
        assertEquals(PAOperations.newFileName("a.txt", "dd", true, false), "dd.txt"); // tar
    }

    public void testRegex_01() throws Exception {
        String p = "s\\d*[kKmMgG]?-?\\d*[kKmMgG]?";
        assertEquals("s5m".matches(p), true);
        assertEquals("s10m".matches(p), true);
        assertEquals("s10m-20m".matches(p), true);
        assertEquals("s-20m".matches(p), true);
        assertEquals("s1g".matches(p), true);
        assertEquals("s1G-2G".matches(p), true);
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
        Params params = new Params();
        params.noPath = true;
        Filters filters = Filters.getFilters(filefrom, params);
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
        Params params = new Params();
        params.noPath = true;
        Filters filters = Filters.getFilters(from, params);
        return filters.accept(line, pos);
    }

    private void doTest2(String line, String from, String expect) {
        Params params = new Params();
        Filters filters = Filters.getFilters(from, params);
        assertEquals(filters.getFirst(), expect);
    }

    private void doTestFP(String s, String s2) {
        FiltersPattern p = new FiltersPattern();
        p.p = s;
        p.params = new Params();
        p.init();
        assertEquals(p.toString(), s2);
    }

    private void setDebug() {
        FileUtil.debug2_ = true;
    }

    private void setNoDebug() {
        FileUtil.debug2_ = false;
    }

}
