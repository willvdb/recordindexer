package shared.model;

import org.w3c.dom.Element;

import server.Importer;

/**
 * 
 * Class modeling the field object in the indexer
 * @author Will
 *
 */
public class Field
{
	private int fieldID;
	private int fieldNum;
	private String title;
	private int xCoord;
	private int width;
	private String helpHTML;
	private String knownData;
	
	private int projectID;
	
	public Field(Element item, int key, int projectID, int fieldNum)
	{
		title = Importer.getValue((Element)item.getElementsByTagName("title").item(0));
		xCoord = Integer.parseInt(Importer.getValue((Element)item.getElementsByTagName("xcoord").item(0)));
		width = Integer.parseInt(Importer.getValue((Element)item.getElementsByTagName("width").item(0)));
		helpHTML = Importer.getValue((Element)item.getElementsByTagName("helphtml").item(0));
		this.fieldID = key;
		this.setProjectID(projectID);
		this.fieldNum = fieldNum;
		
		if(Importer.getChildren(item).size() > 4)//for conditional knowndata
			knownData = Importer.getValue((Element)item.getElementsByTagName("knowndata").item(0));
		else knownData = null;
	}
	
	public Field () {}
	
	public int getFieldID()
	{
		return fieldID;
	}
	public void setFieldID(int key)
	{
		this.fieldID = key;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public int getXCoord()
	{
		return xCoord;
	}
	public void setXCoord(int sCoord)
	{
		this.xCoord = sCoord;
	}
	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public String getHelpHTML()
	{
		return helpHTML;
	}
	public void setHelpHTML(String helpHTML)
	{
		this.helpHTML = helpHTML;
	}
	public String getKnownData()
	{
		return knownData;
	}
	public void setKnownData(String knownData)
	{
		this.knownData = knownData;
	}
	public int getProjectID()
	{
		return projectID;
	}
	public void setProjectID(int parentKey)
	{
		this.projectID = parentKey;
	}
	public int getFieldNum()
	{
		return fieldNum;
	}
	public void setFieldNum(int fieldNum)
	{
		this.fieldNum = fieldNum;
	}
}
