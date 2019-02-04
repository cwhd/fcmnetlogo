import org.megadix.jfcm.utils.FcmIO;
import org.nlogo.api.*;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;
import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.FcmConnection;
import org.megadix.jfcm.act.SignumActivator;
import org.megadix.jfcm.conn.WeightedConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;

//TODO still need to figure out how to load FCMs...maybe JSON instead of XML - or use XML first then switch to JSON
public class FuzzyCognitiveMap implements Reporter {
    public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(
                new int[] {Syntax.NumberType()}, Syntax.ListType());
    }

    public Object report(Argument args[], Context context)
            throws ExtensionException {
        // create a NetLogo list for the result
        LogoListBuilder list = new LogoListBuilder();
        int n ;
        // use typesafe helper method from
        // org.nlogo.api.Argument to access argument
        try {
            n = args[0].getIntValue();
        }
        catch(LogoException e) {
            throw new ExtensionException( e.getMessage() ) ;
        }
        if (n < 0) {
            // signals a NetLogo runtime error to the modeler
            throw new ExtensionException
                    ("input must be positive");
        }
        // populate the list. note that we use Double objects; NetLogo
        // numbers are always Doubles
        for (int i = 0; i < n; i++) {
            list.add(Double.valueOf(i));
        }
        return list.toLogoList();
    }

    public CognitiveMap getMapFromFile(String filePath) throws FileNotFoundException, ParseException {
        CognitiveMap map;
        File initialFile = new File(filePath);
        //File initialFile = new File("C:\\Program Files\\NetLogo 6.0.4\\app\\extensions\\fcmnetlogo\\PredatorAndPrey.xml");
        InputStream targetStream = new FileInputStream(initialFile);
        map = FcmIO.loadXml(targetStream).get(0);

        return map;
    }

    public static CognitiveMap buildTestMap() {
        CognitiveMap map = new CognitiveMap("Investments");
        SignumActivator af = new SignumActivator();
        af.setIncludePreviousOutput(false);

        Concept c1 = new Concept("c1", "Interest rate", af, 0.0, 0.0, false);
        map.addConcept(c1);

        Concept c2 = new Concept("c2", "Productive investments", af, 0.0, 0.0, false);
        map.addConcept(c2);

        Concept c3 = new Concept("c3", "Occupation", af, 0.0, 0.0, false);
        map.addConcept(c3);

        Concept c4 = new Concept("c4", "Inflation", af, 0.0, 0.0, false);
        map.addConcept(c4);

        FcmConnection conn_1 = new WeightedConnection("c1 -> c2", "Interest rate -> Productive investments", -0.8);
        map.addConnection(conn_1);
        FcmConnection conn_2 = new WeightedConnection("c2 -> c3", "Productive investments -> Occupation", 1.0);
        map.addConnection(conn_2);
        FcmConnection conn_3 = new WeightedConnection("c3 -> c4", "Occupation -> Inflation", 0.9);
        map.addConnection(conn_3);
        FcmConnection conn_4 = new WeightedConnection("c4 -> c1", "Inflation -> Interest rate", 1.0);
        map.addConnection(conn_4);

        map.connect("c1", "c1 -> c2", "c2");
        map.connect("c2", "c2 -> c3", "c3");
        map.connect("c3", "c3 -> c4", "c4");
        map.connect("c4", "c4 -> c1", "c1");

        return map;
    }
}
