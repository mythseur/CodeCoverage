import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import processors.ClassProcessor;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.support.StandardEnvironment;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CodeCoverage {

    private static String artifactName;

    public static void main(String[] args) throws IOException, XmlPullParserException {
        String programmePath = args[0];

        Logger logger = Logger.getLogger(CodeCoverage.class);
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(programmePath+"/pom.xml"));
        artifactName = model.getArtifactId();

        StandardEnvironment env = new StandardEnvironment();
        env.setAutoImports(true);
        env.setComplianceLevel(8);
        env.useTabulations(true);
        SpoonAPI spoon = new Launcher();
        spoon.addProcessor(new ClassProcessor());

        spoon.addInputResource(programmePath+"/src/main");
        spoon.setSourceOutputDirectory("target/CodeCoverage/"+artifactName+"/src/main/java");
        spoon.run();
        try {
            FileUtils.copyDirectory(new File("CoverageAPI/src/main"),
                    new File("target/CodeCoverage/CoverageAPI/src/main"));
            FileUtils.copyFile(new File("CoverageAPI/pom.xml"),
                    new File("target/CodeCoverage/CoverageAPI/pom.xml"));
            FileUtils.copyFile(new File(programmePath+"/pom.xml"),
                    new File("target/CodeCoverage/"+artifactName+"/pom.xml"));
            FileUtils.copyDirectory(new File(programmePath+"/src/test"),
                    new File("target/CodeCoverage/"+artifactName+"/src/test"));
            FileUtils.copyFile(new File("CoverageAPI/src/test/java/RunTestCoverage.java"),
                    new File("target/CodeCoverage/"+artifactName+"/src/test/java/RunTestCoverage.java"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        configMavenPomOrigin();
        createMavenPomCoverage();
    }

    private static void createMavenPomCoverage() {
        Logger logger = Logger.getLogger(CodeCoverage.class);
        MavenXpp3Writer writer = new MavenXpp3Writer();

        Model model = new Model();
        model.setModelVersion("4.0.0");
        model.setGroupId("fr.istic.master2.vv");
        model.setArtifactId(artifactName+"Coveraged");
        model.setVersion("1.0-SNAPSHOT");
        model.addProperty("maven.compiler.source","1.8");
        model.addProperty("maven.compiler.target","1.8");
        model.setPackaging("pom");

        List<String> modules = Arrays.asList("CoverageAPI",artifactName);

        model.setModules(modules);

        try {
            writer.write(new FileOutputStream("target/CodeCoverage/pom.xml"),
                    model);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void configMavenPomOrigin() {
        Logger logger = Logger.getLogger(CodeCoverage.class);
        MavenXpp3Reader reader = new MavenXpp3Reader();
        MavenXpp3Writer writer = new MavenXpp3Writer();

        try {
            Model model = reader.read(new FileReader("target/CodeCoverage/"+artifactName+"/pom.xml"));
            Dependency dependency = new Dependency();
            dependency.setArtifactId("CoverageAPI");
            dependency.setGroupId("fr.istic.master2.vv");
            dependency.setVersion("1.0-SNAPSHOT");
            model.addDependency(dependency);

            String source = model.getProperties().getProperty("maven.compiler.source");
            if(source == null)
            {
                model.addProperty("maven.compiler.source","1.8");
            }
            else
            {
                model.getProperties().setProperty("maven.compiler.source", "1.8");
            }
            String target = model.getProperties().getProperty("maven.compiler.target");
            if(target == null)
            {
                model.addProperty("maven.compiler.target","1.8");
            }
            else
            {
                model.getProperties().setProperty("maven.compiler.target", "1.8");
            }

            Xpp3Dom value = new Xpp3Dom("value");
            value.setValue("RunTestCoverage");
            Xpp3Dom name = new Xpp3Dom("name");
            name.setValue("listener");

            Xpp3Dom property = new Xpp3Dom("property");
            property.addChild(name);
            property.addChild(value);

            Xpp3Dom properties = new Xpp3Dom("properties");
            properties.addChild(property);

            Xpp3Dom configuration = new Xpp3Dom("configuration");
            configuration.addChild(properties);

            Plugin plugin = new Plugin();
            plugin.setGroupId("org.apache.maven.plugins");
            plugin.setArtifactId("maven-surefire-plugin");
            plugin.setVersion("2.12.4");
            plugin.setConfiguration(configuration);

            Build build = new Build();
            build.addPlugin(plugin);

            model.setBuild(build);

            writer.write(new FileOutputStream("target/CodeCoverage/"+artifactName+"/pom.xml"),
                    model);
        } catch (IOException | XmlPullParserException e) {
            logger.error(e.getMessage(), e);
        }
    }
}