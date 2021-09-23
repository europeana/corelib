package eu.europeana.corelib.definitions.model.web;

import static org.junit.Assert.*;

import org.junit.Test;

public class BreadCrumbTest {

	@Test
	public void testTwoParamConstructor() {
		BreadCrumb breadcrumb = new BreadCrumb("test", "test.html");
		assertEquals("test", breadcrumb.getDisplay());
		assertEquals("test.html", breadcrumb.getHref());
		assertFalse(breadcrumb.isLast());
		assertNull(breadcrumb.getParam());
		assertNull(breadcrumb.getValue());
	}

	@Test
	public void testFourParamConstructor() {
		BreadCrumb breadcrumb = new BreadCrumb("test", "qf", "TYPE:TEXT", null);
		assertEquals("test", breadcrumb.getDisplay());
		assertEquals("qf=TYPE:TEXT", breadcrumb.getHref());
		assertFalse(breadcrumb.isLast());
	}

	public void testLastMarker() {
		BreadCrumb breadcrumb = new BreadCrumb("test", "qf", "TYPE:TEXT", null);
		breadcrumb.markAsLast();
		assertTrue(breadcrumb.isLast());
	}

	public void testParamsAndValue() {
		BreadCrumb breadcrumb = new BreadCrumb("test", "qf", "TYPE:TEXT", null);
		assertEquals("qf", breadcrumb.getParam());
		assertEquals("TYPE:TEXT", breadcrumb.getValue());
	}
}
