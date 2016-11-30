import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import processors.ClassProcessor;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.support.StandardEnvironment;

import java.io.*;

public class CodeCoverage {


    private static void printLines(String name, InputStream ins) throws Exception {
        Logger logger = Logger.getLogger(CodeCoverage.class.getName());
        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            logger.info(name + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Logger logger = Logger.getLogger(CodeCoverage.class.getName());
        Process pro = Runtime.getRuntime().exec(command);
        printLines(" stdout:", pro.getInputStream());
        printLines(" stderr:", pro.getErrorStream());
        pro.waitFor();
        logger.info(" exitValue() " + pro.exitValue());
    }

    public static void main(String[] args)
    {
        Logger logger = Logger.getLogger(CodeCoverage.class);
        StandardEnvironment env = new StandardEnvironment();
        env.setAutoImports(true);
        env.setComplianceLevel(8);
        env.useTabulations(true);
        SpoonAPI spoon = new Launcher();
        spoon.addProcessor(new ClassProcessor());
        spoon.addInputResource("ProgrammeTest/src/main");
        spoon.setSourceOutputDirectory("target/src/spooned");
        spoon.run();
//        SpoonCompiler compiler = spoon.createCompiler();
//        compiler.setBinaryOutputDirectory(new File("target/spooned"));
//        compiler.compileInputSources();
        try {
            FileUtils.copyDirectory(new File("src/main/java/coverage"),new File("target/src/coverage"));
            FileUtils.copyFile(new File("src/main/resources/log4j.xml"),new File("target/src/log4j.xml"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
//        SpoonCompiler compiler = spoon.createCompiler();
//        compiler.setBinaryOutputDirectory(new File("target/spooned"));
//        compiler.compileInputSources();

//        try {
//            runProcess("javac /home/benji/IdeaProjects/CodeCoverage/spooned/Main.java");
//            runProcess("java -classpath /home/benji/IdeaProjects/CodeCoverage/spooned/ Main");
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
    }
}