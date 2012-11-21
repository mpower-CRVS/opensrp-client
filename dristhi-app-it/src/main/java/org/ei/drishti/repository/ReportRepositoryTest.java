package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import com.google.gson.Gson;
import org.ei.drishti.domain.Report;
import org.ei.drishti.dto.MonthSummaryDatum;
import org.ei.drishti.util.Session;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

public class ReportRepositoryTest extends AndroidTestCase{
    private ReportRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new ReportRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldInsertReport() throws Exception {
        List<MonthSummaryDatum> monthlySummaries = asList(new MonthSummaryDatum("1", "2012", "2", "2", asList("123", "456")));
        Report iudReport = new Report("IUD", "40", new Gson().toJson(monthlySummaries));

        repository.update(iudReport);

        assertEquals(asList(iudReport), repository.all());
    }

    public void testShouldUpdateIfSameIndicatorReportExists() throws Exception {
        List<MonthSummaryDatum> monthlySummaries = asList(new MonthSummaryDatum("1", "2012", "2", "2", asList("123", "456")));
        Report oldIUDReport = new Report("IUD", "40", new Gson().toJson(monthlySummaries));
        Report newIUDReport = new Report("IUD", "50", new Gson().toJson(monthlySummaries));

        repository.update(oldIUDReport);
        repository.update(newIUDReport);

        assertEquals(asList(newIUDReport), repository.all());
    }
}
