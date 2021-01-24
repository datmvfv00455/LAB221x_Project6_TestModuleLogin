package lms.lab221x.ass6.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
	"valid", "messege", "redirectUrl", "errorFields"
})
public class JsonResponse {

	/** The is valid. */
	private boolean isValid;

	/** The messege. */
	private String messege;

	/** The redirect url. */
	private String redirectUrl;

	/** The error fields. */
	private List<Field> errorFields;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Field {

		/** The field. */
		String field;

		/** The message. */
		String message;
	}
}
