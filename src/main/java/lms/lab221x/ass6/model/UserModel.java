package lms.lab221x.ass6.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lms.lab221x.ass6.utils.Number;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class UserModel.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {	
 
	/** The user id. */
	@CsvBindByName(column = "ID")
	@CsvBindByPosition(position = Number.ZERO)
	private String id;

	/** The user password. */
	@CsvBindByName(column = "Password")
	@CsvBindByPosition(position = Number.ONE)
	private String password;
	
	/** The user password. */
	@CsvBindByName(column = "FirstLogin")
	@CsvBindByPosition(position = Number.TWO)
	private boolean isFirstLogin = true;

	/** The attempt. */
	@CsvBindByName(column = "Attempt")
	@CsvBindByPosition(position = Number.THREE)
	private int attempt;

	/** The question 01. */
	@CsvBindByName(column = "Question01")
	@CsvBindByPosition(position = Number.FOUR)
	private String question01;

	/** The question 02. */
	@CsvBindByName(column = "Question02")
	@CsvBindByPosition(position = Number.FIVE)
	private String question02;

	/** The question 03. */
	@CsvBindByName(column = "Question03")
	@CsvBindByPosition(position = Number.SIX)
	private String question03;

	/** The question 04. */
	@CsvBindByName(column = "Question04")
	@CsvBindByPosition(position = Number.SEVEN)
	private String question04;

	/** The question 05. */
	@CsvBindByName(column = "Question05")
	@CsvBindByPosition(position = Number.EIGHT)
	private String question05;
}
