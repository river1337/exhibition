// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.main;

import java.util.List;
import joptsimple.OptionSet;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import exhibition.MCHook;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import java.net.PasswordAuthentication;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;
import joptsimple.OptionSpec;
import java.io.File;
import joptsimple.OptionParser;

public class Main
{
    private static final String __OBFID = "CL_00001461";
    
    public static void main(final String[] p_main_0_) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();
        optionparser.accepts("demo");
        optionparser.accepts("fullscreen");
        optionparser.accepts("checkGlErrors");
        OptionSpec<String> var2 = optionparser.accepts("server").withRequiredArg();
        OptionSpec<Integer> var3 = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565), new Integer[0]);
        OptionSpec<File> var4 = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
        OptionSpec<File> var5 = optionparser.accepts("assetsDir").withRequiredArg().<File>ofType(File.class);
        OptionSpec<File> var6 = optionparser.accepts("resourcePackDir").withRequiredArg().<File>ofType(File.class);
        OptionSpec<String> var7 = optionparser.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> var8 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).<Integer>ofType(Integer.class);
        OptionSpec<String> var9 = optionparser.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> var10 = optionparser.accepts("proxyPass").withRequiredArg();
        OptionSpec<String> var11 = optionparser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, new String[0]);
        OptionSpec<String> var12 = optionparser.accepts("uuid").withRequiredArg();
        OptionSpec<String> var13 = optionparser.accepts("accessToken").withRequiredArg().required();
        OptionSpec<String> var14 = optionparser.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> var15 = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(854), new Integer[0]);
        OptionSpec<Integer> var16 = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(480), new Integer[0]);
        OptionSpec<String> var17 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}", new String[0]);
        OptionSpec<String> var18 = optionparser.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> var19 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy", new String[0]);
        OptionSpec<String> var20 = optionparser.nonOptions();
        OptionSet var21 = optionparser.parse(p_main_0_);
        List<String> var22 = var21.valuesOf(var20);
        if (!var22.isEmpty()) {
            System.out.println("Completely ignored arguments: " + var22);
        }
        final String var23 = (String)var21.valueOf((OptionSpec)var7);
        Proxy var24 = Proxy.NO_PROXY;
        if (var23 != null) {
            try {
                var24 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(var23, (int)var21.valueOf((OptionSpec)var8)));
            }
            catch (Exception ex) {}
        }
        final String var25 = (String)var21.valueOf((OptionSpec)var9);
        final String var26 = (String)var21.valueOf((OptionSpec)var10);
        if (!var24.equals(Proxy.NO_PROXY) && func_110121_a(var25) && func_110121_a(var26)) {
            Authenticator.setDefault(new Authenticator() {
                private static final String __OBFID = "CL_00000828";
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(var25, var26.toCharArray());
                }
            });
        }
        final int var27 = (int)var21.valueOf((OptionSpec)var15);
        final int var28 = (int)var21.valueOf((OptionSpec)var16);
        final boolean var29 = var21.has("fullscreen");
        final boolean var30 = var21.has("checkGlErrors");
        final boolean var31 = var21.has("demo");
        final String var32 = (String)var21.valueOf((OptionSpec)var14);
        final PropertyMap var33 = (PropertyMap)new GsonBuilder().registerTypeAdapter((Type)PropertyMap.class, (Object)new PropertyMap.Serializer()).create().fromJson((String)var21.valueOf((OptionSpec)var17), (Class)PropertyMap.class);
        final File var34 = (File)var21.valueOf((OptionSpec)var4);
        final File var35 = (File)(var21.has((OptionSpec)var5) ? var21.valueOf((OptionSpec)var5) : new File(var34, "assets/"));
        final File var36 = (File)(var21.has((OptionSpec)var6) ? var21.valueOf((OptionSpec)var6) : new File(var34, "resourcepacks/"));
        final String var37 = (String)(var21.has((OptionSpec)var12) ? var12.value(var21) : ((String)var11.value(var21)));
        final String var38 = var21.has((OptionSpec)var18) ? ((String)var18.value(var21)) : null;
        final String var39 = (String)var21.valueOf((OptionSpec)var2);
        final Integer var40 = (Integer)var21.valueOf((OptionSpec)var3);
        final Session var41 = new Session((String)var11.value(var21), var37, (String)var13.value(var21), (String)var19.value(var21));
        final GameConfiguration var42 = new GameConfiguration(new GameConfiguration.UserInformation(var41, var33, var24), new GameConfiguration.DisplayInformation(var27, var28, var29, var30), new GameConfiguration.FolderInformation(var34, var36, var35, var38), new GameConfiguration.GameInformation(var31, var32), new GameConfiguration.ServerInformation(var39, var40));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
            private static final String __OBFID = "CL_00000829";
            
            @Override
            public void run() {
                Minecraft.stopIntegratedServer();
            }
        });
        Thread.currentThread().setName("Client thread");
        new MCHook(var42).run();
    }
    
    private static boolean func_110121_a(final String p_110121_0_) {
        return p_110121_0_ != null && !p_110121_0_.isEmpty();
    }
}
