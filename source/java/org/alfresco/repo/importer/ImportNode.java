package org.alfresco.repo.importer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AccessPermission;
import org.alfresco.service.namespace.QName;


/**
 * Description of node to import.
 * 
 * @author David Caruana
 *
 */
public interface ImportNode
{
    /**
     * @return  the parent context
     */
    public ImportParent getParentContext();

    /**
     * @return  the type definition
     */
    public TypeDefinition getTypeDefinition();
    
    /**
     * @return  is this a node reference
     */
    public boolean isReference();
    
    /**
     * @return  the node ref
     */
    public NodeRef getNodeRef();
    
    /**
     * @return  node uuid to create node with
     */
    public String getUUID();

    /**
     * @return  the child name
     */
    public String getChildName();
    
    /**
     * Gets all properties for the node
     * 
     * @return the properties
     */
    public Map<QName,Serializable> getProperties();

    /**
     * Gets the property data type
     * 
     * @param propertyName  name of property
     * @return  data type of named property
     */
    public DataTypeDefinition getPropertyDataType(QName propertyName);
    
    /**
     * @return  the aspects of this node
     */
    public Set<QName> getNodeAspects();
    
    /**
     * @return  true => the node inherits permissions from its parent
     */
    public boolean getInheritPermissions();
    
    /**
     * @return  the permissions applied to this node
     */
    public List<AccessPermission> getAccessControlEntries();
    
}
