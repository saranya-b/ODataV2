package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.core.edm.provider.EdmEntitySetImplProv;

public class MetadataValidation {
    private static final String ENTITYSET_HEADER = new StringBuilder().append(String.format("%" + 5 + "s", " ")).append(String.format("%-25s", "EntitySet Name")).append(String.format("%-2s", '|'))
            .append("EntityType Name").toString();
    private static final String FI_HEADER = new StringBuilder().append(String.format("%" + 5 + "s", " ")).append(String.format("%-25s", "FunctionImport Name")).append(String.format("%-2s", '|'))
            .append(String.format("%-25s", "Return type")).append(String.format("%-2s", '|')).append("HTTP Method").toString();
    private static final String ENTITYSET_COUNT = "Entityt Set count : {0}";
    private static final String FI_COUNT = "FunctionImport count : {0}";

    public static void main(String[] args) {

        String edmx = "C:/Desktop/SFSFmetadata.xml";
        Edm edm = getEdm(edmx);
        try {
            List<EdmEntitySet> entityset = null;
            entityset = edm.getEntitySets();
            printEntityNames(entityset);

            List<EdmFunctionImport> functionImport =  edm.getFunctionImports();
            printFunctionImports(functionImport);

        } catch (EdmException e) {
            e.printStackTrace();
        }
    }

    public static Edm getEdm(String edmxPath) {
        try {
            File file = new File(edmxPath);
            InputStream is = new FileInputStream(file);
            return EntityProvider.readMetadata(is, true);
        } catch (EntityProviderException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printEntityNames(List<EdmEntitySet> entityset) throws EdmException {
        if (entityset != null) {
            System.out.println(MessageFormat.format(ENTITYSET_COUNT, entityset.size()));
            System.out.println(ENTITYSET_HEADER);
            for (int i = 0; i < entityset.size(); i++) {
                EdmEntitySetImplProv es = (EdmEntitySetImplProv) entityset.get(i);
                System.out.println(new StringBuilder().append(String.format("%2s%-2s", i + 1, " ")).append(String.format("%-25s", es.getName())).append(String.format("%-2s", '|'))
                        .append(es.getEntityType().getName()));
            }
        }
    }

    private static void printFunctionImports(List<EdmFunctionImport> functionImport) {
        if (functionImport != null) {
            try {
                System.out.println(MessageFormat.format(FI_COUNT, functionImport.size()));
                System.out.println(FI_HEADER);
                int i = 0;
                for (EdmFunctionImport eachFI : functionImport) {
                    i++;
                    String funcImpName = eachFI.getName();
                    String returntype = eachFI.getReturnType() != null ? eachFI.getReturnType().getName() : "";
                    String httpMethod = eachFI.getHttpMethod();

                    System.out.println(new StringBuilder().append(String.format("%2s%-2s", i, " ")).append(String.format("%" + 5 + "s", " ")).append(String.format("%-25s", funcImpName))
                            .append(String.format("%-2s", '|')).append(String.format("%-25s", returntype)).append(String.format("%-2s", '|')).append(httpMethod).toString());
                }
            } catch (EdmException e) {
                e.printStackTrace();
            }
        }
    }

}
