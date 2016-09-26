
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Says "Hi" to the user.
 *
 */
@Mojo( name = "sayhi")
public class GreetingMojo extends AbstractMojo
{
    public void execute() throws MojoExecutionException
    {
        InputStream inputXml = null;
        try
        {
            inputXml  = new URL("http://informer.gismeteo.ru/xml/33345.xml").openConnection().getInputStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.
                    newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputXml);
            NodeList nodi = doc.getElementsByTagName("TEMPERATURE");

            if (nodi.getLength() > 0)
            {
                Element nodo = (Element)nodi.item(0);

                String strLow = nodo.getAttribute("min");
                Element nodo1 = (Element)nodi.item(0);
                String strHigh = nodo1.getAttribute("max");
                System.out.println("Temperature low: " + strLow);
                System.out.println("Temperature high: " + strHigh);

            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            try
            {
                if (inputXml != null)
                    inputXml.close();
            }
            catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}