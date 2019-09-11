package main.java;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.UriParser;

public class URIInfoCheck {
    private static UriInfo uriInfo;

    public static void main(String[] args) {
        String url = "https://services.odata.org/V2/(S(test))/OData/OData.svc/Products?$filter=Price le 3.2m or Rating eq 5&$top=3&$skip=2&$orderby=Price desc";
        
        
        try {
            String edmxFilePath = "C:/Users/i065677/Desktop/ReadWritemetadata.xml";
            Edm edm = MetadataValidation.getEdm(edmxFilePath);
            List<PathSegment> pathSegments = getPathSegment("Products");
            Map<String, String> queryParams = getQueryMap(url);
            uriInfo = UriParser.parse(edm, pathSegments, queryParams);

        } catch (EdmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ODataMessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ODataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Example: Products, Products(1), Products(1)/Supplier
     */
    private static List<PathSegment> getPathSegment(String entitySet) {
        List<PathSegment> pathSegments = new ArrayList<PathSegment>();
        ODataPathSegmentImpl pathSeg = new ODataPathSegmentImpl();
        pathSeg.setPath(entitySet);
        pathSegments.add(pathSeg);
        return pathSegments;
    }

    private static Map<String, String> getQueryMap(String urlstring) {
        try {
            URL url = new URL(urlstring);
            Map<String, String> query_pairs = new LinkedHashMap<String, String>();
            String query = url.getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            return query_pairs;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
