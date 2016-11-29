import org.apache.log4j.Logger;
import processors.ClassProcessor;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.compiler.SpoonCompiler;
import spoon.support.StandardEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        spoon.addInputResource("ProgrammeTest/src");
        spoon.setSourceOutputDirectory("target/src/spoonded");
        spoon.run();
        SpoonCompiler compiler = spoon.createCompiler();
        compiler.setBinaryOutputDirectory(new File("target/spooned"));
        compiler.compileInputSources();

//        try {
//            runProcess("javac /home/benji/IdeaProjects/CodeCoverage/spooned/Main.java");
//            runProcess("java -classpath /home/benji/IdeaProjects/CodeCoverage/spooned/ Main");
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
    }
}