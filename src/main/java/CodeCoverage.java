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
        spoon.setSourceOutputDirectory("target/CodeCoverage/src/main/java");
        spoon.run();
        try {
            FileUtils.copyDirectory(new File("src/main/java/coverage"),
                    new File("target/CodeCoverage/src/main/java/coverage"));
            FileUtils.copyFile(new File("src/main/resources/log4j.xml"),
                    new File("target/CodeCoverage/src/main/resources/log4j.xml"));
            FileUtils.copyFile(new File("ProgrammeTest/pom.xml"),
                   new File("target/CodeCoverage/pom.xml"));
            FileUtils.copyDirectory(new File("ProgrammeTest/src/test"),
                    new File("target/CodeCoverage/src/test"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}