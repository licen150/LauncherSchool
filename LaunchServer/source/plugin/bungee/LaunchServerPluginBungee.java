package launchserver.plugin.bungee;

import launcher.helper.CommonHelper;
import launchserver.plugin.LaunchServerPluginBridge;
import net.md_5.bungee.api.plugin.Plugin;

public final class LaunchServerPluginBungee extends Plugin
{
    public volatile LaunchServerPluginBridge bridge = null;

    @Override
    public void onDisable()
    {
        super.onDisable();
        if (bridge != null)
        {
            bridge.close();
            bridge = null;
        }
    }

    @Override
    public void onEnable()
    {
        super.onEnable();

        // Initialize LaunchServer
        try
        {
            bridge = new LaunchServerPluginBridge(getDataFolder().toPath());
        }
        catch (Throwable exc)
        {
            exc.printStackTrace();
        }

        // Register command
        CommonHelper.newThread("LaunchServer Thread", true, bridge).start();
        getProxy().getPluginManager().registerCommand(this, new LaunchServerCommandBungee(this));
    }
}
