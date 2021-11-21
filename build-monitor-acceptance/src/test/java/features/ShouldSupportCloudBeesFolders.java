package features;

import com.cloudbees.hudson.plugins.folder.HaveAFolderCreated;
import com.cloudbees.hudson.plugins.folder.HaveANestedProjectCreated;
import com.smartcodeltd.jenkinsci.plugins.build_monitor.questions.ProjectWidget;
import com.smartcodeltd.jenkinsci.plugins.build_monitor.tasks.CreateABuildMonitorView;
import com.smartcodeltd.jenkinsci.plugins.build_monitor.tasks.configuration.DisplayAllProjects;
import com.smartcodeltd.jenkinsci.plugins.build_monitor.tasks.configuration.DisplayNestedProjects;
import net.serenitybdd.integration.jenkins.JenkinsInstance;
import net.serenitybdd.integration.jenkins.environment.rules.ApplicativeTestRule;
import net.serenitybdd.integration.jenkins.environment.rules.InstallPlugins;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplayx.actions.Navigate;
import net.thucydides.junit.annotations.TestData;

import org.junit.Before;
import org.junit.Test;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ShouldSupportCloudBeesFolders extends BuilMonitorAcceptanceTest {

    Actor anna = Actor.named("Anna");

    public ShouldSupportCloudBeesFolders(String jenkinsVersion) {
        super(jenkinsVersion);
    }

    protected List<? extends ApplicativeTestRule<JenkinsInstance>> jenkinsBeforeStartRules() {
        return Arrays.asList(InstallPlugins.fromCache(getpluginsCache(), "cloudbees-folder"));
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
    public void visualising_projects_nested_in_folders() throws Exception {

        givenThat(anna).wasAbleTo(
                Navigate.to(jenkins.url()),
                HaveAFolderCreated.called("Search Services").andInsideIt(
                        HaveANestedProjectCreated.called("Librarian"),
                        HaveAFolderCreated.called("Contracts").andInsideIt(
                                HaveANestedProjectCreated.called("Third Party System")
                        )
                )
        );

        when(anna).attemptsTo(
                CreateABuildMonitorView.called("Build Monitor").andConfigureItTo(
                        DisplayAllProjects.usingARegularExpression(),
                        DisplayNestedProjects.fromSubfolders()
                )
        );

        then(anna).should(seeThat(ProjectWidget.of("Search Services » Librarian").state(), isVisible()));
        then(anna).should(seeThat(ProjectWidget.of("Search Services » Contracts » Third Party System").state(), isVisible()));
    }
}
