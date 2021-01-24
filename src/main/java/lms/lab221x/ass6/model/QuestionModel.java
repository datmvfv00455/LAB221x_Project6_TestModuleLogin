package lms.lab221x.ass6.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Question.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionModel {

	/** The id. */
	String id;

	/** The label. */
	String label;

	/** The answer. */
	String answer;

}
