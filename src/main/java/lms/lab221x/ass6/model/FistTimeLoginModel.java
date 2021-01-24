package lms.lab221x.ass6.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class FistTimeLoginModel.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FistTimeLoginModel {

	/** The question list. */
	List<QuestionModel> questionList;

	/** The old password. */
	String oldPassword;

	/** The new password. */
	String newPassword;

	/** The confirm password. */
	String confirmPassword;

}

