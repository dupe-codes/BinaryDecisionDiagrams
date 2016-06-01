package BDD.graphWriter;

/*
 * Defines a simple API for writing out graphs as GraphViz files
 *
 * Assumes the system is running on MacOSX
 * GraphViz (and the dot command line utility) must be installed on the system
 * in order to generate graphs from the resulting .dot files.
 */

import java.io.File;
import java.io.FileWriter;

 public final class GraphWriter {

   private StringBuilder graph = new StringBuilder();

   private static final String dotExecutable = "/usr/local/bin/dot";
   private static final String outputDir = "./tmp/";

   public GraphWriter() {
     // Nothing...
   }

   public void startGraph() {
     addln("digraph G {");
   }

   public void addHeader() {
     addln("fontsize = 12;");
     addln("center = true;");
     addln("ratio=auto;");
     //addln("size=\"4,4\";");
   }

   public void addln(String line) {
     graph.append(line + "\n");
   }

   public void endGraph() {
     addln("}");
   }

   public boolean writeGraphToFile(String fileName) {
     File out = new File(outputDir + fileName);
     try {
       out.getParentFile().mkdirs();
       out.createNewFile();
     } catch (Exception e) {
       System.out.println("Error occurred making new file.");
       return false;
     }

     try (FileWriter fout = new FileWriter(new File(outputDir + fileName))) {
       fout.write(graph.toString());
     } catch (Exception e) {
       System.out.println("Error ocurred writing the file");
       System.out.println(e);
       return false;
     }
     return true;
   }

 }
