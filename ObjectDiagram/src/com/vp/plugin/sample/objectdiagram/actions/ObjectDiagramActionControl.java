package com.vp.plugin.sample.objectdiagram.actions;

import java.awt.Point;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.IObjectDiagramUIModel;
import com.vp.plugin.diagram.IStateDiagramUIModel;
import com.vp.plugin.diagram.connector.ILinkUIModel;
import com.vp.plugin.diagram.shape.IInstanceSpecificationUIModel;
import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.ICompositeValueSpecification;
import com.vp.plugin.model.IInstanceSpecification;
import com.vp.plugin.model.ILink;
import com.vp.plugin.model.IObject;
import com.vp.plugin.model.ISlot;
import com.vp.plugin.model.factory.IModelElementFactory;

public class ObjectDiagramActionControl implements VPActionController {

	@Override
	public void performAction(VPAction arg0) {
		// create base classifiers Person and its attribute
		IClass classPerson = IModelElementFactory.instance().createClass();
		classPerson.setName("Person");
		IAttribute attrPersonName = classPerson.createAttribute();
		attrPersonName.setName("name");
		attrPersonName.setType("String");
		
		// create base classifiers Car and its attributes
		IClass classCar = IModelElementFactory.instance().createClass();
		classCar.setName("Car");
		IAttribute attrCarBrand = classCar.createAttribute();
		attrCarBrand.setName("brand");
		attrCarBrand.setType("String");
		IAttribute attrCarType = classCar.createAttribute();
		attrCarType.setName("type");
		attrCarType.setType("String");

		// create association between Person and Car
		IAssociation association = IModelElementFactory.instance().createAssociation();
		association.setName("owns");
		association.setFrom(classPerson);
		association.setTo(classCar);
						
		// create blank object diagram
		DiagramManager diagramManager = ApplicationManager.instance().getDiagramManager();
		IObjectDiagramUIModel diagram = (IObjectDiagramUIModel) diagramManager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_OBJECT_DIAGRAM);
		
		// create instance specification model for Person
		IInstanceSpecification objPerson = IModelElementFactory.instance().createInstanceSpecification();
		objPerson.setName("John Doe");  // specify the name of the instance specification model
		objPerson.addClassifier(classPerson);  // specify base classifier for the instance specification
		
		// create slot for name attribute of Person instance specification
		ISlot slotPersonName = IModelElementFactory.instance().createSlot();
		slotPersonName.setFeature(attrPersonName); // specify the slot is reference to the name attribute of Person class
		// create value specification for the slot
		ICompositeValueSpecification valueSpecPersonName = IModelElementFactory.instance().createCompositeValueSpecification();
		valueSpecPersonName.setValue("John Doe"); // specify the value
		slotPersonName.addValue(valueSpecPersonName); // add value specification to slot
		objPerson.addSlot(slotPersonName); // add the slot to Person instance specification
		
		// create view for instance specification of Person on diagram
		IInstanceSpecificationUIModel objPersonShape = (IInstanceSpecificationUIModel) diagramManager.createDiagramElement(diagram, objPerson);		
		objPersonShape.setBounds(100, 105, 110, 45);  // specify the location for instance specification of Person 
		objPersonShape.setRequestResetCaption(true);  // set to automatic calculate the initial caption position
		
		// create instance specification model for Car		
		IInstanceSpecification objCar = IModelElementFactory.instance().createInstanceSpecification();
		objCar.setName("Kuga");
		objCar.addClassifier(classCar);
		
		// create slot for brand attribute of Car
		ISlot slotCarBrand = IModelElementFactory.instance().createSlot();
		slotCarBrand.setFeature(attrCarBrand);
		ICompositeValueSpecification valueSpecCarBrand = IModelElementFactory.instance().createCompositeValueSpecification();
		valueSpecCarBrand.setValue("Ford");
		slotCarBrand.addValue(valueSpecCarBrand);
		objCar.addSlot(slotCarBrand);
		
		// create slot of type attribute of Car
		ISlot slotCarType = IModelElementFactory.instance().createSlot();
		slotCarType.setFeature(attrCarType);
		ICompositeValueSpecification valueSpecCarType = IModelElementFactory.instance().createCompositeValueSpecification();
		valueSpecCarType.setValue("SUV");
		slotCarType.addValue(valueSpecCarType);
		objCar.addSlot(slotCarType);
		
		// create view for instance specification of Car on diagram
		IInstanceSpecificationUIModel objCarShape = (IInstanceSpecificationUIModel) diagramManager.createDiagramElement(diagram, objCar);
		objCarShape.setBounds(400, 100, 90, 60);
		objCarShape.setRequestResetCaption(true);
		
		// create link model between instance specification of Person and Car		
		ILink link = IModelElementFactory.instance().createLink();
		// set the from end to instance specification of Person 
		link.setFrom(objPerson);  
		// set the to end to instance specification of Car 
		link.setTo(objCar);
		link.addClassifier(association); // set the association as its base classifier
		
		// create view of the link in diagram
		ILinkUIModel linkShape = (ILinkUIModel) diagramManager.createConnector(diagram, link, objPersonShape, objCarShape, new Point[] {new Point(210, 130), new Point(400, 130)});
		linkShape.setRequestResetCaption(true);
		
		// show up the diagram
		diagramManager.openDiagram(diagram);
		
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
