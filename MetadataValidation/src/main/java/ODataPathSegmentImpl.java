package main.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.uri.PathSegment;

public class ODataPathSegmentImpl implements PathSegment {

    private String path;
    private Map<String, List<String>> matrixParams = new HashMap<String, List<String>>();

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Map<String, List<String>> getMatrixParameters() {
        return matrixParams;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMatrixParams(Map<String, List<String>> matrixParams) {
        this.matrixParams = matrixParams;
    }
}
