package lms.lab221x.ass6.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import lms.lab221x.ass6.dao.UserDao;
import lms.lab221x.ass6.model.QuestionModel;
import lms.lab221x.ass6.model.UserModel;
import lms.lab221x.ass6.utils.Number;

@Service
public class UserService {

    /** The user list. */
    private static List<UserModel> userList;

    /** The user dao. */
    @Autowired
    UserDao userDao;

    /**
     * Get user from data.
     *
     * @param userID
     *     the user ID
     * 
     * @return the user if existed, else return null.
     */
    @Nullable
    public UserModel getUser(String userID) {

        reloadUserData();

        return userList.stream()
                .filter(u -> u.getId().equals(userID))
                .findFirst().orElse(null);
    }

    /**
     * Checks if user is existed and password is right.<br>
     * If user is existed and password is wrong, increase user's attempt.
     *
     * @param inputUser
     *     the input user
     * 
     * @return true if user is existed and password is right,<br>
     *     else return false
     */
    public boolean isValidUser(UserModel inputUser) {

        String    inputUserId       = inputUser.getId();
        String    inputUserPassword = inputUser.getPassword();
        UserModel user              = getUser(inputUserId);

        boolean isExistedUser = (user != null);

        boolean isRightPassword = isExistedUser
                ? user.getPassword().equals(inputUserPassword)
                : false;

        if (isExistedUser && !isRightPassword) {
            increaseAttempt(user);
            updateUserData();
        }

        return isExistedUser && isRightPassword;

    }

    /**
     * Checks if user password is right.
     * 
     * @param inputUser
     *     the input user
     * 
     * @return true if user is existed and password is right,<br>
     *     else return false
     */
    public boolean isRightPassword(UserModel inputUser) {

        String    inputUserId       = inputUser.getId();
        String    inputUserPassword = inputUser.getPassword();
        UserModel user              = getUser(inputUserId);

        boolean isExistedUser = (user != null);

        return isExistedUser
                ? user.getPassword().equals(inputUserPassword)
                : false;
    }

    /**
     * Get attempt left of user.
     *
     * @param userId
     *     the user id
     * 
     * @return the attempt left
     */
    public int getAttemptLeft(String userId) {

        UserModel user = getUser(userId);
        return (user != null)
                ? Number.THREE - user.getAttempt()
                : Number.NEGATIVE_ONE;
    }

    /**
     * Set user's attempt.
     *
     * @param userId
     *     the user ID
     * @param attempt
     *     the attempt which between 0 and 3
     */
    public void setAttempt(String userId, int attempt) {

        final int minAttemp = Number.ZERO;
        final int maxAttemp = Number.THREE;

        UserModel user = getUser(userId);

        boolean isValidAttempt = (user != null)
                && (attempt >= minAttemp)
                && (attempt <= maxAttemp);

        if (isValidAttempt) {
            user.setAttempt(attempt);
            updateUserData();
        }

    }

    /**
     * Increase user's attempt.
     *
     * @param user
     *     the user
     */
    private void increaseAttempt(UserModel user) {

        if (user == null) {
            return;
        }

        final int minAttemp = Number.ZERO;
        final int maxAttemp = Number.THREE;

        int     attempt       = user.getAttempt();
        boolean isValidAttemp = (attempt >= minAttemp)
                && (attempt < maxAttemp);

        if (isValidAttemp) {
            attempt++;
            user.setAttempt(attempt);
        }

    }

    /**
     * Load new data in CSV file and set to userList.
     */
    public void reloadUserData() {

        try {
            userList = userDao.getAll();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Update userList to CSV File.
     */
    public void updateUserData() {

        try {
            userDao.save(userList);
        } catch (CsvDataTypeMismatchException | IOException
                | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if user is first login.
     *
     * @param userId
     *     the user id
     * 
     * @return true, if user is first login
     */
    public boolean isFirstLogin(String userId) {

        reloadUserData();

        UserModel user = getUser(userId);

        boolean isFirstLogin = (user != null) && user.isFirstLogin();

        if (isFirstLogin) {
            user.setFirstLogin(false);
            updateUserData();
        }

        return isFirstLogin;

    }

    /**
     * Update fist time login.
     *
     * @param userID
     *     the user ID
     * @param questionsList
     *     the answered questions list
     * @param newPassword
     *     the new password
     * 
     * @return true, if successful
     */
    public boolean updateFistTimeLogin(
            String userID,
            List<QuestionModel> questionsList, 
            String newPassword) {

        UserModel user = getUser(userID);

        if (user != null) {
            // update password
            user.setPassword(newPassword);

            // update question
            for (QuestionModel question : questionsList) {
                String questionID     = question.getId();
                String questionAnswer = question.getAnswer();
                switch (questionID) {
                case "q01":
                    user.setQuestion01(questionAnswer);
                    break;
                case "q02":
                    user.setQuestion02(questionAnswer);
                    break;
                case "q03":
                    user.setQuestion03(questionAnswer);
                    break;
                case "q04":
                    user.setQuestion04(questionAnswer);
                    break;
                case "q05":
                    user.setQuestion05(questionAnswer);
                    break;
                default:
                    break;
                }
            }

            updateUserData();
            return true;
        }
        return false;

    }

}
