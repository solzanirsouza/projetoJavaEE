package logic.solzanir.conta.bdd.runners;

import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;
import cucumber.runtime.arquillian.api.Glues;
import logic.solzanir.conta.bdd.steps.ContaSteps;
import org.junit.runner.RunWith;

@Glues({ ContaSteps.class })
@Features({ "src/test/resources/features/obter_saldo_conta.feature" })
@RunWith(ArquillianCucumber.class)
public class ContaRunner {
}
