package coverage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

class OutputWriter {

    private static Configuration cfg;

    public static void writeResults(Map<String, Map<Integer, Integer>> probRes) {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        try {
            cfg.setDirectoryForTemplateLoading(new File(OutputWriter.class.getResource("").toURI()));
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        //Tri des sondes par classe et par numero de ligne
        Map<String, SortedMap<Integer, Integer>> sorted = new HashMap<>();

        probRes.forEach((s, map) -> {
            //sort map
            TreeMap<Integer, Integer> tree = new TreeMap<>(Comparator.comparingInt(o -> o));
            tree.putAll(map);
            sorted.put(s, tree);
        });

        Map<String, Object> results = new HashMap<>();
        results.put("probes", sorted);

        try {
            Template temp = cfg.getTemplate("template.html");
            Writer out = new OutputStreamWriter(new FileOutputStream("results.html"));
            temp.process(results, out);
        } catch (IOException | TemplateException e) {
            System.err.println(e.getMessage());
        }
    }
}

