import org.megadix.jfcm.CognitiveMap;

import org.nlogo.api.*;
import org.nlogo.core.LogoList;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.utils.FcmIO;
import org.megadix.jfcm.utils.FcmRunner;
import org.megadix.jfcm.utils.SimpleFcmRunner;
import scala.collection.mutable.StringBuilder;


public class FCMExtension extends org.nlogo.api.DefaultClassManager {

    public void load(PrimitiveManager primitiveManager) {
        primitiveManager.addPrimitive(
                "callfcm", new CallFCM());
    }

    @Override
    public java.util.List<String> additionalJars() {
        java.util.List<String> list = new java.util.ArrayList<String>();
        list.add("fcm.jar");
        return list;
    }

    public static class CallFCM implements Reporter  {
        FuzzyCognitiveMap fcmHelper = new FuzzyCognitiveMap();

        @Override
        public Syntax getSyntax() {
            return SyntaxJ.reporterSyntax(new int[]{Syntax.StringType(), Syntax.ListType()},
                    Syntax.WildcardType());
        }

        @Override
        public Object report(Argument args[], Context context)
                throws ExtensionException, LogoException {

            CognitiveMap map = new CognitiveMap();
            //TODO improve these error messages
            try {
                map = fcmHelper.getMapFromFile(args[0].get().toString());
            } catch(FileNotFoundException f) {
                throw new ExtensionException("File not found! " + f.getMessage());
            } catch (ParseException p) {
                throw new ExtensionException("Parse not working!" + p.getMessage());
            }

            FcmRunner runner = new SimpleFcmRunner(map, 0.1, 1000);

            for(int i = 0; i < args[1].getList().length(); i = i + 2) {
                //get the name of the node and the node
                map.getConcepts().get(args[1].getList().get(i)).setOutput(((Number)args[1].getList().get(i+1)).doubleValue());
            }
            //this is just an example...
            //map.getConcepts().get("c1").setInput(((Number)args[0].getList().get(0)).doubleValue());
            return showResults("Scenario1", runner.converge(), map);
        }

        String showResults(String scenario, boolean converged, CognitiveMap map) {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            nf.setMaximumFractionDigits(8);

            StringBuilder sb = new StringBuilder();

            sb.append(scenario);
            sb.append(" : ");
            sb.append(converged);
            sb.append(" : ");

            for (Concept c : map.getConcepts().values()) {
                sb.append(c.getOutput() != null ? nf.format(c.getOutput()) : " ");
                sb.append(" ");
            }
            return sb.toString();
        }
    }
}
