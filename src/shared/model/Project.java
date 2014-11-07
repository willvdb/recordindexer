package shared.model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import server.Importer;
import server.ManagedConnection;

/**
 * 
 * Class modeling the project object in the indexer
 * 
 * @author Will
 * 
 */
public class Project
{
	private int projectID;
	private String title;
	private int recordsPerImage;
	private int fieldsPerImage;
	private int firstYCoord;
	private int recordHeight;
	private ArrayList<Field> fields;
	private ArrayList<Image> images;

	public Project(ManagedConnection managedConnection, Element projectElement,
			int key)
	{
		fields = new ArrayList<Field>();
		images = new ArrayList<Image>();

		title = Importer.getValue((Element) projectElement
				.getElementsByTagName("title").item(0));

		recordsPerImage = Integer.parseInt(Importer
				.getValue((Element) projectElement.getElementsByTagName(
						"recordsperimage").item(0)));
		firstYCoord = Integer.parseInt(Importer
				.getValue((Element) projectElement.getElementsByTagName(
						"firstycoord").item(0)));
		recordHeight = Integer.parseInt(Importer
				.getValue((Element) projectElement.getElementsByTagName(
						"recordheight").item(0)));
		this.projectID = key;

		Element fieldsElement = (Element) projectElement.getElementsByTagName(
				"fields").item(0);
		NodeList fieldElements = fieldsElement.getElementsByTagName("field");

		// adding fields

		for (int i = 0; i < fieldElements.getLength(); i++)
		{
			fields.add(new Field((Element) fieldElements.item(i),
					keyMaker.fieldKey, keyMaker.projectKey, i));
			keyMaker.fieldKey++;
		}
		fieldsPerImage = fields.size();
		Element imagesElement = (Element) projectElement.getElementsByTagName(
				"images").item(0);
		NodeList imageElements = imagesElement.getElementsByTagName("image");

		// adding images
		for (int i = 0; i < imageElements.getLength(); i++)
		{
			Image thisImage = new Image(managedConnection,
					(Element) imageElements.item(i), fields.size(),
					recordsPerImage, keyMaker.imageKey, keyMaker.projectKey);
			keyMaker.imageKey++;
			images.add(thisImage);
		}

		// ***********INSERT ALL FieldS**************
		for (Field temp : fields)
		{
			managedConnection.getFieldAccess().insert(temp);
		}
		// ***********INSERT ALL Images**************
		for (Image temp : images)
		{
			managedConnection.getImageAccess().insert(temp);
		}
	}

	public Project()
	{
	}

	public int getProjectID()
	{
		return projectID;
	}

	public void setProjectID(int key)
	{
		this.projectID = key;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getRecordsPerImage()
	{
		return recordsPerImage;
	}

	public void setRecordsPerImage(int recordsPerImage)
	{
		this.recordsPerImage = recordsPerImage;
	}

	public int getFieldsPerImage()
	{
		return fieldsPerImage;
	}

	public void setFieldsPerImage(int fieldsPerImage)
	{
		this.fieldsPerImage = fieldsPerImage;
	}

	public int getFirstYCoord()
	{
		return firstYCoord;
	}

	public void setFirstYCoord(int firstYCoord)
	{
		this.firstYCoord = firstYCoord;
	}

	public int getRecordHeight()
	{
		return recordHeight;
	}

	public void setRecordHeight(int recordHeight)
	{
		this.recordHeight = recordHeight;
	}

	public ArrayList<Field> getFields()
	{
		return fields;
	}

	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}

	public ArrayList<Image> getImages()
	{
		return images;
	}

	public void setImages(ArrayList<Image> images)
	{
		this.images = images;
	}
}
