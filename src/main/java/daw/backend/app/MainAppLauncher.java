package daw.backend.app;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonObject;
import io.vertx.core.VertxOptions;

public class MainAppLauncher extends Launcher
{
    public static void main(String[] args)
    {
        System.out.println("Main launcher");
        MainAppLauncher appLauncher = new MainAppLauncher();
        appLauncher.dispatch(args);
    }

    @Override
    public void beforeStartingVertx(VertxOptions options)
    {
//        options.setMetricsOptions(DropWizard.getMetricsOptions());
    }

    @Override
    public void beforeDeployingVerticle(DeploymentOptions deploymentOptions)
    {
        JsonObject config = new JsonObject();
        config.put("port", 8888);
    }

}
