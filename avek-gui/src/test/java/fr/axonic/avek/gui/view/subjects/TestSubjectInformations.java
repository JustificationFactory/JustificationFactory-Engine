package fr.axonic.avek.gui.view.subjects;

import fr.axonic.avek.gui.model.results.ExempleState;
import fr.axonic.avek.gui.model.results.ExempleStateBool;
import fr.axonic.avek.gui.model.results.ExpEffect;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Nathaël N on 08/07/16.
 */
public class TestSubjectInformations extends ApplicationTest {
	static {
		System.setProperty("testfx.headless", "true");
		System.setProperty("testfx.robot", "glass");
		System.setProperty("prism.order", "sw");
		System.setProperty("java.awt.headless", "true");
		System.setProperty("prism.text", "t2k");
	}

	private ExpSubject subject;
	private Accordion acc;

	@Override
	public void start(Stage stage) {
		try {
			this.subject = new ExpSubject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(subject, 200, 200);
		stage.setScene(scene);
		stage.show();

		acc = (Accordion) subject.getCenter();
	}

	@Test
	public void testMonitoredSystem1() {
		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string","strval1"));
		ms.addAVar("Category 1", new ANumber("an integer",123456789));
		ms.addAVar("Category 1", new ANumber("a double",12345.6789));
		ms.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer",987654321));
		ms.addAVar("Category 2", new ANumber("a double",98765.4321));

		subject.setMonitoredSystem(ms);
		sleep(500); // Because setMonitoredSystem is parallelled

		assertEquals(2, acc.getPanes().size());

		TitledPane tp = acc.getPanes().get(0);
		ScrollPane sp = (ScrollPane) tp.getContent();
		VBox vb = (VBox) sp.getContent();
		assertEquals(4, vb.getChildren().size());

		tp = acc.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(2, vb.getChildren().size());

		ms = new MonitoredSystem(21);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string","strval1"));
		ms.addAVar("Category 1", new ANumber("an integer",123456789));
		ms.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer",987654321));
		ms.addAVar("Category 2", new ANumber("a double",12345.6789));
		ms.addAVar("Category 2", new ANumber("a double",98765.4321));

		ms.addCategory("Category 3");

		subject.setMonitoredSystem(ms);
		sleep(500); // Because setMonitoredSystem is parallelled

		assertEquals(3, acc.getPanes().size());

		tp = acc.getPanes().get(0);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(3, vb.getChildren().size());

		tp = acc.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(3, vb.getChildren().size());

		tp = acc.getPanes().get(2);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(0, vb.getChildren().size());
	}



}
