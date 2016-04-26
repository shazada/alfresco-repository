package org.alfresco.repo.calendar;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.calendar.CalendarEntry;
import org.alfresco.service.cmr.calendar.CalendarEntryDTO;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

/**
 * @author Nick Burch (based on existing webscript controllers in the REST API)
 * @since 4.0
 */
public class CalendarEntryImpl extends CalendarEntryDTO
{
    private NodeRef nodeRef;
    private NodeRef containerNodeRef;
    private String systemName;
    
    /**
     * Wraps an existing Calendar Entry node
     */
    protected CalendarEntryImpl(NodeRef nodeRef, NodeRef containerNodeRef, String systemName)
    {
       this.nodeRef = nodeRef;
       this.systemName = systemName;
       this.containerNodeRef = containerNodeRef;
    }
    
    protected void recordStorageDetails(NodeRef nodeRef, NodeRef containerNodeRef, String systemName)
    {
       this.nodeRef = nodeRef;
       this.systemName = systemName;
       this.containerNodeRef = containerNodeRef;
    }

    @Override
    public NodeRef getNodeRef() 
    {
       return nodeRef;
    }
    
    @Override
    public NodeRef getContainerNodeRef() 
    {
       return containerNodeRef;
    }
    
    @Override
    public String getSystemName() 
    {
       return systemName;
    }
    
    /**
     * Builds up the node properties for a given Calendar Entry
     * Doesn't handle tags, those must be done in the service itself.
     */
    protected static Map<QName,Serializable> toNodeProperties(CalendarEntry entry)
    {
       Map<QName,Serializable> properties = new HashMap<QName, Serializable>();
       properties.put(CalendarModel.PROP_WHAT, entry.getTitle());
       properties.put(CalendarModel.PROP_DESCRIPTION, entry.getDescription());
       properties.put(CalendarModel.PROP_WHERE, entry.getLocation());
       properties.put(CalendarModel.PROP_FROM_DATE, entry.getStart());
       properties.put(CalendarModel.PROP_TO_DATE, entry.getEnd());
       properties.put(CalendarModel.PROP_RECURRENCE_RULE, entry.getRecurrenceRule());
       properties.put(CalendarModel.PROP_RECURRENCE_LAST_MEETING, entry.getLastRecurrence());
       properties.put(CalendarModel.PROP_IS_OUTLOOK, entry.isOutlook());
       properties.put(CalendarModel.PROP_OUTLOOK_UID, entry.getOutlookUID());
       properties.put(CalendarModel.PROP_DOC_FOLDER, entry.getSharePointDocFolder());
     
//     properties.put(CalendarModel.PROP_COLOR, entry.getColor();
       
       return properties;
    }
    
    /**
     * Populates a Calendar Entry from the given node properties.
     * Doesn't handle tags, those must be done in the service itself.
     */
    protected static void populate(CalendarEntry entry, Map<QName,Serializable> properties)
    {
       entry.setTitle((String)properties.get(CalendarModel.PROP_WHAT));
       entry.setLocation((String)properties.get(CalendarModel.PROP_WHERE));
       entry.setDescription((String)properties.get(CalendarModel.PROP_DESCRIPTION));
       entry.setStart((Date)properties.get(CalendarModel.PROP_FROM_DATE));
       entry.setEnd((Date)properties.get(CalendarModel.PROP_TO_DATE));
       entry.setRecurrenceRule((String)properties.get(CalendarModel.PROP_RECURRENCE_RULE));
       entry.setLastRecurrence((Date)properties.get(CalendarModel.PROP_RECURRENCE_LAST_MEETING));
       entry.setSharePointDocFolder((String)properties.get(CalendarModel.PROP_DOC_FOLDER));
       
       Boolean isOutlook = (Boolean)properties.get(CalendarModel.PROP_IS_OUTLOOK);
       entry.setOutlook(isOutlook == null ? false : isOutlook);
       entry.setOutlookUID((String)properties.get(CalendarModel.PROP_OUTLOOK_UID));

       //entry.setColor(properties.get(CalendarModel.PROP_COLOR));
       
       // If the auditable properties are available and the type is correct, record them
       if(entry instanceof CalendarEntryDTO)
       {
          if(properties.containsKey(ContentModel.PROP_CREATED))
          {
             ((CalendarEntryDTO)entry).setCreatedAt((Date)properties.get(ContentModel.PROP_CREATED));
          }
          if(properties.containsKey(ContentModel.PROP_MODIFIED))
          {
             ((CalendarEntryDTO)entry).setModifiedAt((Date)properties.get(ContentModel.PROP_MODIFIED));
          }
       }
    }
    
    /**
     * Populates this entry from the given node properties
     * Doesn't handle tags, those are done in the service itself.
     */
    protected void populate(Map<QName,Serializable> properties)
    {
       populate(this, properties);
    }
    
    /**
     * Sets the list of tags for the entry 
     */
    protected void setTags(List<String> tags)
    {
       super.getTags().clear();
       super.getTags().addAll(tags);
    }
}