//package com.ignoretheextraclub.siteswapfactory;
//
//import com.fasterxml.jackson.core.PrettyPrinter;
//import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
///**
// Created by caspar on 10/12/16.
// */
//@Ignore("not fully thought through yet")
//public class JsonTest
//{
//    private static final String BASE_PATH = "src/test/resources/json/";
//    private static final String JSON = ".json";
//
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    private static final String[] fhsTests = new String[]{"6789A", "975", "78686"};
//    private static final PrettyPrinter pp = new DefaultPrettyPrinter();
//
//    @Test
//    public void fhstojson() throws Exception
//    {
//        final Type type = Type.FHS;
//
//        for (String constructor : fhsTests)
//        {
//            final FourHandedSiteswap fhs = FourHandedSiteswap.create(constructor);
//            final String fileName = getFileName(fhs);
//            final String actual = objectMapper.writer(pp).writeValueAsString(fhs);
//            final File file = new File(fileName);
//            if (!file.exists())
//            {
//                final File dir = new File(getDirectory(fhs));
//                dir.mkdirs();
//                file.createNewFile();
//                writeToFile(file, actual);
//                System.out.println("CREATED " + fileName + " PLEASE INSPECT FOR ACCURACY");
//            }
//            else
//            {
//                final String expected = loadFile(file.getPath());
//                Assert.assertEquals(fhs.toString(), expected, actual);
//            }
//        }
//    }
//
//    private String getFileName(AbstractSiteswap siteswap)
//    {
//        return getDirectory(siteswap) + "/" + siteswap.toString() + JSON;
//    }
//
//    private String getDirectory(AbstractSiteswap siteswap)
//    {
//        return BASE_PATH + siteswap.getClass().getSimpleName();
//    }
//
//    private void writeToFile(final File file, final String body) throws IOException
//    {
//        try (FileWriter f = new FileWriter(file, false))
//        {
//            f.append(body);
//        }
//    }
//
//    private String loadFile(final String path) throws IOException
//    {
//        return new String(Files.readAllBytes(Paths.get(path)));
//    }
//
//    private enum Type
//    {
//        FHS
//    }
//}
