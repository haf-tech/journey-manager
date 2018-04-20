package com.haddouti.journeymanager.here.route;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.haddouti.journeymanager.here.HereConfig;
import com.haddouti.journeymanager.service.route.spi.Route;
import com.navteq.lbsp.routing_calculateroute._4.CalculateRouteResponseType;
import com.navteq.lbsp.routing_calculateroute._4.CalculateRouteType;

@RunWith(MockitoJUnitRunner.class)
public class HereRouteServiceTest {

	@Spy
	private HereRouteService hereService;

	@MockBean
	private RestTemplate mockRestTemplate;

	private String geocoderPositiveResponse;
	private String geocoderNothingFound;
	private String routePositive;

	@Before
	public void before() {

		geocoderPositiveResponse = getResource("here/geocoder.result.json");
		geocoderNothingFound = getResource("here/geocoder.result.failure1.json");
		routePositive = getResource("here/route.result.xml");
	}

	@Test
	public void testGeoCoderPositive() {

		final HereConfig mockConfig = mock(HereConfig.class);
		when(mockConfig.getUrlForGeocoderService()).thenReturn("{0}");
		Mockito.doReturn(geocoderPositiveResponse).when(hereService).callService(anyString(), ArgumentMatchers.eq(String.class));

		hereService.setConfig(mockConfig);

		final String geo = hereService.getGeoWaypoint("Frankfurter Allee, Berlin");

		Assert.assertEquals("geo!52.5148,13.46478", geo);
	}

	@Test
	public void testGeoCoderNegativeNothingFound() {

		final HereConfig mockConfig = mock(HereConfig.class);
		when(mockConfig.getUrlForGeocoderService()).thenReturn("{0}");
		Mockito.doReturn(geocoderNothingFound).when(hereService).callService(anyString(), ArgumentMatchers.eq(String.class));

		hereService.setConfig(mockConfig);

		final String geo = hereService.getGeoWaypoint("Not-Relevant");

		Assert.assertNull(geo);
	}

	@Test
	public void testGeoCoderNegativeGeoCoderResponseNull() {

		final HereConfig mockConfig = mock(HereConfig.class);
		when(mockConfig.getUrlForGeocoderService()).thenReturn("{0}");
		// Return null to simulate a timeout or similar
		Mockito.doReturn(null).when(hereService).callService(anyString(), ArgumentMatchers.eq(String.class));

		hereService.setConfig(mockConfig);

		final String geo = hereService.getGeoWaypoint("Not-relevant");

		Assert.assertNull(geo);
	}

	@Test
	public void testGeoCoderNegativeSourceNull() {

		final HereConfig mockConfig = mock(HereConfig.class);
		when(mockConfig.getUrlForGeocoderService()).thenReturn("{0}");
		Mockito.doReturn(null).when(hereService).callService(anyString(), ArgumentMatchers.eq(String.class));

		hereService.setConfig(mockConfig);

		// Provide null, to check NPE
		final String geo = hereService.getGeoWaypoint(null);

		Assert.assertNull(geo);
	}

	@Test
	public void testFindPositive() {

		final HereConfig mockConfig = mock(HereConfig.class);
		when(mockConfig.getUrlForGeocoderService()).thenReturn("{0}");
		when(mockConfig.getUrlForRoutingService()).thenReturn("{0}");
		final CalculateRouteType responseCalculateRouteType = unmarshal(routePositive, CalculateRouteType.class, CalculateRouteType.class,
				CalculateRouteResponseType.class);
		Mockito.doReturn(geocoderPositiveResponse).when(hereService).callService(anyString(), ArgumentMatchers.eq(String.class));
		Mockito.doReturn(responseCalculateRouteType).when(hereService).callService(anyString(), ArgumentMatchers.eq(CalculateRouteType.class));

		hereService.setConfig(mockConfig);

		final List<Route> route = hereService.find("Frankfurter Allee, Berlin", "Hermannplatz, Berlin");

		Assert.assertNotNull(route);
		Assert.assertNotNull(route.get(0));
		Assert.assertNotNull(route.get(0).getLegs());
		// Route with only one leg
		Assert.assertEquals(1, route.get(0).getLegs().size());
	}

	private String getResource(final String filename) {

		final InputStream in = HereRouteServiceTest.class.getClassLoader().getResourceAsStream(filename);

		String resource = null;
		try {
			resource = IOUtils.toString(in, "UTF-8");
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return resource;
	}

	private <T> T unmarshal(final String data, final Class<T> ret, final Class... clazz) {

		try {
			final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			final JAXBElement<T> obj = unmarshaller.unmarshal(new StreamSource(new StringReader(data)), ret);
			return obj.getValue();
		} catch (final JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}

}
