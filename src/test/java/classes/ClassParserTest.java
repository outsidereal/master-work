package classes;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.relationships.IRelationship;
import com.diploma.parser.ClassParser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 25.08.12
 * Time: 23:12
 *
 * TODO: MUST BE FORMED AS TEST!
 */
public class ClassParserTest {
    private static final String filePathRelationship = "./src/test/resources/diagrams/class/test_dep.uml";
    private static final String filePathClass = "./src/test/resources/diagrams/class/class_tester.uml";


    public static void main(String[] args) {

        ClassParser cp = new ClassParser();
        cp.parse(filePathRelationship);

        Map<String, Class> classes = cp.getParsedClasses();
        List<IRelationship> relationships = cp.getParsedRelationships();

        int count = 0;
        for (Iterator iterator = classes.values().iterator(); iterator.hasNext(); ) {
            count++;
            Class cl = (Class) iterator.next();
            System.out.print(count + ". " + cl.toString() + "\n\n");
        }

        count = 0;
        for (Iterator iterator = relationships.iterator(); iterator.hasNext(); ) {
            count++;
            IRelationship rel = (IRelationship) iterator.next();

            System.out.print(count + ". " + rel.toString() + "\n");
        }
    }
}
