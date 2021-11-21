package features;

import com.smartcodeltd.jenkinsci.plugins.build_monitor.questions.ProjectWidget;
import com.smartcodeltd.jenkinsci.plugins.build_monitor.tasks.HaveABuildMonitorViewCreated;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.jenkins.HaveAFailingProjectCreated;
import net.serenitybdd.screenplay.jenkins.HaveASuccessfulProjectCreated;
import net.serenitybdd.screenplayx.actions.Navigate;
import net.thucydides.junit.annotations.TestData;

import org.junit.Before;
import org.junit.Test;

import static com.smartcodeltd.jenkinsci.plugins.build_monitor.matchers.ProjectInformationMatchers.displaysProjectStatusAs;
import static com.smartcodeltd.jenkinsci.plugins.build_monitor.model.ProjectStatus.Failing;
import static com.smartcodeltd.jenkinsci.plugins.build_monitor.model.ProjectStatus.Successful;
import static net.serenitybdd.screenplay.GivenWhenThen.*;

import java.util.Collection;

public class ProjectStatusShouldBeEasyToDetermine extends BuilMonitorAcceptanceTest {

    private Actor anna = Actor.named("Anna");

    public ProjectStatusShouldBeEasyToDetermine(String jenkinsVersion) {
        super(jenkinsVersion);
    }

    @TestData
    public static Collection<Object[]> testData(){
        return BuilMonitorAcceptanceTest.testData();
    }
    
    @Before
    public void actorCanBrowseTheWeb() {
        anna.can(BrowseTheWeb.with(browser));
    }

    @Test
    public void visualising_a_successful_project() throws Exception {

        givenThat(anna).wasAbleTo(
                Navigate.to(jenkins.url()),
                HaveASuccessfulProjectCreated.called("My App")
        );

        when(anna).attemptsTo(HaveABuildMonitorViewCreated.showingAllTheProjects());

        then(anna).should(seeThat(ProjectWidget.of("My App").information(),
                displaysProjectStatusAs(Successful)
        ));
    }

    @Test
    public void visualising_a_failing_project() throws Exception {

        givenThat(anna).wasAbleTo(
                Navigate.to(jenkins.url()),
                HaveAFailingProjectCreated.called("My App")
        );

        when(anna).attemptsTo(HaveABuildMonitorViewCreated.showingAllTheProjects());

        then(anna).should(seeThat(ProjectWidget.of("My App").information(),
                displaysProjectStatusAs(Failing)
        ));
    }
}
