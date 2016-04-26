package org.alfresco.filesys.alfresco;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.ScriptableObject;

/**
 * Desktop Response Class
 * 
 *  <p>Holds the status code, optional status message and optional values returned by a desktop action.
 *  
 * @author gkspencer
 */
public class DesktopResponse extends ScriptableObject {

	// Version id
	
	private static final long serialVersionUID = 6421278986221629296L;
	
	//	Desktop action status and optional status message
	
	private int m_status;
	private String m_statusMsg;

	// Optional return values
	
	private List<Object> m_responseValues;
	
	/**
	 * Class constructor
	 * 
	 * @param sts int
	 */
	public DesktopResponse(int sts)
	{
		m_status = sts;
	}
	
	/**
	 * Class constructor
	 * 
	 * @param sts int
	 * @param msg String
	 */
	public DesktopResponse(int sts, String msg)
	{
		m_status = sts;
		m_statusMsg = msg;
	}

	/**
	 * Javascript constructor
	 * 
	 * @param sts int
	 * @param msg String
	 */
	public void jsConstructor(int sts, String msg)
	{
		m_status    = sts;
		m_statusMsg = msg;
	}
	
	/**
	 * Return the class name
	 * 
	 * @return String
	 */
	@Override
	public String getClassName() {
		return "DesktopResponse";
	}

	/**
	 * Return the status code
	 * 
	 * @return int
	 */
	public final int getStatus()
	{
		return m_status;
	}
	
	/**
	 * Determine if there is an optional status message
	 * 
	 * @return boolean
	 */
	public final boolean hasStatusMessage()
	{
		return m_statusMsg != null ? true : false;
	}
	
	/**
	 * Return the status message
	 * 
	 * @return String
	 */
	public final String getStatusMessage()
	{
		return m_statusMsg;
	}
	
	/**
	 * Determine if there are optional response values
	 * 
	 * @return boolean
	 */
	public final boolean hasResponseValues()
	{
		return m_responseValues != null ? true : false;
	}
	
	/**
	 * Return the count of response values
	 * 
	 * @return int
	 */
	public final int numberOfResponseValues()
	{
		return m_responseValues != null ? m_responseValues.size() : 0;
	}
	
	/**
	 * Get the response value list
	 * 
	 * @return List<Object>
	 */
	public final List<Object> getResponseValues()
	{
		return m_responseValues;
	}
	
	/**
	 * Add a response value
	 * 
	 * @param respObj Object
	 */
	public final void addResponseValue(Object respObj)
	{
		if ( m_responseValues == null)
			m_responseValues = new ArrayList<Object>();
		
		m_responseValues.add(respObj);
	}

	/**
	 * Set the status code and message
	 * 
	 * @param sts int
	 * @param msg String
	 */
	public final void setStatus(int sts, String msg)
	{
		m_status    = sts;
		m_statusMsg = msg;
	}
	
	/**
	 * Set the status property
	 * 
	 * @param sts int
	 */
	public void jsSet_status(int sts)
	{
		m_status = sts;
	}
	
	/**
	 * Set the status message property
	 * 
	 * @param msg String
	 */
	public void jsSet_message(String msg)
	{
		m_statusMsg = msg;
	}
	
	/**
	 * Return the desktop response as a string
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append("[");
		str.append(getStatus());
		str.append(":");
		if ( hasStatusMessage())
			str.append(getStatusMessage());
		else
			str.append("<NoMsg>");
		str.append(":Values=");
		str.append(numberOfResponseValues());
		str.append("]");
		
		return str.toString();
	}
}
