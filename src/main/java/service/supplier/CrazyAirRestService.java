package main.java.service.supplier;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import main.java.service.supplier.exception.GenericRestException;
import main.java.service.supplier.model.CrazyAirErrorResponse;
import main.java.service.supplier.model.CrazyAirRequest;
import main.java.service.supplier.model.CrazyAirResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CrazyAirRestService {
	private RestTemplate restTemplate;

	private String url;

	private static final String GET_FLIGHT_END_POINT = "/get/flight";

	public CrazyAirRestService() {
		this.restTemplate = new RestTemplate();
		this.url = "http://crazyAir.co.uk";
	}

	private URI buildCrazyAirUri(String endPoint) throws GenericRestException {
		try {
			return new URI(url + endPoint);
		} catch (URISyntaxException e) {
			// TODO Be more specific about the exception
			throw new GenericRestException();
		}
	}

	public List<CrazyAirResponse> getCrazyAirResponse(CrazyAirRequest request)
			throws GenericRestException {

		URI uri = buildCrazyAirUri(GET_FLIGHT_END_POINT);

		HttpEntity<CrazyAirRequest> body = new HttpEntity<CrazyAirRequest>(
				request);

		ResponseEntity<String> response = restTemplate.exchange(uri,
				HttpMethod.POST, body, String.class);

		CrazyAirResponse[] listResponse = RestHelper.getInstance()
				.processRequestBody(response, CrazyAirResponse[].class,
						CrazyAirErrorResponse.class);

		return Arrays.asList(listResponse);
	}
}
