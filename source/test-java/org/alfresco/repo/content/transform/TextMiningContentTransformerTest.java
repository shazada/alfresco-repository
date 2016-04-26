package org.alfresco.repo.content.transform;

import java.io.File;
import java.io.InputStream;

import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.filestore.FileContentWriter;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.TransformationOptions;
import org.alfresco.util.TempFileProvider;

/**
 * @see org.alfresco.repo.content.transform.TextMiningContentTransformer
 * Note - Is actually POI (soon to be Tika), and not the
 *  old and unsupported Text Mining library!
 * 
 * @author Derek Hulley
 */
public class TextMiningContentTransformerTest extends AbstractContentTransformerTest
{
    private TextMiningContentTransformer transformer;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        transformer = new TextMiningContentTransformer();
        transformer.setMimetypeService(mimetypeService);
        transformer.setTransformerDebug(transformerDebug);
        transformer.setTransformerConfig(transformerConfig);
    }
    
    /**
     * @return Returns the same transformer regardless - it is allowed
     */
    protected ContentTransformer getTransformer(String sourceMimetype, String targetMimetype)
    {
        return transformer;
    }
    
    @Override
    protected String[] getQuickFilenames(String sourceMimetype) {
      return new String[] {
            "quick.doc", "quick95.doc", "quick6.doc"
      };
    }
    
    public void testIsTransformable() throws Exception
    {
        assertFalse(transformer.isTransformable(MimetypeMap.MIMETYPE_TEXT_PLAIN, -1, MimetypeMap.MIMETYPE_WORD, new TransformationOptions()));
        assertTrue(transformer.isTransformable(MimetypeMap.MIMETYPE_WORD, -1, MimetypeMap.MIMETYPE_TEXT_PLAIN, new TransformationOptions()));
    }
    
    /**
     * Tests a specific failure in the library
     */
    public void testBugFixAR1() throws Exception
    {
        File tempFile = TempFileProvider.createTempFile(
                getClass().getSimpleName() + "_" + getName() + "_",
                ".doc");
        FileContentWriter writer = new FileContentWriter(tempFile);
        writer.setMimetype(MimetypeMap.MIMETYPE_WORD);
        // get the test resource and write it (MS Word)
        InputStream is = getClass().getClassLoader().getResourceAsStream("farmers_markets_list_2003.doc");
        assertNotNull("Test resource not found: farmers_markets_list_2003.doc");
        writer.putContent(is);
        
        // get the source of the transformation
        ContentReader reader = writer.getReader(); 
        
        // make a new location of the transform output (plain text)
        tempFile = TempFileProvider.createTempFile(
                getClass().getSimpleName() + "_" + getName() + "_",
                ".txt");
        writer = new FileContentWriter(tempFile);
        writer.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        
        // transform it
        transformer.transform(reader, writer);
    }
}
