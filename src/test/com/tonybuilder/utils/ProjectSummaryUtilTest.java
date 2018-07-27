package test.com.tonybuilder.utils;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

/**
 * ProjectSummaryUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>七月 27, 2018</pre>
 */
public class ProjectSummaryUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: genProjectSummaryForSingleProject(ProjectEntity project, YearMonth sinceDate, YearMonth untilDate)
     */
    @Test
    public void testGenProjectSummaryForSingleProject() throws Exception {
        YearMonth sinceDate = YearMonth.of(2017,1);
        YearMonth untilDate = YearMonth.now();
        LocalDateTime localDateTime = null;
        ZonedDateTime zonedDateTime = null;
        ZonedDateTime untilDateTime = null;
        for(YearMonth month = sinceDate; month.isBefore(untilDate); month = month.plusMonths(1)){
            System.out.println("month " + month);
            localDateTime = LocalDateTime.of(month.getYear(), month.getMonth(), 1, 0,0,0);
            System.out.println("localDateTime " + localDateTime);
            zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Z"));
            System.out.println("zonedDateTime " + zonedDateTime);
            untilDateTime = zonedDateTime.plusMonths(1).minusDays(1);
            System.out.println("untilDateTime " + untilDateTime);
        }
    }

} 
