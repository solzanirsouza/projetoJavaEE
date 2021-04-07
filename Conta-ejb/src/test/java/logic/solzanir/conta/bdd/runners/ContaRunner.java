package logic.solzanir.conta.bdd.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/obter_saldo_conta.feature",
        glue = "logic.solzanir.conta.bdd.steps",
        tags = {"~@ignore"},
        snippets = SnippetType.CAMELCASE,
        dryRun = false)
public class ContaRunner {
}
