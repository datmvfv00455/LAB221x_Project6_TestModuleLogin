package lms.lab221x.ass6.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import lms.lab221x.ass6.model.UserModel;

@Repository
public class UserDao {

    /** The user data rsc. */
    @Value("classpath:UserData.csv")
    private Resource userDataRsc;

    /**
     * Gets the user data file.
     *
     * @return the user data file
     * 
     * @throws IOException
     *     Signals that an I/O exception has occurred.
     */
    public File getUserDataFile() throws IOException {
        return userDataRsc.getFile();
    }

    /**
     * Get all user data from csv file.
     *
     * @return List of UserModel Object
     * 
     * @throws IOException
     *     Signals that an I/O exception has occurred.
     */
    public List<UserModel> getAll() throws IOException {
        File userDataCsvFile = userDataRsc.getFile();

        InputStream       is  = new FileInputStream(userDataCsvFile);
        InputStreamReader isr = new InputStreamReader(is);

        return new CsvToBeanBuilder<UserModel>(isr)
                .withSkipLines(1)
                .withSeparator(';')
                .withType(UserModel.class)
                .build().parse();
    }

    /**
     * Save.
     *
     * @param beans
     *     List of UserModel to be saved
     * 
     * @throws IOException
     *     Signals that an I/O exception has occurred.
     * @throws CsvDataTypeMismatchException
     *     the csv data type mismatch exception
     * @throws CsvRequiredFieldEmptyException
     *     the csv required field empty exception
     */
    public void save(List<UserModel> beans)
            throws IOException,
            CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {

        CustomMappingStrategy<UserModel> mappingStrategy;
        StatefulBeanToCsv<UserModel>     beanToCsv;

        File userDataCsvFile = userDataRsc.getFile();

        Writer writer = new FileWriter(userDataCsvFile);

        mappingStrategy = new CustomMappingStrategy<>(UserModel.class);

        beanToCsv = new StatefulBeanToCsvBuilder<UserModel>(writer)
                .withMappingStrategy(mappingStrategy)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withOrderedResults(true)
                .withSeparator(';')
                .build();

        beanToCsv.write(beans);
        writer.close();
    }

    /**
     * @author MaiDat
     *     Custom Mapping Strategy CSV File
     * 
     * @param <T>
     */
    class CustomMappingStrategy<T>
            extends ColumnPositionMappingStrategy<T> {

        /**
         * Instantiates a new custom mapping strategy.
         *
         * @param persistentClass
         *     the persistent class
         */
        CustomMappingStrategy(Class<T> persistentClass) {
            super.setType(persistentClass);
        }

        @Override
        public String[] generateHeader(T bean)
                throws CsvRequiredFieldEmptyException {
            final int numColumns = getFieldMap().values().size();
            super.generateHeader(bean);

            String[] header = new String[numColumns];

            BeanField beanField;
            String    columnHeaderName;
            for (int i = 0; i < numColumns; i++) {
                beanField        = findField(i);
                columnHeaderName = extractHeaderName(beanField);
                header[i]        = columnHeaderName;
            }
            return header;
        }

        private String extractHeaderName(final BeanField beanField) {

            boolean isBeanFieldEmpty = beanField
                    .getField()
                    .getDeclaredAnnotationsByType(
                            CsvBindByName.class).length == 0;

            if (beanField == null || beanField.getField() == null
                    || isBeanFieldEmpty) {
                return StringUtils.EMPTY;
            }

            final CsvBindByName bindByNameAnnotation = beanField
                    .getField()
                    .getDeclaredAnnotationsByType(
                            CsvBindByName.class)[0];
            return bindByNameAnnotation.column();
        }
    }

}
